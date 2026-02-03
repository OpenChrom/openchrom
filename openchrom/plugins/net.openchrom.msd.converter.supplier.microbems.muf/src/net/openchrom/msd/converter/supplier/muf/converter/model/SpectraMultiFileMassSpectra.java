/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.muf.converter.model;

import org.eclipse.chemclipse.msd.model.core.AbstractMassSpectra;

public class SpectraMultiFileMassSpectra extends AbstractMassSpectra implements ISpectraMultiFileMassSpectra {

	private String genus;
	private String species;
	private String strain;
	private int taxonmicIdentifierNCBI;
	private int unmodifiedTaxonmicIdentifierNCBI;

	private String growthTime;
	private String temperature;
	private String atmosphere;
	private String medium;
	private boolean sporeFormer;

	private String type;
	private String sampleConcentration;
	private String sampleTreatment;
	private String extraInformation;

	private String laserParameters;
	private String calibrationInfo;
	private String measurementMethod;
	private String customerInfo;
	private String path;
	private int classAssignment;
	private String peakTableInfo; // TODO: investigate and parse

	@Override
	public String getGenus() {

		return genus;
	}

	@Override
	public void setGenus(String genus) {

		this.genus = genus;
	}

	@Override
	public String getSpecies() {

		return species;
	}

	@Override
	public void setSpecies(String species) {

		this.species = species;
	}

	@Override
	public String getStrain() {

		return strain;
	}

	@Override
	public void setStrain(String strain) {

		this.strain = strain;
	}

	@Override
	public int getTaxonmicIdentifierNCBI() {

		return taxonmicIdentifierNCBI;
	}

	@Override
	public void setTaxonmicIdentifierNCBI(int taxonmicIdentifierNCBI) {

		this.taxonmicIdentifierNCBI = taxonmicIdentifierNCBI;
	}

	@Override
	public int getUnmodifiedTaxonmicIdentifierNCBI() {

		return unmodifiedTaxonmicIdentifierNCBI;
	}

	@Override
	public void setUnmodifiedTaxonmicIdentifierNCBI(int unmodifiedTaxonmicIdentifierNCBI) {

		this.unmodifiedTaxonmicIdentifierNCBI = unmodifiedTaxonmicIdentifierNCBI;
	}

	@Override
	public String getGrowthTime() {

		return growthTime;
	}

	@Override
	public void setGrowthTime(String growthTime) {

		this.growthTime = growthTime;
	}

	@Override
	public String getTemperature() {

		return temperature;
	}

	@Override
	public void setTemperature(String temperature) {

		this.temperature = temperature;
	}

	@Override
	public String getAtmosphere() {

		return atmosphere;
	}

	@Override
	public void setAtmosphere(String atmosphere) {

		this.atmosphere = atmosphere;
	}

	@Override
	public String getMedium() {

		return medium;
	}

	@Override
	public void setMedium(String medium) {

		this.medium = medium;
	}

	@Override
	public boolean isSporeFormer() {

		return sporeFormer;
	}

	@Override
	public void setSporeFormer(boolean sporeFormer) {

		this.sporeFormer = sporeFormer;
	}

	@Override
	public String getType() {

		return type;
	}

	@Override
	public void setType(String type) {

		this.type = type;
	}

	@Override
	public String getSampleConcentration() {

		return sampleConcentration;
	}

	@Override
	public void setSampleConcentration(String sampleConcentration) {

		this.sampleConcentration = sampleConcentration;
	}

	@Override
	public String getSampleTreatment() {

		return sampleTreatment;
	}

	@Override
	public void setSampleTreatment(String sampleTreatment) {

		this.sampleTreatment = sampleTreatment;
	}

	@Override
	public String getExtraInformation() {

		return extraInformation;
	}

	@Override
	public void setExtraInformation(String extraInformation) {

		this.extraInformation = extraInformation;
	}

	@Override
	public String getLaserParameters() {

		return laserParameters;
	}

	@Override
	public void setLaserParameters(String laserParameters) {

		this.laserParameters = laserParameters;
	}

	@Override
	public String getCalibrationInformation() {

		return calibrationInfo;
	}

	@Override
	public void setCalibrationInformation(String calibrationInfo) {

		this.calibrationInfo = calibrationInfo;
	}

	@Override
	public String getMeasurementMethod() {

		return measurementMethod;
	}

	@Override
	public void setMeasurementMethod(String measurementMethod) {

		this.measurementMethod = measurementMethod;
	}

	@Override
	public String getCustomerInformation() {

		return customerInfo;
	}

	@Override
	public void setCustomerInformation(String customerInfo) {

		this.customerInfo = customerInfo;
	}

	@Override
	public String getSpectrumPath() {

		return path;
	}

	@Override
	public void setSpectrumPath(String path) {

		this.path = path;
	}

	@Override
	public int getClassAssignment() {

		return classAssignment;
	}

	@Override
	public void setClassAssignment(int classAssignment) {

		this.classAssignment = classAssignment;
	}

	@Override
	public String getPeakTableInformation() {

		return peakTableInfo;
	}

	@Override
	public void setPeakTableInformation(String peakTableInfo) {

		this.peakTableInfo = peakTableInfo;
	}
}