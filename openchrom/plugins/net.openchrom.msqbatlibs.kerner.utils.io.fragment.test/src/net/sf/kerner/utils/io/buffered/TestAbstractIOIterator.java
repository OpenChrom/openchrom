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
 */
package net.sf.kerner.utils.io.buffered;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.NoSuchElementException;

import net.sf.kerner.utils.io.buffered.impl.BufferedStringReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-03-13
 */
public class TestAbstractIOIterator {

	private static class My extends AbstractIOIterator<String> {

		protected final BufferedStringReader reader2 = new BufferedStringReader(super.reader);

		public My(Reader reader) throws IOException {
			super(reader);
		}

		protected String doRead() throws IOException {

			String s = reader2.nextString();
			return s;
		}
	}

	private String in;
	private AbstractIOIterator<String> it;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		in = "input001";
		it = new My(new StringReader(in));
	}

	@After
	public void tearDown() throws Exception {

		it = null;
		in = null;
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#hasNext()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testHasNext() throws IOException {

		assertTrue(it.hasNext());
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#hasNext()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testHasNext01() throws IOException {

		it.next();
		assertFalse(it.hasNext());
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#hasNext()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testHasNext02() throws IOException {

		in = "";
		it = new My(new StringReader(in));
		assertFalse(it.hasNext());
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#next()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public final void testNext() throws IOException {

		String s = it.next();
		assertEquals(in, s);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#next()}.
	 * 
	 * @throws IOException
	 */
	@Test(expected = NoSuchElementException.class)
	public final void testNext01() throws IOException {

		it.next();
		it.next();
	}

	@Test
	public final void testhasNext01() throws IOException {

		for(int i = 0; i < 100; i++) {
			assertTrue(it.hasNext());
		}
		it.next();
		assertFalse(it.hasNext());
	}
}
