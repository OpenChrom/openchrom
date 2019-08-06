/******************************************
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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.nmr.converter.core.ScanConverterNMR;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.ui.support.ProcessingInfoViewSupport;
import org.eclipse.chemclipse.support.ui.swt.ExtendedTableViewer;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.DataListUI;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class NMRDataListUI extends DataListUI {

	Map<File, MeasurementLoadResult> filesMap = new HashMap<>();

	public NMRDataListUI(Composite parent, Consumer<Boolean> dirtyListener, IPreferenceStore preferenceStore, String userPathKey, DataType[] dataTypes) {
		super(parent, dirtyListener, preferenceStore, userPathKey, dataTypes);
		ExtendedTableViewer tableViewer = getTableViewer();
		tableViewer.getTableViewerColumn("Name").setEditingSupport(new EditingSupport(tableViewer) {

			@Override
			protected CellEditor getCellEditor(Object element) {

				MeasurementLoadResult loadResult = filesMap.get(element);
				ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(tableViewer.getTable(), SWT.DROP_DOWN | SWT.READ_ONLY);
				editor.setContentProvider(ArrayContentProvider.getInstance());
				editor.setLabelProvider(new ColumnLabelProvider() {

					@Override
					public String getText(Object element) {

						if(element instanceof IComplexSignalMeasurement<?>) {
							return loadResult.getName((IComplexSignalMeasurement<?>)element);
						}
						return super.getText(element);
					}
				});
				if(loadResult.measurements == null) {
					editor.setInput(new Object[0]);
				} else {
					editor.setInput(loadResult.measurements);
				}
				return editor;
			}

			@Override
			protected boolean canEdit(Object element) {

				MeasurementLoadResult loadResult = filesMap.get(element);
				if(loadResult != null) {
					return loadResult.measurements != null && loadResult.measurements.size() > 1;
				}
				return false;
			}

			@Override
			protected Object getValue(Object element) {

				MeasurementLoadResult loadResult = filesMap.get(element);
				return loadResult.selected;
			}

			@Override
			protected void setValue(Object element, Object value) {

				MeasurementLoadResult loadResult = filesMap.get(element);
				if(loadResult != null) {
					loadResult.selected = (IComplexSignalMeasurement<?>)value;
					tableViewer.update(element, null);
				}
			}
		});
	}

	@Override
	public String getName(File file) {

		MeasurementLoadResult measurement = filesMap.get(file);
		if(measurement != null) {
			return measurement.getName();
		}
		return super.getName(file);
	}

	@Override
	protected void removed(File file) {

		filesMap.remove(file);
	}

	@Override
	protected void addFiles(Collection<File> newFiles) {

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getTableViewer().getControl().getShell());
		try {
			dialog.run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

					SubMonitor subMonitor = SubMonitor.convert(monitor, "Loading Files", newFiles.size() * 100);
					for(File file : newFiles) {
						IProcessingInfo<Collection<IComplexSignalMeasurement<?>>> convert = ScanConverterNMR.convert(file, subMonitor.split(100));
						Collection<IComplexSignalMeasurement<?>> result = convert.getProcessingResult();
						filesMap.put(file, new MeasurementLoadResult(file, result));
						if(subMonitor.isCanceled()) {
							throw new InterruptedException("canceled");
						}
					}
				};
			});
			super.addFiles(newFiles);
		} catch(InvocationTargetException e) {
			ProcessingInfoViewSupport.updateProcessingInfoError("NMR Batch Job", "loading files failed", e.getCause());
		} catch(InterruptedException e) {
			return;
		}
	}

	private static final class MeasurementLoadResult {

		private Collection<IComplexSignalMeasurement<?>> measurements;
		private File file;
		private IComplexSignalMeasurement<?> selected;

		public MeasurementLoadResult(File file, Collection<IComplexSignalMeasurement<?>> measurements) {
			this.file = file;
			this.measurements = measurements;
			for(IComplexSignalMeasurement<?> measurement : measurements) {
				selected = measurement;
			}
		}

		public String getName() {

			return getName(selected);
		}

		public String getName(IComplexSignalMeasurement<?> measurement) {

			return file.getName() + " [ " + measurement.getDataName() + " ]";
		}
	}
}
