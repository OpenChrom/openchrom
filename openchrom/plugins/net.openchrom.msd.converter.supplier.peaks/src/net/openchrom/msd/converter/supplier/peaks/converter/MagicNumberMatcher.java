/*******************************************************************************
 * Copyright (c) 2017, 2021 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.peaks.converter;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.logging.core.Logger;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	private static final Logger logger = Logger.getLogger(MagicNumberMatcher.class);

	@Override
	public boolean checkFileFormat(File file) {

		if(checkFileExtension(file, ".zip")) {
			try (ZipFile zipFile = new ZipFile(file)) {
				Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
				while(zipEntries.hasMoreElements()) {
					ZipEntry zipEntry = zipEntries.nextElement();
					String name = zipEntry.getName();
					if(name.endsWith(".peaks")) {
						return true;
					}
				}
			} catch(Exception e) {
				logger.warn(e);
				return false;
			}
		}
		return false;
	}
}
