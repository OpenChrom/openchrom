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

import junit.framework.TestCase;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormulaSet;

import net.openchrom.logging.core.Logger;
import net.openchrom.supplier.cdk.core.formula.GenericMassToFormulaTool;

/**
 * A class for testing the behaviour of the file GenericMassToFormulaTool.java
 * and the file IsotopeDecider.java, that allow the client to choose what isotopes to
 * consider in the mass to formula calculation.
 * 
 * @author administrator_marwin
 * 
 */
public class GenericMassToFormula_1_Test extends TestCase{
	private static final Logger logger = Logger.getLogger(GenericMassToFormula_1_Test.class);
	static IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
	private IMolecularFormulaSet set; 
	GenericMassToFormulaTool genericMassToFormula; 
	@Override
	protected void setUp()
	{
		genericMassToFormula = new GenericMassToFormulaTool(builder);
		set = genericMassToFormula.generate(107.957461);
	}
	@Override
	protected void tearDown()
	{
		genericMassToFormula = null; set = null;
	}

	public void testMethod_1(){
		for(int i = 0; i < set.size(); i++) {
			IMolecularFormula formula = set.getMolecularFormula(i);
			for(IIsotope iso : formula.isotopes()) {
				logger.info(iso.getSymbol() + formula.getIsotopeCount(iso)+"\n");
			}
			
		}
	}
}
