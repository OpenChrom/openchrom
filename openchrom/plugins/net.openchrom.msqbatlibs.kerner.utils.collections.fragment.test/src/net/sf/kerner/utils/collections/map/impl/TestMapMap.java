/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.map.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.kerner.utils.collections.map.MapMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMapMap {

	private MapMap<String, String, String> map;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		map = new MapMap<String, String, String>();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testSize101() {

		assertEquals(0, map.size1());
	}

	@Test
	public final void testSize102() {

		map.put("A", "A", "v");
		assertEquals(1, map.size1());
	}

	@Test
	public final void testSize103() {

		map.put("A", "A", "v");
		map.put("A", "B", "v2");
		assertEquals(1, map.size1());
	}

	@Test
	public final void testSize201() {

		map.put("A", "A", "v");
		map.put("A", "B", "v2");
		assertEquals(2, map.size2());
	}

	@Test
	public final void testIsEmpty01() {

		assertTrue(map.isEmpty());
	}

	@Test
	public final void testIsEmpty02() {

		assertTrue(map.isEmpty());
	}

	@Test
	public final void testIsEmpty03() {

		map.put("A", "A", "v");
		assertFalse(map.isEmpty());
	}

	@Test
	public final void testIsEmpty04() {

		map.put("A", "A", "v");
		map.put("A", "B", "v2");
		assertFalse(map.isEmpty());
	}

	@Test
	public final void testContainsKey101() {

		map.put("A", "A", "v");
		assertTrue(map.containsKey1("A"));
	}

	@Test
	public final void testContainsKey102() {

		map.put("A", "A", "v");
		map.put("A", "B", "v");
		assertFalse(map.containsKey1("B"));
	}

	@Test
	public final void testContainsKey201() {

		map.put("A", "A", "v");
		map.put("A", "B", "v");
		assertTrue(map.containsKey2("B"));
	}

	@Test
	public final void testContainsValue01() {

		map.put("A", "A", "a");
		map.put("A", "B", "b");
		assertTrue(map.containsValue("a"));
		assertTrue(map.containsValue("b"));
	}

	@Test
	public final void testGet01() {

		map.put("A", "A", "a");
		map.put("A", "B", "b");
		assertEquals("a", map.get("A").get("A"));
		assertEquals("b", map.get("A").get("B"));
	}

	@Test
	public final void testRemove101() {

		map.put("A", "A", "a");
		map.put("A", "B", "b");
		map.remove1("A");
		assertTrue(map.isEmpty());
	}

	@Test
	public final void testRemove102() {

		map.put("A", "A", "a");
		map.put("A", "B", "b");
		map.put("B", "C", "c");
		map.put("B", "D", "d");
		map.remove1("A");
		assertEquals(1, map.size1());
		assertEquals(2, map.size2());
	}

	@Test
	public final void testRemove201() {

		map.put("A", "A", "a");
		map.put("A", "B", "b");
		map.put("B", "C", "c");
		map.put("B", "D", "d");
		map.remove2("A");
		assertEquals(2, map.size1());
		assertEquals(3, map.size2());
	}

	@Test
	public final void testRemove202() {

		map.put("A", "A", "a");
		map.put("A", "B", "b");
		map.put("B", "C", "c");
		map.put("B", "D", "d");
		map.remove2("B");
		assertEquals(2, map.size1());
		assertEquals(3, map.size2());
	}

	@Test
	public final void testValuesK01() {

		map.put("A", "A", "a");
		map.put("A", "B", "b");
		map.put("B", "C", "c");
		map.put("B", "D", "d");
		assertArrayEquals(new String[]{"a", "b"}, map.values("A").toArray());
	}

	@Test
	public final void testValues01() {

		map.put("A", "A", "a");
		map.put("A", "B", "b");
		map.put("B", "C", "c");
		map.put("B", "D", "d");
		assertArrayEquals(new String[]{"a", "b", "c", "d"}, map.values().toArray());
	}
}
