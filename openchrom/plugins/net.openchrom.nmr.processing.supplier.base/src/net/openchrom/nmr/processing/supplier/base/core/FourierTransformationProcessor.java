/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.nmr.processing.supplier.base.core;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.nmr.model.core.IMeasurementNMR;
import org.eclipse.chemclipse.nmr.model.core.MeasurementNMR;
import org.eclipse.chemclipse.nmr.model.selection.IDataNMRSelection;
import org.eclipse.chemclipse.nmr.model.support.ISignalExtractor;
import org.eclipse.chemclipse.nmr.model.support.SignalExtractor;
import org.eclipse.chemclipse.nmr.processor.core.AbstractScanProcessor;
import org.eclipse.chemclipse.nmr.processor.core.IScanProcessor;
import org.eclipse.chemclipse.nmr.processor.settings.IProcessorSettings;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.nmr.processing.supplier.base.settings.FourierTransformationSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.ZeroFillingFactor;

public class FourierTransformationProcessor extends AbstractScanProcessor implements IScanProcessor {

	@Override
	public IProcessingInfo process(final IDataNMRSelection dataNMRSelection, final IProcessorSettings processorSettings, final IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(dataNMRSelection, processorSettings);
		if(!processingInfo.hasErrorMessages()) {
			FourierTransformationSettings settings = (FourierTransformationSettings)processorSettings;
			ISignalExtractor signalExtractor = new SignalExtractor(dataNMRSelection);
			Complex[] fourierTransformedData = transform(dataNMRSelection, settings);
			UtilityFunctions utilityFunction = new UtilityFunctions();
			double[] chemicalShift = utilityFunction.generateChemicalShiftAxis((IMeasurementNMR)dataNMRSelection.getMeasurement());
			signalExtractor.storeFrequencyDomainSpectrum(fourierTransformedData, chemicalShift);
			processingInfo.setProcessingResult(dataNMRSelection);
		}
		return processingInfo;
	}

	private Complex[] transform(IDataNMRSelection dataNMRSelection, FourierTransformationSettings processorSettings) {

		/*
		 * Header Data and Raw Data
		 * ==> prepared in each respective reader
		 */
		//
		ISignalExtractor signalExtractor = new SignalExtractor(dataNMRSelection);
		ZeroFillingFactor zeroFillingFactor = processorSettings.getZeroFillingFactor();
		IMeasurementNMR measurementNMR = (IMeasurementNMR)dataNMRSelection.getMeasurement();
		/*
		 * according to J.Holy:
		 * ~~~~~~~
		 * extractRawIntesityFID(); will be used for data where/until the digital filter is removed
		 * and
		 * extractIntesityFID(); will hold the data after apodization (=> setScanFIDCorrection();)
		 * => both operation are performed in the ScanReader
		 */
		UtilityFunctions utilityFunction = new UtilityFunctions();
		/*
		 * modified signals after apodization
		 */
		Complex[] freeInductionDecayShiftedWindowMultiplication = signalExtractor.extractIntensityProcessedFID();
		//
		if(measurementNMR.getHeaderDataMap().containsValue("Bruker BioSpin GmbH")) {
			// On A*X data, FCOR (from proc(s)) allows you to control the DC offset of the spectrum; value between 0.0 and 2.0
			double firstFIDDataPointMultiplicationFactor = measurementNMR.getProcessingParameters("firstFIDDataPointMultiplicationFactor");
			// multiply first data point
			freeInductionDecayShiftedWindowMultiplication[0].multiply(firstFIDDataPointMultiplicationFactor);
		} else if(measurementNMR.getHeaderDataMap().containsValue("Nanalysis Corp.")) {
			// no multiplication necessary?
		} else {
			// another approach
		}
		// zero filling // Automatic zero filling if size != 2^n
		ZeroFilling zeroFiller = new ZeroFilling();
		// TODO fix cast later
		Complex[] freeInductionDecayShiftedWindowMultiplicationZeroFill = zeroFiller.zerofill(freeInductionDecayShiftedWindowMultiplication, (MeasurementNMR)dataNMRSelection.getMeasurement(), zeroFillingFactor);
		// Fourier transform, shift and flip the data
		Complex[] nmrSpectrumProcessed = fourierTransformNmrData(freeInductionDecayShiftedWindowMultiplicationZeroFill);
		return nmrSpectrumProcessed;
	}

	public static Complex[] fourierTransformNmrData(Complex[] fid) {

		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] nmrSpectrum = fFourierTransformer.transform(fid, TransformType.FORWARD);
		Complex[] nmrSpectrumProcessed = new Complex[nmrSpectrum.length];
		System.arraycopy(nmrSpectrum, 0, nmrSpectrumProcessed, 0, nmrSpectrum.length); // NmrData.SPECTRA
		UtilityFunctions.rightShiftNMRComplexData(nmrSpectrumProcessed, nmrSpectrumProcessed.length / 2);
		ArrayUtils.reverse(nmrSpectrumProcessed);
		return nmrSpectrumProcessed;
	}

	public static Complex[] inverseFourierTransformData(Complex[] spectrum) {

		ArrayUtils.reverse(spectrum);
		UtilityFunctions.rightShiftNMRComplexData(spectrum, spectrum.length / 2);
		FastFourierTransformer fFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] unfilteredFID = fFourierTransformer.transform(spectrum, TransformType.INVERSE);
		Complex[] analogFID = new Complex[unfilteredFID.length];
		System.arraycopy(unfilteredFID, 0, analogFID, 0, unfilteredFID.length);
		return analogFID;
	}
}
