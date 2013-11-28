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
package net.openchrom.supplier.cdk.core.massToFormula;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;

import net.openchrom.supplier.cdk.core.formula.GenericMassToFormulaTool;

/**
 * A class for testing the behaviour of the file GenericMassToFormulaTool.java
 * and the file IsotopeDecider.java, that allow the client to choose what isotopes to
 * consider in the mass to formula calculation.
 * 
 * @author administrator_marwin
 * 
 */
public class GenericMassToFormulaTest {

	static IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();

	public static void main(String[] args) {

		GenericMassToFormulaTool genericMassToFormula = new GenericMassToFormulaTool(builder);
		IMolecularFormulaSet set = genericMassToFormula.generate(107.957461);
		for(int i = 0; i < set.size(); i++) {
			IMolecularFormula formula = set.getMolecularFormula(i);
			for(IIsotope iso : formula.isotopes()) {
				System.out.print(iso.getSymbol() + formula.getIsotopeCount(iso));
			}
			System.out.println();
		}
	}
}
