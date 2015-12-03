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
package net.sf.jranges.range.doublerange.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.jranges.range.RangeException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-04-13
 * 
 */
public class TestZeroPositiveDoubleRange {

	private ZeroPositiveDoubleRange r;

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
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double)}
	 * .
	 */
	@Test
	public final void testZeroPositiveDoubleRangeDoubleDouble() {
		r = new ZeroPositiveDoubleRange(1, 1);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double)}
	 * .
	 */
	@Test
	public final void testZeroPositiveDoubleRangeDoubleDouble01() {
		r = new ZeroPositiveDoubleRange(0, 1);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double)}
	 * .
	 */
	@Test(expected = RangeException.class)
	public final void testZeroPositiveDoubleRangeDoubleDouble02() {
		r = new ZeroPositiveDoubleRange(-1, 1);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double)}
	 * .
	 */
	@Test(expected = RangeException.class)
	public final void testZeroPositiveDoubleRangeDoubleDouble03() {
		r = new ZeroPositiveDoubleRange(2, 1);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double, double)}
	 * .
	 */
	@Test
	public final void testZeroPositiveDoubleRangeDoubleDoubleDouble() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.1);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double, double)}
	 * .
	 */
	@Test(expected = RangeException.class)
	public final void testZeroPositiveDoubleRangeDoubleDoubleDouble01() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.2);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double, double)}
	 * .
	 */
	@Test
	public final void testZeroPositiveDoubleRangeDoubleDoubleDouble02() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.05);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double, double)}
	 * .
	 */
	@Test(expected = RangeException.class)
	public final void testZeroPositiveDoubleRangeDoubleDoubleDouble03() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.03);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double, double)}
	 * .
	 */
	@Test
	public final void testZeroPositiveDoubleRangeDoubleDoubleDouble04() {
		r = new ZeroPositiveDoubleRange(1, 2, 1);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double, double)}
	 * .
	 */
	@Test
	public final void testZeroPositiveDoubleRangeDoubleDoubleDouble05() {
		r = new ZeroPositiveDoubleRange(1, 11, 1.25);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange#ZeroPositiveDoubleRange(double, double, double)}
	 * .
	 */
	@Test
	public final void testZeroPositiveDoubleRangeDoubleDoubleDouble09() {

		// System.out.println(3249.6240 % 1.0005);
		//
		// for(double d = 750.3755; d < 4010; d+= 1.0005){
		// if(d>3200 || d < 800)
		// System.out.println(MathUtils.round(d, 6));
		// }

		r = new ZeroPositiveDoubleRange(750.3755, 3999.9995, 1.0005);
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.AbstractDoubleRange#includes(double)}
	 * .
	 */
	@Test
	public final void testIncludes() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.05);
		assertTrue(r.includes(1));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.AbstractDoubleRange#includes(double)}
	 * .
	 */
	@Test
	public final void testIncludes01() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.05);
		assertTrue(r.includes(1.1));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.AbstractDoubleRange#includes(double)}
	 * .
	 */
	@Test
	public final void testIncludes02() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.05);
		assertFalse(r.includes(0.05));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.AbstractDoubleRange#includes(double)}
	 * .
	 */
	@Test
	public final void testIncludes03() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.05);
		assertTrue(r.includes(1.05));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.AbstractDoubleRange#includes(double)}
	 * .
	 */
	@Test
	public final void testIncludes04() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.05);
		assertFalse(r.includes(1.04));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.AbstractDoubleRange#includes(double)}
	 * .
	 */
	@Test
	public final void testIncludes05() {
		r = new ZeroPositiveDoubleRange(1, 1.1, 0.05);
		assertFalse(r.includes(1.06));
	}

	@Test
	public final void testIncludes06() {
		r = new ZeroPositiveDoubleRange(0, 4.8, 0.2);
		assertTrue(r.includes(4.8));
	}

	@Test
	public final void testIncludes07() {
		r = new ZeroPositiveDoubleRange(0, 4.8, 0.2);
		assertTrue(r.includes(2.4));
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.AbstractDoubleRange#shift(double)}
	 * .
	 */
	@Test
	@Ignore
	public final void testShift() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link net.sf.jranges.range.doublerange.impl.AbstractDoubleRange#expandRange(double, boolean)}
	 * .
	 */
	@Test
	@Ignore
	public final void testExpandRangeDoubleBoolean() {
		fail("Not yet implemented"); // TODO
	}

}
