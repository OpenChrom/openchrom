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

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.formula.MassToFormulaTool;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;

public class CDKMassToFormula {
	
	//List <IMolecularFormula> formulas = new ArrayList<IMolecularFormula>();
	public IMolecularFormulaSet generate(double mass) {
		MassToFormulaTool t = new MassToFormulaTool(DefaultChemObjectBuilder.getInstance());
		IMolecularFormulaSet result = t.generate(mass);
		return result;
	}
	
	public List<Double> getRatings(double actualMass, IMolecularFormulaSet formulas)
	{
		List<Double> ratings = new ArrayList<Double>();
		for(int j = 0; j < formulas.size(); j++)
		{
			IMolecularFormula formula = formulas.getMolecularFormula(j);
			
			double mass = 0.0;
			for(IIsotope isotope : formula.isotopes() ){
				mass += isotope.getExactMass()*formula.getIsotopeCount(isotope);
			}
			System.out.println("mass : " + mass + " of formula " +actualMass);
			double deviation = Math.abs(mass-actualMass)/ (actualMass);// for example (99-100) = -1 / 100 => 1% deviation
			ratings.add(1-deviation);
		}
		
		return ratings;
	}
	
	
	
	public List<String> getNames(IMolecularFormulaSet formulas)
	{
		
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
	
	

	public static void main(String[] args) {

		CDKMassToFormula massToFormula = new CDKMassToFormula();
		double mass = 78.046950;
		IMolecularFormulaSet formulas = massToFormula.generate(mass);
		List<String> names = massToFormula.getNames(formulas);
		List<Double> ratings = massToFormula.getRatings(mass, formulas);
		
		for ( String name : names )
		{
			System.out.println(name);
		}
		for ( Double rating : ratings )
		{
			System.out.println(rating);
		}
		
		
	}
	
}
