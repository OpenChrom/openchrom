/*******************************************************************************
 * Copyright (c) 2010, 2026 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.peak.detector.supplier.amdis.preferences;

import java.io.File;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.osgi.framework.FrameworkUtil;

public class PreferenceSupplier extends AbstractPreferenceSupplier {

	private static final Logger logger = Logger.getLogger(PreferenceSupplier.class);

	public static final String IDENTIFIER = "AMDIS Identifier";

	public static final String AMDIS_EXECUTABLE = "AMDIS32$.exe";

	public static final String P_MAC_WINE_BINARY = "macWineBinary";
	public static final String DEF_MAC_WINE_BINARY = "/Applications/Wine.app";

	public static final String P_AMDIS_APPLICATION_PATH = "amdisApplication";
	public static final String DEF_AMDIS_APPLICATION_PATH = "";
	public static final String P_AMDIS_TMP_PATH = "amdisTmpPath";
	public static final String DEF_AMDIS_TMP_PATH = "";

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return FrameworkUtil.getBundle(PreferenceSupplier.class).getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_MAC_WINE_BINARY, DEF_MAC_WINE_BINARY);
	}

	public static File getInstallationFolder() {

		return getFolder(P_AMDIS_APPLICATION_PATH);
	}

	public static File getDataFolder() {

		return getFolder(P_AMDIS_TMP_PATH);
	}

	public static String getMacWineBinary() {

		return INSTANCE().get(P_MAC_WINE_BINARY, DEF_MAC_WINE_BINARY);
	}

	public static File getFolder(String key) {

		try {
			String path = INSTANCE().get(key, "");
			if(path != null && !path.isEmpty()) {

				File file = new File(path);
				if(file.isFile()) {
					file.getParentFile();
				} else {
					return file;
				}
			}
		} catch(Exception e) {
			logger.warn(e);
		}

		return null;
	}
}