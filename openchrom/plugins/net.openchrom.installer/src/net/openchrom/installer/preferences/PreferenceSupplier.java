/*******************************************************************************
 * Copyright (c) 2023, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - preference initializer
 *******************************************************************************/
package net.openchrom.installer.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.osgi.framework.FrameworkUtil;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_PROPRIETARY_CONVERTERS = "proprietaryConverters";
	public static final String DEF_PROPRIETARY_CONVERTERS = MessageDialogWithToggle.ALWAYS;

	public static final String P_FILTER_PATH_IMPORT = "filterPathImport";
	public static final String DEF_FILTER_PATH_IMPORT = "";
	public static final String P_FILTER_PATH_EXPORT = "filterPathExport";
	public static final String DEF_FILTER_PATH_EXPORT = "";

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return FrameworkUtil.getBundle(PreferenceSupplier.class).getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_PROPRIETARY_CONVERTERS, DEF_PROPRIETARY_CONVERTERS);
		putDefault(P_FILTER_PATH_IMPORT, DEF_FILTER_PATH_IMPORT);
		putDefault(P_FILTER_PATH_EXPORT, DEF_FILTER_PATH_EXPORT);
	}

	public static String getProprietaryConverters() {

		return INSTANCE().get(P_PROPRIETARY_CONVERTERS, DEF_PROPRIETARY_CONVERTERS);
	}

	public static void setProprietaryConverters(String selection) {

		INSTANCE().set(P_PROPRIETARY_CONVERTERS, selection);
	}

	public static String getFilterPathImport() {

		return INSTANCE().get(P_FILTER_PATH_IMPORT, DEF_FILTER_PATH_IMPORT);
	}

	public static void setFilterPathImport(String path) {

		INSTANCE().put(P_FILTER_PATH_IMPORT, path);
	}

	public static String getFilterPathExport() {

		return INSTANCE().get(P_FILTER_PATH_EXPORT, DEF_FILTER_PATH_EXPORT);
	}

	public static void setFilterPathExport(String path) {

		INSTANCE().put(P_FILTER_PATH_EXPORT, path);
	}
}