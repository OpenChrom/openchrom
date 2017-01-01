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
package net.sf.kerner.utils;

import static org.junit.Assert.assertTrue;
import net.sf.kerner.utils.comparator.ComparatorDoubleDefault;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestComparatorDoubleDefault {

	private ComparatorDoubleDefault c;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		c = new ComparatorDoubleDefault();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testCompare01() {

		assertTrue(0 > c.compare(Double.valueOf(1), Double.valueOf(2)));
	}

	@Test
	public final void testCompare02() {

		assertTrue(0 < c.compare(Double.valueOf(3), Double.valueOf(0)));
	}

	@Test
	public final void testCompare03() {

		assertTrue(0 == c.compare(Double.valueOf(3), Double.valueOf(3)));
	}
}
