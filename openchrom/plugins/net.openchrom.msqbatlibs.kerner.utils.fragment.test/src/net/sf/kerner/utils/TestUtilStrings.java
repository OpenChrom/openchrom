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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-01-13
 */
public class TestUtilStrings {

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
	public final void testEmptyString() {

		assertTrue(UtilString.emptyString(null));
	}

	@Test
	public final void testEmptyString01() {

		assertTrue(UtilString.emptyString(""));
	}

	@Test
	public final void testEmptyString02() {

		assertTrue(UtilString.emptyString(" "));
	}

	@Test
	public final void testEmptyString03() {

		assertFalse(UtilString.emptyString(" a "));
	}

	@Test
	public final void testGetRandomString01() {

		assertEquals(16, UtilString.getRandomString().length());
	}
}
