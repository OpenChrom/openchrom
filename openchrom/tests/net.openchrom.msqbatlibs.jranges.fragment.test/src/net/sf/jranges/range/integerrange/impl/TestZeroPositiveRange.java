/*******************************************************************************
 * Copyright (c) 2015, 2021 Lablicate GmbH.
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
package net.sf.jranges.range.integerrange.impl;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.jranges.range.RangeException;

/**
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-12-08
 * 
 */
public class TestZeroPositiveRange {

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
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int)} .
	 */
	@Test
	public final void testZeroPositiveRangeIntInt() {

		new ZeroPositiveIntegerRange(0, 5);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int)} .
	 */
	@Test
	public final void testZeroPositiveRangeIntInt01() {

		new ZeroPositiveIntegerRange(0, Integer.MAX_VALUE);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int)} .
	 */
	@Test
	public final void testZeroPositiveRangeIntInt02() {

		new ZeroPositiveIntegerRange(34, Integer.MAX_VALUE);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int)} .
	 */
	@Test(expected = RangeException.class)
	public final void testZeroPositiveRangeIntInt03() {

		new ZeroPositiveIntegerRange(-1, Integer.MAX_VALUE);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int)} .
	 */
	@Test(expected = RangeException.class)
	public final void testZeroPositiveRangeIntInt04() {

		new ZeroPositiveIntegerRange(-1, 1);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int)} .
	 */
	@Test
	public final void testZeroPositiveRangeIntInt05() {

		new ZeroPositiveIntegerRange(0, 0);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int, int)} .
	 */
	@Test
	public final void testZeroPositiveRangeIntIntInt() {

		new ZeroPositiveIntegerRange(0, 0, 1);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int, int)} .
	 */
	@Test
	public final void testZeroPositiveRangeIntIntInt01() {

		new ZeroPositiveIntegerRange(0, 8, 2);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int, int)} .
	 */
	@Test(expected = RangeException.class)
	public final void testZeroPositiveRangeIntIntInt02() {

		new ZeroPositiveIntegerRange(0, 9, 2);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#ZeroPositiveRange(int, int, int)} .
	 */
	@Test(expected = RangeException.class)
	public final void testZeroPositiveRangeIntIntInt03() {

		new ZeroPositiveIntegerRange(-1, 8, 2);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.impl.ZeroPositiveIntegerRange#newInstange(int, int, int, int)} .
	 */
	@Test
	@Ignore("delegate to constructor")
	public final void testNewInstangeIntIntIntInt() {

		fail("Not yet implemented"); // TODO
	}
}
