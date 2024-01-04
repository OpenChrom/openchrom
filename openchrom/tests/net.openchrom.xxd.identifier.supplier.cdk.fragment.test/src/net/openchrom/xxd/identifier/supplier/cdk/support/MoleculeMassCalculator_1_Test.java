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

import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import junit.framework.TestCase;

public class MoleculeMassCalculator_1_Test extends TestCase {

	public void test1() {

		/*
		 * https://pubchem.ncbi.nlm.nih.gov/compound/137628494
		 * Molecular Weight: 688.8 g/mol
		 * Exact Mass: 688.36700646
		 * Formula: C34H56O14
		 * Synonym: Fumonisin Py4
		 * IUPAC: (2R)-2-[2-[(5R,6R,7S,9S,18S)-6-[(3R)-3,4-dicarboxybutanoyl]oxy-18-hydroxy-5,9-dimethyl-19-oxoicosan-7-yl]oxy-2-oxoethyl]butanedioic acid
		 */
		// assertEquals(688.3682407538258d, MoleculeMassCalculator.getNeutralMass("[M-H]-", 687.3603d));
		assertEquals(688.8013716910069d, MoleculeMassCalculator.calculateMolecularWeight("C34H56O14"), 0.000001d);
		assertEquals(688.367006472d, MoleculeMassCalculator.calculateExactMass("C34H56O14"), 0.000001d);
	}

	public void test2() {

		/*
		 * Phacidin
		 * C16H22O5
		 * 294.14672366
		 */
		// assertEquals(294.14555924617423d, MoleculeMassCalculator.getNeutralMass("[M+H]+", 295.1535d));
		assertEquals(294.3434955706221d, MoleculeMassCalculator.calculateMolecularWeight("C16H22O5"), 0.000001d);
		assertEquals(294.146723804d, MoleculeMassCalculator.calculateExactMass("C16H22O5"), 0.000001d);
	}

	public void test3() {

		// assertEquals(127.764d, MoleculeMassCalculator.getNeutralMass("", 127.764d));
		assertEquals(0.0d, MoleculeMassCalculator.calculateMolecularWeight(""), 0.000001d);
		assertEquals(0.0d, MoleculeMassCalculator.calculateExactMass(""), 0.000001d);
	}

	public void test4() {

		/*
		 * Fumonisin B3
		 * C34H59NO14
		 * 705.39355517
		 */
		// assertEquals(705.39243072d, MoleculeMassCalculator.getNeutralMass("[M+Na]+", 728.3822d));
		assertEquals(705.8318971595199d, MoleculeMassCalculator.calculateMolecularWeight("C34H59NO14"), 0.000001d);
		assertEquals(705.393555568d, MoleculeMassCalculator.calculateExactMass("C34H59NO14"), 0.000001d);
	}

	public void test5() {

		/*
		 * 7,8-Dihydroxy calonectrin
		 * C19H26O8
		 * 382.16276758
		 */
		// assertEquals(382.1662456813952d, MoleculeMassCalculator.getNeutralMass("[M-H2O+H]+", 365.1589d));
		assertEquals(382.4056810589974d, MoleculeMassCalculator.calculateMolecularWeight("C19H26O8"), 0.000001d);
		assertEquals(382.162767792d, MoleculeMassCalculator.calculateExactMass("C19H26O8"), 0.000001d);
	}

	public void test6() {

		/*
		 * Trospium
		 * [C25H30NO3]+
		 */
		// assertEquals(392.222d, MoleculeMassCalculator.getNeutralMass("[M]+", 392.222d));
		assertEquals(392.5115380242175d, MoleculeMassCalculator.calculateMolecularWeight("C25H30NO3"), 0.000001d);
		assertEquals(392.22256882d, MoleculeMassCalculator.calculateExactMass("C25H30NO3"), 0.000001d);
	}

	public void test7() {

		/*
		 * 24-Ethylcoprostanol
		 * C29H52O
		 * 416.40181616
		 */
		// assertEquals(416.5073456813952, MoleculeMassCalculator.getNeutralMass("[M-H2O+H]+", 399.5d));
		assertEquals(416.7236651333624d, MoleculeMassCalculator.calculateMolecularWeight("C29H52O"), 0.000001d);
		assertEquals(416.401816284d, MoleculeMassCalculator.calculateExactMass("C29H52O"), 0.000001d);
	}

	public void test8() {

		/*
		 * Cer[NP] t34:0
		 * C34H69NO4
		 * 555.52265947
		 */
		// assertEquals(555.4898960898079d, MoleculeMassCalculator.getNeutralMass("[M+CH3COOH-H]-", 614.534d));
		assertEquals(555.917255422085d, MoleculeMassCalculator.calculateMolecularWeight("C34H69NO4"), 0.000001d);
		assertEquals(555.522659688d, MoleculeMassCalculator.calculateExactMass("C34H69NO4"), 0.000001d);
	}

	public void test9() {

		/*
		 * Bisphenol A bis(3-chloro-2-hydroxypropyl) ether
		 * C21H26Cl2O4
		 * 412.12081458
		 */
		// assertEquals(412.1172337776609d, MoleculeMassCalculator.getNeutralMass("[M+NH4]+", 430.1557d));
		assertEquals(413.335408298664d, MoleculeMassCalculator.calculateMolecularWeight("C21H26Cl2O4"), 0.000001d);
		assertEquals(412.120814672d, MoleculeMassCalculator.calculateExactMass("C21H26Cl2O4"), 0.000001d);
	}

	public void test10() {

		/*
		 * alpha-Hederin
		 * C41H66O12
		 * 750.45542718
		 */
		// assertEquals(750.4361439460774d, MoleculeMassCalculator.getNeutralMass("[M+HCOO]-", 795.45363045183d));
		assertEquals(750.9571206516413d, MoleculeMassCalculator.calculateMolecularWeight("C41H66O12"), 0.000001d);
		assertEquals(750.455427552d, MoleculeMassCalculator.calculateExactMass("C41H66O12"), 0.000001d);
	}

	public void test11() {

		/*
		 * Milbemycin A4
		 * C32H46O7
		 * 542.32435358
		 */
		// assertEquals(542.3073456813952d, MoleculeMassCalculator.getNeutralMass("[M+H-H2O]+", 525.3d));
		assertEquals(542.7046578661876d, MoleculeMassCalculator.calculateMolecularWeight("C32H46O7"), 0.000001d);
		assertEquals(542.324353812d, MoleculeMassCalculator.calculateExactMass("C32H46O7"), 0.000001d);
	}

	public void test12() {

		/*
		 * Dihydrotestosterone
		 * C19H30O2
		 * 290.2245801
		 */
		// assertEquals(290.3654418417346d, MoleculeMassCalculator.getNeutralMass("[M+CH3]+", 305.4d));
		assertEquals(290.4410145088841d, MoleculeMassCalculator.calculateMolecularWeight("C19H30O2"), 0.000001d);
		assertEquals(290.2245802d, MoleculeMassCalculator.calculateExactMass("C19H30O2"), 0.000001d);
	}

	public void test13() {

		/*
		 * Sphingomyelin d18:2-C23:0
		 * C46H91N2O6P
		 * 798.66147483
		 */
		// assertEquals(0.0d, MoleculeMassCalculator.getNeutralMass("[M+CH3COO]-/[M-CH3]-", 0.0d)); // TODO
		assertEquals(799.2000574598827d, MoleculeMassCalculator.calculateMolecularWeight("C46H91N2O6P"), 0.000001d);
		assertEquals(798.661475262d, MoleculeMassCalculator.calculateExactMass("C46H91N2O6P"), 0.000001d);
	}

	public void test14() {

		/*
		 * Adenylosuccinate
		 * C14H18N5O11P
		 * 463.07404264
		 */
		// assertEquals(0.0d, MoleculeMassCalculator.getNeutralMass("[M-2H]--", 0.0d)); // TODO
		assertEquals(463.2939679923395d, MoleculeMassCalculator.calculateMolecularWeight("C14H18N5O11P"), 0.000001d);
		assertEquals(463.074043026d, MoleculeMassCalculator.calculateExactMass("C14H18N5O11P"), 0.000001d);
	}

	public void test15() {

		/*
		 * Neomycin
		 * C23H46N6O13
		 * 614.31228518
		 */
		// assertEquals(0.0d, MoleculeMassCalculator.getNeutralMass("[M+2H]++", 0.0d)); // TODO
		assertEquals(614.6446836027279d, MoleculeMassCalculator.calculateMolecularWeight("C23H46N6O13"), 0.000001d);
		assertEquals(614.312285532d, MoleculeMassCalculator.calculateExactMass("C23H46N6O13"), 0.000001d);
	}

	public void test16() {

		/*
		 * Yessotoxin
		 * C55H82O21S2
		 * 1142.47900046
		 */
		// assertEquals(1142.4797822276516d, MoleculeMassCalculator.getNeutralMass("[M-2H+Na]-", 1163.45367d)); // TODO
		assertEquals(1143.361289602087d, MoleculeMassCalculator.calculateMolecularWeight("C55H82O21S2"), 0.000001d);
		assertEquals(1142.479001644d, MoleculeMassCalculator.calculateExactMass("C55H82O21S2"), 0.000001d);
	}

	public void test17() {

		/*
		 * But-3-enylglucosinolate
		 * C11H19NO9S2
		 * 373.05012237
		 */
		// assertEquals(372.9157803831888d, MoleculeMassCalculator.getNeutralMass("[M+K-2H]-", 409.9982d)); // TODO
		assertEquals(373.4024867285945d, MoleculeMassCalculator.calculateMolecularWeight("C11H19NO9S2"), 0.000001d);
		assertEquals(373.050123188d, MoleculeMassCalculator.calculateExactMass("C11H19NO9S2"), 0.000001d);
	}

	public void test18() {

		/*
		 * Dehydroepiandrosterone
		 * C19H28O2
		 * 288.20893004
		 */
		// assertEquals(288.2226321166162d, MoleculeMassCalculator.getNeutralMass("[M-2H2O+H]+", 253.2d)); // TODO
		assertEquals(288.4251330012329d, MoleculeMassCalculator.calculateMolecularWeight("C19H28O2"), 0.000001d);
		assertEquals(288.208930136d, MoleculeMassCalculator.calculateExactMass("C19H28O2"), 0.000001d);
	}

	public void test19() {

		/*
		 * Toluene
		 * https://pubchem.ncbi.nlm.nih.gov/compound/1140
		 * CC1=CC=CC=C1
		 * C7H8
		 * Molecular Weight: 92.13867730812235 g/mol
		 * Exact Mass: 92.062600255
		 */
		assertEquals(92.13867730812235d, MoleculeMassCalculator.calculateMolecularWeight("C7H8"), 0.000001d);
		assertEquals(92.062600255d, MoleculeMassCalculator.calculateExactMass("C7H8"), 0.000001d);
		assertEquals(92.062600255d, MoleculeMassCalculator.calculateExactMass(MolecularFormulaManipulator.getMolecularFormula(SmilesSupport.generate("CC1=CC=CC=C1", false))), 0.000001d);
		assertEquals(92.062600255d, MoleculeMassCalculator.calculateExactMass(MolecularFormulaManipulator.getMolecularFormula(SmilesSupport.generate("CC1=CC=CC=C1", true))), 0.000001d);
	}
}