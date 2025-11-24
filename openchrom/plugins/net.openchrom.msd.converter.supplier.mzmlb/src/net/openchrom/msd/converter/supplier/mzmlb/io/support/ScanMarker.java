/*******************************************************************************
 * Copyright (c) 2021, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.mzmlb.io.support;

public class ScanMarker implements IScanMarker {

	private int length;
	private int offset;

	public ScanMarker(int length, int offset) {

		this.length = length;
		this.offset = offset;
	}

	@Override
	public int getLength() {

		return length;
	}

	@Override
	public void setLength(int length) {

		this.length = length;
	}

	@Override
	public int getOffset() {

		return offset;
	}

	@Override
	public void setOffset(int offset) {

		this.offset = offset;
	}
}
