/*******************************************************************************
 * Copyright (c) 2013, 2026 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.cdf.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.osgi.framework.FrameworkUtil;

public class PreferenceSupplier extends AbstractPreferenceSupplier {

	public static final int MIN_MODULATION_TIME = 1;
	public static final int MAX_MODULATION_TIME = Integer.MAX_VALUE;

	public static final String P_MODULATION_TIME_2D = "modulationTime2D"; // $NON-NLS-1$
	public static final int DEF_MODULATION_TIME_2D = 10000; // Milliseconds = 10 s

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return FrameworkUtil.getBundle(PreferenceSupplier.class).getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_MODULATION_TIME_2D, Integer.toString(DEF_MODULATION_TIME_2D));
	}

	public static int getModulationTime2D() {

		return INSTANCE().getInteger(P_MODULATION_TIME_2D, DEF_MODULATION_TIME_2D);
	}
}