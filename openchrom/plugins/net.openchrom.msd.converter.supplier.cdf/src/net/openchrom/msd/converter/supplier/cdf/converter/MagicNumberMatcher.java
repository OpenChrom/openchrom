/*******************************************************************************
 * Copyright (c) 2016, 2021 Lablicate GmbH.
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

import org.eclipse.chemclipse.converter.core.AbstractMagicNumberMatcher;
import org.eclipse.chemclipse.converter.core.IMagicNumberMatcher;
import org.eclipse.chemclipse.logging.core.Logger;

import ucar.nc2.NetcdfFile;

public class MagicNumberMatcher extends AbstractMagicNumberMatcher implements IMagicNumberMatcher {

	private static final Logger logger = Logger.getLogger(MagicNumberMatcher.class);
	private static final String VARIABLE_MASS_VALUES = "mass_values";

	@Override
	public boolean checkFileFormat(File file) {

		boolean isValidFormat = false;
		if(checkFileExtension(file, ".cdf")) {
			NetcdfFile ncfile = null;
			try {
				ncfile = NetcdfFile.open(file.getAbsolutePath());
				if(ncfile != null) {
					/*
					 * If mass values are stored, assume that it is a MSD file.
					 */
					if(ncfile.findVariable(VARIABLE_MASS_VALUES) != null) {
						isValidFormat = true;
					}
				}
			} catch(Exception e) {
				logger.warn(e);
			} finally {
				if(ncfile != null) {
					try {
						ncfile.close();
					} catch(IOException e) {
						logger.warn(e);
					}
				}
			}
		}
		return isValidFormat;
	}
}