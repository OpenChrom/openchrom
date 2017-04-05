/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.ui.runnables;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorSettings;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

public class MassShiftDetectorRunnable implements IRunnableWithProgress {

	private ProcessorData processorData;
	private Map<Integer, Map<Integer, Map<Integer, Double>>> massShifts;

	public MassShiftDetectorRunnable(ProcessorData processorData) {
		this.processorData = processorData;
	}

	public Map<Integer, Map<Integer, Map<Integer, Double>>> getMassShifts() {

		return massShifts;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		if(processorData != null) {
			IChromatogramMSD reference = processorData.getReferenceChromatogram();
			IChromatogramMSD shifted = processorData.getIsotopeChromatogram();
			//
			IProcessorModel processorModel = processorData.getProcessorModel();
			IProcessorSettings processorSettings = processorModel.getProcessorSettings();
			int startShiftLevel = processorSettings.getStartShiftLevel();
			int stopShiftLevel = processorSettings.getStopShiftLevel();
			//
			MassShiftDetector massShiftDetector = new MassShiftDetector();
			massShifts = massShiftDetector.detectMassShifts(reference, shifted, startShiftLevel, stopShiftLevel, monitor);
		}
	}
}
