/*******************************************************************************
 * Copyright (c) 2017, 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.microbems.pkf.converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.logging.core.Logger;

public class MagicNumberMatcherDatabase extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	private static final byte[] PKF_HEADER = "MATLAB 5.0 MAT-file".getBytes();
	private static final int PKF_HEADER_SIZE = PKF_HEADER.length;
	private static final Logger logger = Logger.getLogger(MagicNumberMatcherDatabase.class);

	@Override
	public boolean checkFileFormat(File file) {

		if(checkFileExtension(file, ".pkf")) {
			byte[] buffer = new byte[PKF_HEADER_SIZE];
			try (InputStream inputStream = Files.newInputStream(file.toPath())) {
				if(inputStream.read(buffer) != PKF_HEADER_SIZE) {
					return false;
				}
			} catch(IOException e) {
				logger.warn(e);
				return false;
			}
			if(Arrays.equals(buffer, PKF_HEADER)) {
				return true;
			}
		}
		return false;
	}
}
