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
package net.openchrom.wsd.converter.supplier.cdf.converter;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.core.AbstractFileContentMatcher;
import org.eclipse.chemclipse.logging.core.Logger;

import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFiles;

public class FileContentMatcher extends AbstractFileContentMatcher {

	private static final Logger logger = Logger.getLogger(FileContentMatcher.class);

	@Override
	public boolean checkFileFormat(File file) {

		NetcdfFile netcdfFile = null;
		try {
			netcdfFile = NetcdfFiles.open(file.getAbsolutePath());

			Attribute type = netcdfFile.findGlobalAttribute("separation_experiment_type");
			if(type != null && type.isString()) {
				if(type.getStringValue().equalsIgnoreCase("Liquid Chromatography") //
						|| type.getStringValue().contains("Liquid Chromatography")) // "Normal Phase Liquid Chromoatgraphy"
				{
					if(netcdfFile.findVariable("mass_values") == null) {
						return true;
					}
				}
			}

			Attribute detectorName = netcdfFile.findGlobalAttribute("detector_name");
			if(detectorName != null && detectorName.isString()) {
				if(detectorName.getStringValue().equals("PDA") || detectorName.getStringValue().contains("DAD") //
						|| detectorName.getStringValue().contains("UV-Vis")) {
					return true;
				}
			}

			Attribute detectorUnit = netcdfFile.findGlobalAttribute("detector_unit");
			if(detectorUnit != null && detectorUnit.isString()) {
				if(detectorUnit.getStringValue().equals("mAU") || detectorUnit.getStringValue().equals("AU") || detectorUnit.getStringValue().equals("A.U.")) {
					return true;
				}
			}
		} catch(IOException e) {
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
		return false;
	}
}
