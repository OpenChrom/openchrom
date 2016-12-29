/*******************************************************************************
 * Copyright (c) 2014, 2016 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.io.support;

import java.util.ArrayList;

import ucar.nc2.Dimension;

public interface IDimensionSupport {

	Dimension getByteString32();

	Dimension getByteString64();

	Dimension getNumberOfScans();

	Dimension getInstrumentNumber();

	Dimension getErrorNumber();

	void addVariableCharD2(String varName, Dimension firstDimension, Dimension secondDimension, String content);

	void addVariableDoubleD1(String varName, Dimension firstDimension, double value);

	void addVariableShortD1(String varName, Dimension firstDimension, short value);

	void addVariableIntD1(String varName, Dimension firstDimension, int value);

	void addVariableOrdinateValues();

	/**
	 * In the array list are all data entries stored that are needed to write
	 * the cdf file.
	 * 
	 * @return
	 */
	ArrayList<IDataEntry> getDataEntries();
}
