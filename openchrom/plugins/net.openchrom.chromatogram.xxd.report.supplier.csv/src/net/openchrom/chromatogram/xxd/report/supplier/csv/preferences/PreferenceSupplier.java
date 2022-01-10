/*******************************************************************************
 * Copyright (c) 2018, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.csv.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

import net.openchrom.chromatogram.xxd.report.supplier.csv.Activator;
import net.openchrom.chromatogram.xxd.report.supplier.csv.model.Delimiter;
import net.openchrom.chromatogram.xxd.report.supplier.csv.settings.ChromatogramReportSettings;

public class PreferenceSupplier implements IPreferenceSupplier {

	public static final String P_DELIMITER = "delimiter";
	public static final Delimiter DEF_DELIMITER = Delimiter.COMMA;
	public static final String[][] DELIMITERS = new String[][]{//
			{Delimiter.COMMA.name(), Delimiter.COMMA.label()}, //
			{Delimiter.SEMICOLON.name(), Delimiter.SEMICOLON.label()}, //
			{Delimiter.TAB.name(), Delimiter.TAB.label()} //
	};
	public static final String P_REPORT_REFERENCED_CHROMATOGRAMS = "reportReferencedChromatograms";
	public static final boolean DEF_REPORT_REFERENCED_CHROMATOGRAMS = false;
	//
	private static IPreferenceSupplier preferenceSupplier;

	public static IPreferenceSupplier INSTANCE() {

		if(preferenceSupplier == null) {
			preferenceSupplier = new PreferenceSupplier();
		}
		return preferenceSupplier;
	}

	@Override
	public IScopeContext getScopeContext() {

		return InstanceScope.INSTANCE;
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public Map<String, String> getDefaultValues() {

		Map<String, String> defaultValues = new HashMap<>();
		defaultValues.put(P_DELIMITER, DEF_DELIMITER.toString());
		defaultValues.put(P_REPORT_REFERENCED_CHROMATOGRAMS, Boolean.toString(DEF_REPORT_REFERENCED_CHROMATOGRAMS));
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static ChromatogramReportSettings getReportSettings() {

		ChromatogramReportSettings reportSettings = new ChromatogramReportSettings();
		reportSettings.setDelimiter(getDeliminter());
		reportSettings.setReportReferencedChromatograms(getReportReferencedChromatograms());
		return reportSettings;
	}

	public static Delimiter getDeliminter() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return Delimiter.valueOf(preferences.get(P_DELIMITER, DEF_DELIMITER.name()));
	}

	public static boolean getReportReferencedChromatograms() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REPORT_REFERENCED_CHROMATOGRAMS, DEF_REPORT_REFERENCED_CHROMATOGRAMS);
	}
}
