/*******************************************************************************
 * Copyright (c) 2014 Dr. Philip Wenig.
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

import net.chemclipse.chromatogram.fid.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.chemclipse.chromatogram.fid.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;

import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * This class offers some convenient methods to parse the cdf file.
 * 
 * @author eselmeister
 */
public class CDFChromtogramArrayReader extends AbstractCDFChromatogramArrayReader implements ICDFChromatogramArrayReader {

	private Variable valuesIon;
	private Variable valuesAbundance;
	private float[] valueArrayAbundance;
	private Variable valuesPointCount;
	private Variable valuesScanIndex;

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
		//
		valueArrayAbundance = (float[])valuesAbundance.read().get1DJavaArray(float.class);
	}
}
