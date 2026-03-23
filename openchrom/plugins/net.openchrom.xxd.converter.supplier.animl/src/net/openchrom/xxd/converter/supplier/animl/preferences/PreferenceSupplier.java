/*******************************************************************************
 * Copyright (c) 2021, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.converter.supplier.animl.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.osgi.framework.FrameworkUtil;

import net.openchrom.xxd.converter.supplier.animl.converter.Format;

public class PreferenceSupplier extends AbstractPreferenceSupplier {

	public static final String P_CHROMATOGRAM_VERSION_SAVE = "chromatogramVersionSave";
	public static final String DEF_CHROMATOGRAM_VERSION_SAVE = Format.VERSION_LATEST;

	public static final String P_CHROMATOGRAM_SAVE_ENCODED = "chromatogramEncoded";
	public static final boolean DEF_CHROMATOGRAM_SAVE_ENCODED = true;

	public static final String P_MASS_SPECTRUM_SAVE_ENCODED = "massSpectrumEncoded";
	public static final boolean DEF_MASS_SPECTRUM_SAVE_ENCODED = true;

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return FrameworkUtil.getBundle(PreferenceSupplier.class).getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_CHROMATOGRAM_VERSION_SAVE, DEF_CHROMATOGRAM_VERSION_SAVE);
		putDefault(P_CHROMATOGRAM_SAVE_ENCODED, Boolean.toString(DEF_CHROMATOGRAM_SAVE_ENCODED));
		putDefault(P_MASS_SPECTRUM_SAVE_ENCODED, Boolean.toString(DEF_MASS_SPECTRUM_SAVE_ENCODED));
	}

	public static String getChromatogramVersionSave() {

		return INSTANCE().get(P_CHROMATOGRAM_VERSION_SAVE, DEF_CHROMATOGRAM_VERSION_SAVE);
	}

	public static String[][] getChromatogramVersions() {

		String[][] elements = new String[1][2];
		elements[0][0] = Format.ANIML_V_090;
		elements[0][1] = Format.ANIML_V_090;
		return elements;
	}

	public static boolean getChromatogramSaveEncoded() {

		return INSTANCE().getBoolean(P_CHROMATOGRAM_SAVE_ENCODED, DEF_CHROMATOGRAM_SAVE_ENCODED);
	}

	public static boolean getMassSpectrumSaveEncoded() {

		return INSTANCE().getBoolean(P_MASS_SPECTRUM_SAVE_ENCODED, DEF_MASS_SPECTRUM_SAVE_ENCODED);
	}
}