/*******************************************************************************
 * Copyright (c) 2021, 2024 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.csd.converter.supplier.cdf.Activator;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final int MIN_MODULATION_TIME = 1;
	public static final int MAX_MODULATION_TIME = Integer.MAX_VALUE;
	//
	public static final String P_MODULATION_TIME_2D = "modulationTime2D"; // $NON-NLS-1$
	public static final int DEF_MODULATION_TIME_2D = 10000; // Milliseconds = 10 s

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_MODULATION_TIME_2D, Integer.toString(DEF_MODULATION_TIME_2D));
	}

	public static int getModulationTime2D() {

		return INSTANCE().getInteger(P_MODULATION_TIME_2D, DEF_MODULATION_TIME_2D);
	}
}