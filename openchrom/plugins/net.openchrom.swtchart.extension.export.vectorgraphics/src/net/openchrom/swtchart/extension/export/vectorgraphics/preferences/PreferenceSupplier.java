/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
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

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.swtchart.extension.export.vectorgraphics.Activator;
import net.openchrom.swtchart.extension.export.vectorgraphics.model.PageSizeOption;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_PAGE_SIZE_OPTION = "pageSizeOption";
	public static final String DEF_PAGE_SIZE_OPTION = PageSizeOption.FULL_LANDSCAPE.name();
	//
	public static final String P_PATH_IMPORT = "pathImport";
	public static final String DEF_PATH_IMPORT = "";
	public static final String P_PATH_EXPORT = "pathExport";
	public static final String DEF_PATH_EXPORT = "";
	//
	private static IPreferenceSupplier preferenceSupplier = null;

	public static IPreferenceSupplier INSTANCE() {

		if(preferenceSupplier == null) {
			preferenceSupplier = new PreferenceSupplier();
		}
		//
		return preferenceSupplier;
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_PAGE_SIZE_OPTION, DEF_PAGE_SIZE_OPTION);
		putDefault(P_PATH_IMPORT, DEF_PATH_IMPORT);
		putDefault(P_PATH_EXPORT, DEF_PATH_EXPORT);
	}

	public static PageSizeOption getPageSizeOption() {

		try {
			return PageSizeOption.valueOf(INSTANCE().get(P_PAGE_SIZE_OPTION, DEF_PAGE_SIZE_OPTION));
		} catch(Exception e) {
			return PageSizeOption.FULL_LANDSCAPE;
		}
	}

	public static void setPageSizeOption(PageSizeOption pageSizeOption) {

		INSTANCE().put(P_PAGE_SIZE_OPTION, pageSizeOption.name());
	}

	public static String getPathImport() {

		return INSTANCE().get(P_PATH_IMPORT, DEF_PATH_IMPORT);
	}

	public static void setPathImport(String path) {

		INSTANCE().put(P_PATH_IMPORT, path);
	}

	public static String getPathExport() {

		return INSTANCE().get(P_PATH_EXPORT, DEF_PATH_EXPORT);
	}

	public static void setPathExport(String path) {

		INSTANCE().put(P_PATH_EXPORT, path);
	}
}