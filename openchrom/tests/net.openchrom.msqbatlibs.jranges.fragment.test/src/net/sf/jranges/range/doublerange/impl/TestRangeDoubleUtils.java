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
package net.sf.jranges.range.doublerange.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.sf.jranges.range.doublerange.RangeDouble;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestRangeDoubleUtils {

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
	public final void testSplit01() {

		RangeDouble range = new RangeDoubleDummy(0, 10);
		List<? extends RangeDouble> result = RangeDoubleUtils.split(range, 2, new FactoryRangeDoubleZeroPositive());
		assertEquals(10, result.size());
	}

	@Ignore
	@Test
	public final void testSplit02() {

		RangeDouble range = new ZeroPositiveDoubleRange(0, 10, 0.2);
		List<? extends RangeDouble> result = RangeDoubleUtils.split(range, 2, new FactoryRangeDoubleZeroPositive());
		System.out.println(result);
	}
}
