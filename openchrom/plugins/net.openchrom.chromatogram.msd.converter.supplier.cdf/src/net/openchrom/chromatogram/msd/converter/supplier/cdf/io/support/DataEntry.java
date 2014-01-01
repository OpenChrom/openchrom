/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.io.support;

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
