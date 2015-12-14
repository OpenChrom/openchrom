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
package net.sf.kerner.utils.collections.map.collection.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import net.sf.kerner.utils.collections.map.MapSetObject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TestMapSetObject {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private MapSetObject mapset1;
	private MapSetObject mapset2;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

		mapset1 = null;
		mapset2 = null;
	}

	@Test
	public final void testClear01() {

		mapset1 = new MapSetObject();
		mapset1.put(1, 1);
		mapset1.clear();
		assertTrue(mapset1.isEmpty());
		assertTrue(mapset1.size() == 0);
		assertTrue(mapset1.size(1) == 0);
	}

	@Test
	public final void testEqualsObject01() {

		mapset1 = new MapSetObject();
		mapset2 = new MapSetObject();
		assertEquals(mapset1, mapset2);
	}

	@Test
	public final void testHashCode01() {

		mapset1 = new MapSetObject();
		mapset2 = new MapSetObject();
		assertEquals(mapset1.hashCode(), mapset2.hashCode());
	}

	@Test
	public final void testMapSetObject01() {

		mapset1 = new MapSetObject();
		mapset1.put(1, 1);
		mapset2 = new MapSetObject(mapset1);
		assertArrayEquals(new LinkedHashSet(Arrays.asList(1)).toArray(), mapset2.get(1).toArray());
	}

	@Test
	public final void testPut01() {

		mapset1 = new MapSetObject();
		mapset1.put(1, 1);
		assertArrayEquals(new LinkedHashSet(Arrays.asList(1)).toArray(), mapset1.get(1).toArray());
	}

	@Test
	public final void testPut02() {

		mapset1 = new MapSetObject();
		mapset1.put(1, 1);
		assertArrayEquals(new LinkedHashSet(Arrays.asList(1)).toArray(), mapset1.get(1).toArray());
	}

	@Test
	public final void testPut03() {

		mapset1 = new MapSetObject();
		mapset1.put(1, Arrays.asList(1, 2));
		assertTrue(mapset1.containsKey(1));
		assertTrue(mapset1.containsValue(2));
		assertTrue(mapset1.size(1) == 2);
	}

	@Test
	public final void testPutAll01() {

		mapset1 = new MapSetObject();
		mapset1.putAll(1, Arrays.asList(1, 2, 3));
		assertFalse(mapset1.containsKey(2));
		assertTrue(mapset1.containsValue(2));
		assertFalse(mapset1.containsValue(4));
	}

	@Test
	public final void testPutAll02() {

		mapset1 = new MapSetObject();
		mapset1.putAll(1, Arrays.asList(1, 2, 3));
		assertArrayEquals(new LinkedHashSet(Arrays.asList(1, 2, 3)).toArray(), mapset1.get(1).toArray());
	}

	@Test
	public final void testPutAll03() {

		mapset1 = new MapSetObject();
		final Map map = new HashMap();
		map.put(1, 1);
		map.put(2, 2);
		mapset1.putAll(map);
		System.out.println(mapset1);
		assertTrue(mapset1.containsKey(1));
		assertTrue(mapset1.containsValue(1));
		assertTrue(mapset1.containsKey(2));
		assertTrue(mapset1.containsValue(2));
	}
}
