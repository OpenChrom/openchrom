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
import net.openchrom.chromatogram.ms.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;
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
