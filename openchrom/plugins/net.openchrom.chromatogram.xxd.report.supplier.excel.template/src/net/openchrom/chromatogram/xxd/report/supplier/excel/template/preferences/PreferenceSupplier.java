/*******************************************************************************
 * Copyright (c) 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - modular placeholder support
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.preferences;

import java.io.File;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.chromatogram.xxd.report.supplier.excel.template.Activator;
import net.openchrom.chromatogram.xxd.report.supplier.excel.template.settings.ChromatogramReportSettings;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_TEMPLATE = "excelReportTemplateFile";
	public static final String DEF_TEMPLATE = ".xltx";
	public static final String P_LIST_PATH_EXPORT = "listPathExport";
	public static final String DEF_LIST_PATH_EXPORT = "";

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_TEMPLATE, DEF_TEMPLATE);
		putDefault(P_LIST_PATH_EXPORT, DEF_LIST_PATH_EXPORT);
	}

	public static ChromatogramReportSettings getReportSettings() {

		ChromatogramReportSettings reportSettings = new ChromatogramReportSettings();
		reportSettings.setTemplate(getTemplate());
		return reportSettings;
	}

	public static File getTemplate() {

		return new File(INSTANCE().get(P_TEMPLATE, DEF_TEMPLATE));
	}

	public static void setTemplate(File file) {

		INSTANCE().put(P_TEMPLATE, file.getAbsolutePath());
	}

	public static String getListPathExport() {

		return INSTANCE().get(P_LIST_PATH_EXPORT);
	}

	public static void setListPathExport(String filterPath) {

		INSTANCE().put(P_LIST_PATH_EXPORT, filterPath);
	}
}