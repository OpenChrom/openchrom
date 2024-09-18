/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.io.support;

import java.io.IOException;

import ucar.ma2.ArrayFloat;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Variable;
import ucar.nc2.write.NetcdfFormatWriter;
import ucar.nc2.write.NetcdfFormatWriter.Builder;

public class VariableSupport {

	private VariableSupport() {

	}

	public static void defineFloatVariable(Builder builder, String name) {

		builder.getRootGroup().addVariable(Variable.builder() //
				.setName(name) //
				.setDataType(DataType.FLOAT) //
				.setParentGroupBuilder(builder.getRootGroup()) //
				.setIsScalar());
	}

	public static void writeScalarFloat(NetcdfFormatWriter writer, String variable, Float value) throws IOException, InvalidRangeException {

		ArrayFloat.D0 scalarValue = new ArrayFloat.D0();
		scalarValue.set(value);
		writer.write(variable, scalarValue);
	}
}
