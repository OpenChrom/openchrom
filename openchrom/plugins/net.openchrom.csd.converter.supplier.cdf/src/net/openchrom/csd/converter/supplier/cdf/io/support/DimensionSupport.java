/*******************************************************************************
 * Copyright (c) 2014, 2024 Lablicate GmbH.
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

import java.util.ArrayList;

import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;

import ucar.ma2.ArrayChar;
import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayInt;
import ucar.ma2.ArrayShort;
import ucar.ma2.DataType;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.write.NetcdfFormatWriter.Builder;

public class DimensionSupport implements IDimensionSupport {

	public final int NULL_VALUE = 0;
	public final double NULL_VALUE_DOUBLE = -9999.0d;
	public final short NULL_VALUE_SHORT = -9999;
	public final float NULL_VALUE_TIME = 9.96921E36f;
	private ArrayList<IDataEntry> dataEntries;
	private Builder builder;
	private IChromatogramCSD chromatogram;
	private Dimension byteString32;
	private Dimension byteString64;
	private Dimension numberOfScans;
	private Dimension instrumentNumber;
	private Dimension errorNumber;

	public DimensionSupport(Builder builder, IChromatogramCSD chromatogram) {

		dataEntries = new ArrayList<>();
		this.builder = builder;
		this.chromatogram = chromatogram;
		initializeDimensions();
	}

	private void initializeDimensions() {

		builder.addDimension(CDFConstants.DIMENSION_2_BYTE_STRING, 2);
		builder.addDimension(CDFConstants.DIMENSION_4_BYTE_STRING, 4);
		builder.addDimension(CDFConstants.DIMENSION_8_BYTE_STRING, 8);
		builder.addDimension(CDFConstants.DIMENSION_16_BYTE_STRING, 16);
		// ----------
		byteString32 = builder.addDimension(CDFConstants.DIMENSION_32_BYTE_STRING, 32);
		byteString64 = builder.addDimension(CDFConstants.DIMENSION_64_BYTE_STRING, 64);
		// ----------
		builder.addDimension(CDFConstants.DIMENSION_128_BYTE_STRING, 128);
		builder.addDimension(CDFConstants.DIMENSION_255_BYTE_STRING, 255);
		// ----------
		errorNumber = builder.addDimension(CDFConstants.DIMENSION_ERROR_NUMBER, 1);
		numberOfScans = builder.addDimension(CDFConstants.DIMENSION_POINT_NUMBER, chromatogram.getNumberOfScans());
		// ----------
	}

	@Override
	public void addVariableCharD2(String varName, Dimension firstDimension, Dimension secondDimension, String content) {

		ArrayList<Dimension> dimension = new ArrayList<>();
		dimension.add(firstDimension);
		dimension.add(secondDimension);
		builder.addVariable(varName, DataType.CHAR, dimension);
		ArrayChar.D2 values = new ArrayChar.D2(firstDimension.getLength(), secondDimension.getLength());
		values.setString(0, content);
		dataEntries.add(new DataEntry(varName, values));
	}

	@Override
	public void addVariableDoubleD1(String varName, Dimension firstDimension, double value) {

		ArrayList<Dimension> dimension = new ArrayList<>();
		dimension.add(firstDimension);
		builder.addVariable(varName, DataType.DOUBLE, dimension);
		ArrayDouble.D1 values = new ArrayDouble.D1(firstDimension.getLength());
		for(int i = 0; i < firstDimension.getLength(); i++) {
			values.set(i, value);
		}
		dataEntries.add(new DataEntry(varName, values));
	}

	@Override
	public void addVariableShortD1(String varName, Dimension firstDimension, short value) {

		ArrayList<Dimension> dimension = new ArrayList<>();
		dimension.add(firstDimension);
		builder.addVariable(varName, DataType.SHORT, dimension);
		ArrayShort.D1 values = new ArrayShort.D1(firstDimension.getLength(), false);
		for(int i = 0; i < firstDimension.getLength(); i++) {
			values.set(i, value);
		}
		dataEntries.add(new DataEntry(varName, values));
	}

	@Override
	public void addVariableIntD1(String varName, Dimension firstDimension, int value) {

		ArrayList<Dimension> dimension = new ArrayList<>();
		dimension.add(firstDimension);
		builder.addVariable(varName, DataType.INT, dimension);
		ArrayInt.D1 values = new ArrayInt.D1(firstDimension.getLength(), false);
		for(int i = 0; i < firstDimension.getLength(); i++) {
			values.set(i, value);
		}
		dataEntries.add(new DataEntry(varName, values));
	}

	@Override
	public void addVariableOrdinateValues() {

		String varName = CDFConstants.VARIABLE_ORDINATE_VALUES;
		ArrayList<Dimension> dimension = new ArrayList<>();
		dimension.add(numberOfScans);
		builder.addVariable(varName, DataType.DOUBLE, dimension);
		ArrayDouble.D1 values = new ArrayDouble.D1(numberOfScans.getLength());
		for(int i = 0; i < numberOfScans.getLength(); i++) {
			values.set(i, chromatogram.getScan(i + 1).getTotalSignal());
		}
		builder.addAttribute(new Attribute("units", "Arbitrary Intensity Units"));
		dataEntries.add(new DataEntry(varName, values));
	}

	@Override
	public ArrayList<IDataEntry> getDataEntries() {

		return dataEntries;
	}

	@Override
	public Dimension getByteString32() {

		return byteString32;
	}

	@Override
	public Dimension getByteString64() {

		return byteString64;
	}

	@Override
	public Dimension getNumberOfScans() {

		return numberOfScans;
	}

	@Override
	public Dimension getInstrumentNumber() {

		return instrumentNumber;
	}

	@Override
	public Dimension getErrorNumber() {

		return errorNumber;
	}
}
