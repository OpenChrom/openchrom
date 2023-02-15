/*******************************************************************************
 * Copyright (c) 2014, 2023 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.support;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IMolecularFormulaSet;

import net.openchrom.xxd.identifier.supplier.cdk.formula.GenericMassToFormulaBridge;
import net.openchrom.xxd.identifier.supplier.cdk.formula.NameAndRating;
import net.openchrom.xxd.identifier.supplier.cdk.preferences.PreferenceSupplier;

public class FormulaSupport {

	private static FormulaSupport instance = null;

	private FormulaSupport() {

	}

	public static FormulaSupport getInstance() {

		if(instance == null) {
			instance = new FormulaSupport();
		}
		return instance;
	}

	public List<NameAndRating> getFormulaNamesAndRatings(Double ion) {

		GenericMassToFormulaBridge massToFormulaBridge = new GenericMassToFormulaBridge(PreferenceSupplier.getIsotopeDecider(), ion);
		IMolecularFormulaSet formulas = massToFormulaBridge.generate();
		List<String> formulaNames = new ArrayList<>();
		if(formulas != null) {
			formulaNames = massToFormulaBridge.getNames(formulas);
		}
		//
		List<Double> formulaRatings = new ArrayList<>();
		if(formulas != null) {
			formulaRatings = massToFormulaBridge.getRatings(ion, formulas);
		}
		//
		List<NameAndRating> formulaNamesAndRatings = new ArrayList<>();
		for(int i = 0; i < formulaNames.size() && i < formulaRatings.size(); i++) {
			String formulaName = formulaNames.get(i);
			Double formulaRating = formulaRatings.get(i);
			NameAndRating nameAndRating = new NameAndRating(formulaName, formulaRating);
			formulaNamesAndRatings.add(nameAndRating);
		}
		//
		return formulaNamesAndRatings;
	}
}
