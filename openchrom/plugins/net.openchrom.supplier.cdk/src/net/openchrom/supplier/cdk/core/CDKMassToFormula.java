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

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.formula.MassToFormulaTool;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;

public class CDKMassToFormula {

	String[] resultString;

	public void generate(double mass) {

		MassToFormulaTool t = new MassToFormulaTool(DefaultChemObjectBuilder.getInstance());
		IMolecularFormulaSet result = t.generate(mass);
		resultString = new String[result.size()];
		//
		for(int i = 0; i < result.size(); i++) {
			IMolecularFormula iter = result.getMolecularFormula(i);
			String formula = "";
			for(IIsotope isoIter : iter.isotopes()) {
				formula += isoIter.getSymbol() + iter.getIsotopeCount(isoIter);
			}
			resultString[i] = formula;
		}
	}

	public static void main(String[] args) {

		CDKMassToFormula massToFormula = new CDKMassToFormula();
		double mass = 180.06;
		massToFormula.generate(mass);
		for(String formula : massToFormula.resultString) {
			System.out.println(formula);
		}
	}
}
