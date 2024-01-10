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
package net.openchrom.xxd.identifier.supplier.cdk.preferences;

import java.util.Set;

import org.eclipse.chemclipse.support.preferences.AbstractPreferenceSupplier;
import org.eclipse.chemclipse.support.preferences.IPreferenceSupplier;
import org.openscience.cdk.interfaces.IIsotope;

import net.openchrom.xxd.identifier.supplier.cdk.Activator;
import net.openchrom.xxd.identifier.supplier.cdk.formula.IsotopeDecider;
import net.openchrom.xxd.identifier.supplier.cdk.formula.IsotopeDeciderFactory;
import net.openchrom.xxd.identifier.supplier.cdk.formula.IsotopeParser;
import net.openchrom.xxd.identifier.supplier.cdk.settings.CleanerSettings;
import net.openchrom.xxd.identifier.supplier.cdk.settings.IdentifierSettings;

public class PreferenceSupplier extends AbstractPreferenceSupplier implements IPreferenceSupplier {

	public static final int MIN_LENGTH_NAME_EXPORT = 1;
	public static final int MAX_LENGTH_NAME_EXPORT = 1000;
	//
	public static final String P_ISOTOPE_SET = "isotopeSet";
	public static final String DEF_ISOTOPE_SET = IsotopePreference.BASIC.toString();
	public static final String P_ISOTOPE_ITERATION_DEPTH = "isotopesIterationDepth";
	public static final int DEF_ISOTOPE_ITERATION_DEPTH = 15;
	public static final String P_USER_DEFINED_ISOTOPES = "userDefinedIsotopes";
	public static final String DEF_USER_DEFINED_ISOTOPES = "C H N O";
	/*
	 * CDK
	 */
	public static final String P_SMILES_STRICT = "smilesStrict";
	public static final boolean DEF_SMILES_STRICT = true;
	/*
	 * OPSIN
	 */
	public static final String P_ALLOW_RADICALS = "allowRadicals";
	public static final boolean DEF_ALLOW_RADICALS = false;
	public static final String P_OUTPUT_RADICALS_AS_WILD_CARD_ATOMS = "outputRadicalsAsWildCardAtoms";
	public static final boolean DEF_OUTPUT_RADICALS_AS_WILD_CARD_ATOMS = false;
	public static final String P_DETAILED_FAILURE_ANALYSIS = "detailedFailureAnalysis";
	public static final boolean DEF_DETAILED_FAILURE_ANALYSIS = false;
	public static final String P_INTERPRET_ACIDS_WITHOUT_THE_WORD_ACID = "interpretAcidsWithoutTheWordAcid";
	public static final boolean DEF_INTERPRET_ACIDS_WITHOUT_THE_WORD_ACID = false;
	public static final String P_WARN_RATHER_THAN_FAIL = "warnRatherThanFailOnUninterpretableStereochemistry";
	public static final boolean DEF_WARN_RATHER_THAN_FAIL = false;
	//
	public static final String P_DELETE_SCAN_TARGETS = "deleteScanTargets";
	public static final boolean DEF_DELETE_SCAN_TARGETS = false;
	public static final String P_DELETE_PEAK_TARGETS = "deletePeakTargets";
	public static final boolean DEF_DELETE_PEAK_TARGETS = false;
	/*
	 * Image Converter
	 */
	public static final String P_SHOW_ATOMS_H = "showAtomsH";
	public static final boolean DEF_SHOW_ATOMS_H = false;

	public static IPreferenceSupplier INSTANCE() {

		return INSTANCE(PreferenceSupplier.class);
	}

	@Override
	public String getPreferenceNode() {

		return Activator.getContext().getBundle().getSymbolicName();
	}

	@Override
	public void initializeDefaults() {

		putDefault(P_ISOTOPE_ITERATION_DEPTH, Integer.toString(DEF_ISOTOPE_ITERATION_DEPTH));
		putDefault(P_ISOTOPE_SET, DEF_ISOTOPE_SET);
		putDefault(P_USER_DEFINED_ISOTOPES, DEF_USER_DEFINED_ISOTOPES);
		//
		putDefault(P_SMILES_STRICT, Boolean.toString(DEF_SMILES_STRICT));
		//
		putDefault(P_ALLOW_RADICALS, Boolean.toString(DEF_ALLOW_RADICALS));
		putDefault(P_OUTPUT_RADICALS_AS_WILD_CARD_ATOMS, Boolean.toString(DEF_OUTPUT_RADICALS_AS_WILD_CARD_ATOMS));
		putDefault(P_DETAILED_FAILURE_ANALYSIS, Boolean.toString(DEF_DETAILED_FAILURE_ANALYSIS));
		putDefault(P_INTERPRET_ACIDS_WITHOUT_THE_WORD_ACID, Boolean.toString(DEF_INTERPRET_ACIDS_WITHOUT_THE_WORD_ACID));
		putDefault(P_WARN_RATHER_THAN_FAIL, Boolean.toString(DEF_WARN_RATHER_THAN_FAIL));
		//
		putDefault(P_DELETE_SCAN_TARGETS, Boolean.toString(DEF_DELETE_SCAN_TARGETS));
		putDefault(P_DELETE_PEAK_TARGETS, Boolean.toString(DEF_DELETE_PEAK_TARGETS));
		//
		putDefault(P_SHOW_ATOMS_H, Boolean.toString(DEF_SHOW_ATOMS_H));
	}

	public static IsotopeDecider getIsotopeDecider() {

		IsotopePreference isotopePreference = IsotopePreference.valueOf(INSTANCE().get(P_ISOTOPE_SET, DEF_ISOTOPE_SET));
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
				Set<IIsotope> isotopes = isotopeParser.extract(INSTANCE().get(P_USER_DEFINED_ISOTOPES, DEF_USER_DEFINED_ISOTOPES));
				isotopeDecider = IsotopeDeciderFactory.getInstance().getIsotopeDeciderFromIsotopeSet(isotopes);
				break;
			default: // BASIC
				isotopeDecider = IsotopeDeciderFactory.getInstance().getBasicIsotopes();
				break;
		}
		/*
		 * Set the iteration depth.
		 */
		int iterationDepth = INSTANCE().getInteger(P_ISOTOPE_ITERATION_DEPTH, DEF_ISOTOPE_ITERATION_DEPTH);
		isotopeDecider.setIterationDepth(iterationDepth);
		//
		return isotopeDecider;
	}

	public static String[][] getIsotopePreferenceOptions() {

		return new String[][]{{"&Basic Isotope Set (C,H,N,O)", IsotopePreference.BASIC.toString()}, {"&Organic Isotope Set (C,H,N,O,Cl,Br,S,P,I,B)", IsotopePreference.ORGANIC.toString()}, {"&User Defined Isotope Set", IsotopePreference.USER_DEFINED.toString()}};
	}

	public static IdentifierSettings getIdentifierSettings() {

		IdentifierSettings settings = new IdentifierSettings();
		settings.setAllowRadicals(isAllowRadicals());
		settings.setDetailedFailureAnalysis(isDetailedFailureAnalysis());
		settings.setInterpretAcidsWithoutTheWordAcid(isInterpretAcidsWithoutTheWordAcid());
		settings.setOutputRadicalsAsWildCardAtoms(isOutputRadicalsAsWildCardAtoms());
		settings.setWarnRatherThanFailOnUninterpretableStereochemistry(isWarnRatherThanFail());
		return settings;
	}

	public static CleanerSettings getCleanerSettings() {

		CleanerSettings settings = new CleanerSettings();
		settings.setDeleteScanTargets(isDeleteScanTargets());
		return settings;
	}

	public static boolean isSmilesStrict() {

		return INSTANCE().getBoolean(P_SMILES_STRICT, DEF_SMILES_STRICT);
	}

	public static boolean isAllowRadicals() {

		return INSTANCE().getBoolean(P_ALLOW_RADICALS, DEF_ALLOW_RADICALS);
	}

	public static boolean isOutputRadicalsAsWildCardAtoms() {

		return INSTANCE().getBoolean(P_OUTPUT_RADICALS_AS_WILD_CARD_ATOMS, DEF_OUTPUT_RADICALS_AS_WILD_CARD_ATOMS);
	}

	public static boolean isDetailedFailureAnalysis() {

		return INSTANCE().getBoolean(P_DETAILED_FAILURE_ANALYSIS, DEF_DETAILED_FAILURE_ANALYSIS);
	}

	public static boolean isInterpretAcidsWithoutTheWordAcid() {

		return INSTANCE().getBoolean(P_INTERPRET_ACIDS_WITHOUT_THE_WORD_ACID, DEF_INTERPRET_ACIDS_WITHOUT_THE_WORD_ACID);
	}

	public static boolean isWarnRatherThanFail() {

		return INSTANCE().getBoolean(P_WARN_RATHER_THAN_FAIL, DEF_WARN_RATHER_THAN_FAIL);
	}

	public static boolean isDeleteScanTargets() {

		return INSTANCE().getBoolean(P_DELETE_SCAN_TARGETS, DEF_DELETE_SCAN_TARGETS);
	}

	public static boolean isDeletePeakTargets() {

		return INSTANCE().getBoolean(P_DELETE_PEAK_TARGETS, DEF_DELETE_PEAK_TARGETS);
	}

	public static boolean isShowAtomsH() {

		return INSTANCE().getBoolean(P_SHOW_ATOMS_H, DEF_SHOW_ATOMS_H);
	}
}