package net.openchrom.nmr.processing.supplier.base.settings.support;

import org.apache.commons.math3.distribution.CauchyDistribution;
import org.ejml.simple.SimpleMatrix;

import net.openchrom.nmr.processing.supplier.base.core.UtilityFunctions;
import net.openchrom.nmr.processing.supplier.base.settings.IcoShiftAlignmentSettings;
import net.openchrom.nmr.processing.supplier.base.settings.support.IcoShiftAlignmentUtilities.Interval;

public class Calibrate implements CalculateTargetFunction {

	private double medianCauchyDistribution = 0;
	private double scaleCauchyDistribution = 0.01;
	private double rangeCauchyDistribution = 2;

	@Override
	public double[] calculateTarget(SimpleMatrix data, Interval<Integer> interval, IcoShiftAlignmentSettings settings) {

		SimpleMatrix experimentalDatasetsMatrix = data;
		//
		int[] referenceWindow = IcoShiftAlignmentUtilities.generateReferenceWindow(interval);
		SimpleMatrix experimentalDatasetsMatrixPartForProcessing = new SimpleMatrix(experimentalDatasetsMatrix.numRows(), referenceWindow.length);
		int numberOfRows = experimentalDatasetsMatrixPartForProcessing.numRows();
		int numberOfCols = experimentalDatasetsMatrixPartForProcessing.numCols();
		for(int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
			// extract necessary part of data matrix
			for(int c = 0; c < numberOfCols; c++) {
				experimentalDatasetsMatrixPartForProcessing.set(rowIndex, c, experimentalDatasetsMatrix.get(rowIndex, referenceWindow[c]));
			}
		}
		/*
		 * generate lorentzian distribution and calculate a peak from it
		 */
		CauchyDistribution cDistribution = new CauchyDistribution(medianCauchyDistribution, scaleCauchyDistribution);
		UtilityFunctions utilityFunction = new UtilityFunctions();
		//
		int windowWidth = experimentalDatasetsMatrix.numCols();
		double[] xAxisVector = utilityFunction.generateLinearlySpacedVector(-rangeCauchyDistribution / 2, rangeCauchyDistribution / 2, windowWidth);
		double[] probabilityDensityFunction = new double[xAxisVector.length];
		for(int i = 0; i < xAxisVector.length; i++) {
			// calculate a peak with some intensity and peak maximum in the middle of interval
			probabilityDensityFunction[i] = Math.pow(cDistribution.density(xAxisVector[i]), 2); // TARGET
		}
		//
		return probabilityDensityFunction;
	}
}