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
package net.sf.kerner.utils.io.lazy;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-22
 */
public class TestLazyStringWriter {

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
	 * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringWriter#LazyStringWriter(Object)}.
	 */
	@Test
	public final void testLazyStringWriterString() {

		new LazyStringWriter("test");
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringWriter#LazyStringWriter(Object)}.
	 */
	@Test(expected = NullPointerException.class)
	public final void testLazyStringWriterString01() {

		new LazyStringWriter(null);
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringWriter#write(java.io.Writer)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testWriteWriter() throws IOException {

		final java.io.StringWriter wr = new java.io.StringWriter();
		new LazyStringWriter("test").write(wr);
		assertEquals("test", wr.toString());
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringWriter#write(java.io.File)}.
	 * 
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public final void testWriteFile() throws IOException {

		// skip this, since this method delegates to write(java.io.Writer) using
		// new java.io.FileWriter(file).
		// can only break, if java.io.FileWriter breaks.
		new LazyStringWriter("test").write(new File("/dieses/file/kann/hoffentlich/nicht/angelegt/werden"));
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringWriter#write(java.io.OutputStream)} .
	 * 
	 * @throws IOException
	 */
	@Test(expected = IOException.class)
	public final void testWriteOutputStream() throws IOException {

		// skip this, since this methods delegates to write(java.io.Writer)
		// using IOUtils.outputStreamToWriter(stream).
		// can only break, if IOUtils.outputStreamToWriter() breaks.
		new LazyStringWriter("test").write(new FileOutputStream(new File("/dieses/file/kann/hoffentlich/nicht/angelegt/werden")));
	}

	/**
	 * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringWriter#write(java.io.File)}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void example() throws IOException {

		final java.io.StringWriter wr = new java.io.StringWriter();
		new LazyStringWriter("Hallo Welt!").write(wr);
		assertEquals("Hallo Welt!", wr.toString());
	}
}
