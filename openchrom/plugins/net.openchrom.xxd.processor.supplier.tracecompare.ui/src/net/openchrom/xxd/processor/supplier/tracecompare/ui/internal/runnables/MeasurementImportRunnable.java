/*******************************************************************************
 * Copyright (c) 2017, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class MeasurementImportRunnable implements IRunnableWithProgress {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MeasurementImportRunnable.class);
	//
	private List<IChromatogramWSD> measurements;
	private List<File> measurementFiles;

	public MeasurementImportRunnable(List<File> measurementFiles) {
		measurements = new ArrayList<>();
		this.measurementFiles = measurementFiles;
	}

	public List<IChromatogramWSD> getMeasurements() {

		return measurements;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		for(File file : measurementFiles) {
			IChromatogramWSD measurement = importChromatogram(file.getAbsolutePath(), monitor);
			if(measurement != null) {
				measurements.add(measurement);
			}
		}
	}

	public IChromatogramWSD importChromatogram(String chromatogramPath, IProgressMonitor monitor) {

		IChromatogramWSD chromatogramWSD = null;
		File file = new File(chromatogramPath);
		IProcessingInfo<IChromatogramWSD> processingInfo = ChromatogramConverterWSD.getInstance().convert(file, monitor);
		chromatogramWSD = processingInfo.getProcessingResult();
		return chromatogramWSD;
	}
}
