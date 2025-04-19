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
package net.openchrom.msd.converter.supplier.cdf.io.support;

import java.util.ArrayList;

import ucar.nc2.Dimension;

public interface IDimensionSupport {

	Dimension getByteString32();

	Dimension getByteString64();

	Dimension getNumberOfScans();

	Dimension getNumberOfScanIons();

	Dimension getInstrumentNumber();

	Dimension getErrorNumber();

	void addVariableCharD2(String varName, Dimension firstDimension, Dimension secondDimension, String content);

	void addVariableDoubleD1(String varName, Dimension firstDimension, double value);

	void addVariableShortD1(String varName, Dimension firstDimension, short value);

	void addVariableIntD1(String varName, Dimension firstDimension, int value);

	// Convenience methods
	void addVariableScanAcquisitionTime();

	void addVariableActualScanNumber();

	void addVariableTotalIntensity();

	void addVariableMassRangeMin();

	void addVariableMassRangeMax();

	void addVariableScanIndex();

	void addVariablePointCount();

	void addVariableScanValues();

	/**
	 * In the array list are all data entries stored that are needed to write
	 * the cdf file.
	 * 
	 * @return
	 */
	ArrayList<IDataEntry> getDataEntries();
}
