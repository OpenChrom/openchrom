/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

public class TestMassUnitMicro {

	private MassUnit m;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {

		m = MassUnit.MICRODALTON;
	}

	@After
	public void tearDown() throws Exception {

		m = null;
	}

	@Test
	public final void testToNanos01() {

		assertEquals(1000, m.toNanos(1), 0);
	}

	@Test
	public final void testToMicros01() {

		assertEquals(1, m.toMicros(1), 0);
	}

	@Test
	public final void testToMillis01() {

		assertEquals(0.001, m.toMillis(1), 0);
	}

	@Test
	public final void testToDaltons01() {

		assertEquals(0.000001, m.toUnits(1), 0);
	}

	@Test
	public final void testToKilos01() {

		assertEquals(0.000000001, m.toKilos(1), 0);
	}

	@Ignore("delegate method")
	@Test
	public final void testConvert() {

		fail("Not yet implemented"); // TODO
	}
}
