/*******************************************************************************
 * Copyright (c) 2016, 2026 Matthias Mailänder, Philip Wenig.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.core;

import java.io.File;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher {

	// abbreviation ABIF stands for Applied Biosystems, Inc. Format
	private static final byte[] MAGIC_CODE = new byte[]{(byte)'A', (byte)'B', (byte)'I', (byte)'F'};

	@Override
	public boolean checkFileFormat(File file) {

		if(!checkFileExtension(file, ".ab1")) {
			return false;
		}
		return checkMagicCode(file, MAGIC_CODE);
	}
}
