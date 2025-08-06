/*******************************************************************************
 * Copyright (c) 2017, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.osgi.framework.FrameworkUtil;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_FILTER_PATH_REFERENCE_CHROMATOGRAM = "filterPathReferenceChromatogram";
	public static final String DEF_FILTER_PATH_REFERENCE_CHROMATOGRAM = "";
	public static final String P_FILTER_PATH_ISOTOPE_CHROMATOGRAM = "filterPathIsotopeChromatogram";
	public static final String DEF_FILTER_PATH_ISOTOPE_CHROMATOGRAM = "";

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return FrameworkUtil.getBundle(PreferenceSupplier.class).getSymbolicName();
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