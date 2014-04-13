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

import ucar.ma2.ArrayDouble;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public abstract class AbstractCDFChromatogramArrayReader implements IAbstractCDFChromatogramArrayReader {

	private NetcdfFile chromatogram;
	private Attribute operator;
	private Attribute date;
	private Dimension scans;
	private Variable valuesScanAcquisitionTime;
	private ArrayDouble.D1 valueArrayScanAcquisitionTime;

	public AbstractCDFChromatogramArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		this.chromatogram = chromatogram;
		initializeVariables();
	}

	private void initializeVariables() throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		// Tests if a chromatogram has at least 2 scans.
		if(scans.getLength() < 2) {
			throw new NotEnoughScanDataStored();
		}
		valueArrayScanAcquisitionTime = (ArrayDouble.D1)valuesScanAcquisitionTime.read();
	}

	// ------------------------------------------------IAbstractCDFChromatogramArrayReader
	@Override
	public int getNumberOfScans() {

		return scans.getLength();
	}

	@Override
	public int getScanDelay() {

		return (int)(valueArrayScanAcquisitionTime.get(0) * 1000);
	}

	@Override
	public int getScanInterval() {

		double interval = 0;
		int massSpectra = scans.getLength() / (10 * 4);
		if(massSpectra < 2) {
			massSpectra = 2;
		}
		/*
		 * Length is greater than 1.<br/> Otherwise the class couldn't be
		 * created without throwing an exception.
		 */
		for(int i = 0; i < massSpectra - 1; i++) {
			interval += (valueArrayScanAcquisitionTime.get(i + 1) - valueArrayScanAcquisitionTime.get(i));
		}
		return (int)((interval / massSpectra) * 1000);
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
	public int getScanAcquisitionTime(int scan) {

		// Decrement scan because the array has another index.
		return (int)(valueArrayScanAcquisitionTime.get(--scan) * 1000);
	}
}
