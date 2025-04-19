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
			scanMarker = massShiftDetector.extractMassShiftMarker(processorData, monitor);
		}
	}
}
