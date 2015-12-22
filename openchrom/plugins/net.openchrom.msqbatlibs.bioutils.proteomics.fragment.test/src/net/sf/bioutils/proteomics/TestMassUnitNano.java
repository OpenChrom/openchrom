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
package net.sf.bioutils.proteomics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestMassUnitNano {

	private MassUnit m;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		m = MassUnit.NANODALTON;
	}

	@After
	public void tearDown() throws Exception {

		m = null;
	}

	@Test
	public final void testToNanos01() {

		assertEquals(1, m.toNanos(1), 0);
	}

	@Test
	public final void testToNanos02() {

		assertEquals(15, m.toNanos(15), 0);
	}

	@Test
	public final void testToMicros01() {

		assertEquals(0.001, m.toMicros(1), 0);
	}

	@Test
	public final void testToMicros02() {

		assertEquals(1.456, m.toMicros(1456), 0);
	}

	@Test
	public final void testToMicros03() {

		assertEquals(1.567, m.toMicros(1567), 0);
	}

	@Test
	public final void testToMillis01() {

		assertEquals(0.000001, m.toMillis(1), 0);
	}

	@Test
	public final void testToMillis02() {

		assertEquals(0.001345, m.toMillis(1345), 0);
	}

	@Test
	public final void testToDaltons01() {

		assertEquals(0.000000001, m.toUnits(1), 0);
	}

	@Test
	public final void testToKilos() {

		assertEquals(0.000000000001, m.toKilos(1), 0);
	}

	@Ignore("delegate method")
	@Test
	public final void testConvert() {

		fail("Not yet implemented"); // TODO
	}
}
