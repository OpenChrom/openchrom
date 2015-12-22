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
package net.sf.kerner.utils.collections.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.kerner.utils.collections.ObjectToIndexMapperImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-12-06
 */
public class TestObjectToIndexMapperImpl {

	private ObjectToIndexMapperImpl mapper;

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
	public final void testObjectToIndexMapperImpl01() {

		List<Object> keys = new ArrayList<Object>();
		mapper = new ObjectToIndexMapperImpl<Object>(keys);
		assertEquals(0, mapper.keys().size());
		assertEquals(0, mapper.values().size());
	}

	@Test
	public final void testObjectToIndexMapperImpl02() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl<Object>(keys);
		assertEquals(2, mapper.keys().size());
		assertEquals(2, mapper.values().size());
	}

	@Test
	public final void testObjectToIndexMapperImpl03() {

		mapper = new ObjectToIndexMapperImpl<Object>("eins", "zwei");
		assertEquals(2, mapper.keys().size());
		assertEquals(2, mapper.values().size());
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#get(java.lang.Object)} .
	 */
	@Test
	public final void testGet() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		assertEquals(0, mapper.get("eins"));
		assertEquals(1, mapper.get("zwei"));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#get(java.lang.Object)} .
	 */
	@Test(expected = NoSuchElementException.class)
	public final void testGet01() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		mapper.get("peter");
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#getValue(int)} .
	 */
	@Test
	public final void testGetValue() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		assertEquals("eins", mapper.getValue(0));
		assertEquals("zwei", mapper.getValue(1));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#getValue(int)} .
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testGetValue01() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		mapper.getValue(-1);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#getValue(int)} .
	 */
	@Test(expected = NoSuchElementException.class)
	public final void testGetValue02() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		mapper.getValue(2);
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#containsKey(java.lang.Object)} .
	 */
	@Test
	public final void testContainsKey() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		assertTrue(mapper.containsKey("eins"));
		assertTrue(mapper.containsKey("zwei"));
		assertFalse(mapper.containsKey("drei"));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#containsValue(int)} .
	 */
	@Test
	public final void testContainsValue() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		assertTrue(mapper.containsValue(0));
		assertTrue(mapper.containsValue(1));
		assertFalse(mapper.containsValue(2));
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#values()} .
	 */
	@Test
	public final void testValues() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		assertArrayEquals(new Integer[]{0, 1}, mapper.values().toArray());
	}

	/**
	 * Test method for {@link net.sf.kerner.utils.collections.ObjectToIndexMapperImpl#keys()} .
	 */
	@Test
	public final void testkeys() {

		List<Object> keys = new ArrayList<Object>();
		keys.add("eins");
		keys.add("zwei");
		mapper = new ObjectToIndexMapperImpl(keys);
		assertArrayEquals(keys.toArray(), mapper.keys().toArray());
	}
}
