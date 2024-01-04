/*******************************************************************************
 * Copyright (c) 2023, 2024 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	private static final Pattern PATTERN_ELEMENT = Pattern.compile("([+-]\\w*)");
	private static final Pattern PATTERN_COUNT = Pattern.compile("^([0-9])(.*)");

	/**
	 * NOTE: This method needs further inspection.
	 * ----
	 * Precursor Type:
	 * ----
	 * [M+H]+
	 * [M-H]-
	 * ...
	 * [M-H-CO2-C14H23O2]-
	 * [M+H-C36H64O10-C12H20O10]+
	 * [M+H-C8H10N4O3]+
	 * [M+H-C8H18O2]+
	 * [M+H-C12H9NS]+
	 * [M+H-C23H38O21-C15H24O2]+
	 * [M-H-C18H32O18-CO]-
	 * [M+H-CH3N-C2H2O]+
	 * [M+H-CH5NO2-CH2O2]+
	 * [M-H-C12H18]-
	 */
	@SuppressWarnings("unused")
	private static double getNeutralMass(String precursorType, double precursorIon) {

		if(precursorIon > 0.0d) {
			if(precursorType != null && !precursorType.isEmpty()) {
				String[] parts = precursorType.split("]");
				if(parts.length == 2) {
					double adjustByMass = 0;
					String chemicalIonization = parts[0].trim().replace("[M", "");
					Matcher matcher = PATTERN_ELEMENT.matcher(chemicalIonization);
					while(matcher.find()) {
						/*
						 * Add / Subtract
						 */
						String formula = matcher.group();
						boolean add;
						if(formula.startsWith("+")) {
							add = false; // subtract to get the neutral mass
						} else {
							add = true; // add to get the neutral mass
						}
						/*
						 * Formula
						 * Handle differently 2H, 2H2O, ...
						 */
						double count = 1;
						double mass;
						//
						formula = formula.substring(1, formula.length());
						Matcher matcherCount = PATTERN_COUNT.matcher(formula);
						if(matcherCount.find()) {
							try {
								count = Integer.parseInt(matcherCount.group(1));
								formula = matcherCount.group(2);
							} catch(NumberFormatException e) {
							}
						}
						//
						mass = calculateMolecularWeight(formula);
						mass *= count;
						adjustByMass += add ? mass : -mass;
					}
					//
					return precursorIon + adjustByMass;
				}
			}
		}
		//
		return precursorIon;
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
		//
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
		//
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
		//
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
		//
		return exactMass;
	}
}