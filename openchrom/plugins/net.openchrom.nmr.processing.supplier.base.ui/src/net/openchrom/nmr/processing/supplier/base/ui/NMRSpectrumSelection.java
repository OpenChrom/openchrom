/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Christoph Läubrich - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;
import org.eclipse.chemclipse.processing.ProcessorFactory;
import org.eclipse.chemclipse.processing.filter.Filtered;
import org.eclipse.chemclipse.ux.extension.xxd.ui.actions.IMeasurementFilterAction;
import org.eclipse.chemclipse.xxd.process.support.ProcessTypeSupport;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;

public class NMRSpectrumSelection {

	private static final Object NULL = new Object();
	private TableViewer viewer;
	private ProcessorFactory filterFactory;
	private ProcessTypeSupport processTypeSupport;

	public NMRSpectrumSelection(Composite parent, ProcessorFactory filterFactory) {

		this.filterFactory = filterFactory;
		processTypeSupport = new ProcessTypeSupport();
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		{
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setWidth(20);
			column.getColumn().setText("");
			column.setLabelProvider(new ColumnLabelProvider() {

				@Override
				public String getText(Object element) {

					return "";
				}

				@Override
				public Color getBackground(Object element) {

					IDataNMRSelection selection = getSelection(element);
					if(selection instanceof IColorProvider colorProvider) {
						return colorProvider.getBackground(element);
					}
					return null;
				}
			});
		}
		{
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setWidth(200);
			column.getColumn().setText("Name");
			column.setLabelProvider(new ColumnLabelProvider() {

				@Override
				public String getText(Object element) {

					return getSelection(element).getName();
				}
			});
		}
		{
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setWidth(200);
			column.getColumn().setText("Measurement");
			ColumnLabelProvider measurmentLabelProvider = new ColumnLabelProvider() {

				@Override
				public String getText(Object element) {

					return getName(getSelection(element).getMeasurement());
				}
			};
			column.setLabelProvider(measurmentLabelProvider);
			column.setEditingSupport(new EditingSupport(viewer) {

				@Override
				protected void setValue(Object element, Object value) {

					if(value == NULL) {
						value = null;
					}
					try {
						IDataNMRSelection selection = getSelection(element);
						selection.setActiveMeasurement((IComplexSignalMeasurement<?>)value);
						viewer.update(element, null);
					} catch(RuntimeException e) {
						// can't set the selection then, e.g. unmodifiable
						e.printStackTrace();
					}
				}

				@Override
				protected Object getValue(Object element) {

					Object value = getSelection(element).getMeasurement();
					if(value == null) {
						return NULL;
					}
					return value;
				}

				@Override
				protected CellEditor getCellEditor(Object element) {

					ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(viewer.getTable(), SWT.DROP_DOWN | SWT.READ_ONLY);
					editor.setContentProvider(ArrayContentProvider.getInstance());
					IComplexSignalMeasurement<?>[] measurements = getSelection(element).getMeasurements();
					List<Object> list = new ArrayList<>();
					list.add(NULL);
					for(IComplexSignalMeasurement<?> measurement : measurements) {
						if(measurement instanceof SpectrumMeasurement) {
							list.add(measurement);
						}
					}
					editor.setInput(list);
					editor.setLabelProvider(new ColumnLabelProvider() {

						@Override
						public String getText(Object element) {

							if(element instanceof SpectrumMeasurement measurement) {
								return getName(measurement);
							}
							return "-";
						}
					});
					return editor;
				}

				@Override
				protected boolean canEdit(Object element) {

					return getSelection(element) != null;
				}
			});
		}
		createContextMenu();
	}

	private void createContextMenu() {

		MenuManager contextMenu = new MenuManager("ViewerContextMenu"); //$NON-NLS-1$
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager mgr) {

				Iterator<?> iterator = viewer.getStructuredSelection().iterator();
				Map<SpectrumMeasurement, IDataNMRSelection> items = new LinkedHashMap<>();
				while(iterator.hasNext()) {
					Object object = iterator.next();
					if(object instanceof IDataNMRSelection selection) {
						IComplexSignalMeasurement<?> measurement = selection.getMeasurement();
						if(measurement instanceof SpectrumMeasurement spectrumMeasurement) {
							items.put(spectrumMeasurement, selection);
						}
					}
				}
				if(!items.isEmpty()) {
					Set<SpectrumMeasurement> measurements = items.keySet();
					BiPredicate<IMeasurementFilter<?>, Map<String, ?>> acceptor = new BiPredicate<IMeasurementFilter<?>, Map<String, ?>>() {

						@Override
						public boolean test(IMeasurementFilter<?> filter, Map<String, ?> properties) {

							return filter.acceptsIMeasurements(measurements);
						}
					};
					Collection<IMeasurementFilter<?>> filters = filterFactory.getProcessors(ProcessorFactory.genericClass(IMeasurementFilter.class), acceptor);
					Consumer<Collection<? extends IMeasurement>> consumer = new Consumer<Collection<? extends IMeasurement>>() {

						@Override
						public void accept(Collection<? extends IMeasurement> t) {

							for(IMeasurement measurement : t) {
								if(items.get(measurement) == null && measurement instanceof Filtered<?, ?> filtered) {
									Object filteredObject = filtered.getFilterContext().getFilteredObject();
									IDataNMRSelection dataNMRSelection = items.get(filteredObject);
									if(dataNMRSelection != null) {
										if(measurement instanceof IComplexSignalMeasurement<?>) {
											try {
												dataNMRSelection.addMeasurement((IComplexSignalMeasurement<?>)measurement);
											} catch(UnsupportedOperationException e) {
												// optional operation not supported... ignore then
											}
										}
									}
								}
							}
							Display.getDefault().asyncExec(viewer::refresh);
						}
					};
					for(IMeasurementFilter<?> filter : filters) {
						mgr.add(new IMeasurementFilterAction(filter, measurements, consumer, processTypeSupport));
					}
				}
			}
		});
		Menu menu = contextMenu.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
	}

	public void update(Collection<IDataNMRSelection> collection) {

		viewer.setInput(collection);
		viewer.refresh();
	}

	private IDataNMRSelection getSelection(Object element) {

		if(element instanceof IDataNMRSelection dataNMRSelection) {
			return dataNMRSelection;
		}
		return null;
	}

	public static String getName(IMeasurement measurement) {

		if(measurement == null) {
			return "-";
		}
		if(measurement instanceof Filtered) {
			Object object = ((Filtered<?, ?>)measurement).getFilterContext().getFilteredObject();
			if(object instanceof IMeasurement filteredMeasurement) {
				return getName(filteredMeasurement) + " > " + measurement.getDataName();
			}
		}
		return measurement.getDataName();
	}
}
