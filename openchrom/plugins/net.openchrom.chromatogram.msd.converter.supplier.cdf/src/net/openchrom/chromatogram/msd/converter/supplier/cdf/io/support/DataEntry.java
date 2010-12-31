/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
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
