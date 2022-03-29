/*******************************************************************************
 * Copyright (c) 2013, 2022 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.io.support;

import java.io.IOException;

import org.eclipse.chemclipse.logging.core.Logger;

import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import ucar.ma2.ArrayChar;
import ucar.ma2.ArrayFloat;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public abstract class AbstractCDFChromatogramArrayReader implements IAbstractCDFChromatogramArrayReader {

	private static final Logger logger = Logger.getLogger(AbstractCDFChromatogramArrayReader.class);
	private NetcdfFile chromatogram;
	private Attribute operator;
	private Attribute date;
	private Attribute retentionUnit;
	private Dimension scans;
	private Variable valuesIntensity;
	private ArrayFloat.D1 valueArrayIntensity;
	private Variable valueScanDelayTime;
	private Variable valueScanInterval;
	private Variable valueRunTimeLength;
	//
	private int scanDelay;
	private int scanInterval;

	public AbstractCDFChromatogramArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		this.chromatogram = chromatogram;
		initializeVariables();
	}

	private void initializeVariables() throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		String variable;
		//
		String dimension = CDFConstants.DIMENSION_POINT_NUMBER;
		scans = chromatogram.findDimension(dimension);
		if(scans == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + dimension);
		}
		// Tests if a chromatogram has at least 2 scans.
		if(scans.getLength() < 2) {
			throw new NotEnoughScanDataStored();
		}
		//
		variable = CDFConstants.VARIABLE_ACTUAL_DELAY_TIME;
		valueScanDelayTime = chromatogram.findVariable(variable);
		if(valueScanDelayTime == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		//
		variable = CDFConstants.VARIABLE_ACTUAL_SAMPLING_INTERVAL;
		valueScanInterval = chromatogram.findVariable(variable);
		if(valueScanInterval == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		/*
		 * Calculate the scan delay and interval.
		 */
		scanDelay = 0; // milliseconds
		scanInterval = 0; // milliseconds
		//
		double retentionTimeScaleFactor;
		retentionUnit = chromatogram.findGlobalAttribute(CDFConstants.ATTRIBUTE_RETENTION_UNIT);
		if(retentionUnit != null) {
			String unit = retentionUnit.getStringValue().trim();
			if(unit.equals("seconds") || unit.equals("Seconds")) {
				retentionTimeScaleFactor = 1000;
			} else if(unit.equals("minutes") || unit.equals("Minutes") || unit.equals("time in minutes")) {
				retentionTimeScaleFactor = 1000 * 60;
			} else {
				/*
				 * Milliseconds
				 */
				retentionTimeScaleFactor = 1;
			}
		} else {
			retentionTimeScaleFactor = 1;
		}
		/*
		 * Not all supplier store a run time length.
		 */
		variable = CDFConstants.VARIABLE_ACTUAL_RUN_TIME_LENGTH;
		valueRunTimeLength = chromatogram.findVariable(variable);
		if(valueRunTimeLength == null) {
			/*
			 * Normal
			 */
			scanDelay = (int)(valueScanDelayTime.readScalarFloat() * retentionTimeScaleFactor);
			scanInterval = (int)(valueScanInterval.readScalarFloat() * retentionTimeScaleFactor);
		} else {
			/*
			 * DataApex
			 */
			scanDelay = (int)(valueScanDelayTime.readScalarFloat() * retentionTimeScaleFactor);
			scanInterval = (int)(((valueRunTimeLength.readScalarFloat() - valueScanDelayTime.readScalarFloat()) * retentionTimeScaleFactor) / (scans.getLength() - 1));
		}
		/*
		 * Add a default scan interval if none has set yet.
		 */
		if(scanInterval == 0) {
			scanInterval = 200; // milliseconds
		}
		//
		variable = CDFConstants.VARIABLE_ORDINATE_VALUES;
		valuesIntensity = chromatogram.findVariable(variable);
		if(valuesIntensity == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		//
		valueArrayIntensity = (ArrayFloat.D1)valuesIntensity.read();
	}

	// ------------------------------------------------IAbstractCDFChromatogramArrayReader
	@Override
	public int getNumberOfScans() {

		return scans.getLength();
	}

	@Override
	public int getScanDelay() {

		return scanDelay;
	}

	@Override
	public int getScanInterval() {

		return scanInterval;
	}

	@Override
	public String getOperator() throws NoCDFAttributeDataFound {

		operator = chromatogram.findGlobalAttribute(CDFConstants.ATTRIBUTE_OPERATOR_NAME);
		if(operator == null) {
			throw new NoCDFAttributeDataFound("There could be no data found for the attribute: " + CDFConstants.ATTRIBUTE_OPERATOR_NAME);
		}
		return operator.getStringValue().trim();
	}

	@Override
	public String getDate() throws NoCDFAttributeDataFound {

		date = chromatogram.findGlobalAttribute(CDFConstants.ATTRIBUTE_DATASET_DATE_TIME_STAMP);
		if(date == null) {
			throw new NoCDFAttributeDataFound("There could be no data found for the attribute: " + CDFConstants.ATTRIBUTE_DATASET_DATE_TIME_STAMP);
		}
		return date.getStringValue().trim();
	}

	@Override
	public NetcdfFile getChromatogram() {

		return this.chromatogram;
	}

	@Override
	public float getIntensity(int scan) {

		return valueArrayIntensity.get(scan--);
	}

	@Override
	public void readPeakTable() {

		try {
			Variable valuesPeakName = chromatogram.findVariable(CDFConstants.VARIABLE_PEAK_NAME);
			if(valuesPeakName == null) {
				return;
			}
			ArrayChar.D2 valuesArrayPeakName = (ArrayChar.D2)valuesPeakName.read();
			Variable valuesPeakRententionTime = chromatogram.findVariable(CDFConstants.VARIABLE_PEAK_RETENTION_TIME);
			if(valuesPeakRententionTime == null) {
				return;
			}
			ArrayFloat.D1 valuesArrayPeakRententionTime = (ArrayFloat.D1)valuesPeakRententionTime.read();
			Variable valuesPeakAmount = chromatogram.findVariable(CDFConstants.VARIABLE_PEAK_AMOUNT);
			if(valuesPeakAmount == null) {
				return;
			}
			Variable valuesPeakHeight = chromatogram.findVariable(CDFConstants.VARIABLE_PEAK_HEIGHT);
			if(valuesPeakHeight == null) {
				return;
			}
			Variable valuesPeakArea = chromatogram.findVariable(CDFConstants.VARIABLE_PEAK_AREA);
			if(valuesPeakArea == null) {
				return;
			}
			Variable valuesPeakAreaPercent = chromatogram.findVariable(CDFConstants.VARIABLE_PEAK_AREA_PERCENT);
			if(valuesPeakAreaPercent == null) {
				return;
			}
			// TODO: actually import the peaks
			long size = Math.min(valuesPeakName.getSize(), valuesArrayPeakRententionTime.getSize());
			for(int p = 0; p < size; p++) {
				logger.warn("Ignoring peak " + valuesArrayPeakName.getString(p) + " at " + valuesArrayPeakRententionTime.get(p));
			}
		} catch(Exception e) {
			System.out.println("Failed to read the peak table.");
			logger.warn(e);
		}
	}
}
