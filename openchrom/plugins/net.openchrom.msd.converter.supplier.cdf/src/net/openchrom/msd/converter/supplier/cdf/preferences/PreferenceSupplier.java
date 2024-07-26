/*******************************************************************************
 * Copyright (c) 2013, 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.preferences;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.msd.converter.supplier.cdf.Activator;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final String P_PRECISION = "precision";
	public static final int DEF_PRECISION = 2;
	public static final int MIN_PRECISION = 0;
	public static final int MAX_PRECISION = 10;
	public static final int PRECISION_INCEREMENT = 1;
	//
	public static final int MIN_MODULATION_TIME = 1;
	public static final int MAX_MODULATION_TIME = Integer.MAX_VALUE;
	//
	public static final String P_FORCE_PARSE_NOMINAL = "forceParseNominal";
	public static final boolean DEF_FORCE_PARSE_NOMINAL = false;
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

		putDefault(P_PRECISION, Integer.toString(DEF_PRECISION));
		putDefault(P_FORCE_PARSE_NOMINAL, Boolean.toString(DEF_FORCE_PARSE_NOMINAL));
		putDefault(P_MODULATION_TIME_2D, Integer.toString(DEF_MODULATION_TIME_2D));
	}

	public static int getPrecision() {

		int precision = INSTANCE().getInteger(P_PRECISION, DEF_PRECISION);
		/*
		 * Validate the precision.
		 */
		if(precision < MIN_PRECISION || precision > MAX_PRECISION) {
			precision = DEF_PRECISION;
		}
		return precision;
	}

	public static boolean isForceParseNominal() {

		return INSTANCE().getBoolean(P_FORCE_PARSE_NOMINAL, DEF_FORCE_PARSE_NOMINAL);
	}

	public static int getModulationTime2D() {

		return INSTANCE().getInteger(P_MODULATION_TIME_2D, DEF_MODULATION_TIME_2D);
	}
}