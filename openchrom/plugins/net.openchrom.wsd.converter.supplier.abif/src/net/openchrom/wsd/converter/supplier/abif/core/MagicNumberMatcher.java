/*******************************************************************************
 * Copyright (c) 2016, 2023 Matthias Mailänder, Dr. Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.abif.core;

import java.io.File;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

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
