/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
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
import java.util.List;

import org.eclipse.chemclipse.filter.Filtered;
import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;
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

public class NMRSpectrumSelection {

	private static final Object NULL = new Object();
	private TableViewer viewer;

	public NMRSpectrumSelection(Composite parent) {
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
					if(selection instanceof IColorProvider) {
						return ((IColorProvider)selection).getBackground(element);
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

							if(element instanceof SpectrumMeasurement) {
								SpectrumMeasurement measurement = (SpectrumMeasurement)element;
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
	}

	public void update(Collection<IDataNMRSelection> collection) {

		viewer.setInput(collection);
		viewer.refresh();
	}

	private IDataNMRSelection getSelection(Object element) {

		if(element instanceof IDataNMRSelection) {
			return (IDataNMRSelection)element;
		}
		return null;
	}

	private String getName(IMeasurement measurement) {

		if(measurement == null) {
			return "-";
		}
		if(measurement instanceof Filtered) {
			Object object = ((Filtered<?>)measurement).getFilteredObject();
			if(object instanceof IMeasurement) {
				return getName((IMeasurement)object) + " > " + measurement.getDataName();
			}
		}
		return measurement.getDataName();
	}
}
