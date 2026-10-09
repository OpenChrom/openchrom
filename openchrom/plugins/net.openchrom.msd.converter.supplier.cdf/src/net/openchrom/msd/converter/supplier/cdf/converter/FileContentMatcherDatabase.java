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
package net.openchrom.msd.converter.supplier.cdf.converter;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;
import org.eclipse.chemclipse.logging.core.Logger;

import net.openchrom.msd.converter.supplier.cdf.io.support.AttributeSupport;
import net.openchrom.msd.converter.supplier.cdf.io.support.CDFConstants;

import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFiles;

/**
 * Accepts the libraries only, the chromatographic runs are handled by
 * {@link FileContentMatcher}.
 */
public class FileContentMatcherDatabase extends AbstractFileContentMatcher {

	private static final Logger logger = Logger.getLogger(FileContentMatcherDatabase.class);

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		NetcdfFile netcdfFile = null;
		try {
			netcdfFile = NetcdfFiles.open(file.getAbsolutePath());
			if(netcdfFile.findVariable(CDFConstants.VARIABLE_MASS_VALUES) != null && AttributeSupport.isLibrary(netcdfFile)) {
				isValidFormat = true;
			}
		} catch(Exception e) {
			logger.warn(e);
		} finally {
			if(netcdfFile != null) {
				try {
					netcdfFile.close();
				} catch(IOException e) {
					logger.warn(e);
				}
			}
		}
		return isValidFormat;
	}
}
