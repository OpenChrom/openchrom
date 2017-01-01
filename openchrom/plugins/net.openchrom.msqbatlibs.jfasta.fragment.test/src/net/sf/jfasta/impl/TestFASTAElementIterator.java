/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
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
 */
package net.sf.jfasta.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFileReader;
import net.sf.kerner.utils.io.UtilIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-07-02
 * 
 */
public class TestFASTAElementIterator {

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

	// START SNIPPET: example_1
	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testExample01() throws IOException {

		// Read a multi FASTA file element by element.
		final File file = new File("src/test/resources/fasta02.fasta");
		final FASTAFileReader reader = new FASTAFileReaderImpl(file);
		final FASTAElementIterator it = reader.getIterator();
		while(it.hasNext()) {
			final FASTAElement el = it.next();
			assertTrue(el.getHeader().contains("Homo sapiens spastin (SPAST)"));
		}
	}

	// END SNIPPET: example_1
	@Test
	public final void testExample02() throws IOException {

		// Read a multi FASTA file element by element. Throw an exception, if
		// FASTA sequence contains characters that are invalid for a DNA
		// sequence.
		final File file = new File("src/test/resources/fasta02.fasta");
		final FASTAFileReader reader = new FASTAFileReaderImpl(file, FASTAFileReaderImpl.DNA_ALPHABET_IGNORE_CASE);
		final FASTAElementIterator it = reader.getIterator();
		while(it.hasNext()) {
			final FASTAElement el = it.next();
			assertTrue(el.getHeader().contains("Homo sapiens spastin (SPAST)"));
		}
	}

	// START SNIPPET: example_2
	/**
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testNext() throws IOException {

		final String in = ">header" + UtilIO.NEW_LINE_STRING + "ATGC" + UtilIO.NEW_LINE_STRING + ">header2" + UtilIO.NEW_LINE_STRING + "ATGC";
		final FASTAElementIterator it = new FASTAFileReaderImpl(new StringReader(in)).getIterator();
		while(it.hasNext()) {
			assertEquals("ATGC", it.next().getSequence());
		}
	}
	// END SNIPPET: example_2
}
