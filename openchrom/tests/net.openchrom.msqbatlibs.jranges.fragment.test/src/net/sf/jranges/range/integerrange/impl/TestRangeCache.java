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
package net.sf.jranges.range.integerrange.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import net.sf.jranges.range.integerrange.RangeInteger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-18
 *
 */
public class TestRangeCache {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	RangeCache cache;
	RangeInteger range1;
	RangeInteger range2;
	RangeInteger range3;
	RangeInteger range4;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Test method for {@link net.sf.kerner.commons.range.impl.RangeCache#next()}.
	 */
	@Test
	public final void testNext() {

		final ArrayList<RangeInteger> range = new ArrayList<RangeInteger>();
		range1 = new RangeIntegerDummy(1, 10);
		range2 = new RangeIntegerDummy(11, 20);
		range3 = new RangeIntegerDummy(21, 30);
		range4 = new RangeIntegerDummy(31, 40);
		range.add(range1);
		range.add(range2);
		range.add(range3);
		range.add(range4);
		cache = new RangeCache(range, 2);
		assertTrue(cache.hasNext());
		assertEquals(Arrays.asList(range1, range2), cache.next());
		assertTrue(cache.hasNext());
		assertEquals(Arrays.asList(range3, range4), cache.next());
		assertFalse(cache.hasNext());
	}
}
