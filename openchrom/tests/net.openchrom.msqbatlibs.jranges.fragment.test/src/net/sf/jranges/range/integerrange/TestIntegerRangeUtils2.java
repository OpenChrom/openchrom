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
package net.sf.jranges.range.integerrange;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.jranges.range.integerrange.impl.RangeIntegerDummy;

/**
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-17
 * 
 */
public class TestIntegerRangeUtils2 {

	private RangeInteger range1;
	private RangeInteger range2;
	private RangeInteger range3;
	private RangeInteger range4;

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
	 * Test method for {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#positionFrequencies(java.util.Collection, java.util.Collection)} .
	 */
	@Test
	public final void testPositionFrequencies() {

	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#medianPositionFrequencies(java.util.Collection, java.util.Collection)} .
	 */
	@Test
	public final void testMedianPositionFrequencies() {

		range1 = new RangeIntegerDummy(1, 10000);
		range2 = range1;
		assertEquals(1, UtilsRangeInteger.medianPositionFrequencies(Arrays.asList(range1), Arrays.asList(range2)), 0);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#medianPositionFrequencies(java.util.Collection, java.util.Collection)} .
	 */
	@Test
	public final void testMedianPositionFrequencies01() {

		range1 = new RangeIntegerDummy(10001, 20000);
		range2 = new RangeIntegerDummy(1, 100000);
		assertEquals(0.1, UtilsRangeInteger.medianPositionFrequencies(Arrays.asList(range1), Arrays.asList(range2)), 0);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#medianPositionFrequencies(java.util.Collection, java.util.Collection)} .
	 */
	@Test
	public final void testMedianPositionFrequencies02() {

		range1 = new RangeIntegerDummy(10001, 20000);
		range2 = new RangeIntegerDummy(20001, 30000);
		range3 = new RangeIntegerDummy(1, 100000);
		assertEquals(0.2, UtilsRangeInteger.medianPositionFrequencies(Arrays.asList(range1, range2), Arrays.asList(range3)), 0);
	}

	/**
	 * Test method for {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#medianPositionFrequencies(java.util.Collection, java.util.Collection)} .
	 */
	@Test
	public final void testMedianPositionFrequencies03() {

		range1 = new RangeIntegerDummy(10001, 20000);
		range2 = new RangeIntegerDummy(20001, 30000);
		range3 = new RangeIntegerDummy(1, 50000);
		range4 = new RangeIntegerDummy(50001, 100000);
		assertEquals(0.2, UtilsRangeInteger.medianPositionFrequencies(Arrays.asList(range1, range2), Arrays.asList(range3, range4)), 0);
	}
}
