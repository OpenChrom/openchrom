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
 *
 */
package net.sf.jranges.range.integerrange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.jranges.range.integerrange.UtilsRangeInteger;
import net.sf.jranges.range.integerrange.impl.RangeIntegerDummy;
import net.sf.jranges.range.integerrange.impl.FactoryRangeIntegerDummy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-18
 * 
 */
public class TestIntegerRangeUtils {

	private List<RangeInteger> ranges;

	// private List<IntegerRange> ranges2;
	// private List<IntegerRange> ranges3;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ranges = new ArrayList<RangeInteger>();

		// ranges2 = new ArrayList<IntegerRange>();
		//
		// ranges3 = new ArrayList<IntegerRange>();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#includedByOne(java.util.List, int)}
	 * .
	 */
	@Test
	public final void testIncludedByOne() {
		ranges.add(new RangeIntegerDummy(1, 2));
		ranges.add(new RangeIntegerDummy(3, 4));
		ranges.add(new RangeIntegerDummy(5, 6));
		ranges.add(new RangeIntegerDummy(7, 8));
		assertTrue(UtilsRangeInteger.includedByOne(ranges, 1));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#includedByOne(java.util.List, int)}
	 * .
	 */
	@Test
	public final void testIncludedByOne01() {
		ranges.add(new RangeIntegerDummy(1, 2));
		ranges.add(new RangeIntegerDummy(3, 4));
		ranges.add(new RangeIntegerDummy(5, 6));
		ranges.add(new RangeIntegerDummy(7, 8));
		assertTrue(UtilsRangeInteger.includedByOne(ranges, 8));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#includedByOne(java.util.List, int)}
	 * .
	 */
	@Test
	public final void testIncludedByOne02() {
		ranges.add(new RangeIntegerDummy(1, 2));
		ranges.add(new RangeIntegerDummy(3, 4));
		ranges.add(new RangeIntegerDummy(5, 6));
		ranges.add(new RangeIntegerDummy(7, 8));
		assertFalse(UtilsRangeInteger.includedByOne(ranges, 0));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#includedByOne(java.util.List, int)}
	 * .
	 */
	@Test
	public final void testIncludedByOne03() {
		ranges.add(new RangeIntegerDummy(1, 2));
		ranges.add(new RangeIntegerDummy(3, 4));
		ranges.add(new RangeIntegerDummy(5, 6));
		ranges.add(new RangeIntegerDummy(7, 8));
		assertFalse(UtilsRangeInteger.includedByOne(ranges, -1));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#includedByOne(java.util.List, int)}
	 * .
	 */
	@Test
	public final void testIncludedByOne04() {
		ranges.add(new RangeIntegerDummy(1, 2));
		ranges.add(new RangeIntegerDummy(3, 4));
		ranges.add(new RangeIntegerDummy(5, 6));
		ranges.add(new RangeIntegerDummy(7, 8));
		assertFalse(UtilsRangeInteger.includedByOne(ranges, 9));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.integerrange.UtilsRangeInteger.range.impl.UtilRange#merge(java.util.List, net.sf.kerner.commons.range.RangeFactory)}
	 * .
	 */
	@Test
	public final void testMerge() {
		ranges.add(new RangeIntegerDummy(1, 2));
		ranges.add(new RangeIntegerDummy(3, 4));
		ranges.add(new RangeIntegerDummy(5, 6));
		ranges.add(new RangeIntegerDummy(7, 8));
		assertEquals(new RangeIntegerDummy(1, 8), UtilsRangeInteger.merge(ranges, new FactoryRangeIntegerDummy()));
	}

}
