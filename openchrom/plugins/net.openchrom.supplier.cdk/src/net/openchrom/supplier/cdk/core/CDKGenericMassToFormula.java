/*******************************************************************************
 * Copyright (c) 2013 Marwin Wollschläger.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Marwin Wollschläger - initial API and implementation
 *******************************************************************************/
package net.openchrom.supplier.cdk.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.formula.MassToFormulaTool;
import org.openscience.cdk.formula.MolecularFormulaRange;
import org.openscience.cdk.formula.rules.ChargeRule;
import org.openscience.cdk.formula.rules.ElementRule;
import org.openscience.cdk.formula.rules.IRule;
import org.openscience.cdk.formula.rules.ToleranceRangeRule;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;

/**
 * This class is deprecated!
 * Take a look at GenericMassToFormula in the package net.openchrom.supplier.cdk.core.massToFormula
 * instead!
 * => move all usages to the class GenericMassToFormula
 * 
 * @author administrator_marwin
 * 
 */
@Deprecated
public class CDKGenericMassToFormula {

	IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
	MassToFormulaTool massToFormulaTool = new MassToFormulaTool(builder);

	// List <IMolecularFormula> formulas = new ArrayList<IMolecularFormula>();
	public IMolecularFormulaSet generate(double mass) {

		MassToFormulaTool t = new MassToFormulaTool(DefaultChemObjectBuilder.getInstance());
		IMolecularFormulaSet result = t.generate(mass);
		return result;
	}

	public List<Double> getRatings(double actualMass, IMolecularFormulaSet formulas) {

		List<Double> ratings = new ArrayList<Double>();
		for(int j = 0; j < formulas.size(); j++) {
			IMolecularFormula formula = formulas.getMolecularFormula(j);
			double mass = 0.0;
			for(IIsotope isotope : formula.isotopes()) {
				mass += isotope.getExactMass() * formula.getIsotopeCount(isotope);
			}
			System.out.println("mass : " + mass + " of formula " + actualMass);
			double deviation = Math.abs(mass - actualMass) / (actualMass);// for example (99-100) = -1 / 100 => 1% deviation
			ratings.add(1 - deviation);
		}
		return ratings;
	}

	public List<String> getNames(IMolecularFormulaSet formulas) {

		List<String> results = new ArrayList<String>();
		//
		for(int i = 0; i < formulas.size(); i++) {
			IMolecularFormula iter = formulas.getMolecularFormula(i);
			String formulaString = "";
			for(IIsotope isoIter : iter.isotopes()) {
				formulaString += isoIter.getSymbol() + iter.getIsotopeCount(isoIter);
			}
			results.add(formulaString);
		}
		return results;
	}

	// an example usage
	// public static void main(String[] args) {
	//
	// CDKMassToFormula massToFormula = new CDKMassToFormula();
	// double mass = 78.046950;
	// IMolecularFormulaSet formulas = massToFormula.generate(mass);
	// List<String> names = massToFormula.getNames(formulas);
	// List<Double> ratings = massToFormula.getRatings(mass, formulas);
	// for(String name : names) {
	// System.out.println(name);
	// }
	// for(Double rating : ratings) {
	// System.out.println(rating);
	// }
	// }
	private void callDefaultRestrictions() throws CDKException, IOException {

		List<IRule> rules1 = new ArrayList<IRule>();
		IsotopeFactory ifac = IsotopeFactory.getInstance(builder);
		// restriction for occurrence elements
		MolecularFormulaRange mfRange1 = new MolecularFormulaRange();
		mfRange1.addIsotope(ifac.getMajorIsotope("C"), 0, 15);
		mfRange1.addIsotope(ifac.getMajorIsotope("H"), 0, 15);
		mfRange1.addIsotope(ifac.getMajorIsotope("N"), 0, 15);
		mfRange1.addIsotope(ifac.getMajorIsotope("O"), 0, 15);
		IRule rule = new ElementRule();
		Object[] params = new Object[1];
		params[0] = mfRange1;
		rule.setParameters(params);
		rules1.add(rule);
		// occurrence for charge
		rule = new ChargeRule(); // default 0.0 neutral
		rules1.add(rule);
		// Charge ch;
		// ICharge charge = (Double) ((Object[])rule.getParameters())[0];
		// occurrence for tolerance
		rule = new ToleranceRangeRule(); // default 0.05
		rules1.add(rule);
		// this.tolerance = (Double) ((Object[])rule.getParameters())[1];
		// this.matrix_Base = getMatrix(mfRange1.getIsotopeCount());
		// this.mfRange = mfRange1;
		// this.rules = rules1;
	}
}
