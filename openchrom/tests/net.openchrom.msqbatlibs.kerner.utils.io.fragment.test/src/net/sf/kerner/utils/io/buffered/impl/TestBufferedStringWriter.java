/*******************************************************************************
 * Copyright (c) 2015, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.io.buffered.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.lazy.LazyStringReader;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-09-13
 */
public class TestBufferedStringWriter {

	private String value;
	private StringWriter sw;
	private BufferedStringWriter writer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		value = "test";
		sw = new StringWriter();
		writer = new BufferedStringWriter(sw);
		new File("src/test/resources/smallTestFileWrite.txt").delete();
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#BufferedStringWriter(java.io.File)} .
	 * 
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public final void testBufferedStringWriterFile() throws IOException {

		new BufferedStringWriter(new File("/dieses/file/kann/hoffentlich/nicht/angelegt/werden"));
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#BufferedStringWriter(java.io.Writer)}.
	 */
	@Test
	public final void testBufferedStringWriterWriter() {

		new BufferedStringWriter(new StringWriter());
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#BufferedStringWriter(java.io.OutputStream)}.
	 */
	@Test
	public final void testBufferedStringWriterOutputStream() {

		new BufferedStringWriter(new ByteArrayOutputStream());
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#write(java.lang.String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteString() throws IOException {

		writer.write(value);
		writer.close();
		assertEquals(value, sw.toString());
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#write(java.lang.String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteString01() throws IOException {

		writer.write(value);
		writer.flush();
		assertEquals(value, sw.toString());
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#close()}.
	 * 
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public final void testClose() throws IOException {

		writer.close();
		writer.write(value);
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#writeNextLine(java.lang.String)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteNextLine() throws IOException {

		writer.writeNextLine(value);
		writer.close();
		assertEquals(value + UtilIO.NEW_LINE_STRING, sw.toString());
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#write(char)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteChar() throws IOException {

		writer.write(value.charAt(0));
		writer.close();
		assertEquals(String.valueOf(value.charAt(0)), sw.toString());
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#write(char[])}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteCharArray() throws IOException {

		writer.write(value.toCharArray());
		writer.close();
		assertEquals(value, sw.toString());
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringWriter#flush()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testFlush() throws IOException {

		writer.write(value);
		writer.flush();
		assertEquals(value, sw.toString());
	}

	@Test
	public final void exampleWriteStringToAWriter() throws IOException {

		// Use BufferedStringWriter to write a string buffered to a java.io.Writer
		StringWriter sw = new StringWriter();
		final BufferedStringWriter writer = new BufferedStringWriter(sw);
		// write first two characters
		writer.write("ab");
		// flush the writer (it's buffer)
		writer.flush();
		assertEquals("ab", sw.toString());
		writer.write("cd");
		// closing the writer will automatically flush it's buffer
		writer.close();
		assertEquals("abcd", sw.toString());
	}

	@Test
	public final void exampleWriteStringToAFile() throws IOException {

		// Use BufferedStringWriter to write a string buffered to a file
		final File file = new File("src/test/resources/smallTestFileWrite.txt");
		final BufferedStringWriter writer = new BufferedStringWriter(file);
		// write first two characters
		writer.write("ab");
		// flush the writer (it's buffer)
		writer.flush();
		assertEquals("ab", new LazyStringReader().read(file));
		writer.write("cd");
		// closing the writer will automatically flush it's buffer
		writer.close();
		assertEquals("abcd", new LazyStringReader().read(file));
	}
}