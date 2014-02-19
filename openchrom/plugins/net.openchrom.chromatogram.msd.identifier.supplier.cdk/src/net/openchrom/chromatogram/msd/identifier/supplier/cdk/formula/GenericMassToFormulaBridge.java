/*******************************************************************************
 * Copyright (c) 2013, 2014 Marwin Wollschläger.
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
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.formula.MassToFormulaTool;
import org.openscience.cdk.formula.rules.ElementRule;
import org.openscience.cdk.formula.rules.IRule;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;

import net.openchrom.logging.core.Logger;

/**
 * A Bridge class that should simplify the communication between the
 * GenericMassToFormula instance and the views that are part of the mass to formula calculations.
 * 
 * @author administrator_marwin
 * 
 */
public class GenericMassToFormulaBridge {

	private static final Logger logger = Logger.getLogger(GenericMassToFormulaBridge.class);
	private MassToFormulaTool massToFormula;

	public GenericMassToFormulaBridge() {

		massToFormula = new MassToFormulaTool(DefaultChemObjectBuilder.getInstance());
	}

	/**
	 * Sets which isotopes shall be taken into account for the formula calculation.
	 * 
	 * @param isotopeDecider
	 */
	public void setIsotopeDecider(IsotopeDecider isotopeDecider) {

		/*
		 * Set molecular formula range to params object.
		 * And create a new rule and set restrictions.
		 */
		Object[] params = new Object[1];
		params[0] = isotopeDecider.getMolecularFormulaRange();
		List<IRule> rulesNew = new ArrayList<IRule>();
		IRule rule = new ElementRule();
		try {
			rule.setParameters(params);
			rulesNew.add(rule);
			massToFormula.setRestrictions(rulesNew);
		} catch(CDKException e) {
			logger.warn(e);
		}
	}

	public IMolecularFormulaSet generate(double mass) {

		return massToFormula.generate(mass);
	}

	/**
	 * Calculates the deviation between the given mass and the calculated formulas.
	 * 
	 * @param actualMass
	 * @param formulas
	 * @return List<Double>
	 */
	public List<Double> getRatings(double actualMass, IMolecularFormulaSet formulas) {

		List<Double> ratings = new ArrayList<Double>();
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

		List<String> results = new ArrayList<String>();
		//
		for(int i = 0; i < formulas.size(); i++) {
			IMolecularFormula molecularFormulaIterator = formulas.getMolecularFormula(i);
			String formulaString = "";
			for(IIsotope isoIter : molecularFormulaIterator.isotopes()) {
				formulaString += isoIter.getSymbol() + molecularFormulaIterator.getIsotopeCount(isoIter);
			}
			results.add(formulaString);
		}
		return results;
	}
}
