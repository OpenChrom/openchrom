/*******************************************************************************
 * Copyright (c) 2013, 2025 Lablicate GmbH.
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

import net.openchrom.csd.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
import net.openchrom.csd.converter.supplier.cdf.model.VendorChromatogramCSD;

import ucar.nc2.NetcdfFile;

public interface IAbstractCDFChromatogramArrayReader {

	/**
	 * Returns the number of stored scans.
	 * 
	 * @return int
	 */
	public int getNumberOfScans();

	/**
	 * Returns the scan delay in milliseconds.
	 * 
	 * @return int
	 */
	public int getScanDelay();

	/**
	 * Returns the scan interval in milliseconds.
	 * 
	 * @return
	 */
	public int getScanInterval();

	/**
	 * Returns the operator string.
	 * 
	 * @throws NoCDFAttributeDataFound
	 * @return String
	 */
	public String getOperator() throws NoCDFAttributeDataFound;

	/**
	 * Returns the time stamp of the file creation.
	 * 
	 * @return String
	 * @throws NoCDFAttributeDataFound
	 */
	public String getDate() throws NoCDFAttributeDataFound;

	/**
	 * Returns the net cdf file.
	 * 
	 * @return NetcdfFile
	 */
	public NetcdfFile getChromatogram();

	public float getIntensity(int scan);

	public void readPeakTable(VendorChromatogramCSD vendorChromatogram);
}
