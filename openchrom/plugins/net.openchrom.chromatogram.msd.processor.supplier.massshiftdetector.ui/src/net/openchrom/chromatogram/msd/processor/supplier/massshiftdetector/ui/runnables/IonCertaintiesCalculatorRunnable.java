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

import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.core.MassShiftDetector;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.CalculatedIonCertainties;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorModel;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.IProcessorSettings;
import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.model.ProcessorData;

public class IonCertaintiesCalculatorRunnable implements IRunnableWithProgress {

	private ProcessorData processorData;
	private CalculatedIonCertainties calculatedIonCertainties;

	public IonCertaintiesCalculatorRunnable(ProcessorData processorData) {
		this.processorData = processorData;
	}

	public CalculatedIonCertainties getCalculatedIonCertainties() {

		return calculatedIonCertainties;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

		if(processorData != null) {
			IChromatogramMSD referenceChromatogram = processorData.getReferenceChromatogram();
			IChromatogramMSD isotopeChromatogram = processorData.getIsotopeChromatogram();
			IProcessorModel processorModel = processorData.getProcessorModel();
			IProcessorSettings processorSettings = processorModel.getProcessorSettings();
			//
			MassShiftDetector massShiftDetector = new MassShiftDetector();
			calculatedIonCertainties = massShiftDetector.calculateIonCertainties(referenceChromatogram, isotopeChromatogram, processorSettings, monitor);
		}
	}
}
