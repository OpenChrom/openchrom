/*******************************************************************************
 * Copyright (c) 2016, 2017 Walter Whitlock, Philip Wenig.
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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

import net.openchrom.msd.converter.supplier.cms.Activator;

public class PreferenceSupplier implements IPreferenceSupplier {

	public static final String P_USE_UNIT_MASS_RESOLUTION = "useUnitMassResolution";
	public static final boolean DEF_USE_UNIT_MASS_RESOLUTION = true;
	public static final String P_REMOVE_INTENSITIES_LOWER_THAN_ONE = "removeIntensitiesLowerThanOne";
	public static final boolean DEF_REMOVE_INTENSITIES_LOWER_THAN_ONE = true;
	public static final String P_NORMALIZE_INTENSITIES = "normalizeIntensities";
	public static final boolean DEF_NORMALIZE_INTENSITIES = true;
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

		Map<String, String> defaultValues = new HashMap<String, String>();
		//
		defaultValues.put(P_USE_UNIT_MASS_RESOLUTION, Boolean.toString(DEF_USE_UNIT_MASS_RESOLUTION));
		defaultValues.put(P_REMOVE_INTENSITIES_LOWER_THAN_ONE, Boolean.toString(DEF_REMOVE_INTENSITIES_LOWER_THAN_ONE));
		defaultValues.put(P_NORMALIZE_INTENSITIES, Boolean.toString(DEF_NORMALIZE_INTENSITIES));
		//
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static boolean isUseUnitMassResolution() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_USE_UNIT_MASS_RESOLUTION, DEF_USE_UNIT_MASS_RESOLUTION);
	}

	public static boolean isRemoveIntensitiesLowerThanOne() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_REMOVE_INTENSITIES_LOWER_THAN_ONE, DEF_REMOVE_INTENSITIES_LOWER_THAN_ONE);
	}

	public static boolean isNormalizeIntensities() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_NORMALIZE_INTENSITIES, DEF_NORMALIZE_INTENSITIES);
	}
}
