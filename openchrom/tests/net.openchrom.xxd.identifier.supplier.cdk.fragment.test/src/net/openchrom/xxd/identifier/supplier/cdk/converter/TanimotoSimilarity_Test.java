/*******************************************************************************
 * Copyright (c) 2025 Lablicate GmbH.
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
package net.openchrom.xxd.identifier.supplier.cdk.converter;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.fingerprint.CircularFingerprinter;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.fingerprint.IBitFingerprint;
import org.openscience.cdk.fingerprint.IFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.similarity.Tanimoto;
import org.openscience.cdk.smiles.SmilesParser;

/**
 * T. T. Tanimoto, (1958) “An elementary mathematical theory of classification and prediction,” IBM Internal Report.
 */
public class TanimotoSimilarity_Test {

	private static SmilesParser smilesParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());

	private static IAtomContainer benzene;
	private static IAtomContainer pyridine;

	@BeforeClass
	public static void setUp() throws InvalidSmilesException {

		benzene = smilesParser.parseSmiles("C1=CC=CC=C1");
		pyridine = smilesParser.parseSmiles("C1=CC=NC=C1");
	}

	@Test
	public void test1() throws CDKException {

		IFingerprinter fingerprinter = new Fingerprinter();
		IBitFingerprint fingerprintBenzene = fingerprinter.getBitFingerprint(benzene);
		IBitFingerprint fingerprintPyridine = fingerprinter.getBitFingerprint(pyridine);
		double similarity = Tanimoto.calculate(fingerprintBenzene, fingerprintPyridine);
		assertEquals(0.2777777777777778d, similarity, 0.0d);
	}

	@Test
	public void test2() throws CDKException {

		IFingerprinter fingerprinter = new CircularFingerprinter(CircularFingerprinter.CLASS_ECFP4);
		IBitFingerprint fingerprintBenzene = fingerprinter.getBitFingerprint(benzene);
		IBitFingerprint fingerprintPyridine = fingerprinter.getBitFingerprint(pyridine);
		double similarity = Tanimoto.calculate(fingerprintBenzene, fingerprintPyridine);
		assertEquals(0.3333333333333333d, similarity, 0.0d);
	}

	@Test
	public void test3() throws CDKException {

		IFingerprinter fingerprinter = new CircularFingerprinter(CircularFingerprinter.CLASS_ECFP6);
		IBitFingerprint fingerprintBenzene = fingerprinter.getBitFingerprint(benzene);
		IBitFingerprint fingerprintPyridine = fingerprinter.getBitFingerprint(pyridine);
		double similarity = Tanimoto.calculate(fingerprintBenzene, fingerprintPyridine);
		assertEquals(0.2727272727272727d, similarity, 0.0d);
	}
}