/*******************************************************************************
 * Copyright (c) 2013, 2016 Dr. Philip Wenig.
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

import net.openchrom.msd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.msd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;

import ucar.ma2.ArrayDouble;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class CDFChromatogramOverviewArrayReader extends AbstractCDFChromatogramArrayReader implements ICDFChromatogramOverviewArrayReader {

	private Variable valuesTotalIntensity;
	private ArrayDouble.D1 valueArrayTotalIntensity;

	public CDFChromatogramOverviewArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {
		super(chromatogram);
		initializeVariables();
	}

	private void initializeVariables() throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		String variable;
		variable = CDFConstants.VARIABLE_TOTAL_INTENSITY;
		valuesTotalIntensity = getChromatogram().findVariable(variable);
		if(valuesTotalIntensity == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		valueArrayTotalIntensity = (ArrayDouble.D1)valuesTotalIntensity.read();
	}

	// ------------------------------------------------ICDFChromatogramOverviewArrayReader
	@Override
	public float getTotalSignal(int scan) {

		return (float)valueArrayTotalIntensity.get(--scan);
	}
	// ------------------------------------------------ICDFChromatogramOverviewArrayReader
}
