/*******************************************************************************
 * Copyright (c) 2018, 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Alexander Stark - initial API and implementation
 * Christoph Läubrich - rework for new filter model
 *******************************************************************************/
package net.openchrom.nmr.processing.phasecorrection;

import java.math.BigDecimal;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.model.core.FilteredMeasurement;
import org.eclipse.chemclipse.model.filter.IMeasurementFilter;
import org.eclipse.chemclipse.nmr.model.core.FIDMeasurement;
import org.eclipse.chemclipse.nmr.model.core.FilteredSpectrumMeasurement;
import org.eclipse.chemclipse.nmr.model.core.SpectrumMeasurement;
import org.eclipse.chemclipse.processing.core.MessageConsumer;
import org.eclipse.chemclipse.processing.filter.Filter;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IProgressMonitor;
import org.osgi.service.component.annotations.Component;

import net.openchrom.nmr.processing.supplier.base.core.AbstractSpectrumSignalFilter;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions.SpectrumData;
import net.openchrom.nmr.processing.supplier.base.settings.PhaseCorrectionSettings;

@Component(service = {Filter.class, IMeasurementFilter.class})
public class PhaseCorrectionProcessor extends AbstractSpectrumSignalFilter<PhaseCorrectionSettings> {

	private static final String NAME = "Phase Correction";

	public PhaseCorrectionProcessor() {

		super(PhaseCorrectionSettings.class);
	}

	@Override
	public String getFilterName() {

		return NAME;
	}

	@Override
	protected FilteredMeasurement<?> doFiltering(SpectrumMeasurement measurement, PhaseCorrectionSettings settings, MessageConsumer messageConsumer, IProgressMonitor monitor) {

		FIDMeasurement fidMeasurement = Adapters.adapt(measurement, FIDMeasurement.class);
		SpectrumData spectrumData = UtilityFunctions.toComplexSpectrumData(measurement.getSignals());
		double firstDataOffset;
		double sweepWidth;
		if(fidMeasurement != null) {
			// FIXME get necessary values for spectrum from fid measurement
			sweepWidth = fidMeasurement.getSweepWidth();
			firstDataOffset = fidMeasurement.getFirstDataPointOffset();
		} else {
			// FIXME correlates with FIXME above; find default values if possible
			sweepWidth = 20.5524343527429;
			firstDataOffset = 15.07362;
		}
		Complex[] phaseCorrection = perform(spectrumData, settings, firstDataOffset, sweepWidth);
		for(int i = 0; i < phaseCorrection.length; i++) {
			spectrumData.signals[i] = spectrumData.signals[i].multiply(phaseCorrection[i]);
		}
		FilteredSpectrumMeasurement filtered = new FilteredSpectrumMeasurement(measurement);
		filtered.setSignals(spectrumData.toSignal());
		return filtered;
	}

	private static Complex[] perform(SpectrumData spectrumData, final PhaseCorrectionSettings phaseCorrectionSettings, double firstDataOffset, double sweepWidth) {

		Number[] deltaAxisPPM = spectrumData.chemicalShift;
		//
		double leftPhaseChange = 0;
		double rightPhaseChange = 0;
		double firstOrderPhaseCorrection = 0;
		double zeroOrderPhaseCorrection = 0;
		Complex phaseCorrectionFactor = new Complex(0, (Math.PI / 180));
		int complexSize = spectrumData.signals.length;
		Complex phaseCorrectionComplexFactor;
		double phasingPivotpoint = 0;
		/*
		 * get settings from enum
		 */
		double dspPhaseFactor = phaseCorrectionSettings.getDspPhaseFactor();
		// firstOrderPhaseCorrection
		leftPhaseChange = phaseCorrectionSettings.getFirstOrderPhaseCorrection();
		// zeroOrderPhaseCorrection
		rightPhaseChange = phaseCorrectionSettings.getZeroOrderPhaseCorrection();
		//
		UtilityFunctions utilityFunction = new UtilityFunctions();
		double[] leftPhaseCorrection = utilityFunction.generateLinearlySpacedVector(0, 1, complexSize);
		for(int i = 0; i < leftPhaseCorrection.length; i++) {
			leftPhaseCorrection[i] *= leftPhaseChange;
		}
		double[] leftPhaseCorrectionDSP = new double[complexSize];
		//
		switch(phaseCorrectionSettings.getPivotPointSelection()) {
			case LEFT:
				// position off the peaks
				phasingPivotpoint = deltaAxisPPM[0].doubleValue();
				break;
			case MIDDLE:
				// middle of spectrum
				phasingPivotpoint = deltaAxisPPM[(complexSize / 2) - 1].doubleValue();
				break;
			case PEAK_MAX:
				// getMaxVal; at biggest peak in spectrum
				phasingPivotpoint = deltaAxisPPM[spectrumData.maxIndex].doubleValue();
				break;
			case USER_DEFINED:
				// user input position
				double userDefinedPosition = phaseCorrectionSettings.getUserDefinedPivotPointValue();
				int userPeakPosition = UtilityFunctions.findIndexOfValue(deltaAxisPPM, userDefinedPosition);
				phasingPivotpoint = deltaAxisPPM[userPeakPosition].doubleValue();
				break;
			case NOT_DEFINED:
				// without setting pivot point
				leftPhaseCorrectionDSP = utilityFunction.generateLinearlySpacedVector(0, 1, complexSize);
				for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
					leftPhaseCorrectionDSP[i] *= dspPhaseFactor; // dspPhase
				}
				// generate correcti)on array
				Complex[] phaseCorrection = new Complex[leftPhaseCorrectionDSP.length];
				for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
					double phaseCorrectionFactorTerm = (rightPhaseChange + leftPhaseCorrection[i] + leftPhaseCorrectionDSP[i]);
					phaseCorrectionComplexFactor = phaseCorrectionFactor.multiply(phaseCorrectionFactorTerm);
					phaseCorrection[i] = phaseCorrectionComplexFactor.exp();
				}
				return phaseCorrection;
		}
		/*
		 * to be used later on with the GUI
		 */
		double phaseCorrectionTermA = (phasingPivotpoint - (firstDataOffset - sweepWidth));
		double phaseCorrectionTermB = phaseCorrectionTermA / sweepWidth;
		double phaseCorrectionTermC = Math.round(complexSize * phaseCorrectionTermB);
		double rightPhaseCorrectionleftPhase = -(leftPhaseCorrection[(int)phaseCorrectionTermC]);
		rightPhaseChange += rightPhaseCorrectionleftPhase;
		//
		firstOrderPhaseCorrection = phaseCorrectionSettings.getFirstOrderPhaseCorrection() + leftPhaseChange;
		zeroOrderPhaseCorrection = phaseCorrectionSettings.getZeroOrderPhaseCorrection() + rightPhaseChange;
		phaseCorrectionSettings.setFirstOrderPhaseCorrection(firstOrderPhaseCorrection);
		phaseCorrectionSettings.setZeroOrderPhaseCorrection(zeroOrderPhaseCorrection);
		// }
		// generate correction array
		Complex[] phaseCorrection = new Complex[leftPhaseCorrectionDSP.length];
		for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
			double phaseCorrectionFactorTerm = (rightPhaseChange + leftPhaseCorrection[i] + leftPhaseCorrectionDSP[i]);
			phaseCorrectionComplexFactor = phaseCorrectionFactor.multiply(phaseCorrectionFactorTerm);
			phaseCorrection[i] = phaseCorrectionComplexFactor.exp();
		}
		return phaseCorrection;
	}
}
