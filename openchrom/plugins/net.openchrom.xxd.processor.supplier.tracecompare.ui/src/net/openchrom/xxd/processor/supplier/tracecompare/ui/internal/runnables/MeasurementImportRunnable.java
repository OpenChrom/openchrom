/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.xxd.processor.supplier.tracecompare.ui.internal.runnables;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.chromatogram.ChromatogramConverterWSD;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class MeasurementImportRunnable implements IRunnableWithProgress {

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
