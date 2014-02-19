/*******************************************************************************
 * Copyright (c) 2014 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings.CdkPeakIdentifierSettings;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings.ICdkPeakIdentifierSettings;

public class IdentifierPreferences {

	public static final String P_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA = "deleteIdentificationsWithoutFormula";
	public static final boolean DEF_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA = false;
	private static final String NODE = "net.openchrom.chromatogram.msd.identifier.supplier.cdk.ui";

	public static ICdkPeakIdentifierSettings getPeakIdentifierSettings() {

		ICdkPeakIdentifierSettings peakIdentifierSettings = new CdkPeakIdentifierSettings();
		peakIdentifierSettings.setDeleteIdentificationsWithoutFormula(isDeleteIdentificationsWithoutFormula());
		return peakIdentifierSettings;
	}

	public static boolean isDeleteIdentificationsWithoutFormula() {

		Preferences preferences = InstanceScope.INSTANCE.getNode(NODE);
		return preferences.getBoolean(P_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA, DEF_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA);
	}
}
