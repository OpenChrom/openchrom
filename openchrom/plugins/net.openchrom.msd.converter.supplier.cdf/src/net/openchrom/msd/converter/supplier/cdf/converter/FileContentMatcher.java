/*******************************************************************************
 * Copyright (c) 2016, 2023 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.converter;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;
import org.eclipse.chemclipse.converter.core.IFileContentMatcher;
import org.eclipse.chemclipse.logging.core.Logger;

import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFiles;

public class FileContentMatcher extends AbstractFileContentMatcher implements IFileContentMatcher {

	private static final Logger logger = Logger.getLogger(FileContentMatcher.class);
	private static final String VARIABLE_MASS_VALUES = "mass_values";

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		NetcdfFile netcdfFile = null;
		try {
			netcdfFile = NetcdfFiles.open(file.getAbsolutePath());
			/*
			 * If mass values are stored, assume that it is a MSD file.
			 */
			if(netcdfFile.findVariable(VARIABLE_MASS_VALUES) != null) {
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