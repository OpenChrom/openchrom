/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
package net.openchrom.wsd.converter.supplier.arw.core;

import java.io.File;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher {

	private static final byte[] MAGIC_CODE = new byte[]{0x57, 0x61, 0x76, 0x65, 0x6C, 0x65, 0x6E, 0x67, 0x74, 0x68}; // Wavelength

	@Override
	public boolean checkFileFormat(File file) {

		if(checkFileExtension(file, ".arw")) {
			return checkMagicCode(file, MAGIC_CODE);
		}

		return false;
	}
}
