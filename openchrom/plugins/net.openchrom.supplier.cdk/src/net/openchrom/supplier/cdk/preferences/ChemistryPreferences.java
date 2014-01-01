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
package net.openchrom.supplier.cdk.preferences;

import java.util.Set;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.openscience.cdk.interfaces.IIsotope;
import org.osgi.service.prefs.Preferences;

import net.openchrom.supplier.cdk.formula.IsotopeDecider;
import net.openchrom.supplier.cdk.formula.IsotopeDeciderFactory;
import net.openchrom.supplier.cdk.formula.IsotopeParser;

public class ChemistryPreferences {

	public static final String P_ISOTOPE_SET = "isotopeSet";
	public static final String DEF_ISOTOPE_SET = IsotopePreference.BASIC.toString();
	public static final String P_ISOTOPE_ITERATION_DEPTH = "isotopesIterationDepth";
	public static final int DEF_ISOTOPE_ITERATION_DEPTH = 15;
	public static final String P_USER_DEFINED_ISOTOPES = "userDefinedIsotopes";
	public static final String DEF_USER_DEFINED_ISOTOPES = "C H N O";
	//
	private static final String NODE = "net.openchrom.supplier.cdk.ui";

	public static IsotopeDecider getIsotopeDecider() {

		Preferences preferences = InstanceScope.INSTANCE.getNode(NODE);
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
}
