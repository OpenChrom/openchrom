/*******************************************************************************
 * Copyright (c) 2016, 2018 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Walter Whitlock - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

import java.util.ArrayList;

import org.eclipse.chemclipse.logging.core.Logger;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class DecompositionResult {

	private static final Logger logger = Logger.getLogger(MassSpectraDecomposition.class);
	private ArrayList<ICalibratedVendorLibraryMassSpectrum> libraryComponents; // only need some of the info in CalibratedVendorLibraryMassSpectrum, but take it all for now
	private ArrayList<Double> xComp; // for library component i = fraction of library ion current spectrum which was found in scan ion current spectrum
	private ArrayList<Boolean> isQuantitative;
	private ICalibratedVendorMassSpectrum residualSpectrum;
	private CorrelationResult correlationResult;
	private double sumOfSquaresError;
	private double weightedSumOfSquaresError;
	private double solutionQuality;
	private double sourcePressure; // source pressure, from scan record
	private String sourcePressureUnits; // source pressure units, from scan record
	private String signalUnits;
	private double eTimeS; // elapsed time in seconds, from scan record
	private int componentCount;
	private boolean isCalibrated; // set false if at least one componen result not quantitative

	public DecompositionResult(double ssErr, double wssErr, double sourcePressure, String sourcePressureUnits, double eTimeS, String sigUnits) {
		sumOfSquaresError = ssErr;
		weightedSumOfSquaresError = wssErr;
		componentCount = 0;
		this.sourcePressure = sourcePressure;
		this.sourcePressureUnits = sourcePressureUnits;
		this.eTimeS = eTimeS;
		this.signalUnits = sigUnits;
		libraryComponents = new ArrayList<ICalibratedVendorLibraryMassSpectrum>();
		xComp = new ArrayList<Double>();
		isQuantitative = new ArrayList<Boolean>(); // it is possible to have a mix of quantitative and non-quantitative component results
		isCalibrated = true;
		residualSpectrum = null;
		correlationResult = null;
	}

	public boolean isCalibrated() {

		return isCalibrated;
	}

	public void addComponent(double x, ICalibratedVendorLibraryMassSpectrum libraryMassSpectrum, boolean isQuantitative) {

		if((null != xComp) && (null != libraryComponents)) {
			xComp.add(x);
			libraryComponents.add(libraryMassSpectrum);
			this.isQuantitative.add(isQuantitative);
			if(isCalibrated) {
				isCalibrated = isQuantitative;
			}
			componentCount++;
			assert (componentCount == this.xComp.size());
			assert (componentCount == this.libraryComponents.size());
			assert (componentCount == this.isQuantitative.size());
		}
	}

	public CorrelationResult getCorrelationResult() {

		return correlationResult;
	}

	public double getETimeS() {

		return eTimeS;
	}

	public String getLibCompName(int i) {

		return libraryComponents.get(i).getLibraryInformation().getName();
	}

	public int getNumberOfComponents() {

		return componentCount;
	}

	public double getLibraryFraction(int libIndex) {

		return this.xComp.get(libIndex);
	}

	public double getMolFraction(int libIndex) {

		if(isQuantitative.get(libIndex)) {
			return this.getPartialPressure(libIndex, this.sourcePressureUnits) / this.getSourcePressure();
		} else {
			return Double.NaN;
		}
	}

	public double getPartialPressure(int libIndex, String units) {

		if(isQuantitative.get(libIndex)) {
			return this.xComp.get(libIndex) * this.libraryComponents.get(libIndex).getSourcePressure(units);
		} else {
			return Double.NaN;
		}
	}

	public double getSolutionQuality() {

		return solutionQuality;
	}

	public ICalibratedVendorMassSpectrum getResidualSpectrum() {

		return residualSpectrum;
	}

	public String getSignalUnits() {

		return signalUnits;
	}

	public double getSourcePressure() {

		return sourcePressure;
	}

	public String getSourcePressureUnits() {

		return sourcePressureUnits;
	}

	public double getSumOfSquaresError() {

		return sumOfSquaresError;
	}

	public double getWeightedSumOfSquaresError() {

		return weightedSumOfSquaresError;
	}

	public boolean isQuantitative(int index) {

		return isQuantitative.get(index);
	}

	public void setCorrelationResult(CorrelationResult correlationResult) {

		if(null != correlationResult) {
			this.correlationResult = correlationResult;
		}
	}

	public void setResidualSpectrum(ICalibratedVendorMassSpectrum spec) {

		if(null != spec) {
			residualSpectrum = spec;
		}
	}

	public void setSolutionQuality(double solutionQuality) {

		this.solutionQuality = solutionQuality;
	}
}
