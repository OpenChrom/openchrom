/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFile;
import net.sf.kerner.utils.io.UtilIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-30
 * 
 */
public class TestFASTAFileWriter {

	private FASTAFileWriter writer;
	private StringWriter out;
	private final static File file = new File("src/test/resources/fasta.out.fasta");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		out = new StringWriter();
		writer = new FASTAFileWriter(out);
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#FASTAFileWriter(java.io.File)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testFASTAFileWriterFile() throws IOException {

		writer = new FASTAFileWriter(file);
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#FASTAFileWriter(java.io.OutputStream)} .
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public final void testFASTAFileWriterOutputStream() throws FileNotFoundException {

		writer = new FASTAFileWriter(new FileOutputStream(file));
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#FASTAFileWriter(java.io.Writer)} .
	 */
	@Test
	public final void testFASTAFileWriterWriter() {

		writer = new FASTAFileWriter(out);
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#write(net.sf.jfasta.FASTAFile)} .
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteFASTAFile() throws IOException {

		FASTAElement e = new FASTAElementImpl("header", "seq");
		FASTAFile f = new FASTAFileImpl(e);
		writer.write(f);
		writer.close();
		assertEquals(f.toString() + UtilIO.NEW_LINE_STRING, out.toString());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#write(net.sf.jfasta.FASTAFile)} .
	 * 
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public final void testWriteFASTAFile01() throws IOException {

		writer.write((FASTAFile)null);
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#write(net.sf.jfasta.FASTAFile)} .
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteFASTAFile02() throws IOException {

		FASTAElement e1 = new FASTAElementImpl("header", "seq");
		FASTAElement e2 = new FASTAElementImpl("header2", "seq2");
		FASTAFile f = new FASTAFileImpl(Arrays.asList(e1, e2));
		writer.write(f);
		writer.close();
		assertEquals(f.toString() + UtilIO.NEW_LINE_STRING, out.toString());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#write(net.sf.jfasta.FASTAElement)} .
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteFASTAElement() throws IOException {

		FASTAElement e1 = new FASTAElementImpl("header", "seq");
		writer.write(e1);
		writer.flush();
		assertEquals(e1.toString() + UtilIO.NEW_LINE_STRING, out.toString());
	}

	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#write(net.sf.jfasta.FASTAElement)} .
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteFASTAElement01() throws IOException {

		FASTAElement e1 = new FASTAElementImpl("header", "seq");
		FASTAElement e2 = new FASTAElementImpl("header2", "seq2");
		writer.write(e1);
		writer.write(e2);
		writer.flush();
		assertEquals(new FASTAFileImpl(Arrays.asList(e1, e2)).toString() + UtilIO.NEW_LINE_STRING, out.toString());
	}

	// START SNIPPET: example_3
	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#write(net.sf.jfasta.FASTAElement)} .
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testExample03() throws IOException {

		// Write a FASTA file element by element
		FASTAElement e1 = new FASTAElementImpl("header", "seq");
		FASTAElement e2 = new FASTAElementImpl("header2", "seq2");
		FASTAElement e3 = new FASTAElementImpl("head", "atgc");
		List<FASTAElement> elements = Arrays.asList(e1, e2, e3);
		FASTAFileWriter writer = new FASTAFileWriter(out);
		for(FASTAElement e : elements) {
			writer.write(e);
		}
		writer.close();
		assertEquals(new FASTAFileImpl(elements).toString() + UtilIO.NEW_LINE_STRING, out.toString());
	}

	// END SNIPPET: example_3
	// START SNIPPET: example_4
	/**
	 * Test method for {@link net.sf.jfasta.impl.FASTAFileWriter#write(net.sf.jfasta.FASTAElement)} .
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testExample04() throws IOException {

		// Write a FASTA file at once
		FASTAElement e1 = new FASTAElementImpl("header", "seq");
		FASTAElement e2 = new FASTAElementImpl("header2", "seq2");
		FASTAElement e3 = new FASTAElementImpl("head", "atgc");
		FASTAFile file = new FASTAFileImpl(Arrays.asList(e1, e2, e3));
		FASTAFileWriter writer = new FASTAFileWriter(out);
		writer.write(file);
		writer.close();
		assertEquals(file.toString() + UtilIO.NEW_LINE_STRING, out.toString());
	}

	// END SNIPPET: example_4
	/**
	 * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractBufferedWriter#close()}.
	 */
	@Test
	@Ignore
	public final void testClose() {

		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractBufferedWriter#flush()}.
	 */
	@Test
	@Ignore
	public final void testFlush() {

		fail("Not yet implemented"); // TODO
	}
}
