/*******************************************************************************
 * Copyright (c) 2013, 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.io.support;

import java.io.IOException;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;

import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
import net.openchrom.csd.converter.supplier.cdf.model.VendorChromatogramCSD;

import ucar.ma2.ArrayChar;
import ucar.ma2.ArrayFloat;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public abstract class AbstractCDFChromatogramArrayReader implements IAbstractCDFChromatogramArrayReader {

	private static final Logger logger = Logger.getLogger(AbstractCDFChromatogramArrayReader.class);
	private NetcdfFile chromatogram;
	private Dimension scans;
	private ArrayFloat.D1 valueArrayIntensity;
	//
	private int scanDelay;
	private int scanInterval;

	protected AbstractCDFChromatogramArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

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
		Variable valueScanDelayTime = chromatogram.findVariable(variable);
		if(valueScanDelayTime == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		//
		variable = CDFConstants.VARIABLE_ACTUAL_SAMPLING_INTERVAL;
		Variable valueScanInterval = chromatogram.findVariable(variable);
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
		Attribute retentionUnit = chromatogram.findGlobalAttribute(CDFConstants.ATTRIBUTE_RETENTION_UNIT);
		if(retentionUnit != null) {
			String unit = retentionUnit.getStringValue().trim();
			if(unit.equals("seconds") || unit.equals("Seconds") || unit.equals("s")) {
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
		Variable valueRunTimeLength = chromatogram.findVariable(variable);
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
		Variable valuesIntensity = chromatogram.findVariable(variable);
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

		Attribute operator = chromatogram.findGlobalAttribute(CDFConstants.ATTRIBUTE_OPERATOR_NAME);
		if(operator == null) {
			throw new NoCDFAttributeDataFound("There could be no data found for the attribute: " + CDFConstants.ATTRIBUTE_OPERATOR_NAME);
		}
		return operator.getStringValue().trim();
	}

	@Override
	public String getDate() throws NoCDFAttributeDataFound {

		Attribute date = chromatogram.findGlobalAttribute(CDFConstants.ATTRIBUTE_DATASET_DATE_TIME_STAMP);
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

		return valueArrayIntensity.get(scan);
	}

	@Override
	public void readPeakTable(VendorChromatogramCSD vendorChromatogram) {

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
			long rts = valuesArrayPeakRententionTime.getSize();
			for(int p = 0; p < rts; p++) {
				float retentionTime = valuesArrayPeakRententionTime.get(p);
				ILibraryInformation libraryInformation = new LibraryInformation();
				libraryInformation.setName(valuesArrayPeakName.getString(p));
				IComparisonResult comparisonResult = ComparisonResult.COMPARISON_RESULT_BEST_MATCH;
				IIdentificationTarget identificationTarget = new IdentificationTarget(libraryInformation, comparisonResult);
				int scanNumber = vendorChromatogram.getScanNumber(retentionTime);
				if(scanNumber >= 1) {
					vendorChromatogram.getScan(scanNumber).getTargets().add(identificationTarget);
				}
			}
		} catch(Exception e) {
			logger.warn("Failed to read the peak table.", e);
		}
	}
}