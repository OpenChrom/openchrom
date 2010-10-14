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

import net.openchrom.chromatogram.ms.converter.supplier.cdf.exceptions.NoCDFAttributeDataFound;
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
