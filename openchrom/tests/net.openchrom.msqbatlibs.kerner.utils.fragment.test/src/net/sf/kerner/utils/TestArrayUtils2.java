/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestArrayUtils2 {

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
	public final void testEmptyArray01() {

		assertTrue(UtilArray.emptyArray(new Object[]{}));
	}

	@Test
	public final void testEmptyArray03() {

		assertFalse(UtilArray.emptyArray(new Object[]{new Object()}));
	}

	@Test
	public final void testEmptyArray04() {

		assertFalse(UtilArray.emptyArray(new Object[]{new Object[]{new Integer(1)}}));
	}

	@Ignore
	@Test
	public final void testIsArray() {

		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testNullArray01() {

		assertTrue(UtilArray.nullArray(null));
	}

	@Test
	public final void testNullArray02() {

		assertTrue(UtilArray.nullArray(new Object[]{}));
	}

	@Test
	public final void testNullArray03() {

		assertTrue(UtilArray.nullArray(new Object[]{null}));
	}

	@Test
	public final void testNullArray04() {

		assertTrue(UtilArray.nullArray(null));
	}

	@Test
	public final void testNullArray05() {

		assertTrue(UtilArray.nullArray(new Object[]{new Object[]{null}}));
	}

	@Test
	public final void testNullArray06() {

		assertFalse(UtilArray.nullArray(new Object[]{new Object[]{new Integer(1)}}));
	}

	@Test
	public final void testNullArray07() {

		assertFalse(UtilArray.nullArray(new Object[]{new Integer(1)}));
	}

	@Ignore
	@Test
	public final void testToObjectByte() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testToObjectByteArray() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testToObjectDouble() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testToObjectDoubleArray() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testToObjectInt() {

		fail("Not yet implemented"); // TODO
	}

	@Ignore
	@Test
	public final void testToObjectIntArray() {

		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testToPrimitiveDoubleArray01() {

		final Double[] arr = new Double[0];
		final double[] r = UtilArray.toPrimitive(arr);
		assertNotNull(r);
		assertEquals(0, r.length);
	}

	@Test
	public final void testToPrimitiveDoubleArray02() {

		final Double[] arr = new Double[]{Double.valueOf(1)};
		final double[] r = UtilArray.toPrimitive(arr);
		assertEquals(1, r.length);
		assertEquals(Double.valueOf(1), arr[0]);
	}

	@Test(expected = NullPointerException.class)
	public final void testToPrimitiveDoubleArray03() {

		final Double[] arr = new Double[]{Double.valueOf(1), null};
		UtilArray.toPrimitive(arr);
	}

	@Ignore
	@Test
	public final void testToPrimitiveIntegerArray() {

		fail("Not yet implemented"); // TODO
	}
}
