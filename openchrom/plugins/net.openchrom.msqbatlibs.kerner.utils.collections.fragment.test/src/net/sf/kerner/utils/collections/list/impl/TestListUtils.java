/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.list.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import net.sf.kerner.utils.collections.list.UtilList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestListUtils {

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

	@Test
	public final void testMeld01() {

		List<String> l1 = Arrays.asList("a", "b");
		List<String> l2 = Arrays.asList("c", "d");
		List<String> l3 = Arrays.asList("a", "c", "b", "d");
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld02() {

		List<String> l1 = Arrays.asList("a", "b");
		List<String> l2 = Arrays.asList("a", "c");
		List<String> l3 = Arrays.asList("a", "b", "c");
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld03() {

		List<String> l1 = Arrays.asList("a", "b");
		List<String> l2 = Arrays.asList("a", "c");
		List<String> l3 = Arrays.asList("a", "b", "c");
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld04() {

		List<String> l1 = Arrays.asList("a", "b");
		List<String> l2 = Arrays.asList(null, "c");
		List<String> l3 = Arrays.asList("a", "b", "c");
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld04b() {

		List<String> l1 = Arrays.asList("a", "b");
		List<String> l2 = Arrays.asList(null, "c");
		List<String> l3 = Arrays.asList("a", "b", "c");
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld05() {

		List<String> l1 = Arrays.asList(null, null);
		List<String> l2 = Arrays.asList(null, null);
		List<String> l3 = Arrays.asList(null, null);
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld05b() {

		List<String> l1 = Arrays.asList(null, null);
		List<String> l2 = Arrays.asList(null, null);
		List<String> l3 = Arrays.asList(null, null);
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld06() {

		List<String> l1 = Arrays.asList(null, null);
		List<String> l2 = Arrays.asList("a", null);
		List<String> l3 = Arrays.asList("a", null);
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld06b() {

		List<String> l1 = Arrays.asList(null, null);
		List<String> l2 = Arrays.asList("a", null);
		List<String> l3 = Arrays.asList("a", null);
		List<String> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Test
	public final void testMeld07() {

		List<Double> l1 = Arrays.asList(0.1, 0.2, 0.4);
		List<Double> l2 = Arrays.asList(null, 0.3);
		List<Double> l3 = Arrays.asList(0.1, 0.2, 0.3, 0.4);
		List<Double> l4 = UtilList.meld(l1, l2);
		assertEquals(l3.size(), l4.size());
		assertArrayEquals(l3.toArray(), l4.toArray());
	}

	@Ignore
	@Test
	public final void testFill() {

		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSetAll01() {

		List<Integer> parent = Arrays.asList(0, 1, 2, 3, 4);
		List<Integer> child = Arrays.asList(0, 0);
		List<Integer> parentNew = Arrays.asList(0, 0, 0, 3, 4);
		UtilList.setAll(parent, child, 1);
		assertArrayEquals(parentNew.toArray(), parent.toArray());
	}

	@Test
	public final void testSetAll02() {

		List<Integer> parent = Arrays.asList(0, 1, 2, 3, 4);
		List<Integer> child = Arrays.asList(0, 0);
		List<Integer> parentNew = Arrays.asList(0, 0, 0, 3, 4);
		UtilList.setAll(parent, child, 1);
		assertArrayEquals(parentNew.toArray(), parent.toArray());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetAll03() {

		List<Integer> parent = Arrays.asList(0, 1, 2, 3, 4);
		List<Integer> child = Arrays.asList(0, 0);
		List<Integer> parentNew = Arrays.asList(0, 0, 0, 3, 4);
		UtilList.setAll(parent, child, -1);
		assertArrayEquals(parentNew.toArray(), parent.toArray());
	}

	@Test(expected = IllegalArgumentException.class)
	public final void testSetAll04() {

		List<Integer> parent = Arrays.asList(0, 1, 2, 3, 4);
		List<Integer> child = Arrays.asList(0, 0);
		List<Integer> parentNew = Arrays.asList(0, 0, 0, 3, 4);
		UtilList.setAll(parent, child, 4);
		assertArrayEquals(parentNew.toArray(), parent.toArray());
	}

	@Test
	public final void testSetAll05() {

		List<Integer> parent = Arrays.asList(0, 1, 2, 3, 4);
		List<Integer> child = Arrays.asList(0, 0);
		List<Integer> parentNew = Arrays.asList(0, 0, 2, 3, 4);
		UtilList.setAll(parent, child, 0);
		assertArrayEquals(parentNew.toArray(), parent.toArray());
	}

	@Test
	public final void testSetAll06() {

		List<Integer> parent = Arrays.asList(0, 1, 2, 3, 4);
		List<Integer> child = Arrays.asList(0, 0);
		List<Integer> parentNew = Arrays.asList(0, 1, 2, 0, 0);
		UtilList.setAll(parent, child, 3);
		assertArrayEquals(parentNew.toArray(), parent.toArray());
	}
}
