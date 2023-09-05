/*******************************************************************************
 * Copyright (c) 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.identifier.supplier.massbank.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

import net.openchrom.msd.identifier.supplier.massbank.Activator;

public class PreferenceSupplier implements IPreferenceSupplier {

	public static final String MIRROR_EU = "EU";
	public static final String MIRROR_JP = "JP";
	//
	public static final String P_MASSBANK_MIRROR = "massBankMirror";
	public static final String DEF_MASSBANK_MIRROR = MIRROR_EU;
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
		defaultValues.put(P_MASSBANK_MIRROR, DEF_MASSBANK_MIRROR);
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static String getMassBankMirror() {

		return INSTANCE().get(P_MASSBANK_MIRROR, DEF_MASSBANK_MIRROR);
	}

	public static String[][] getMirrors() {

		int mirrors = 2;
		String[][] elements = new String[mirrors][2];
		//
		elements[0][0] = "Europe";
		elements[0][1] = MIRROR_EU;
		//
		elements[1][0] = "Japan";
		elements[1][1] = MIRROR_JP;
		//
		return elements;
	}

	public static String getDomain() {

		switch(getMassBankMirror()) {
			case MIRROR_EU: {
				return "https://massbank.eu/MassBank/";
			}
			case MIRROR_JP: {
				return "https://massbank.jp/";
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + getMassBankMirror());
		}
	}
}
