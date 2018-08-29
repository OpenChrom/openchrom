/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

import net.openchrom.xxd.process.supplier.templates.Activator;

public class PreferenceSupplier implements IPreferenceSupplier {

	public static final String P_PEAK_DETECTOR_LIST_MSD = "peakDetectorListMSD";
	public static final String DEF_PEAK_DETECTOR_LIST_MSD = "";
	public static final String P_PEAK_DETECTOR_LIST_CSD = "peakDetectorListCSD";
	public static final String DEF_PEAK_DETECTOR_LIST_CSD = "";
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
		defaultValues.put(P_PEAK_DETECTOR_LIST_MSD, DEF_PEAK_DETECTOR_LIST_MSD);
		defaultValues.put(P_PEAK_DETECTOR_LIST_CSD, DEF_PEAK_DETECTOR_LIST_CSD);
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}
}
