/*******************************************************************************
 * Copyright (c) 2017, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.Activator;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_FILTER_PATH_REFERENCE_CHROMATOGRAM = "filterPathReferenceChromatogram";
	public static final String DEF_FILTER_PATH_REFERENCE_CHROMATOGRAM = "";
	public static final String P_FILTER_PATH_ISOTOPE_CHROMATOGRAM = "filterPathIsotopeChromatogram";
	public static final String DEF_FILTER_PATH_ISOTOPE_CHROMATOGRAM = "";
	//
	private static IPreferenceSupplier preferenceSupplier = null;

	public static IPreferenceSupplier INSTANCE() {

		if(preferenceSupplier == null) {
			preferenceSupplier = new PreferenceSupplier();
		}
		return preferenceSupplier;
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_FILTER_PATH_REFERENCE_CHROMATOGRAM, DEF_FILTER_PATH_REFERENCE_CHROMATOGRAM);
		putDefault(P_FILTER_PATH_ISOTOPE_CHROMATOGRAM, DEF_FILTER_PATH_ISOTOPE_CHROMATOGRAM);
	}

	public static String getFilterPathReferenceChromatogram() {

		return INSTANCE().get(P_FILTER_PATH_REFERENCE_CHROMATOGRAM, DEF_FILTER_PATH_REFERENCE_CHROMATOGRAM);
	}

	public static void setFilterPathReferenceChromatogram(String filterPath) {

		INSTANCE().put(P_FILTER_PATH_REFERENCE_CHROMATOGRAM, filterPath);
	}

	public static String getFilterPathIsotopeChromatogram() {

		return INSTANCE().get(P_FILTER_PATH_ISOTOPE_CHROMATOGRAM, DEF_FILTER_PATH_ISOTOPE_CHROMATOGRAM);
	}

	public static void setFilterPathIsotopeChromatogram(String filterPath) {

		INSTANCE().put(P_FILTER_PATH_ISOTOPE_CHROMATOGRAM, filterPath);
	}
}