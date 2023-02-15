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
 * Dr. Philip Wenig - adjustments
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.formula;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.logging.core.Logger;
import org.openscience.cdk.formula.MolecularFormulaRange;
import org.openscience.cdk.interfaces.IIsotope;

/**
 * A Simple class for handling the settings of the generic mass to formula tool. Using this class,
 * the client can decide on what isotopes to respect in calculating the formula.
 * See also GenericMassToFormulaTool
 * 
 * @author administrator_marwin
 * 
 */
public class IsotopeDecider {

	private static final Logger logger = Logger.getLogger(IsotopeDecider.class);
	private List<IIsotope> isotopeSet;
	private int iterationDepth = 15;
	private IsotopeDeciderFactory isotopeFactory;

	public IsotopeDecider() {

		try {
			IsotopeDeciderFactory.getInstance();
			isotopeFactory = IsotopeDeciderFactory.getInstance();
		} catch(Exception e) {
			logger.warn("Something went wrong with your Isotope Selection.\n" + "Maybe you misspelled some of the element symbols? Anyway,\n" + "Something went wrong because of the following error:\n" + e);
		}
	}

	public void setIterationDepth(int iterationDepth) {

		this.iterationDepth = iterationDepth;
	}

	public int getIterationDepth() {

		return iterationDepth;
	}

	public List<IIsotope> getIsotopeSet() {

		return isotopeSet;
	}

	public void setIsotopeSet(List<IIsotope> isotopeSet) {

		this.isotopeSet = isotopeSet;
	}

	public void setIsotopeSetAsString(List<String> isotopeNames) {

		List<IIsotope> isotopesToSet = new ArrayList<>();
		for(String name : isotopeNames) {
			isotopesToSet.add(isotopeFactory.getIsotope(name));
		}
		isotopeSet = isotopesToSet;
	}

	public void addIsotope(String name) {

		isotopeSet.add(isotopeFactory.getIsotope(name));
	}

	public void addIsotope(List<IIsotope> isotopesToAdd) {

		for(IIsotope isotope : isotopesToAdd) {
			isotopeSet.add(isotope);
		}
	}

	public MolecularFormulaRange getMolecularFormulaRange() {

		MolecularFormulaRange molecularFormulaRange = new MolecularFormulaRange();
		for(IIsotope isotope : isotopeSet) {
			molecularFormulaRange.addIsotope(isotope, 0, iterationDepth);
		}
		return molecularFormulaRange;
	}
}
