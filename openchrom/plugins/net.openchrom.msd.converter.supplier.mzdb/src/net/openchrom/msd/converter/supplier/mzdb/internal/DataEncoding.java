/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mzdb.internal;

import java.nio.ByteOrder;

public class DataEncoding {

	private DataMode dataMode;
	private String compression;
	private ByteOrder byteOrder;
	private Precision mzPrecision;
	private Precision intensityPrecision;

	public DataMode getDataMode() {

		return dataMode;
	}

	public void setDataMode(DataMode dataMode) {

		this.dataMode = dataMode;
	}

	public String getCompression() {

		return compression;
	}

	public void setCompression(String compression) {

		this.compression = compression;
	}

	public ByteOrder getByteOrder() {

		return byteOrder;
	}

	public void setByteOrder(ByteOrder byteOrder) {

		this.byteOrder = byteOrder;
	}

	public Precision getMzPrecision() {

		return mzPrecision;
	}

	public void setMzPrecision(Precision mzPrecision) {

		this.mzPrecision = mzPrecision;
	}

	public Precision getIntensityPrecision() {

		return intensityPrecision;
	}

	public void setIntensityPrecision(Precision intensityPrecision) {

		this.intensityPrecision = intensityPrecision;
	}
}
