/*******************************************************************************
 * Copyright (c) 2023, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.identifier.supplier.cdk.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openscience.cdk.formula.MolecularFormula;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

public class MoleculeMassCalculator {

	/*
	 * CODATA 2018 electron mass in u.
	 */
	public static final double ELECTRON_MASS = 0.000548579909065d;
	/*
	 * [M+H]+, [2M+Na]+, [M-2H]2-, [M+2H]++, [M-H2O+H]+, ...
	 */
	private static final Pattern PATTERN_PRECURSOR_TYPE = Pattern.compile("^\\[([0-9]*)M((?:[+-][0-9]*[A-Z][A-Za-z0-9]*)*)\\]([0-9]*)([+]+|[-]+)$");
	private static final Pattern PATTERN_ADDUCT = Pattern.compile("([+-])([0-9]*)([A-Z][A-Za-z0-9]*)");

	/**
	 * Calculates the monoisotopic neutral mass M from the precursor m/z and the precursor type.
	 * The adducts are calculated by their monoisotopic masses, the electron mass is considered
	 * and the m/z is multiplied by the charge state. Multimers like [2M+H]+ are supported.
	 * If the precursor type can't be parsed, the precursor ion is returned unchanged.
	 * ----
	 * Precursor Type:
	 * ----
	 * [M]+
	 * [M+H]+
	 * [M-H]-
	 * [M+2H]2+
	 * [M-2H]--
	 * [2M+Na]+
	 * [M-H2O+H]+
	 * [M+H-C8H10N4O3]+
	 * [M-H-CO2-C14H23O2]-
	 * ...
	 */
	public static double getNeutralMass(String precursorType, double precursorIon) {

		if(precursorIon <= 0.0d || precursorType == null) {
			return precursorIon;
		}

		Matcher matcher = PATTERN_PRECURSOR_TYPE.matcher(precursorType.trim());
		if(!matcher.matches()) {
			return precursorIon;
		}

		int multimer = matcher.group(1).isEmpty() ? 1 : Integer.parseInt(matcher.group(1));
		String charges = matcher.group(4);
		int sign = charges.startsWith("+") ? 1 : -1;
		int chargeState = matcher.group(3).isEmpty() ? charges.length() : Integer.parseInt(matcher.group(3));
		if(multimer == 0 || chargeState == 0 || (!matcher.group(3).isEmpty() && charges.length() > 1)) {
			return precursorIon;
		}

		double adductMass = 0.0d;
		Matcher matcherAdduct = PATTERN_ADDUCT.matcher(matcher.group(2));
		while(matcherAdduct.find()) {
			int count = matcherAdduct.group(2).isEmpty() ? 1 : Integer.parseInt(matcherAdduct.group(2));
			double mass = count * calculateExactMass(matcherAdduct.group(3));
			adductMass += matcherAdduct.group(1).equals("+") ? mass : -mass;
		}
		/*
		 * m/z * z = n * M + adducts - z * e
		 */
		int charge = sign * chargeState;
		double ionMass = precursorIon * chargeState + charge * ELECTRON_MASS;
		return (ionMass - adductMass) / multimer;
	}

	/**
	 * Returns the molecular weight.
	 * E.g. Toluene: 92.13867730812235 g/mol
	 * https://pubchem.ncbi.nlm.nih.gov/compound/1140
	 * 
	 * @param formula
	 * @return double
	 */
	public static double calculateMolecularWeight(String formula) {

		double molecularWeight = 0.0d;
		if(!formula.isEmpty()) {
			IMolecularFormula molecularFormula = new MolecularFormula();
			molecularFormula = MolecularFormulaManipulator.getMolecularFormula(formula, molecularFormula);
			if(molecularFormula != null) {
				IAtomContainer molecule = MolecularFormulaManipulator.getAtomContainer(molecularFormula);
				molecularWeight = calculateMolecularWeight(molecule);
			}
		}

		return molecularWeight;
	}

	/**
	 * Returns the molecular weight.
	 * E.g. Toluene: 92.13867730812235 g/mol
	 * https://pubchem.ncbi.nlm.nih.gov/compound/1140
	 * 
	 * @param molecule
	 * @return double
	 */
	public static double calculateMolecularWeight(IAtomContainer molecule) {

		double molecularWeight = 0.0d;
		if(molecule != null) {
			molecularWeight = AtomContainerManipulator.getMass(molecule);
		}

		return molecularWeight;
	}

	/**
	 * Returns the exact mass.
	 * E.g. Toluene: 92.062600255 g/mol
	 * https://pubchem.ncbi.nlm.nih.gov/compound/1140
	 * 
	 * @param formula
	 * @return double
	 */
	public static double calculateExactMass(String formula) {

		double exactMass = 0.0d;
		if(!formula.isEmpty()) {
			IMolecularFormula molecularFormula = new MolecularFormula();
			molecularFormula = MolecularFormulaManipulator.getMolecularFormula(formula, molecularFormula);
			exactMass = calculateExactMass(molecularFormula);
		}

		return exactMass;
	}

	/**
	 * Returns the exact mass.
	 * E.g. Toluene: 92.062600255 g/mol
	 * https://pubchem.ncbi.nlm.nih.gov/compound/1140
	 * 
	 * @param molecularFormula
	 * @return double
	 */
	public static double calculateExactMass(IMolecularFormula molecularFormula) {

		double exactMass = 0.0d;
		if(molecularFormula != null) {
			exactMass = MolecularFormulaManipulator.getMass(molecularFormula, MolecularFormulaManipulator.MonoIsotopic);
		}

		return exactMass;
	}
}