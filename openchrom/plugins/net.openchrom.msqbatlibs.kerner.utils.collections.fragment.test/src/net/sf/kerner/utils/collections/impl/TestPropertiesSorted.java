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

import java.util.Iterator;
import java.util.Set;

import net.sf.kerner.utils.collections.PropertiesSorted;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPropertiesSorted {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private PropertiesSorted p;

	@Before
	public void setUp() throws Exception {

		p = new PropertiesSorted();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testKeys01() {

		p.put("2", "zwei");
		p.put("3", "drei");
		p.put("1", "eins");
		final Set<String> s = p.keySet();
		final Iterator<String> it = s.iterator();
		assertEquals("1", it.next());
		assertEquals("2", it.next());
		assertEquals("3", it.next());
	}

	@Test
	public final void testKeySet01() {

		p.put("2", "zwei");
		p.put("3", "drei");
		p.put("1", "eins");
		final Iterator<String> it = p.keySet().iterator();
		assertEquals("1", it.next());
		assertEquals("2", it.next());
		assertEquals("3", it.next());
	}
}
