package net.openchrom.nmr.processing.peakdetection;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.eclipse.chemclipse.nmr.model.core.SpectrumSignal;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities;

public class WaveletPeakDetectorCWT {

	private static boolean paddedData = false;

	private static boolean isPaddedData() {
		return paddedData;
	}

	private static void setPaddedData(boolean paddedData) {
		WaveletPeakDetectorCWT.paddedData = paddedData;
	}

	public static SimpleMatrix calculateWaveletCoefficients(List<? extends SpectrumSignal> signals, WaveletPeakDetectorSettings configuration) {

		double[] psiValues = UtilityFunctions.generateLinearlySpacedVector(-8, 8, 1024);
		double[] psi = calculatePsi(psiValues);
		double subtractPsi = psiValues[0];
		for(int i = 0; i < psiValues.length; i++) {
			psiValues[i] = psiValues[i] - subtractPsi;
		}
		double dxValue = psiValues[1];
		int maxPsiValue = (int) psiValues[psiValues.length - 1];

		// extract data
		double[] dataArray = extractDataFromList(signals);
		int workingLength = dataArray.length;

		// calculate coefficients

		int[] psiScales = configuration.getPsiScales();
		SimpleMatrix coefficients = new SimpleMatrix(workingLength, psiScales.length);
		for(int i = 0; i < psiScales.length; i++) {
			int currentScale = psiScales[i];

			int[] waveValues = calculateWaveValues(currentScale, maxPsiValue, dxValue);

			double[] convolveFunction = calculateConvolveFunction(waveValues, psi, workingLength);

			double[] convolvedSignals = convolveSignals(dataArray, convolveFunction, currentScale, waveValues.length);

			coefficients.setColumn(i, 0, convolvedSignals);
		}
		if(isPaddedData()) {
			/*
			 * cut coefficients back to original data size if padding was done
			 *
			 * extractMatrix(int y0, int y1, int x0, int x1)
			 * *****************************************************************************
			 * y0 - Start row. y1 - Stop row + 1. x0 - Start column. x1 - Stop column + 1.
			 */

			return coefficients.extractMatrix(0, coefficients.numRows(), 0, signals.size());
		}

		return coefficients;
	}

	private static double[] calculatePsi(double[] psiValues) {

		double[] psi = new double[psiValues.length];
		double psiConstant = (2 / Math.sqrt(3)) * Math.pow(Math.PI, -0.25);
		for(int i = 0; i < psiValues.length; i++) {
			psi[i] = psiConstant * (1 - Math.pow(psiValues[i], 2)) * Math.exp(-Math.pow(psiValues[i], 2) / 2);
		}
		return psi;
	}

	private static double[] calculateConvolveFunction(int[] waveValues, double[] psi, int workingLength) {

		double[] convolveFunction = new double[workingLength];
		double[] partsOfPsi = new double[waveValues.length];
		int v = 0;
		for(int value : waveValues) {
			partsOfPsi[v] = psi[value - 1];
			v++;
		}
		ArrayUtils.reverse(partsOfPsi);
		double meanPsi = Arrays.stream(partsOfPsi).sum();
		for(int c = 0; c < waveValues.length; c++) {
			convolveFunction[c] = partsOfPsi[c] - meanPsi;
		}
		if(convolveFunction.length > workingLength) {
			throw new IllegalArgumentException("Current scale is too large!");
		}
		return convolveFunction;
	}

	private static int[] calculateWaveValues(int currentScale, int maxPsiValue, double dxValue) {

		int maxVal = currentScale * maxPsiValue;
		int numberOfPoints = (currentScale * maxPsiValue) + 1;
		double[] waveValuesTemp = UtilityFunctions.generateLinearlySpacedVector(0, maxVal, numberOfPoints);

		int[] waveValues = new int[waveValuesTemp.length];
		double scaleDivisor = currentScale * dxValue;
		for(int w = 1; w < waveValuesTemp.length; w++) {
			waveValues[w] = (int) (1 + Math.floor(waveValuesTemp[w] / scaleDivisor));
		}
		waveValues[0] = 1;

		if(waveValues.length == 1) {
			waveValues = new int[] { 1, 1 };
		}
		return waveValues;
	}

	private static double[] convolveSignals(double[] dataArray, double[] convolveF, int currentScale, int lengthWave) {

		if(!checkIfNumeric(dataArray) || !checkIfNumeric(convolveF)) {
			throw new IllegalArgumentException("Data is not only numeric!");
		}
		if(dataArray.length != convolveF.length) {
			throw new IllegalArgumentException("Arguments length mismatch in convolution!");
		}
		// do the convolution
		FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] complexDataArray = fft.transform(dataArray, TransformType.FORWARD);
		Complex[] complexConvolveF = fft.transform(convolveF, TransformType.FORWARD);
		Complex[] tempOutput = new Complex[complexConvolveF.length];
		for(int i = 0; i < complexConvolveF.length; i++) {
			complexConvolveF[i] = complexConvolveF[i].conjugate();
			tempOutput[i] = complexDataArray[i].multiply(complexConvolveF[i]);
		}
		Complex[] finalOutput = fft.transform(tempOutput, TransformType.INVERSE);
		// process for return
		double[] convolvedSignals = IcoShiftAlignmentUtilities.getRealPartOfComplexArray(finalOutput);
		for(int c = 0; c < convolvedSignals.length; c++) {
			convolvedSignals[c] = convolvedSignals[c] * (1 / Math.sqrt(currentScale));
		}
		int fromBackToFront = (int) (dataArray.length - Math.floor(lengthWave / 2) + 1);
		double[] backPart = Arrays.copyOfRange(convolvedSignals, fromBackToFront, convolvedSignals.length);
		double[] frontPart = Arrays.copyOfRange(convolvedSignals, 0, fromBackToFront);
		convolvedSignals = ArrayUtils.addAll(backPart, frontPart);
		return convolvedSignals;
	}

	private static boolean checkIfNumeric(double[] array) {

		for(double d : array) {
			try {
				Double.valueOf(d);
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	private static double[] extractDataFromList(List<? extends SpectrumSignal> signals) {

		double[] dataArray;
		// check if length of data equals 2^n
		if(!lengthIsPowerOfTwo(signals)) {
			// pseudo zero filling, e.g. append part of data to satisfy FFT condition
			int newLength = (int) (Math.ceil((Math.log(signals.size()) / Math.log(2))));
			int diffLength = newLength - signals.size();
			double[] tempDataArray = new double[signals.size()];
			int position = 0;
			for(SpectrumSignal signal : signals) {
				tempDataArray[position] = signal.getAbsorptiveIntensity().doubleValue();
				position++;
			}
			double[] dataToAppend = Arrays.copyOfRange(tempDataArray, 0, diffLength);
			dataArray = ArrayUtils.addAll(tempDataArray, dataToAppend);
			setPaddedData(true);
		} else {
			// just copy data
			dataArray = new double[signals.size()];
			int position = 0;
			for(SpectrumSignal signal : signals) {
				dataArray[position] = signal.getAbsorptiveIntensity().doubleValue();
				position++;
			}
		}
		return dataArray;
	}

	private static boolean lengthIsPowerOfTwo(List<? extends SpectrumSignal> signals) {

		if(signals.size() == 0) {
			throw new IllegalArgumentException("Signals length can't be 0");
		}
		double divisor = Math.log(2);
		double dividend = Math.log(signals.size());
		return (int) (Math.ceil((dividend / divisor))) == (int) (Math.floor(((dividend / divisor))));
	}
}
