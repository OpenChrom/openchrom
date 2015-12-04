/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.collections.impl;

import static org.junit.Assert.assertEquals;
import net.sf.kerner.utils.collections.ComparatorNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestComparatorNull {

	private ComparatorNull<Integer> c;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		c = new ComparatorNull<Integer>();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testCompare01() {

		assertEquals(0, c.compare(null, null));
	}

	@Test
	public final void testCompare02() {

		assertEquals(-1, c.compare(null, Integer.valueOf(2)));
	}

	@Test
	public final void testCompare03() {

		assertEquals(1, c.compare(Integer.valueOf(2), null));
	}

	@Test
	public final void testCompare04() {

		assertEquals(0, c.compare(Integer.valueOf(2), Integer.valueOf(2)));
	}

	@Test(expected = ClassCastException.class)
	public final void testCompare05() {

		ComparatorNull<Object> c = new ComparatorNull<Object>();
		assertEquals(0, c.compare(new Object(), Integer.valueOf(2)));
	}

	@Test(expected = ClassCastException.class)
	public final void testCompare06() {

		ComparatorNull<Object> c = new ComparatorNull<Object>();
		assertEquals(0, c.compare(Integer.valueOf(2), new Object()));
	}
}
