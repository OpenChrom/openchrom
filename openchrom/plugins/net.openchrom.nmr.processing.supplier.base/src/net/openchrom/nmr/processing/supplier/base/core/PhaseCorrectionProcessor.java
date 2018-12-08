package net.openchrom.nmr.processing.supplier.base.core;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.PhaseCorrectionSettings;

public class PhaseCorrectionProcessor extends AbstractScanProcessor implements IScanProcessor {

	public PhaseCorrectionProcessor() {

		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public IProcessingInfo process(final IDataNMRSelection scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		final IProcessingInfo processingInfo = validate(scanNMR, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			final PhaseCorrectionSettings settings = (PhaseCorrectionSettings)processorSettings;
			ISignalExtractor signalExtractor = new SignalExtractor(scanNMR);
			final Complex[] phaseCorrection = perform(signalExtractor, scanNMR, settings);
			signalExtractor.setPhaseCorrection(phaseCorrection, true);
			processingInfo.setProcessingResult(scanNMR);
		}
		return processingInfo;
	}

	private Complex[] perform(ISignalExtractor signalExtractor, IDataNMRSelection scanNMR, final PhaseCorrectionSettings settings) {

		double[] deltaAxisPPM = signalExtractor.extractChemicalShift();
		//
		double leftPhaseChange = 0;
		double rightPhaseChange = 0;
		double firstOrderPhaseCorrection = 0;
		double zeroOrderPhaseCorrection = 0;
		Complex phaseCorrectionFactor = new Complex(0, (Math.PI / 180));
		Double complexSize = scanNMR.getMeasurmentNMR().getProcessingParameters("numberOfFourierPoints");
		Complex phaseCorrectionComplexFactor;
		double phasingPivotpoint = 0;
		/*
		 * get settings from enum
		 */
		PhaseCorrectionSettings phaseCorrectionSettings = new PhaseCorrectionSettings();
		//
		if(Double.valueOf(phaseCorrectionSettings.getFirstOrderPhaseCorrection()) instanceof Double) {
			leftPhaseChange = phaseCorrectionSettings.getFirstOrderPhaseCorrection();
		} else {
			leftPhaseChange = 0; // lp
		}
		if(Double.valueOf(phaseCorrectionSettings.getZeroOrderPhaseCorrection()) instanceof Double) {
			rightPhaseChange = phaseCorrectionSettings.getZeroOrderPhaseCorrection();
		} else {
			rightPhaseChange = 0; // rp
		}
		//
		UtilityFunctions utilityFunction = new UtilityFunctions();
		double[] leftPhaseCorrection = utilityFunction.generateLinearlySpacedVector(0, 1, complexSize.intValue());
		for(int i = 0; i < leftPhaseCorrection.length; i++) {
			leftPhaseCorrection[i] *= leftPhaseChange;
		}
		double[] leftPhaseCorrectionDSP = new double[complexSize.intValue()];
		//
		switch(phaseCorrectionSettings.getPivotPointSelection()) {
			case LEFT:
				// position off the peaks
				phasingPivotpoint = deltaAxisPPM[0];
				break;
			case MIDDLE:
				// middle of spectrum
				phasingPivotpoint = deltaAxisPPM[(complexSize.intValue() / 2) - 1];
				break;
			case PEAK_MAX:
				// getMaxVal; at biggest peak in spectrum
				double bigPeak = utilityFunction.getMaxValueOfArray(signalExtractor.extractFourierTransformedDataRealPart());
				int peakPosition = ArrayUtils.indexOf(signalExtractor.extractFourierTransformedDataRealPart(), bigPeak);
				phasingPivotpoint = deltaAxisPPM[peakPosition];
				break;
			case USER_DEFINED:
				// user input position
				double userDefinedPosition = phaseCorrectionSettings.getUserDefinedPivotPointValue();
				int userPeakPosition = findIndexOfValue(deltaAxisPPM, userDefinedPosition);
				phasingPivotpoint = deltaAxisPPM[userPeakPosition];
				break;
			case NOT_DEFINED:
				// without setting pivot point
				leftPhaseCorrectionDSP = utilityFunction.generateLinearlySpacedVector(0, 1, complexSize.intValue());
				for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
					leftPhaseCorrectionDSP[i] *= scanNMR.getMeasurmentNMR().getProcessingParameters("dspPhaseFactor"); // dspPhase
				}
				// generate correction array
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
		double phaseCorrectionTermA = (phasingPivotpoint - scanNMR.getMeasurmentNMR().getProcessingParameters("firstDataPointOffset"));
		double phaseCorrectionTermB = phaseCorrectionTermA / scanNMR.getMeasurmentNMR().getProcessingParameters("sweepWidth");
		double phaseCorrectionTermC = Math.round(complexSize * phaseCorrectionTermB);
		double rightPhaseCorrectionleftPhase = -(leftPhaseCorrection[(int)phaseCorrectionTermC]);
		rightPhaseChange += rightPhaseCorrectionleftPhase;
		firstOrderPhaseCorrection += leftPhaseChange;
		zeroOrderPhaseCorrection += rightPhaseChange;
		scanNMR.getMeasurmentNMR().putProcessingParameters("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
		scanNMR.getMeasurmentNMR().putProcessingParameters("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
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

	private int findIndexOfValue(double[] array, double value) {

		int index;
		for(index = 0; index < array.length; index++) {
			if(Math.abs(array[index] - value) < 0.001) {
				break;
			}
		}
		return index;
	}
}
