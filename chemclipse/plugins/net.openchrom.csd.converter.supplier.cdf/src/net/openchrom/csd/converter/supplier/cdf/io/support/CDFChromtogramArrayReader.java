/*******************************************************************************
 * Copyright (c) 2014, 2015 Dr. Philip Wenig.
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

import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.csd.converter.supplier.cdf.exceptions.NotEnoughScanDataStored;

import ucar.nc2.NetcdfFile;

public class CDFChromtogramArrayReader extends AbstractCDFChromatogramArrayReader implements ICDFChromatogramArrayReader {

	public CDFChromtogramArrayReader(NetcdfFile chromatogram) throws IOException, NoCDFVariableDataFound, NotEnoughScanDataStored {

		super(chromatogram);
	}
}
