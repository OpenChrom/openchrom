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
import net.openchrom.msd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;

import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class CDFChromatogramOverviewArrayReader extends AbstractCDFChromatogramArrayReader implements ICDFChromatogramOverviewArrayReader {

	private double[] valueArrayTotalIntensity;

	public CDFChromatogramOverviewArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		super(chromatogram);
		initializeVariables();
	}

	private void initializeVariables() throws IOException, NoCDFVariableDataFound {

		String variable;
		variable = CDFConstants.VARIABLE_TOTAL_INTENSITY;
		Variable valuesTotalIntensity = getChromatogram().findVariable(variable);
		if(valuesTotalIntensity == null) {
			throw new NoCDFVariableDataFound("There could be no data found for the variable: " + variable);
		}
		valueArrayTotalIntensity = VariableSupport.readScaled(valuesTotalIntensity);
	}

	// ------------------------------------------------ICDFChromatogramOverviewArrayReader
	@Override
	public float getTotalSignal(int scan) {

		return (float)valueArrayTotalIntensity[--scan];
	}
	// ------------------------------------------------ICDFChromatogramOverviewArrayReader
}
