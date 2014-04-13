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
package net.chemclipse.chromatogram.fid.converter.supplier.cdf.io.support;

import java.io.IOException;

import net.chemclipse.chromatogram.fid.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;

import ucar.ma2.ArrayFloat;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public abstract class AbstractCDFChromatogramArrayReader implements IAbstractCDFChromatogramArrayReader {

	private NetcdfFile chromatogram;
	private Attribute operator;
	private Attribute date;
	private Dimension scans;
	private Variable valuesIntensity;
	private ArrayFloat.D1 valueArrayIntensity;

	public AbstractCDFChromatogramArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		this.chromatogram = chromatogram;
		initializeVariables();
	}

	private void initializeVariables() throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		String variable = CDFConstants.VARIABLE_ORDINATE_VALUES;
		valuesIntensity = chromatogram.findVariable(variable);
		if(valuesIntensity == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
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
		valueArrayIntensity = (ArrayFloat.D1)valuesIntensity.read();
	}

	// ------------------------------------------------IAbstractCDFChromatogramArrayReader
	@Override
	public int getNumberOfScans() {

		return scans.getLength();
	}

	@Override
	public int getScanDelay() {

		return 0;
	}

	@Override
	public int getScanInterval() {

		return 500;
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
}
