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
package net.sf.kerner.utils.math;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUtilMath {

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

	@Test
	public final void testFactorial01() {

		final BigInteger result = UtilMath.factorial(5);
		assertEquals(new BigInteger("120"), result);
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testFactorial02() {

		UtilMath.factorial(-1);
	}

	@Test
	public final void testFactorial03() {

		final BigInteger result = UtilMath.factorial(0);
		assertEquals(new BigInteger("1"), result);
	}

	@Test
	public final void testFactorial04() {

		final BigInteger result = UtilMath.factorial(1);
		assertEquals(new BigInteger("1"), result);
	}

	@Test
	public final void testFactorial05() {

		final BigInteger result = UtilMath.factorial(2);
		assertEquals(new BigInteger("2"), result);
	}

	@Test
	public final void testRound01() {

		final double d1 = UtilMath.round(1409.70105, 4);
		final double d2 = UtilMath.round(1409.7011, 4);
		assertEquals(d1, d2, 0);
	}

	@Test
	public final void testRound02() {

		final double d1 = Double.NaN;
		assertEquals(d1, UtilMath.round(d1, 10), 0);
	}

	@Test
	public final void testRound03() {

		final double d1 = Double.POSITIVE_INFINITY;
		assertEquals(d1, UtilMath.round(d1, 10), 0);
	}

	@Test
	public final void testRound04() {

		final double d1 = Double.NEGATIVE_INFINITY;
		assertEquals(d1, UtilMath.round(d1, 10), 0);
	}
}
