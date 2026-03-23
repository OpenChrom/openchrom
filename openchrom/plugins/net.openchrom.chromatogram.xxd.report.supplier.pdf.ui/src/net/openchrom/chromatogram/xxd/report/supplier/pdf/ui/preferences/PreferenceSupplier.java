/*******************************************************************************
 * Copyright (c) 2014, 2026 Lablicate GmbH.
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
package net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.Activator;
import net.openchrom.chromatogram.xxd.report.supplier.pdf.ui.settings.ChromatogramReportSettings;

public class PreferenceSupplier extends AbstractPreferenceSupplier {

	/*
	 * Generic
	 */
	public static final String P_NUMBER_IMAGE_PAGES = "numberImagePages";
	public static final int DEF_NUMBER_IMAGE_PAGES = 5;

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getDefault().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_NUMBER_IMAGE_PAGES, Integer.toString(DEF_NUMBER_IMAGE_PAGES));
	}

	public static int getNumberImagePages() {

		return INSTANCE().getInteger(P_NUMBER_IMAGE_PAGES, DEF_NUMBER_IMAGE_PAGES);
	}

	public static ChromatogramReportSettings getReportSettings() {

		ChromatogramReportSettings settings = new ChromatogramReportSettings();
		// TODO
		return settings;
	}
}
