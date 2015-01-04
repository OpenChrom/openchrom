/*******************************************************************************
 * Copyright (c) 2014, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.chromatogram.msd.identifier.supplier.cdk.support;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.interfaces.IMolecularFormulaSet;

import net.chemclipse.chromatogram.msd.identifier.supplier.cdk.formula.GenericMassToFormulaBridge;
import net.chemclipse.chromatogram.msd.identifier.supplier.cdk.formula.NameAndRating;
import net.chemclipse.chromatogram.msd.identifier.supplier.cdk.preferences.PreferenceSupplier;

public class FormulaSupport {

	private static FormulaSupport instance = null;
	private GenericMassToFormulaBridge massToFormulaBridge = new GenericMassToFormulaBridge();

	private FormulaSupport() {

	}

	public static FormulaSupport getInstance() {

		if(instance == null) {
			instance = new FormulaSupport();
		}
		return instance;
	}

	public List<NameAndRating> getFormulaNamesAndRatings(Double ion) {

		massToFormulaBridge.setIsotopeDecider(PreferenceSupplier.getIsotopeDecider());
		IMolecularFormulaSet formulas = massToFormulaBridge.generate(ion);
		List<String> formulaNames = new ArrayList<String>();
		if(formulas != null) {
			formulaNames = massToFormulaBridge.getNames(formulas);
		}
		//
		List<Double> formulaRatings = new ArrayList<Double>();
		if(formulas != null) {
			formulaRatings = massToFormulaBridge.getRatings(ion, formulas);
		}
		//
		List<NameAndRating> formulaNamesAndRatings = new ArrayList<NameAndRating>();
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
