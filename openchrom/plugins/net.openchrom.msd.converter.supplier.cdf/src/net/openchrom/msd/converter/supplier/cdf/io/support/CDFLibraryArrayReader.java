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
package net.openchrom.msd.converter.supplier.cdf.io.support;

import java.io.IOException;

import org.eclipse.chemclipse.model.cas.CasSupport;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;

import net.openchrom.msd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.msd.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.openchrom.msd.converter.supplier.cdf.model.VendorIon;
import net.openchrom.msd.converter.supplier.cdf.model.VendorLibraryMassSpectrum;

import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * Reads an AIA/ANDI library (experiment_type = "Library Mass Spectrum").<br/>
 * Such a file holds one spectrum per entry instead of a time series, hence it
 * carries no usable 'scan_acquisition_time' and cannot be read as a
 * chromatogram. The compound description is spread over several optional
 * variables, of which a vendor fills in only the ones it knows.
 */
public class CDFLibraryArrayReader {

	private final int entries;
	private final double[] valueArrayIon;
	private final float[] valueArrayAbundance;
	private final int[] valueArrayPointCount;
	private final int[] valueArrayScanIndex;
	private final String[] entryNames;
	private final String[] casNames;
	private final String[] formulas;
	private final String[] smiles;
	private final String[] otherInformation;
	private final int[] casNumbers;
	private final int[] entryNumbers;
	private final int[] nominalMasses;
	private final double[] chemicalMasses;
	private final double[] retentionIndices;

	public CDFLibraryArrayReader(NetcdfFile library) throws IOException, NoCDFVariableDataFound {

		Dimension scans = library.findDimension(CDFConstants.DIMENSION_SCAN_NUMBER);
		if(scans == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the dimension: " + CDFConstants.DIMENSION_SCAN_NUMBER);
		}
		entries = scans.getLength();
		valueArrayIon = VariableSupport.readScaled(requireVariable(library, CDFConstants.VARIABLE_MASS_VALUES));
		valueArrayAbundance = VariableSupport.readScaledFloats(requireVariable(library, CDFConstants.VARIABLE_INTENSITY_VALUES));
		valueArrayPointCount = VariableSupport.readIntegers(requireVariable(library, CDFConstants.VARIABLE_POINT_COUNT));
		valueArrayScanIndex = VariableSupport.readIntegers(requireVariable(library, CDFConstants.VARIABLE_SCAN_INDEX));
		/*
		 * Everything below is optional.
		 */
		entryNames = readStrings(library, CDFConstants.VARIABLE_ENTRY_NAME);
		casNames = readStrings(library, CDFConstants.VARIABLE_CAS_NAME);
		formulas = readStrings(library, CDFConstants.VARIABLE_CHEMICAL_FORMULA);
		smiles = readStrings(library, CDFConstants.VARIABLE_SMILES);
		otherInformation = readStrings(library, CDFConstants.VARIABLE_ENTRY_OTHER_INFORMATION);
		casNumbers = readIntegers(library, CDFConstants.VARIABLE_CAS_NUMBER);
		entryNumbers = readIntegers(library, CDFConstants.VARIABLE_ENTRY_NUMBER);
		nominalMasses = readIntegers(library, CDFConstants.VARIABLE_NOMINAL_MASS);
		chemicalMasses = readScaled(library, CDFConstants.VARIABLE_CHEMICAL_MASS);
		retentionIndices = readScaled(library, CDFConstants.VARIABLE_RETENTION_INDEX);
	}

	public int getNumberOfEntries() {

		return entries;
	}

	public VendorLibraryMassSpectrum getMassSpectrum(int entry) throws NoSuchScanStored {

		if(entry < 1 || entry > entries) {
			throw new NoSuchScanStored("The requested entry " + entry + " is not available");
		}
		VendorLibraryMassSpectrum massSpectrum = new VendorLibraryMassSpectrum();
		/*
		 * --entry because the index of the array starts at 0 and not at 1.
		 */
		--entry;
		int peaks = valueArrayPointCount[entry];
		int offset = valueArrayScanIndex[entry];
		for(int i = 0; i < peaks; i++) {
			int position = offset + i;
			float intensity = valueArrayAbundance[position];
			if(intensity > 0) {
				massSpectrum.addIon(new VendorIon(valueArrayIon[position], intensity), false);
			}
		}
		setLibraryInformation(massSpectrum, entry);
		double retentionIndex = getValue(retentionIndices, entry);
		if(retentionIndex > 0) {
			massSpectrum.setRetentionIndex((float)retentionIndex);
		}
		return massSpectrum;
	}

	private void setLibraryInformation(VendorLibraryMassSpectrum massSpectrum, int entry) {

		ILibraryInformation libraryInformation = massSpectrum.getLibraryInformation();
		String name = getValue(entryNames, entry);
		if(name.isEmpty()) {
			name = getValue(casNames, entry);
		}
		libraryInformation.setName(name);
		libraryInformation.setFormula(getValue(formulas, entry));
		libraryInformation.setSmiles(getValue(smiles, entry));
		libraryInformation.setComments(getValue(otherInformation, entry));
		/*
		 * The CAS# is stored without the separators, e.g. 74828 is 74-82-8.
		 */
		int casNumber = getValue(casNumbers, entry);
		if(casNumber > 0) {
			libraryInformation.setCasNumber(CasSupport.format(Integer.toString(casNumber)));
		}
		int entryNumber = getValue(entryNumbers, entry);
		if(entryNumber > 0) {
			libraryInformation.setDatabaseIndex(entryNumber);
		}
		/*
		 * The chemical mass is the more precise of the two, so it wins.
		 */
		double chemicalMass = getValue(chemicalMasses, entry);
		int nominalMass = getValue(nominalMasses, entry);
		if(chemicalMass > 0) {
			libraryInformation.setMolWeight(chemicalMass);
		} else if(nominalMass > 0) {
			libraryInformation.setMolWeight(nominalMass);
		}
	}

	private static Variable requireVariable(NetcdfFile library, String name) throws NoCDFVariableDataFound {

		Variable variable = library.findVariable(name);
		if(variable == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + name);
		}
		return variable;
	}

	private static String[] readStrings(NetcdfFile library, String name) throws IOException {

		Variable variable = library.findVariable(name);
		return variable == null ? null : VariableSupport.readStrings(variable);
	}

	private static int[] readIntegers(NetcdfFile library, String name) throws IOException {

		Variable variable = library.findVariable(name);
		return variable == null ? null : VariableSupport.readIntegers(variable);
	}

	private static double[] readScaled(NetcdfFile library, String name) throws IOException {

		Variable variable = library.findVariable(name);
		return variable == null ? null : VariableSupport.readScaled(variable);
	}

	private static String getValue(String[] values, int entry) {

		if(values == null || entry >= values.length || values[entry] == null) {
			return "";
		}
		return values[entry];
	}

	private static int getValue(int[] values, int entry) {

		if(values == null || entry >= values.length || VariableSupport.isNullValue(values[entry])) {
			return 0;
		}
		return values[entry];
	}

	private static double getValue(double[] values, int entry) {

		if(values == null || entry >= values.length || VariableSupport.isNullValue(values[entry])) {
			return 0.0d;
		}
		return values[entry];
	}
}
