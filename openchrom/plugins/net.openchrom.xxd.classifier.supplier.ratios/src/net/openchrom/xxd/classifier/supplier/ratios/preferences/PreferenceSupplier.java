/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

import net.openchrom.xxd.classifier.supplier.ratios.Activator;
import net.openchrom.xxd.classifier.supplier.ratios.settings.ClassifierSettings;

public class PreferenceSupplier implements IPreferenceSupplier {

	public static final float MIN_DEVIATION = 0.0f;
	public static final float MAX_DEVIATION = 100.0f;
	//
	public static final String P_ALLOWED_DEVIATION = "allowedDeviationOK";
	public static final float DEF_ALLOWED_DEVIATION = 20.0f;
	public static final String P_ALLOWED_DEVIATION_WARN = "allowedDeviationWarn";
	public static final float DEF_ALLOWED_DEVIATION_WARN = 40.0f;
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
		defaultValues.put(P_ALLOWED_DEVIATION, Float.toString(DEF_ALLOWED_DEVIATION));
		defaultValues.put(P_ALLOWED_DEVIATION_WARN, Float.toString(DEF_ALLOWED_DEVIATION_WARN));
		//
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static ClassifierSettings getClassifierSettings() {

		return new ClassifierSettings();
	}
}
