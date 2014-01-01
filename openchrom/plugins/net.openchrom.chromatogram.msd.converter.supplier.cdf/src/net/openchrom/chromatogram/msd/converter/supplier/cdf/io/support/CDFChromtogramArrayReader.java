/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support;

import java.io.IOException;

import net.openchrom.chromatogram.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.model.CDFIon;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.model.CDFMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.AbstractIon;
import net.openchrom.chromatogram.msd.model.exceptions.IonLimitExceededException;
import net.openchrom.logging.core.Logger;

import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * This class offers some convenient methods to parse the cdf file.
 * 
 * @author eselmeister
 */
public class CDFChromtogramArrayReader extends AbstractCDFChromatogramArrayReader implements ICDFChromatogramArrayReader {

	private static final Logger logger = Logger.getLogger(CDFChromtogramArrayReader.class);
	private Variable valuesIon;
	private Variable valuesAbundance;
	private double[] valueArrayIon;
	private float[] valueArrayAbundance;
	private Variable valuesPointCount;
	private Variable valuesScanIndex;
	private int[] valueArrayPointCount;
	private int[] valueArrayScanIndex;

	public CDFChromtogramArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		super(chromatogram);
		initializeVariables();
	}

	private void initializeVariables() throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		String variable;
		variable = CDFConstants.VARIABLE_MASS_VALUES;
		valuesIon = getChromatogram().findVariable(variable);
		if(valuesIon == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		variable = CDFConstants.VARIABLE_INTENSITY_VALUES;
		valuesAbundance = getChromatogram().findVariable(variable);
		if(valuesAbundance == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		variable = CDFConstants.VARIABLE_POINT_COUNT;
		valuesPointCount = getChromatogram().findVariable(variable);
		if(valuesPointCount == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		variable = CDFConstants.VARIABLE_SCAN_INDEX;
		valuesScanIndex = getChromatogram().findVariable(variable);
		if(valuesScanIndex == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		/*
		 * The system vendors store the cdf data arrays in different formats.
		 * Shimadzu: Ion -> Double Abundance -> Int PointCount -> Int ScanIndex
		 * -> Int ------------- Agilent: Ion -> Float Abundance -> Float
		 * PointCount -> Int ScanIndex -> Int
		 */
		valueArrayIon = (double[])valuesIon.read().get1DJavaArray(double.class);
		valueArrayAbundance = (float[])valuesAbundance.read().get1DJavaArray(float.class);
		valueArrayPointCount = (int[])valuesPointCount.read().get1DJavaArray(int.class);
		valueArrayScanIndex = (int[])valuesScanIndex.read().get1DJavaArray(int.class);
	}

	// ------------------------------------------------ICDFChromatogramArrayReader
	@Override
	public CDFMassSpectrum getMassSpectrum(int scan, int precision) throws NoSuchScanStored {

		/*
		 * If the scan is out of a valid range.
		 */
		if(scan < 1 || scan > getNumberOfScans()) {
			throw new NoSuchScanStored("The requested scan " + scan + " is not available");
		}
		CDFIon ion;
		CDFMassSpectrum massSpectrum = new CDFMassSpectrum();
		int peaks;
		int offset;
		int position;
		// --scan because the index of the array starts at 0 and not at 1.
		--scan;
		peaks = valueArrayPointCount[scan];
		offset = valueArrayScanIndex[scan];
		for(int i = 0; i < peaks; i++) {
			position = offset + i;
			try {
				double mz = AbstractIon.getIon(valueArrayIon[position], precision);
				ion = new CDFIon(mz, valueArrayAbundance[position]);
				massSpectrum.addIon(ion, false);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(IonLimitExceededException e) {
				logger.warn(e);
			}
		}
		// ++scan because it was decremented before
		int retentionTime = getScanAcquisitionTime(++scan);
		massSpectrum.setRetentionTime(retentionTime);
		return massSpectrum;
	}
	// ------------------------------------------------ICDFChromatogramArrayReader
}
