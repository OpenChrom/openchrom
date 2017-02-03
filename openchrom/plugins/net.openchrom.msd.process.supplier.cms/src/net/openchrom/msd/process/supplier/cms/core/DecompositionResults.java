/*******************************************************************************
 * Copyright (c) 2016, 2017 Walter Whitlock.
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

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.IIonMeasurement;

public class DecompositionResults {

	private static final Logger logger = Logger.getLogger(MassSpectraDecomposition.class);
	private ArrayList<DecompositionResult> results;
	private boolean isCalibrated; // set false if at least one result in results list is uncalibrated
	private boolean hasETimes; // set false if at least one result does not have an ETIMES field
	private String name;

	DecompositionResults(String nameString) {
		results = new ArrayList<DecompositionResult>();
		name = nameString;
		isCalibrated = true;
		hasETimes = true;
	}

	public void addResult(DecompositionResult result) {

		if(null != this.results) {
			this.results.add(result);
			if (isCalibrated)
				isCalibrated = result.isCalibrated();
			if (hasETimes)
				hasETimes = ( 0 <= result.getResidualSpectrum().getEtimes());
		}
	}

	public ArrayList<DecompositionResult> getResults() {

		return results;
	}

	String getCompositionResultsTable() {

		String ppUnits = "";
		String columnHeading = "";
		StringBuilder headingBuilder = new StringBuilder();
		StringBuilder output = new StringBuilder();
		output.append(String.format("\t\tComponent Partial Pressures Table%n"));
		for(DecompositionResult result : this.getResults()) {
			ppUnits = result.getSourcePressureUnits();
			// make column heading string for this result
			headingBuilder.setLength(0);
			headingBuilder.append(String.format("ETime,s\tPtotal,%s", ppUnits));
			for(int i = 0; i < result.getNumberOfComponents(); i++) {
				if(result.isQuantitative(i))
					headingBuilder.append(String.format("\t%s", result.getLibCompName(i)));
				else
					headingBuilder.append(String.format("\t%s,uncalibrated", result.getLibCompName(i)));
			}
			headingBuilder.append(String.format("%n"));
			if(!columnHeading.equalsIgnoreCase(headingBuilder.toString())) {
				columnHeading = headingBuilder.toString();
				output.append(columnHeading);
			}
			output.append(String.format("%g\t%g", result.getETimeS(), result.getSourcePressure()));
			for(int i = 0; i < result.getNumberOfComponents(); i++) {
				output.append(String.format("\t%g", result.getPartialPressure(i)));
			}
			output.append(String.format("%n"));
		}
		return output.toString();
	}
	
	public boolean hasETimes() {
		return hasETimes;
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
		for(DecompositionResult result : this.getResults()) {
			sigUnits = result.getSignalUnits();
			// make column heading string for this result
			headingBuilder.setLength(0);
			headingBuilder.append("ETime,s");
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
			output.append(String.format("%g", result.getETimeS()));
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
