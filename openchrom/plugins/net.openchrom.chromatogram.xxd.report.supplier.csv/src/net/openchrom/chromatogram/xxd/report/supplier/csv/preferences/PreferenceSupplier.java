/*******************************************************************************
 * Copyright (c) 2018, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 * Philip Wenig - preference initializer
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.preferences;

import org.eclipse.chemclipse.model.settings.Delimiter;
import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.chromatogram.xxd.report.supplier.csv.Activator;
import net.openchrom.chromatogram.xxd.report.supplier.csv.settings.ChromatogramReportSettings;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_DELIMITER = "delimiter";
	public static final Delimiter DEF_DELIMITER = Delimiter.COMMA;
	public static final String[][] DELIMITERS = new String[][]{//
			{Delimiter.COMMA.name(), Delimiter.COMMA.label()}, //
			{Delimiter.SEMICOLON.name(), Delimiter.SEMICOLON.label()}, //
			{Delimiter.TAB.name(), Delimiter.TAB.label()} //
	};
	public static final String P_REPORT_REFERENCED_CHROMATOGRAMS = "reportReferencedChromatograms";
	public static final boolean DEF_REPORT_REFERENCED_CHROMATOGRAMS = false;

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_DELIMITER, DEF_DELIMITER.toString());
		putDefault(P_REPORT_REFERENCED_CHROMATOGRAMS, Boolean.toString(DEF_REPORT_REFERENCED_CHROMATOGRAMS));
	}

	public static ChromatogramReportSettings getReportSettings() {

		ChromatogramReportSettings reportSettings = new ChromatogramReportSettings();
		reportSettings.setDelimiter(getDeliminter());
		reportSettings.setReportReferencedChromatograms(getReportReferencedChromatograms());
		return reportSettings;
	}

	public static Delimiter getDeliminter() {

		return Delimiter.valueOf(INSTANCE().get(P_DELIMITER, DEF_DELIMITER.name()));
	}

	public static boolean getReportReferencedChromatograms() {

		return INSTANCE().getBoolean(P_REPORT_REFERENCED_CHROMATOGRAMS, DEF_REPORT_REFERENCED_CHROMATOGRAMS);
	}
}