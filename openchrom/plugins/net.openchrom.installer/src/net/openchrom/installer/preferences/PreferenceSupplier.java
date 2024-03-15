/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
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
package net.openchrom.installer.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import net.openchrom.installer.Activator;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_PROPRIETARY_CONVERTERS = "proprietaryConverters";
	public static final String DEF_PROPRIETARY_CONVERTERS = "";

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_PROPRIETARY_CONVERTERS, DEF_PROPRIETARY_CONVERTERS);
	}

	public static String getProprietaryConverters() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.get(P_PROPRIETARY_CONVERTERS, DEF_PROPRIETARY_CONVERTERS);
	}
}