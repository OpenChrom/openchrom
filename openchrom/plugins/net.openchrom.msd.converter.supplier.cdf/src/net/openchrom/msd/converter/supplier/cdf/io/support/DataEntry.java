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

import ucar.ma2.Array;

public class DataEntry implements IDataEntry {

	private String varName;
	private Array values;

	public DataEntry(String varName, Array values) {
		this.varName = varName;
		this.values = values;
	}

	@Override
	public String getVarName() {

		return varName;
	}

	@Override
	public void setVarName(String varName) {

		this.varName = varName;
	}

	@Override
	public Array getValues() {

		return values;
	}

	@Override
	public void setValues(Array values) {

		this.values = values;
	}
}
