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
/**
 * 
 */
package net.sf.kerner.utils.math;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-21
 */
public class TestArithmeticSavety {

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
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#rangeCheckInteger(java.math.BigInteger)} .
	 */
	@Test
	public final void testRangeCheckInteger() {

		ArithmeticSavety.rangeCheckInteger(BigInteger.valueOf(Integer.MAX_VALUE));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#rangeCheckInteger(java.math.BigInteger)} .
	 */
	@Test(expected = ArithmeticException.class)
	public final void testRangeCheckInteger01() {

		ArithmeticSavety.rangeCheckInteger(BigInteger.valueOf(Integer.MAX_VALUE).add(BigInteger.ONE));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#rangeCheckInteger(java.math.BigInteger)} .
	 */
	@Test(expected = ArithmeticException.class)
	public final void testRangeCheckInteger02() {

		ArithmeticSavety.rangeCheckInteger(BigInteger.valueOf(Integer.MIN_VALUE).subtract(BigInteger.ONE));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#rangeCheckLong(java.math.BigInteger)} .
	 */
	@Test
	public final void testRangeCheckLong() {

		ArithmeticSavety.rangeCheckLong(BigInteger.valueOf(Long.MAX_VALUE));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#rangeCheckLong(java.math.BigInteger)} .
	 */
	@Test(expected = ArithmeticException.class)
	public final void testRangeCheckLong01() {

		ArithmeticSavety.rangeCheckLong(BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#rangeCheckLong(java.math.BigInteger)} .
	 */
	@Test(expected = ArithmeticException.class)
	public final void testRangeCheckLong02() {

		ArithmeticSavety.rangeCheckLong(BigInteger.valueOf(Long.MIN_VALUE).subtract(BigInteger.ONE));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#addInt(int, int)}.
	 */
	@Test(expected = ArithmeticException.class)
	public final void testAddInt() {

		ArithmeticSavety.addInt(Integer.MAX_VALUE, 1);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#addInt(int, int)}.
	 */
	@Test(expected = ArithmeticException.class)
	public final void testAddInt01() {

		ArithmeticSavety.addInt(Integer.MIN_VALUE, -1);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#addLong(long, long)}.
	 */
	@Test
	public final void testAddLong() {

		assertEquals(2L, ArithmeticSavety.addLong(Long.valueOf(1), Long.valueOf(1)));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#addLong(long, long)}.
	 */
	@Test(expected = ArithmeticException.class)
	public final void testAddLong01() {

		ArithmeticSavety.addLong(Long.MAX_VALUE, 1);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#addLong(long, long)}.
	 */
	@Test(expected = ArithmeticException.class)
	public final void testAddLong02() {

		ArithmeticSavety.addLong(Long.MIN_VALUE, -1);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#multiply(int, int)}.
	 */
	@Test(expected = ArithmeticException.class)
	public final void testMultiplyIntInt() {

		ArithmeticSavety.multiply(Integer.MIN_VALUE, -2);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#multiply(int, int)}.
	 */
	@Test(expected = ArithmeticException.class)
	public final void testMultiplyIntInt01() {

		ArithmeticSavety.multiply(Integer.MAX_VALUE, 2);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#multiply(long, long)}.
	 */
	@Test
	public final void testMultiplyLongLong() {

		assertEquals(4L, ArithmeticSavety.multiply(Long.valueOf(2), Long.valueOf(2)));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#increment(java.lang.Integer)} .
	 */
	@Test(expected = ArithmeticException.class)
	public final void testIncrement() {

		ArithmeticSavety.increment(Integer.MAX_VALUE);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#increment(java.lang.Integer)} .
	 */
	@Test
	public final void testIncrement01() {

		assertEquals(Integer.valueOf(1), ArithmeticSavety.increment(Integer.valueOf(0)));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#add(java.lang.Integer, java.lang.Integer)} .
	 */
	@Test
	public final void testAdd() {

		assertEquals(Integer.valueOf(1), ArithmeticSavety.add(Integer.valueOf(0), Integer.valueOf(1)));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.math.ArithmeticSavety#add(java.lang.Integer, java.lang.Integer)} .
	 */
	@Test(expected = ArithmeticException.class)
	public final void testAdd01() {

		ArithmeticSavety.add(Integer.MAX_VALUE, Integer.valueOf(1));
	}
}
