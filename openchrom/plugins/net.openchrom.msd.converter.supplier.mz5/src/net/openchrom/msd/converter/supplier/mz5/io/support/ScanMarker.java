/*******************************************************************************
 * Copyright (c) 2021 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mz5.io.support;

public class ScanMarker implements IScanMarker {

	private int start;
	private int offset;

	public ScanMarker(int start, int offset) {

		this.start = start;
		this.offset = offset;
	}

	@Override
	public int getStart() {

		return start;
	}

	@Override
	public void setStart(int start) {

		this.start = start;
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
