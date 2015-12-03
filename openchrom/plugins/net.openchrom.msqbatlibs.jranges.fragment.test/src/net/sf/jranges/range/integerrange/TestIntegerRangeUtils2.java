/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * 
 */
package net.sf.jranges.range.integerrange;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.jranges.range.integerrange.UtilsRangeInteger;
import net.sf.jranges.range.integerrange.impl.RangeIntegerDummy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#positionFrequencies(java.util.Collection, java.util.Collection)}
	 * .
	 */
	@Test
	public final void testPositionFrequencies() {

	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#medianPositionFrequencies(java.util.Collection, java.util.Collection)}
	 * .
	 */
	@Test
	public final void testMedianPositionFrequencies() {
		range1 = new RangeIntegerDummy(1, 10000);
		range2 = range1;
		assertEquals(1, UtilsRangeInteger.medianPositionFrequencies(Arrays.asList(range1), Arrays.asList(range2)), 0);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#medianPositionFrequencies(java.util.Collection, java.util.Collection)}
	 * .
	 */
	@Test
	public final void testMedianPositionFrequencies01() {
		range1 = new RangeIntegerDummy(10001, 20000);
		range2 = new RangeIntegerDummy(1, 100000);
		assertEquals(0.1, UtilsRangeInteger.medianPositionFrequencies(Arrays.asList(range1), Arrays.asList(range2)), 0);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#medianPositionFrequencies(java.util.Collection, java.util.Collection)}
	 * .
	 */
	@Test
	public final void testMedianPositionFrequencies02() {
		range1 = new RangeIntegerDummy(10001, 20000);
		range2 = new RangeIntegerDummy(20001, 30000);
		range3 = new RangeIntegerDummy(1, 100000);
		assertEquals(0.2,
				UtilsRangeInteger.medianPositionFrequencies(Arrays.asList(range1, range2), Arrays.asList(range3)), 0);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#medianPositionFrequencies(java.util.Collection, java.util.Collection)}
	 * .
	 */
	@Test
	public final void testMedianPositionFrequencies03() {
		range1 = new RangeIntegerDummy(10001, 20000);
		range2 = new RangeIntegerDummy(20001, 30000);
		range3 = new RangeIntegerDummy(1, 50000);
		range4 = new RangeIntegerDummy(50001, 100000);
		assertEquals(
				0.2,
				UtilsRangeInteger.medianPositionFrequencies(Arrays.asList(range1, range2),
						Arrays.asList(range3, range4)), 0);
	}

}
