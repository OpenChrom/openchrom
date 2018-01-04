/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.peptidesearcher;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPeptideSearcher {

	private PeptideSearcher s;
	private Result r;

	@Before
	public void setUp() throws Exception {

		s = new PeptideSearcher();
	}

	@After
	public void tearDown() throws Exception {

		s = null;
		r = null;
	}

	@Test
	public final void testGetFromDBFile() {

	}

	final Result testReduceToProteotipic(final String i, final String db, final DatabaseID type) throws IOException {

		return s.reduceToProteotipic(i, Arrays.asList(new File(db)), type);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testReduceToProteotipic01() throws IOException {

		testReduceToProteotipic("", "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.ACCESSION);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testReduceToProteotipic02() throws IOException {

		System.out.println(testReduceToProteotipic(null, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.ACCESSION));
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testReduceToProteotipic03() throws IOException {

		System.out.println(testReduceToProteotipic("FLYEYSR", "src/test/resources/20140522_Bovine_SwissProt.fasta", null));
	}

	@Test
	public final void testReduceToProteotipic04() throws IOException {

		testReduceToProteotipicAllForSeqSwissProt("FLYEYSR");
	}

	@Test
	public final void testReduceToProteotipic04b() throws IOException {

		testReduceToProteotipicAllForSeqSwissProtCached("FLYEYSR");
	}

	@Test
	public final void testReduceToProteotipic05() throws IOException {

		testReduceToProteotipicAllForSeqSwissProt("LSQKFPK");
	}

	@Test
	public final void testReduceToProteotipic05b() throws IOException {

		testReduceToProteotipicAllForSeqSwissProtCached("LSQKFPK");
	}

	@Test
	public final void testReduceToProteotipic06() throws IOException {

		testReduceToProteotipicAllForSeqTrmbl("FLYEYSR");
	}

	@Test
	public final void testReduceToProteotipic06b() throws IOException {

		testReduceToProteotipicAllForSeqTrmblCached("FLYEYSR");
	}

	@Test
	public final void testReduceToProteotipic07() throws IOException {

		testReduceToProteotipicAllForSeqTrmbl("LSQKFPK");
	}

	@Test
	public final void testReduceToProteotipic07b() throws IOException {

		testReduceToProteotipicAllForSeqTrmblCached("LSQKFPK");
	}

	@Test
	public final void testReduceToProteotipic08() throws IOException {

		testReduceToProteotipicAllForSeqSwissProtTrmbl("FLYEYSR");
	}

	@Test
	public final void testReduceToProteotipic08b() throws IOException {

		testReduceToProteotipicAllForSeqSwissProtTrmblCached("FLYEYSR");
	}

	@Test
	public final void testReduceToProteotipic09() throws IOException {

		testReduceToProteotipicAllForSeqSwissProtTrmbl("LSQKFPK");
	}

	@Test
	public final void testReduceToProteotipic09b() throws IOException {

		testReduceToProteotipicAllForSeqSwissProtTrmblCached("LSQKFPK");
	}

	final void testReduceToProteotipicAllForSeqSwissProt(final String i) throws IOException {

		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.ACCESSION);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.GENE);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.PROTEIN);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		// e.g. sp|P02769|ALBU_BOVIN Serum albumin OS=Bos taurus GN=ALB PE=1
		// SV=4 PROTEOTYPIC
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.PROTEIN_GENE);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		// Fond only once, so automatically proteotypic independent of
		// identifier
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.SPECIES);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
	}

	final void testReduceToProteotipicAllForSeqSwissProtCached(final String i) throws IOException {

		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.ACCESSION);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.GENE);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.PROTEIN);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		// e.g. sp|P02769|ALBU_BOVIN Serum albumin OS=Bos taurus GN=ALB PE=1
		// SV=4 PROTEOTYPIC
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.PROTEIN_GENE);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		// Fond only once, so automatically proteotypic independent of
		// identifier
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt.fasta", DatabaseID.SPECIES);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
	}

	final void testReduceToProteotipicAllForSeqSwissProtTrmbl(final String i) throws IOException {

		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.ACCESSION);
		assertEquals(Result.Type.DEGEN, r.type);
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.GENE);
		assertEquals(Result.Type.DEGEN, r.type);
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.PROTEIN);
		assertEquals(Result.Type.DEGEN, r.type);
		// e.g. sp|P02769|ALBU_BOVIN Serum albumin OS=Bos taurus GN=ALB PE=1
		// SV=4, tr|G3MYZ3|G3MYZ3_BOVIN Uncharacterized protein OS=Bos taurus
		// GN=AFM PE=4 SV=1 DEGEN
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.PROTEIN_GENE);
		assertEquals(Result.Type.DEGEN, r.type);
		// all have same species
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.SPECIES);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
	}

	final void testReduceToProteotipicAllForSeqSwissProtTrmblCached(final String i) throws IOException {

		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.ACCESSION);
		assertEquals(Result.Type.DEGEN, r.type);
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.GENE);
		assertEquals(Result.Type.DEGEN, r.type);
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.PROTEIN);
		assertEquals(Result.Type.DEGEN, r.type);
		// e.g. sp|P02769|ALBU_BOVIN Serum albumin OS=Bos taurus GN=ALB PE=1
		// SV=4, tr|G3MYZ3|G3MYZ3_BOVIN Uncharacterized protein OS=Bos taurus
		// GN=AFM PE=4 SV=1 DEGEN
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.PROTEIN_GENE);
		assertEquals(Result.Type.DEGEN, r.type);
		// all have same species
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_SwissProt-TrEMBL.fasta", DatabaseID.SPECIES);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
	}

	final void testReduceToProteotipicAllForSeqTrmbl(final String i) throws IOException {

		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.ACCESSION);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.GENE);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.PROTEIN);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		// e.g. tr|G3MYZ3|G3MYZ3_BOVIN Uncharacterized protein OS=Bos taurus
		// GN=AFM PE=4 SV=1 PROTEOTYPIC
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.PROTEIN_GENE);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		// Fond only once, so automatically proteotypic independent of
		// identifier
		r = testReduceToProteotipic(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.SPECIES);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
	}

	final void testReduceToProteotipicAllForSeqTrmblCached(final String i) throws IOException {

		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.ACCESSION);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.GENE);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.PROTEIN);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		// e.g. tr|G3MYZ3|G3MYZ3_BOVIN Uncharacterized protein OS=Bos taurus
		// GN=AFM PE=4 SV=1 PROTEOTYPIC
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.PROTEIN_GENE);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
		// Fond only once, so automatically proteotypic independent of
		// identifier
		r = testReduceToProteotipicCached(i, "src/test/resources/20140522_Bovine_TrEMBL.fasta", DatabaseID.SPECIES);
		assertEquals(Result.Type.PROTEOTYPIC, r.type);
	}

	final Result testReduceToProteotipicCached(final String i, final String db, final DatabaseID type) throws IOException {

		s.setCacheFASTAFile(true);
		return s.reduceToProteotipic(i, Arrays.asList(new File(db)), type);
	}
}
