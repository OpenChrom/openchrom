/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.core;

import junit.framework.TestCase;

public class RelativeResponseCalculator_1_Test extends TestCase {

	private RelativeResponseCalculator calculator;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		calculator = new RelativeResponseCalculator();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() throws Exception {

		assertEquals(0.8687997213664588d, calculator.calculateResponseIUPAC("1,4-Epoxy-p-menthan")); // "1,4-Cineole"
	}

	public void test2() throws Exception {

		assertEquals(0.8687997213664588d, calculator.calculateResponseSMILES("C12(CCC(CC1)(C(C)C)O2)C")); // "1,4-Cineole"
	}

	public void test3() throws Exception {

		assertEquals(0.8515131762486569d, calculator.calculateResponseIUPAC("2-nonanol"));
	}

	public void test4() throws Exception {

		assertEquals(0.8515131762486569d, calculator.calculateResponseSMILES("CC(CCCCCCC)O"));
	}

	public void test5() throws Exception {

		assertEquals(0.709163226258121d, calculator.calculateResponseIUPAC("Tetralin"));
	}

	public void test6() throws Exception {

		assertEquals(0.709163226258121d, calculator.calculateResponseSMILES("C1CCCC2=CC=CC=C12"));
	}

	public void test7() throws Exception {

		assertEquals(1.9199089988431712d, calculator.calculateResponseIUPAC("1,4-dibromobenzene"));
	}

	public void test8() throws Exception {

		assertEquals(1.9199089988431712d, calculator.calculateResponseSMILES("BrC1=CC=C(C=C1)Br"));
	}

	public void test9() throws Exception {

		assertEquals(0.8230647507670505d, calculator.calculateResponseIUPAC("undecanol"));
	}

	public void test10() throws Exception {

		assertEquals(0.8230647507670505d, calculator.calculateResponseSMILES("C(CCCCCCCCCC)O"));
	}

	public void test11() throws Exception {

		assertEquals(0.7110084671942276d, calculator.calculateResponseIUPAC("Styrene"));
	}

	public void test12() throws Exception {

		assertEquals(0.7110084671942276d, calculator.calculateResponseSMILES("C=CC1=CC=CC=C1"));
	}

	public void test13() throws Exception {

		assertEquals(1.218509097206642d, calculator.calculateResponseIUPAC("Pyridine-3-carbonitrile, 2-[2-(3,4-dihydroxyphenyl)-2-oxoethylthio]-4-methoxymethyl-6-methyl-"));
	}

	public void test14() throws Exception {

		assertEquals(1.218509097206642d, calculator.calculateResponseSMILES("OC=1C=C(C=CC1O)C(CSC1=NC(=CC(=C1C#N)COC)C)=O"));
	}

	public void test15() throws Exception {

		assertEquals(1.054904062284301d, calculator.calculateResponseIUPAC("Phenol, 4-(ethoxymethyl)-2-methoxy-"));
	}

	public void test16() throws Exception {

		assertEquals(1.054904062284301d, calculator.calculateResponseSMILES("C(C)OCC1=CC(=C(C=C1)O)OC"));
	}

	public void test17() throws Exception {

		assertEquals(1.4641251546729432d, calculator.calculateResponseIUPAC("Folic Acid"));
	}

	public void test18() throws Exception {

		assertEquals(1.4641251546729432d, calculator.calculateResponseSMILES("C(CC[C@@H](C(=O)O)NC(=O)C1=CC=C(NCC2=CN=C3N=C(N)NC(=O)C3=N2)C=C1)(=O)O"));
	}

	public void test19() throws Exception {

		assertEquals(0.9795334996202579d, calculator.calculateResponseIUPAC("3-Phenylpropylamine, N-acetyl-2-[3-[3,4,5-trimethoxyphenyl]propionyl]-"));
	}

	public void test20() throws Exception {

		assertEquals(0.9795334996202579d, calculator.calculateResponseSMILES("C(C)(=O)NCC(CC1=CC=CC=C1)C(CCC1=CC(=C(C(=C1)OC)OC)OC)=O"));
	}

	public void test21() throws Exception {

		assertEquals(1.0237538924203897d, calculator.calculateResponseIUPAC("1,3,2-Dioxaphosphorinan-2-amine, N,N-dimethyl-5,5-diphenyl-, 2-oxide"));
	}

	public void test22() throws Exception {

		assertEquals(1.0237538924203897d, calculator.calculateResponseSMILES("CN(P1(OCC(CO1)(C1=CC=CC=C1)C1=CC=CC=C1)=O)C"));
	}
}
