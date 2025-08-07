/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model.trace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class TraceRatio_1_Test {

	private TraceRatio peakRatio;

	@Before
	public void setUp() throws Exception {

		peakRatio = new TraceRatio();
	}

	@Test
	public void test1() {

		assertNull(peakRatio.getPeak());
	}

	@Test
	public void test2() {

		assertEquals("", peakRatio.getName());
	}

	@Test
	public void test3() {

		assertEquals(0.0d, peakRatio.getDeviation(), 0);
	}

	@Test
	public void test4() {

		assertEquals(0.0d, peakRatio.getDeviationWarn(), 0);
	}

	@Test
	public void test5() {

		assertEquals(0.0d, peakRatio.getDeviationError(), 0);
	}

	@Test
	public void test6() {

		assertEquals(0.0d, peakRatio.getExpectedRatio(), 0);
	}

	@Test
	public void test7() {

		assertEquals(0.0d, peakRatio.getRatio(), 0);
	}

	@Test
	public void test8() {

		assertEquals("", peakRatio.getTestCase());
	}
}
