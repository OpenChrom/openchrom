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
package net.sf.kerner.utils.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestDoubleUnitKilo {

	private DoubleUnit u;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		u = DoubleUnit.KILO;
	}

	@After
	public void tearDown() throws Exception {

		u = null;
	}

	@Test
	public final void testToPicos01() {

		assertEquals(1.0E+15, u.toPicos(1), 0);
	}

	@Test
	public final void testToNanos() {

		assertEquals(1.0E+12, u.toNanos(1), 0);
	}

	@Test
	public final void testToMicros01() {

		assertEquals(1.0E+9, u.toMicros(1), 0);
	}

	@Test
	public final void testToMillis01() {

		assertEquals(1.0E+6, u.toMillis(1), 0);
	}

	@Test
	public final void testToUnits01() {

		assertEquals(1.0E+3, u.toUnits(1), 0);
	}

	@Test
	public final void testToKilos01() {

		assertEquals(1, u.toKilos(1), 0);
	}

	@Test
	public final void testToMegas01() {

		assertEquals(1.0E-3, u.toMegas(1), 0);
	}

	@Test
	public final void testToGigas01() {

		assertEquals(1.0E-6, u.toGigas(1), 0);
	}

	@Test
	public final void testToTeras01() {

		assertEquals(1.0E-9, u.toTeras(1), 0);
	}

	@Ignore("delegate method")
	@Test
	public final void testConvert() {

		fail("Not yet implemented"); // TODO
	}
}
