/*******************************************************************************
 * Copyright (c) 2013, 2016 Lablicate GmbH.
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

import net.openchrom.msd.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;

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
	 * Returns the miscellaneous information string.
	 * 
	 * @throws NoCDFAttributeDataFound
	 * @return String
	 */
	public String getMiscInfo() throws NoCDFAttributeDataFound;

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
	 * Returns the time stamp of the experiment creation.
	 * 
	 * @return String
	 * @throws NoCDFAttributeDataFound
	 */
	public String getDateOfExperiment() throws NoCDFAttributeDataFound;

	/**
	 * Returns the net cdf file.
	 * 
	 * @return NetcdfFile
	 */
	public NetcdfFile getChromatogram();

	/**
	 * Returns the retention time in milliseconds for the given scan.
	 * 
	 * @param scan
	 * @return int
	 */
	public int getScanAcquisitionTime(int scan);
}
