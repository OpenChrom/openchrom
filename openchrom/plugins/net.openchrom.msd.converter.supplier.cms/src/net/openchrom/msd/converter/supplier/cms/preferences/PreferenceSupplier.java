/*******************************************************************************
 * Copyright (c) 2016, 2024 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.msd.converter.supplier.cms.Activator;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_USE_UNIT_MASS_RESOLUTION = "useUnitMassResolution";
	public static final boolean DEF_USE_UNIT_MASS_RESOLUTION = true;
	public static final String P_REMOVE_INTENSITIES_LOWER_THAN_ONE = "removeIntensitiesLowerThanOne";
	public static final boolean DEF_REMOVE_INTENSITIES_LOWER_THAN_ONE = true;
	public static final String P_NORMALIZE_INTENSITIES = "normalizeIntensities";
	public static final boolean DEF_NORMALIZE_INTENSITIES = true;

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_USE_UNIT_MASS_RESOLUTION, Boolean.toString(DEF_USE_UNIT_MASS_RESOLUTION));
		putDefault(P_REMOVE_INTENSITIES_LOWER_THAN_ONE, Boolean.toString(DEF_REMOVE_INTENSITIES_LOWER_THAN_ONE));
		putDefault(P_NORMALIZE_INTENSITIES, Boolean.toString(DEF_NORMALIZE_INTENSITIES));
	}

	public static boolean isUseUnitMassResolution() {

		return INSTANCE().getBoolean(P_USE_UNIT_MASS_RESOLUTION, DEF_USE_UNIT_MASS_RESOLUTION);
	}

	public static boolean isRemoveIntensitiesLowerThanOne() {

		return INSTANCE().getBoolean(P_REMOVE_INTENSITIES_LOWER_THAN_ONE, DEF_REMOVE_INTENSITIES_LOWER_THAN_ONE);
	}

	public static boolean isNormalizeIntensities() {

		return INSTANCE().getBoolean(P_NORMALIZE_INTENSITIES, DEF_NORMALIZE_INTENSITIES);
	}
}