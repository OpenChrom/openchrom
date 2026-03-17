/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io.support;

import java.io.IOException;

import net.openchrom.msd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.msd.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.openchrom.msd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.openchrom.msd.converter.supplier.cdf.model.VendorIon;
import net.openchrom.msd.converter.supplier.cdf.model.VendorScan;

import ucar.ma2.DataType;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * This class offers some convenient methods to parse the cdf file.
 */
public class CDFChromtogramArrayReader extends AbstractCDFChromatogramArrayReader {

	private double[] valueArrayIon;
	private float[] valueArrayAbundance;
	private int[] valueArrayPointCount;
	private int[] valueArrayScanIndex;

	public CDFChromtogramArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		super(chromatogram);
		initializeScanVariables();
	}

	private void initializeScanVariables() throws IOException, NoCDFVariableDataFound {

		String variable;
		variable = CDFConstants.VARIABLE_MASS_VALUES;
		Variable valuesIon = getChromatogram().findVariable(variable);
		if(valuesIon == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		variable = CDFConstants.VARIABLE_INTENSITY_VALUES;
		Variable valuesAbundance = getChromatogram().findVariable(variable);
		if(valuesAbundance == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		variable = CDFConstants.VARIABLE_POINT_COUNT;
		Variable valuesPointCount = getChromatogram().findVariable(variable);
		if(valuesPointCount == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		variable = CDFConstants.VARIABLE_SCAN_INDEX;
		Variable valuesScanIndex = getChromatogram().findVariable(variable);
		if(valuesScanIndex == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		/*
		 * The system vendors store the cdf data arrays in different formats.
		 * Shimadzu: Ion -> Double Abundance -> Int PointCount -> Int ScanIndex
		 * -> Int ------------- Agilent: Ion -> Float Abundance -> Float
		 * PointCount -> Int ScanIndex -> Int
		 */
		valueArrayIon = (double[])valuesIon.read().get1DJavaArray(DataType.DOUBLE);
		valueArrayAbundance = (float[])valuesAbundance.read().get1DJavaArray(DataType.FLOAT);
		valueArrayPointCount = (int[])valuesPointCount.read().get1DJavaArray(DataType.INT);
		valueArrayScanIndex = (int[])valuesScanIndex.read().get1DJavaArray(DataType.INT);
	}

	public VendorScan getMassSpectrum(int scan) throws NoSuchScanStored {

		/*
		 * If the scan is out of a valid range.
		 */
		if(scan < 1 || scan > getNumberOfScans()) {
			throw new NoSuchScanStored("The requested scan " + scan + " is not available");
		}
		/*
		 * Scan
		 */
		VendorScan massSpectrum = new VendorScan();
		/*
		 * --scan because the index of the array starts at 0 and not at 1.
		 */
		--scan;
		int peaks = valueArrayPointCount[scan];
		int offset = valueArrayScanIndex[scan];

		for(int i = 0; i < peaks; i++) {
			int position = offset + i;

			double mz = valueArrayIon[position];
			float intensity = valueArrayAbundance[position];

			if(intensity > 0) {
				addIon(massSpectrum, mz, intensity);
			}
		}

		/*
		 * ++scan because it was decremented before
		 */
		int retentionTime = getScanAcquisitionTime(++scan);
		massSpectrum.setRetentionTime(retentionTime);
		return massSpectrum;
	}

	private void addIon(VendorScan massSpectrum, double mz, float intensity) {

		VendorIon ion = new VendorIon(mz, intensity);
		massSpectrum.addIon(ion, false);
	}
}