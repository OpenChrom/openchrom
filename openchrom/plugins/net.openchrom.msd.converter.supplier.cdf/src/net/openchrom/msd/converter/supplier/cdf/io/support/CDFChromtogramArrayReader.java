/*******************************************************************************
 * Copyright (c) 2013, 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.AbstractIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;

import net.openchrom.msd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.msd.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.openchrom.msd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.openchrom.msd.converter.supplier.cdf.model.VendorIon;
import net.openchrom.msd.converter.supplier.cdf.model.VendorScan;

import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * This class offers some convenient methods to parse the cdf file.
 * 
 * @author eselmeister
 */
public class CDFChromtogramArrayReader extends AbstractCDFChromatogramArrayReader {

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

	public VendorScan getMassSpectrum(int scan, int precision, boolean forceNominal) throws NoSuchScanStored {

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
		Map<Integer, DataPoint> dataPointMap = (forceNominal) ? new HashMap<>() : null;
		/*
		 * --scan because the index of the array starts at 0 and not at 1.
		 */
		--scan;
		int peaks = valueArrayPointCount[scan];
		int offset = valueArrayScanIndex[scan];
		//
		for(int i = 0; i < peaks; i++) {
			int position = offset + i;
			/*
			 * If force nominal, then first collect and condense the data.
			 */
			if(forceNominal) {
				int mz = (int)Math.round(valueArrayIon[position]);
				float intensity = valueArrayAbundance[position];
				//
				if(intensity > 0) {
					DataPoint dataPoint = dataPointMap.get(mz);
					if(dataPoint == null) {
						dataPoint = new DataPoint(mz, intensity);
						dataPointMap.put(mz, dataPoint);
					} else {
						double abundance = dataPoint.getIntensity() + intensity;
						dataPoint.setIntensity(abundance);
					}
				}
			} else {
				/*
				 * Normal
				 */
				double mz = AbstractIon.getIon(valueArrayIon[position], precision);
				float intensity = valueArrayAbundance[position];
				//
				if(intensity > 0) {
					addIon(massSpectrum, mz, intensity);
				}
			}
		}
		/*
		 * Add the collected and condensed data.
		 */
		if(forceNominal) {
			for(DataPoint dataPoint : dataPointMap.values()) {
				double mz = dataPoint.getMz();
				float intensity = (float)dataPoint.getIntensity();
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

		try {
			VendorIon ion = new VendorIon(mz, intensity);
			massSpectrum.addIon(ion, false);
		} catch(AbundanceLimitExceededException e) {
			logger.warn(e);
		} catch(IonLimitExceededException e) {
			logger.warn(e);
		}
	}
}
