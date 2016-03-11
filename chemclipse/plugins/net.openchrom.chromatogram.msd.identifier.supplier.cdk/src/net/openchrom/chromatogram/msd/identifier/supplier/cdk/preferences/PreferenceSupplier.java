/*******************************************************************************
 * Copyright (c) 2013, 2016 Dr. Philip Wenig.
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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.openscience.cdk.interfaces.IIsotope;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.Activator;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula.IsotopeDecider;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula.IsotopeDeciderFactory;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula.IsotopeParser;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings.VendorPeakIdentifierSettings;
import net.openchrom.chromatogram.msd.identifier.supplier.cdk.settings.IVendorPeakIdentifierSettings;

public class PreferenceSupplier implements IPreferenceSupplier {

	public static final String P_ISOTOPE_SET = "isotopeSet";
	public static final String DEF_ISOTOPE_SET = IsotopePreference.BASIC.toString();
	public static final String P_ISOTOPE_ITERATION_DEPTH = "isotopesIterationDepth";
	public static final int DEF_ISOTOPE_ITERATION_DEPTH = 15;
	public static final String P_USER_DEFINED_ISOTOPES = "userDefinedIsotopes";
	public static final String DEF_USER_DEFINED_ISOTOPES = "C H N O";
	public static final String P_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA = "deleteIdentificationsWithoutFormula";
	public static final boolean DEF_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA = false;
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
		defaultValues.put(P_ISOTOPE_ITERATION_DEPTH, Integer.toString(DEF_ISOTOPE_ITERATION_DEPTH));
		defaultValues.put(P_ISOTOPE_SET, DEF_ISOTOPE_SET);
		defaultValues.put(P_USER_DEFINED_ISOTOPES, DEF_USER_DEFINED_ISOTOPES);
		defaultValues.put(P_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA, Boolean.toString(DEF_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA));
		return defaultValues;
	}

	@Override
	public IEclipsePreferences getPreferences() {

		return getScopeContext().getNode(getPreferenceNode());
	}

	public static IsotopeDecider getIsotopeDecider() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		IsotopePreference isotopePreference = IsotopePreference.valueOf(preferences.get(P_ISOTOPE_SET, DEF_ISOTOPE_SET));
		IsotopeDecider isotopeDecider;
		/*
		 * Get the isotope decider.
		 */
		switch(isotopePreference) {
			case ORGANIC:
				isotopeDecider = IsotopeDeciderFactory.getInstance().getImportantOrganicIsotopes();
				break;
			case USER_DEFINED:
				IsotopeParser isotopeParser = new IsotopeParser();
				Set<IIsotope> isotopes = isotopeParser.extract(preferences.get(P_USER_DEFINED_ISOTOPES, DEF_USER_DEFINED_ISOTOPES));
				isotopeDecider = IsotopeDeciderFactory.getInstance().getIsotopeDeciderFromIsotopeSet(isotopes);
				break;
			default: // BASIC
				isotopeDecider = IsotopeDeciderFactory.getInstance().getBasicIsotopes();
				break;
		}
		/*
		 * Set the iteration depth.
		 */
		int iterationDepth = preferences.getInt(P_ISOTOPE_ITERATION_DEPTH, DEF_ISOTOPE_ITERATION_DEPTH);
		isotopeDecider.setIterationDepth(iterationDepth);
		//
		return isotopeDecider;
	}

	public static String[][] getIsotopePreferenceOptions() {

		return new String[][]{{"&Basic Isotope Set (C,H,N,O)", IsotopePreference.BASIC.toString()}, {"&Organic Isotope Set (C,H,N,O,Cl,Br,S,P,I,B)", IsotopePreference.ORGANIC.toString()}, {"&User Defined Isotope Set", IsotopePreference.USER_DEFINED.toString()}};
	}

	public static IVendorPeakIdentifierSettings getPeakIdentifierSettings() {

		IVendorPeakIdentifierSettings peakIdentifierSettings = new VendorPeakIdentifierSettings();
		peakIdentifierSettings.setDeleteIdentificationsWithoutFormula(isDeleteIdentificationsWithoutFormula());
		return peakIdentifierSettings;
	}

	public static boolean isDeleteIdentificationsWithoutFormula() {

		IEclipsePreferences preferences = INSTANCE().getPreferences();
		return preferences.getBoolean(P_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA, DEF_DELETE_IDENTIFICATIONS_WITHOUT_FORMULA);
	}
}
