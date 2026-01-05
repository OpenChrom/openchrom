/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.cdf.io.support;

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
