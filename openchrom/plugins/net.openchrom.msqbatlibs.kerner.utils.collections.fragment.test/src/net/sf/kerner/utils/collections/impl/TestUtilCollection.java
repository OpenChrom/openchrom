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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.comparator.ComparatorIntegerDefault;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUtilCollection {

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
	public final void testGetHighest01() {

		final List<Integer> input = Arrays.asList(1, 2, 1);
		assertEquals(Integer.valueOf(2), UtilCollection.getHighest(input, new ComparatorIntegerDefault()));
	}

	@Test
	public final void testGetHighest02() {

		final List<Integer> input = Arrays.asList(1, 2, 3);
		assertEquals(Integer.valueOf(3), UtilCollection.getHighest(input, new ComparatorIntegerDefault()));
	}
}
