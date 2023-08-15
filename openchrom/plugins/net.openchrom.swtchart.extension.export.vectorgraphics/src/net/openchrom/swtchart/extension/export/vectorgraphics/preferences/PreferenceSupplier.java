/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.swtchart.extension.export.vectorgraphics.preferences;

import org.eclipse.chemclipse.support.ui.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import net.openchrom.swtchart.extension.export.vectorgraphics.Activator;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;

public class PreferenceSupplier extends AbstractPreferenceInitializer implements IPreferenceSupplier {

	public static final String P_PAGE_SIZE_OPTION = "pageSizeOption";
	public static final String DEF_PAGE_SIZE_OPTION = PageSizeOption.A0_LANDSCAPE.name();
	//
	public static final String P_PATH_IMPORT = "pathImport";
	public static final String DEF_PATH_IMPORT = "";
	public static final String P_PATH_EXPORT = "pathExport";
	public static final String DEF_PATH_EXPORT = "";
	//
	private static IPreferenceSupplier preferenceSupplier = null;

	@Override
	public IPreferenceStore getPreferences() {

		return Activator.getDefault().getPreferenceStore();
	}

	@Override
	public void initializeDefaultPreferences() {

		IPreferenceStore store = INSTANCE().getPreferences();
		//
		store.setDefault(P_PAGE_SIZE_OPTION, DEF_PAGE_SIZE_OPTION);
		store.setDefault(P_PATH_IMPORT, DEF_PATH_IMPORT);
		store.setDefault(P_PATH_EXPORT, DEF_PATH_EXPORT);
	}

	public static IPreferenceSupplier INSTANCE() {

		if(preferenceSupplier == null) {
			preferenceSupplier = new PreferenceSupplier();
		}
		return preferenceSupplier;
	}

	public static PageSizeOption getPageSizeOption() {

		try {
			return PageSizeOption.valueOf(INSTANCE().get(P_PAGE_SIZE_OPTION));
		} catch(Exception e) {
			return PageSizeOption.A0_LANDSCAPE;
		}
	}

	public static void setPageSizeOption(PageSizeOption pageSizeOption) {

		INSTANCE().set(P_PAGE_SIZE_OPTION, pageSizeOption.name());
	}

	public static String getPathImport() {

		return INSTANCE().get(P_PATH_IMPORT);
	}

	public static void setPathImport(String path) {

		INSTANCE().set(P_PATH_IMPORT, path);
	}

	public static String getPathExport() {

		return INSTANCE().get(P_PATH_EXPORT);
	}

	public static void setPathExport(String path) {

		INSTANCE().set(P_PATH_EXPORT, path);
	}
}