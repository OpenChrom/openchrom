/*******************************************************************************
 * Copyright (c) 2016, 2024 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.msd.process.supplier.cms.Activator;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_PATH_LIBRARY_EXPLORER = "pathLibraryExplorer";
	public static final String DEF_PATH_LIBRARY_EXPLORER = "";
	public static final String P_PATH_CMS_SCAN_SPECTRA = "pathCmsScanSpectra";
	public static final String DEF_PATH_CMS_SCAN_SPECTRA = "";
	public static final String P_PATH_CMS_LIBRARY_SPECTRA = "pathCmsLibrarySpectra";
	public static final String DEF_PATH_CMS_LIBRARY_SPECTRA = "";

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_PATH_LIBRARY_EXPLORER, DEF_PATH_LIBRARY_EXPLORER);
		putDefault(P_PATH_CMS_SCAN_SPECTRA, DEF_PATH_CMS_SCAN_SPECTRA);
		putDefault(P_PATH_CMS_LIBRARY_SPECTRA, DEF_PATH_CMS_LIBRARY_SPECTRA);
	}

	public static String getPathLibraryExplorer() {

		return INSTANCE().get(P_PATH_LIBRARY_EXPLORER, DEF_PATH_LIBRARY_EXPLORER);
	}

	public static String getPathCmsLibrarySpectra() {

		return INSTANCE().get(P_PATH_CMS_LIBRARY_SPECTRA, DEF_PATH_CMS_LIBRARY_SPECTRA);
	}

	public static String getPathCmsScanSpectra() {

		return INSTANCE().get(P_PATH_CMS_SCAN_SPECTRA, DEF_PATH_CMS_SCAN_SPECTRA);
	}

	public static void setPathLibraryExplorer(String path) {

		INSTANCE().put(P_PATH_LIBRARY_EXPLORER, path);
	}

	public static void setPathCmsLibrarySpectra(String path) {

		INSTANCE().put(P_PATH_CMS_LIBRARY_SPECTRA, path);
	}

	public static void setPathCmsScanSpectra(String path) {

		INSTANCE().put(P_PATH_CMS_SCAN_SPECTRA, path);
	}
}