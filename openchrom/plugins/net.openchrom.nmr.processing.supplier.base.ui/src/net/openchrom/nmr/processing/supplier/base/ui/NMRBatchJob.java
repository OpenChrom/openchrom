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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.chemclipse.model.core.IComplexSignalMeasurement;
import org.eclipse.chemclipse.model.core.IMeasurement;
import org.eclipse.chemclipse.model.methods.ProcessMethod;
import org.eclipse.chemclipse.model.types.DataType;
import org.eclipse.chemclipse.processing.ProcessorFactory;
import org.eclipse.chemclipse.processing.core.DefaultProcessingResult;
import org.eclipse.chemclipse.processing.ui.support.ProcessingInfoViewSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.part.support.SupplierEditorSupport;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.BatchJobUI;
import org.eclipse.chemclipse.ux.extension.xxd.ui.swt.DataListUI;
import org.eclipse.chemclipse.xxd.process.support.ProcessTypeSupport;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class NMRBatchJob implements IRunnableWithProgress {

	@Inject
	private ProcessorFactory filterFactory;
	private BatchJobUI batchJobUI;
	private ProcessTypeSupport processTypeSupport;

	@PostConstruct
	public void postConstruct(Composite parent) {

		processTypeSupport = new ProcessTypeSupport(filterFactory);
		batchJobUI = new BatchJobUI(parent, processTypeSupport, Activator.getDefault().getPreferenceStore(), "nmrBatchUIUserLocation", new DataType[]{DataType.NMR}, this) {

			@Override
			protected DataListUI createDataList(Composite parent, IPreferenceStore preferenceStore, String userlocationPrefrenceKey, DataType[] dataTypes) {

				return new NMRDataListUI(parent, this::setEditorDirty, preferenceStore, userlocationPrefrenceKey, dataTypes);
			}
		};
		batchJobUI.doLoad(Collections.emptyList(), new ProcessMethod());
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		NMRDataListUI dataList = (NMRDataListUI)batchJobUI.getDataList();
		List<IComplexSignalMeasurement<?>> measurements = dataList.getMeasurements();
		DefaultProcessingResult<Object> processingResult = new DefaultProcessingResult<>();
		Collection<? extends IMeasurement> results = processTypeSupport.applyProcessor(measurements, batchJobUI.getMethod().getProcessMethod(), processingResult, monitor);
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {

				ProcessingInfoViewSupport.updateProcessingInfo(processingResult);
			}
		});
		if(!processingResult.hasErrorMessages()) {
			SupplierEditorSupport editorSupport = new SupplierEditorSupport(DataType.NMR);
			for(IMeasurement measurement : results) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {

						editorSupport.openEditor(measurement);
					}
				});
			}
		}
	}
}