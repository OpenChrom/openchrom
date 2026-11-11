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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

public class MoleculeMassCalculator_1_Test {

	private static final double DELTA_NEUTRAL_MASS = 0.0001d;
	private static final double DELTA_MOLECULAR_WEIGHT = 0.001d;
	private static final double DELTA_EXACT_MASS = 0.00001d;

	@Test
	public void test1() {

		/*
		 * https://pubchem.ncbi.nlm.nih.gov/compound/137628494
		 * Molecular Weight: 688.8 g/mol
		 * Exact Mass: 688.36700646
		 * Formula: C34H56O14
		 * Synonym: Fumonisin Py4
		 * IUPAC: (2R)-2-[2-[(5R,6R,7S,9S,18S)-6-[(3R)-3,4-dicarboxybutanoyl]oxy-18-hydroxy-5,9-dimethyl-19-oxoicosan-7-yl]oxy-2-oxoethyl]butanedioic acid
		 */
		assertEquals(688.3676d, MoleculeMassCalculator.getNeutralMass("[M-H]-", 687.3603d), DELTA_NEUTRAL_MASS);
		assertEquals(688.801d, MoleculeMassCalculator.calculateMolecularWeight("C34H56O14"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(688.36701d, MoleculeMassCalculator.calculateExactMass("C34H56O14"), DELTA_EXACT_MASS);
	}

	@Test
	public void test2() {

		/*
		 * Phacidin
		 * C16H22O5
		 * 294.14672366
		 */
		assertEquals(294.1462d, MoleculeMassCalculator.getNeutralMass("[M+H]+", 295.1535d), DELTA_NEUTRAL_MASS);
		assertEquals(294.1467d, MoleculeMassCalculator.getNeutralMass("[2M+H]+", 589.3007d), DELTA_NEUTRAL_MASS);
		assertEquals(294.343d, MoleculeMassCalculator.calculateMolecularWeight("C16H22O5"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(294.14672d, MoleculeMassCalculator.calculateExactMass("C16H22O5"), DELTA_EXACT_MASS);
	}

	@Test
	public void test3() {

		assertEquals(127.764d, MoleculeMassCalculator.getNeutralMass("", 127.764d), DELTA_NEUTRAL_MASS);
		assertEquals(0.0d, MoleculeMassCalculator.calculateMolecularWeight(""), DELTA_MOLECULAR_WEIGHT);
		assertEquals(0.0d, MoleculeMassCalculator.calculateExactMass(""), DELTA_EXACT_MASS);
	}

	@Test
	public void test4() {

		/*
		 * Fumonisin B3
		 * C34H59NO14
		 * 705.39355517
		 */
		assertEquals(705.3930d, MoleculeMassCalculator.getNeutralMass("[M+Na]+", 728.3822d), DELTA_NEUTRAL_MASS);
		assertEquals(705.832d, MoleculeMassCalculator.calculateMolecularWeight("C34H59NO14"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(705.39356d, MoleculeMassCalculator.calculateExactMass("C34H59NO14"), DELTA_EXACT_MASS);
	}

	@Test
	public void test5() {

		/*
		 * 7,8-Dihydroxy calonectrin
		 * C19H26O8
		 * 382.16276758
		 */
		assertEquals(382.1622d, MoleculeMassCalculator.getNeutralMass("[M-H2O+H]+", 365.1589d), DELTA_NEUTRAL_MASS);
		assertEquals(382.406d, MoleculeMassCalculator.calculateMolecularWeight("C19H26O8"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(382.16277d, MoleculeMassCalculator.calculateExactMass("C19H26O8"), DELTA_EXACT_MASS);
	}

	@Test
	public void test6() {

		/*
		 * Trospium
		 * [C25H30NO3]+
		 */
		assertEquals(392.2225d, MoleculeMassCalculator.getNeutralMass("[M]+", 392.222d), DELTA_NEUTRAL_MASS);
		assertEquals(392.512d, MoleculeMassCalculator.calculateMolecularWeight("C25H30NO3"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(392.22257d, MoleculeMassCalculator.calculateExactMass("C25H30NO3"), DELTA_EXACT_MASS);
	}

	@Test
	public void test7() {

		/*
		 * 24-Ethylcoprostanol
		 * C29H52O
		 * 416.40181616
		 */
		assertEquals(416.5033d, MoleculeMassCalculator.getNeutralMass("[M-H2O+H]+", 399.5d), DELTA_NEUTRAL_MASS);
		assertEquals(416.724d, MoleculeMassCalculator.calculateMolecularWeight("C29H52O"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(416.40182d, MoleculeMassCalculator.calculateExactMass("C29H52O"), DELTA_EXACT_MASS);
	}

	@Test
	public void test8() {

		/*
		 * Cer[NP] t34:0
		 * C34H69NO4
		 * 555.52265947
		 */
		assertEquals(555.5201d, MoleculeMassCalculator.getNeutralMass("[M+CH3COOH-H]-", 614.534d), DELTA_NEUTRAL_MASS);
		assertEquals(555.917d, MoleculeMassCalculator.calculateMolecularWeight("C34H69NO4"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(555.52266d, MoleculeMassCalculator.calculateExactMass("C34H69NO4"), DELTA_EXACT_MASS);
	}

	@Test
	public void test9() {

		/*
		 * Bisphenol A bis(3-chloro-2-hydroxypropyl) ether
		 * C21H26Cl2O4
		 * 412.12081458
		 */
		assertEquals(412.1219d, MoleculeMassCalculator.getNeutralMass("[M+NH4]+", 430.1557d), DELTA_NEUTRAL_MASS);
		assertEquals(413.335d, MoleculeMassCalculator.calculateMolecularWeight("C21H26Cl2O4"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(412.12081d, MoleculeMassCalculator.calculateExactMass("C21H26Cl2O4"), DELTA_EXACT_MASS);
	}

	@Test
	public void test10() {

		/*
		 * alpha-Hederin
		 * C41H66O12
		 * 750.45542718
		 */
		assertEquals(750.4554d, MoleculeMassCalculator.getNeutralMass("[M+HCOO]-", 795.45363045183d), DELTA_NEUTRAL_MASS);
		assertEquals(750.957d, MoleculeMassCalculator.calculateMolecularWeight("C41H66O12"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(750.45543d, MoleculeMassCalculator.calculateExactMass("C41H66O12"), DELTA_EXACT_MASS);
	}

	@Test
	public void test11() {

		/*
		 * Milbemycin A4
		 * C32H46O7
		 * 542.32435358
		 */
		assertEquals(542.3033d, MoleculeMassCalculator.getNeutralMass("[M+H-H2O]+", 525.3d), DELTA_NEUTRAL_MASS);
		assertEquals(542.705d, MoleculeMassCalculator.calculateMolecularWeight("C32H46O7"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(542.32435d, MoleculeMassCalculator.calculateExactMass("C32H46O7"), DELTA_EXACT_MASS);
	}

	@Test
	public void test12() {

		/*
		 * Dihydrotestosterone
		 * C19H30O2
		 * 290.2245801
		 */
		assertEquals(290.3771d, MoleculeMassCalculator.getNeutralMass("[M+CH3]+", 305.4d), DELTA_NEUTRAL_MASS);
		assertEquals(290.441d, MoleculeMassCalculator.calculateMolecularWeight("C19H30O2"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(290.22458d, MoleculeMassCalculator.calculateExactMass("C19H30O2"), DELTA_EXACT_MASS);
	}

	@Test
	public void test13() {

		/*
		 * Sphingomyelin d18:2-C23:0
		 * C46H91N2O6P
		 * 798.66147483
		 */
		assertEquals(857.6753d, MoleculeMassCalculator.getNeutralMass("[M+CH3COO]-/[M-CH3]-", 857.6753d), DELTA_NEUTRAL_MASS); // ambiguous, not supported
		assertEquals(799.200d, MoleculeMassCalculator.calculateMolecularWeight("C46H91N2O6P"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(798.66148d, MoleculeMassCalculator.calculateExactMass("C46H91N2O6P"), DELTA_EXACT_MASS);
	}

	@Test
	public void test14() {

		/*
		 * Adenylosuccinate
		 * C14H18N5O11P
		 * 463.07404264
		 */
		assertEquals(463.0740d, MoleculeMassCalculator.getNeutralMass("[M-2H]--", 230.52974d), DELTA_NEUTRAL_MASS);
		assertEquals(463.0740d, MoleculeMassCalculator.getNeutralMass("[M-2H]2-", 230.52974d), DELTA_NEUTRAL_MASS);
		assertEquals(463.294d, MoleculeMassCalculator.calculateMolecularWeight("C14H18N5O11P"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(463.07404d, MoleculeMassCalculator.calculateExactMass("C14H18N5O11P"), DELTA_EXACT_MASS);
	}

	@Test
	public void test15() {

		/*
		 * Neomycin
		 * C23H46N6O13
		 * 614.31228518
		 */
		assertEquals(614.3123d, MoleculeMassCalculator.getNeutralMass("[M+2H]++", 308.16342d), DELTA_NEUTRAL_MASS);
		assertEquals(614.3123d, MoleculeMassCalculator.getNeutralMass("[M+2H]2+", 308.16342d), DELTA_NEUTRAL_MASS);
		assertEquals(614.645d, MoleculeMassCalculator.calculateMolecularWeight("C23H46N6O13"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(614.31229d, MoleculeMassCalculator.calculateExactMass("C23H46N6O13"), DELTA_EXACT_MASS);
	}

	@Test
	public void test16() {

		/*
		 * Yessotoxin
		 * C55H82O21S2
		 * 1142.47900046
		 */
		assertEquals(1142.4790d, MoleculeMassCalculator.getNeutralMass("[M-2H+Na]-", 1163.45367d), DELTA_NEUTRAL_MASS);
		assertEquals(1143.361d, MoleculeMassCalculator.calculateMolecularWeight("C55H82O21S2"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(1142.47900d, MoleculeMassCalculator.calculateExactMass("C55H82O21S2"), DELTA_EXACT_MASS);
	}

	@Test
	public void test17() {

		/*
		 * But-3-enylglucosinolate
		 * C11H19NO9S2
		 * 373.05012237
		 */
		assertEquals(373.0496d, MoleculeMassCalculator.getNeutralMass("[M+K-2H]-", 409.9982d), DELTA_NEUTRAL_MASS);
		assertEquals(373.402d, MoleculeMassCalculator.calculateMolecularWeight("C11H19NO9S2"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(373.05012d, MoleculeMassCalculator.calculateExactMass("C11H19NO9S2"), DELTA_EXACT_MASS);
	}

	@Test
	public void test18() {

		/*
		 * Dehydroepiandrosterone
		 * C19H28O2
		 * 288.20893004
		 */
		assertEquals(288.2139d, MoleculeMassCalculator.getNeutralMass("[M-2H2O+H]+", 253.2d), DELTA_NEUTRAL_MASS);
		assertEquals(288.425d, MoleculeMassCalculator.calculateMolecularWeight("C19H28O2"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(288.20893d, MoleculeMassCalculator.calculateExactMass("C19H28O2"), DELTA_EXACT_MASS);
	}

	@Test
	public void test19() {

		/*
		 * Toluene
		 * https://pubchem.ncbi.nlm.nih.gov/compound/1140
		 * CC1=CC=CC=C1
		 * C7H8
		 * Molecular Weight: 92.13867730812235 g/mol
		 * Exact Mass: 92.062600255
		 */
		assertEquals(92.139d, MoleculeMassCalculator.calculateMolecularWeight("C7H8"), DELTA_MOLECULAR_WEIGHT);
		assertEquals(92.06260d, MoleculeMassCalculator.calculateExactMass("C7H8"), DELTA_EXACT_MASS);
		assertEquals(92.06260d, MoleculeMassCalculator.calculateExactMass(MolecularFormulaManipulator.getMolecularFormula(SmilesSupport.generate("CC1=CC=CC=C1", false))), DELTA_EXACT_MASS);
		assertEquals(92.06260d, MoleculeMassCalculator.calculateExactMass(MolecularFormulaManipulator.getMolecularFormula(SmilesSupport.generate("CC1=CC=CC=C1", true))), DELTA_EXACT_MASS);
	}
}