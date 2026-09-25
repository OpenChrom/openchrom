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
package net.openchrom.chromatogram.xxd.report.supplier.masc.escape.preferences;

import java.io.File;

import org.eclipse.chemclipse.chromatogram.xxd.report.settings.IChromatogramReportSettings;
import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.osgi.framework.FrameworkUtil;

import net.openchrom.chromatogram.xxd.report.supplier.masc.escape.settings.ChromatogramReportSettings;

public class PreferenceSupplier extends AbstractPreferenceSupplier {

	public static final String P_TEMPLATE = "EscapeExcelTemplateFile";
	public static final String DEF_TEMPLATE = "";

	public static final String P_REPORT = "EscapeExcelReportFile";
	public static final String DEF_REFPORT = "";

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return FrameworkUtil.getBundle(PreferenceSupplier.class).getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_TEMPLATE, DEF_TEMPLATE);
		putDefault(P_REPORT, DEF_REFPORT);
	}

	public static IChromatogramReportSettings getReportSettings() {

		ChromatogramReportSettings reportSettings = new ChromatogramReportSettings();
		reportSettings.setTemplate(getTemplate());
		reportSettings.setExportFolder(getReport());
		return reportSettings;
	}

	public static File getTemplate() {

		return new File(INSTANCE().get(P_TEMPLATE, DEF_TEMPLATE));
	}

	public static void setTemplate(File file) {

		INSTANCE().put(P_TEMPLATE, file.getAbsolutePath());
	}

	public static File getReport() {

		return new File(INSTANCE().get(P_REPORT, DEF_TEMPLATE));
	}

	public static void setReport(File file) {

		INSTANCE().put(P_REPORT, file.getAbsolutePath());
	}
}