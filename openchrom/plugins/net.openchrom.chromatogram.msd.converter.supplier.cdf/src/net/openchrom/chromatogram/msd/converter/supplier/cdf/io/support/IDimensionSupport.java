/*******************************************************************************
 * Copyright (c) 2008, 2013 Philip (eselmeister) Wenig.
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
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support;

import java.util.ArrayList;

import ucar.nc2.Dimension;

public interface IDimensionSupport {

	public Dimension getByteString32();

	public Dimension getByteString64();

	public Dimension getNumberOfScans();

	public Dimension getNumberOfScanIons();

	public Dimension getInstrumentNumber();

	public Dimension getErrorNumber();

	public void addVariableCharD2(String varName, Dimension firstDimension, Dimension secondDimension, String content);

	public void addVariableDoubleD1(String varName, Dimension firstDimension, double value);

	public void addVariableShortD1(String varName, Dimension firstDimension, short value);

	public void addVariableIntD1(String varName, Dimension firstDimension, int value);

	// Convenience methods
	public void addVariableScanAcquisitionTime();

	public void addVariableActualScanNumber();

	public void addVariableTotalIntensity();

	void addVariableMassRangeMin();

	void addVariableMassRangeMax();

	public void addVariableScanIndex();

	public void addVariablePointCount();

	public void addVariableScanValues();

	/**
	 * In the array list are all data entries stored that are needed to write
	 * the cdf file.
	 * 
	 * @return
	 */
	public ArrayList<IDataEntry> getDataEntries();
}
