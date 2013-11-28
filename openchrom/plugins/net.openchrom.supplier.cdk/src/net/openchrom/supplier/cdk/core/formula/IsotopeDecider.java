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
package net.openchrom.supplier.cdk.core.formula;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.config.IsotopeFactory;
import org.openscience.cdk.formula.MolecularFormulaRange;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
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

	private List<IIsotope> isotopeSet;
	private int iterationDepth = 15;

	public void setIterationDepth(int iterationDepth) {

		this.iterationDepth = iterationDepth;
	}

	public List<IIsotope> getIsotopeSet() {

		return isotopeSet;
	}

	public int getIterationDepth() {

		return iterationDepth;
	}

	public void setIsotopeSet(List<IIsotope> isotopeSet) {

		this.isotopeSet = isotopeSet;
	}

	public void setIsotopeSetAsString(List<String> isotopeNames) {

		List<IIsotope> isotopesToSet = new ArrayList<IIsotope>();
		for(String name : isotopeNames) {
			isotopesToSet.add(ifac.getMajorIsotope(name));
		}
		isotopeSet = isotopesToSet;
	}

	public void addIsotope(String name) {

		isotopeSet.add(ifac.getMajorIsotope(name));
	}

	public void addIsotope(List<IIsotope> isotopesToAdd) {

		for(IIsotope isotope : isotopesToAdd) {
			isotopeSet.add(isotope);
		}
	}

	IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
	IsotopeFactory ifac;

	public IsotopeDecider() {

		try {
			ifac = IsotopeFactory.getInstance(builder);
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addIsotopes(MolecularFormulaRange mfRange) {

		/* Example Usage */
		// mfRange1.addIsotope( ifac.getMajorIsotope("C"), 0, 15);
		// mfRange1.addIsotope( ifac.getMajorIsotope("H"), 0, 15);
		// mfRange1.addIsotope( ifac.getMajorIsotope("N"), 0, 15);
		// mfRange1.addIsotope( ifac.getMajorIsotope("O"), 0, 15);
		// mfRange1.addIsotope( ifac.getMajorIsotope("Br"), 0, 15);
		for(IIsotope isotope : isotopeSet) {
			mfRange.addIsotope(isotope, 0, iterationDepth);
		}
	}
}
