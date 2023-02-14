/*******************************************************************************
 * Copyright (c) 2013, 2023 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 * Dr. Philip Wenig - additional implementations
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.formula;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.formula.MolecularFormulaGenerator;
import org.openscience.cdk.formula.MolecularFormulaRange;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

/**
 * A Bridge class that should simplify the communication between the
 * GenericMassToFormula instance and the views that are part of the mass to formula calculations.
 * 
 * @author administrator_marwin
 * 
 */
public class GenericMassToFormulaBridge {

	private MolecularFormulaGenerator molecularFormulaGenerator;

	public GenericMassToFormulaBridge(IsotopeDecider isotopeDecider, double mass) {

		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		MolecularFormulaRange range = isotopeDecider.getMolecularFormulaRange();
		molecularFormulaGenerator = new MolecularFormulaGenerator(builder, mass, mass, range);
	}

	public IMolecularFormulaSet generate() {

		return molecularFormulaGenerator.getAllFormulas();
	}

	/**
	 * Calculates the deviation between the given mass and the calculated formulas.
	 * 
	 * @param actualMass
	 * @param formulas
	 * @return List<Double>
	 */
	public List<Double> getRatings(double actualMass, IMolecularFormulaSet formulas) {

		List<Double> ratings = new ArrayList<>();
		for(int j = 0; j < formulas.size(); j++) {
			IMolecularFormula formula = formulas.getMolecularFormula(j);
			double mass = 0.0;
			for(IIsotope isotope : formula.isotopes()) {
				mass += isotope.getExactMass() * formula.getIsotopeCount(isotope);
			}
			double deviation = Math.abs(mass - actualMass) / (actualMass); // for example (99-100) = -1 / 100 => 1% deviation
			ratings.add(1 - deviation);
		}
		return ratings;
	}

	public List<String> getNames(IMolecularFormulaSet formulas) {

		List<String> results = new ArrayList<>();
		//
		for(int i = 0; i < formulas.size(); i++) {
			IMolecularFormula molecularFormulaIterator = formulas.getMolecularFormula(i);
			StringBuilder formulaString = new StringBuilder();
			for(IIsotope isoIter : molecularFormulaIterator.isotopes()) {
				formulaString.append(isoIter.getSymbol() + molecularFormulaIterator.getIsotopeCount(isoIter));
			}
			results.add(formulaString.toString());
		}
		return results;
	}
}
