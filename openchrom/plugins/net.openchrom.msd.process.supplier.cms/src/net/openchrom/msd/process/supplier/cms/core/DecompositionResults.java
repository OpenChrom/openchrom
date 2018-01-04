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

import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

public class DecompositionResults {

	private static final Logger logger = Logger.getLogger(MassSpectraDecomposition.class);
	//
	private ArrayList<DecompositionResult> decompositionResults = null;
	private boolean isCalibrated; // true if all results in results list are calibrated
	private boolean hasETimes; // true if all results have a valid ETIMES field
	private boolean usingETimes; // if true, use etimes for x-axis
	private String name;

	DecompositionResults(String nameString) {
		decompositionResults = new ArrayList<DecompositionResult>();
		name = nameString;
		isCalibrated = true;
		hasETimes = true;
	}

	public void addDecompositionResult(DecompositionResult result) {

		if(null != this.decompositionResults) {
			this.decompositionResults.add(result);
			if(isCalibrated) {
				isCalibrated = result.isCalibrated();
			}
			if(hasETimes) {
				hasETimes = (0 <= result.getResidualSpectrum().getEtimes());
			}
		}
	}

	public ArrayList<DecompositionResult> getDecompositionResultsList() {

		return decompositionResults;
	}

	String getCompositionResultsTable() {

		String ppUnits = "";
		String columnHeading = "";
		StringBuilder headingBuilder = new StringBuilder();
		StringBuilder output = new StringBuilder();
		output.append(String.format("\t\tComponent Partial Pressures Table%n"));
		for(DecompositionResult result : this.getDecompositionResultsList()) {
			ppUnits = result.getSourcePressureUnits();
			// make column heading string for this result
			headingBuilder.setLength(0);
			headingBuilder.append(String.format("Quality\tScan #\tETime,s\tPtotal,%s", ppUnits));
			for(int i = 0; i < result.getNumberOfComponents(); i++) {
				if(result.isQuantitative(i)) {
					headingBuilder.append(String.format("\t%s", result.getLibCompName(i)));
				} else {
					headingBuilder.append(String.format("\t%s,uncalibrated", result.getLibCompName(i)));
				}
			}
			headingBuilder.append(String.format("%n"));
			if(!columnHeading.equalsIgnoreCase(headingBuilder.toString())) {
				columnHeading = headingBuilder.toString();
				output.append(columnHeading);
			}
			output.append(String.format("%g\t%d\t%g\t%g", result.getSolutionQuality(), result.getResidualSpectrum().getScanNumber(), result.getETimeS(), result.getSourcePressure()));
			for(int i = 0; i < result.getNumberOfComponents(); i++) {
				if(result.isQuantitative(i)) {
					output.append(String.format("\t%g", result.getPartialPressure(i, result.getSourcePressureUnits())));
				} else {
					output.append(String.format("\t%g", result.getLibraryFraction(i)));
				}
			}
			output.append(String.format("%n"));
		}
		return output.toString();
	}

	public boolean hasETimes() {

		return hasETimes;
	}

	public boolean isUsingETimes() {

		return usingETimes;
	}

	public void setUsingETimes(boolean usingETimes) {

		this.usingETimes = usingETimes && this.hasETimes;
	}

	public String getName() {

		return name;
	}

	String getResidualSpectraTable() {

		String sigUnits = "";
		String columHeading = "";
		StringBuilder headingBuilder = new StringBuilder();
		StringBuilder output = new StringBuilder();
		output.append(String.format("\t\tResidual Spectrum Table%n"));
		for(DecompositionResult result : this.getDecompositionResultsList()) {
			sigUnits = result.getSignalUnits();
			// make column heading string for this result
			headingBuilder.setLength(0);
			headingBuilder.append("Scan #\tETime,s");
			for(IIonMeasurement peak : result.getResidualSpectrum().getIonMeasurements()) {
				if(0 == (peak.getMZ() % 1)) {
					headingBuilder.append("\t" + (int)peak.getMZ());
				} else {
					headingBuilder.append("\t" + peak.getMZ());
				}
				headingBuilder.append("(" + sigUnits + ")");
			}
			headingBuilder.append(String.format("%n"));
			if(!columHeading.equalsIgnoreCase(headingBuilder.toString())) {
				columHeading = headingBuilder.toString();
				output.append(columHeading);
			}
			output.append(String.format("%d\t%g", result.getResidualSpectrum().getScanNumber(), result.getETimeS()));
			for(IIonMeasurement peak : result.getResidualSpectrum().getIonMeasurements()) {
				output.append(String.format("\t%g", peak.getSignal()));
			}
			output.append(String.format("%n"));
		}
		return output.toString();
	}

	public boolean isCalibrated() {

		return isCalibrated;
	}
}
