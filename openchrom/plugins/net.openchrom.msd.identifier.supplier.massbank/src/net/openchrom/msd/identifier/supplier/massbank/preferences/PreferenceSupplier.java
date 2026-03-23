/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
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
package net.openchrom.msd.identifier.supplier.massbank.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.osgi.framework.FrameworkUtil;

public class PreferenceSupplier extends AbstractPreferenceSupplier {

	public static final String MIRROR_EU = "EU";
	public static final String MIRROR_JP = "JP";

	public static final String P_MASSBANK_MIRROR = "massBankMirror";
	public static final String DEF_MASSBANK_MIRROR = MIRROR_EU;

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return FrameworkUtil.getBundle(PreferenceSupplier.class).getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_MASSBANK_MIRROR, DEF_MASSBANK_MIRROR);
	}

	public static String getMassBankMirror() {

		return INSTANCE().get(P_MASSBANK_MIRROR, DEF_MASSBANK_MIRROR);
	}

	public static String[][] getMirrors() {

		int mirrors = 2;
		String[][] elements = new String[mirrors][2];

		elements[0][0] = "Europe";
		elements[0][1] = MIRROR_EU;

		elements[1][0] = "Japan";
		elements[1][1] = MIRROR_JP;

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
