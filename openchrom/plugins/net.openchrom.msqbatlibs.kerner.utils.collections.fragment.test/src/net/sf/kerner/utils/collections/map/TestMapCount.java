/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMapCount {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private MapCount<String> m;

	@Before
	public void setUp() throws Exception {

		m = new MapCount<String>();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testGetHighest01() {

		m.put("k1");
		m.put("k1");
		m.put("k2");
		assertEquals("k1", m.getMax());
	}

	@Test
	public final void testGetLoweset01() {

		m.put("k1");
		m.put("k1");
		m.put("k2");
		assertEquals("k2", m.getMin());
	}

	@Test
	public final void testHasHighest01() {

		m.put("k1");
		m.put("k1");
		m.put("k2");
		assertTrue(m.hasHighest());
	}

	@Test
	public final void testHasLowest01() {

		m.put("k1");
		m.put("k1");
		m.put("k2");
		assertTrue(m.hasLowest());
	}
}
