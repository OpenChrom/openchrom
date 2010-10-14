/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.ms.converter.supplier.cdf.io.support;

import java.io.IOException;

import net.openchrom.chromatogram.ms.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.chromatogram.ms.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.openchrom.chromatogram.ms.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.openchrom.chromatogram.ms.converter.supplier.cdf.model.CDFMassFragment;
import net.openchrom.chromatogram.ms.converter.supplier.cdf.model.CDFMassSpectrum;
import net.openchrom.chromatogram.ms.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.ms.model.exceptions.MZLimitExceededException;
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
	private Variable valuesMZ;
	private Variable valuesAbundance;
	private float[] valueArrayMZ;
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
		valuesMZ = getChromatogram().findVariable(variable);
		if(valuesMZ == null) {
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
		 * Shimadzu: MZ -> Double Abundance -> Int PointCount -> Int ScanIndex
		 * -> Int ------------- Agilent: MZ -> Float Abundance -> Float
		 * PointCount -> Int ScanIndex -> Int
		 */
		valueArrayMZ = (float[])valuesMZ.read().get1DJavaArray(float.class);
		valueArrayAbundance = (float[])valuesAbundance.read().get1DJavaArray(float.class);
		valueArrayPointCount = (int[])valuesPointCount.read().get1DJavaArray(int.class);
		valueArrayScanIndex = (int[])valuesScanIndex.read().get1DJavaArray(int.class);
	}

	// ------------------------------------------------ICDFChromatogramArrayReader
	@Override
	public CDFMassSpectrum getMassSpectrum(int scan) throws NoSuchScanStored {

		/*
		 * If the scan is out of a valid range.
		 */
		if(scan < 1 || scan > getNumberOfScans()) {
			throw new NoSuchScanStored("The requested scan " + scan + " is not available");
		}
		CDFMassFragment massFragment;
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
				massFragment = new CDFMassFragment(valueArrayMZ[position], valueArrayAbundance[position]);
				massSpectrum.addMassFragment(massFragment, false);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(MZLimitExceededException e) {
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
