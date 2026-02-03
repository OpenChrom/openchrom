/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.muf.converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.logging.core.Logger;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	private static final byte[] MUF_HEADER = "MATLAB 7.3 MAT-file".getBytes();
	private static final int MUF_HEADER_SIZE = MUF_HEADER.length;
	private static final Logger logger = Logger.getLogger(MagicNumberMatcher.class);

	@Override
	public boolean checkFileFormat(File file) {

		if(checkFileExtension(file, ".muf")) {
			byte[] buffer = new byte[MUF_HEADER_SIZE];
			try (InputStream inputStream = Files.newInputStream(file.toPath())) {
				if(inputStream.read(buffer) != MUF_HEADER_SIZE) {
					return false;
				}
			} catch(IOException e) {
				logger.warn(e);
				return false;
			}
			if(Arrays.equals(buffer, MUF_HEADER)) {
				return true;
			}
		}
		return false;
	}
}
