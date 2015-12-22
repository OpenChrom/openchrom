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
/**
 * 
 */
package net.sf.kerner.utils;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-10-26
 */
public class TestArrayUtils {

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
	public final void testArrayTrimChar01() {

		char[] arr = new char[4];
		assertArrayEquals(new char[]{'\0', '\0', '\0'}, UtilArray.trim(arr, 3));
	}

	@Test
	public final void testArrayTrimChar02() {

		char[] arr = new char[]{};
		assertArrayEquals(new char[]{}, UtilArray.trim(arr, 3));
	}

	@Test
	public final void testArrayTrimByte01() {

		byte[] arr = new byte[]{1, 2, 3, 4};
		assertArrayEquals(new byte[]{1, 2, 3}, UtilArray.trim(arr, 3));
	}

	@Test
	public final void testArrayTrimByte02() {

		byte[] arr = new byte[4];
		assertArrayEquals(new byte[]{0, 0, 0}, UtilArray.trim(arr, 3));
	}

	@Test
	public final void testArrayTrimInt01() {

		int[] arr = new int[4];
		assertArrayEquals(new int[]{0, 0, 0}, UtilArray.trim(arr, 3));
	}

	@Test
	public final void testArrayTrimInt02() {

		int[] arr = new int[4];
		assertArrayEquals(new int[]{}, UtilArray.trim(arr, 0));
	}

	@Test
	public final void testArrayTrimInt03() {

		int[] arr = new int[4];
		assertArrayEquals(arr, UtilArray.trim(arr, 9));
	}

	@Test
	public final void testArrayTrimInt04() {

		int[] arr = new int[4];
		assertArrayEquals(new int[]{}, UtilArray.trim(arr, -1));
	}
}
