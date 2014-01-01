/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

public class ConverterPreferences {

	public static final String P_PRECISION = "precision";
	public static final int DEF_PRECISION = 2;
	public static final int MIN_PRECISION = 0;
	public static final int MAX_PRECISION = 10;
	public static final int PRECISION_INCEREMENT = 1;
	//
	private static final String PREFERENCE_NODE = "net.openchrom.chromatogram.msd.converter.supplier.cdf.ui";

	public static int getPrecision() {

		Preferences preferences = InstanceScope.INSTANCE.getNode(PREFERENCE_NODE);
		int precision = preferences.getInt(P_PRECISION, DEF_PRECISION);
		/*
		 * Validate the precision.
		 */
		if(precision < MIN_PRECISION || precision > MAX_PRECISION) {
			precision = DEF_PRECISION;
		}
		return precision;
	}
}
