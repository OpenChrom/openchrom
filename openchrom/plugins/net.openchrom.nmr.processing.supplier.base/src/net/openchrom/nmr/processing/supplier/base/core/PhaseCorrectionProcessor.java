package net.openchrom.nmr.processing.supplier.base.core;

import org.apache.commons.math3.complex.Complex;
import org.eclipse.chemclipse.nmr.model.core.IScanNMR;
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
	public IProcessingInfo process(final IScanNMR scanNMR, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

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

	private Complex[] perform(ISignalExtractor signalExtractor, IScanNMR scanNMR, final PhaseCorrectionSettings settings) {

		double[] deltaAxisPPM = signalExtractor.extractChemicalShift();
		//
		double leftPhaseChange = 0;
		double rightPhaseChange = 0;
		double firstOrderPhaseCorrection = 0;
		double zeroOrderPhaseCorrection = 0;
		Complex phaseCorrectionFactor = new Complex(0, (Math.PI / 180));
		Double complexSize = scanNMR.getProcessingParameters("numberOfFourierPoints");
		Complex phaseCorrectionComplexFactor;
		double phasingPivotpoint = deltaAxisPPM[(complexSize.intValue() / 2) - 1];
		//
		if(scanNMR.getProcessingParameters("firstOrderPhaseCorrection") != -1) {
			leftPhaseChange = scanNMR.getProcessingParameters("firstOrderPhaseCorrection"); // lp
		} else {
			leftPhaseChange = 0; // lp
		}
		if(scanNMR.getProcessingParameters("zeroOrderPhaseCorrection") != -1) {
			rightPhaseChange = scanNMR.getProcessingParameters("zeroOrderPhaseCorrection"); // rp
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
		boolean notUsingPivot = true;
		if(notUsingPivot) {
			leftPhaseCorrectionDSP = utilityFunction.generateLinearlySpacedVector(0, 1, complexSize.intValue());
			for(int i = 0; i < leftPhaseCorrectionDSP.length; i++) {
				leftPhaseCorrectionDSP[i] *= scanNMR.getProcessingParameters("dspPhaseFactor"); // dspPhase
			}
		} else {
			/*
			 * to be used later on with the GUI
			 */
			double phaseCorrectionTermA = (phasingPivotpoint - scanNMR.getProcessingParameters("firstDataPointOffset"));
			double phaseCorrectionTermB = phaseCorrectionTermA / scanNMR.getProcessingParameters("sweepWidth");
			double phaseCorrectionTermC = Math.round(complexSize * phaseCorrectionTermB);
			double rightPhaseCorrectionleftPhase = -(leftPhaseCorrection[(int)phaseCorrectionTermC]);
			rightPhaseChange += rightPhaseCorrectionleftPhase;
			firstOrderPhaseCorrection += leftPhaseChange;
			zeroOrderPhaseCorrection += rightPhaseChange;
			scanNMR.putProcessingParameters("firstOrderPhaseCorrection", firstOrderPhaseCorrection);
			scanNMR.putProcessingParameters("zeroOrderPhaseCorrection", zeroOrderPhaseCorrection);
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
}
