/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.preferences.ChemistryPreferences;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.preferences.IdentifierPreferences;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {

		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		preferenceStore.setDefault(ChemistryPreferences.P_ISOTOPE_SET, ChemistryPreferences.DEF_ISOTOPE_SET);
		preferenceStore.setDefault(ChemistryPreferences.P_ISOTOPE_ITERATION_DEPTH, ChemistryPreferences.DEF_ISOTOPE_ITERATION_DEPTH);
		preferenceStore.setDefault(ChemistryPreferences.P_USER_DEFINED_ISOTOPES, ChemistryPreferences.DEF_USER_DEFINED_ISOTOPES);
		preferenceStore.setDefault(IdentifierPreferences.P_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA, IdentifierPreferences.DEF_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA);
	}
}
