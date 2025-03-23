/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.extensions.cdk.calculator;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.formula.IsotopePattern;
import org.openscience.cdk.formula.IsotopePatternGenerator;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import net.openchrom.msd.extensions.cdk.preferences.PreferenceSupplier;

public class IsotopePatternCalculator {

	public IsotopePattern getIsotopePatternCalculator(String formula) {

		IMolecularFormula molecularFormula = MolecularFormulaManipulator.getMolecularFormula(formula, DefaultChemObjectBuilder.getInstance());
		MolecularFormulaManipulator.adjustProtonation(molecularFormula, +1); // [M+H]+

		IsotopePatternGenerator isotopePatternGenerator = new IsotopePatternGenerator(PreferenceSupplier.getMinimumIntensity());
		return isotopePatternGenerator.getIsotopes(molecularFormula);
	}
}