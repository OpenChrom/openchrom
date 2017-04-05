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
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IScanMarker;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

public class ScanMarkerDetectorRunnable implements IRunnableWithProgress {

	private ProcessorData processorData;
	private List<IScanMarker> scanMarker;

	public ScanMarkerDetectorRunnable(ProcessorData processorData) {
		this.processorData = processorData;
	}

	public List<IScanMarker> getScanMarker() {

		return scanMarker;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		if(processorData != null) {
			MassShiftDetector massShiftDetector = new MassShiftDetector();
			scanMarker = massShiftDetector.extractMassShiftMarker(processorData.getMassShifts(), processorData.getLevelUncertainty(), monitor);
		}
	}
}
