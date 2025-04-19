/*******************************************************************************
 * Copyright (c) 2014, 2025 Lablicate GmbH.
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
