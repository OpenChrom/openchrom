/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
/**
 * 
 *
 */
package net.sf.jfasta.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFile;
import net.sf.jfasta.FASTAFileReader;
import net.sf.kerner.utils.io.UtilIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-10-06
 * 
 */
public class TestFASTAFileReaderImpl {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#FASTAFileReaderImpl(java.io.BufferedReader)} .
	 */
	@Test
	@Ignore
	public final void testFASTAFileReaderImplBufferedReader() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#FASTAFileReaderImpl(java.io.File)} .
	 */
	@Test
	@Ignore
	public final void testFASTAFileReaderImplFile() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#FASTAFileReaderImpl(java.io.InputStream)} .
	 */
	@Test
	@Ignore
	public final void testFASTAFileReaderImplInputStream() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#FASTAFileReaderImpl(java.io.Reader)} .
	 */
	@Test
	@Ignore
	public final void testFASTAFileReaderImplReader() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#getIterator()}.
	 */
	@Test
	@Ignore
	public final void testGetIterator() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#read()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testRead() throws IOException {

		final String in = ">header" + UtilIO.NEW_LINE_STRING + "ATGC";
		final FASTAFile fasta = new FASTAFileReaderImpl(new StringReader(in)).read();
		assertEquals(1, fasta.size());
		final List<FASTAElement> elements = new ArrayList<FASTAElement>(fasta);
		assertEquals("header", elements.get(0).getHeader());
		assertEquals("ATGC", elements.get(0).getSequence());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#read()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testRead01() throws IOException {

		final String in = ">header" + UtilIO.NEW_LINE_STRING + "ATGC" + UtilIO.NEW_LINE_STRING + ">header2" + UtilIO.NEW_LINE_STRING + "AA";
		final FASTAFile fasta = new FASTAFileReaderImpl(new StringReader(in)).read();
		assertEquals(2, fasta.size());
		final List<FASTAElement> elements = new ArrayList<FASTAElement>(fasta);
		assertEquals("header", elements.get(0).getHeader());
		assertEquals("ATGC", elements.get(0).getSequence());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#read()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testRead03() throws IOException {

		final File file = new File("src/test/resources/seq.100m.txt");
		final FASTAFileReader reader = new FASTAFileReaderImpl(file, FASTAFileReaderImpl.DNA_ALPHABET_IGNORE_CASE);
		// long start = System.currentTimeMillis();
		@SuppressWarnings("unused")
		final FASTAFile fasta = reader.read();
		// long stop = System.currentTimeMillis();
		// log.debug("reading 5MB fasta file took " + new TimePeriod(start, stop).getDuration() + "ms (including content checking case insensitive)");
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#read()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testRead04() throws IOException {

		final File file = new File("src/test/resources/seq.100m.txt");
		final FASTAFileReader reader = new FASTAFileReaderImpl(file, FASTAFileReaderImpl.DNA_ALPHABET_IGNORE_CASE);
		// long start = System.currentTimeMillis();
		@SuppressWarnings("unused")
		final FASTAFile fasta = reader.read();
		// long stop = System.currentTimeMillis();
		// log.debug("reading 5MB fasta file took " + new TimePeriod(start, stop).getDuration() + "ms (including content checking case sensitive)");
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#read()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testRead05() throws IOException {

		final File file = new File("src/test/resources/seq.100m.txt");
		final FASTAFileReader reader = new FASTAFileReaderImpl(file);
		// long start = System.currentTimeMillis();
		@SuppressWarnings("unused")
		final FASTAFile fasta = reader.read();
		// long stop = System.currentTimeMillis();
		// log.debug("reading 5MB fasta file took " + new TimePeriod(start, stop).getDuration() + "ms (without content checking)");
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileReaderImpl#read()}.
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testRead06() throws IOException {

		final String in = ">header" + UtilIO.NEW_LINE_STRING + "ATGC" + UtilIO.NEW_LINE_STRING + ">header2" + UtilIO.NEW_LINE_STRING + "AX";
		new FASTAFileReaderImpl(new StringReader(in), FASTAFileReaderImpl.DNA_ALPHABET_IGNORE_CASE_STRICT).read();
	}
}
