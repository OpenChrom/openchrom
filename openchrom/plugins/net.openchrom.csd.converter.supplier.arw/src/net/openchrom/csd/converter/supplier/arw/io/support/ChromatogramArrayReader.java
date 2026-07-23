/*******************************************************************************
 * Copyright (c) 2014, 2026 Lablicate GmbH.
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
package net.openchrom.csd.converter.supplier.arw.io.support;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.io.support.AbstractArrayReader;

public class ChromatogramArrayReader extends AbstractArrayReader implements IChromatogramArrayReader {

	public ChromatogramArrayReader(File file) throws IOException {

		super(file);
	}

	@Override
	public long readFileFormat() {

		return read4BULongBE();
	}

	@Override
	public int readStartRetentionTime() {

		return (int)read4BFloatBE();
	}

	@Override
	public int readStopRetentionTime() {

		return (int)read4BFloatBE();
	}
}
