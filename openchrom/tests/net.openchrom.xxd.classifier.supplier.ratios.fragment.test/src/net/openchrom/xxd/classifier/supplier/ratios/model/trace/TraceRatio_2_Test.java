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

import junit.framework.TestCase;

public class TraceRatio_2_Test extends TestCase {

	private TraceRatio peakRatio;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		peakRatio = new TraceRatio();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertNull(peakRatio.getPeak());
	}

	public void test2() {

		peakRatio.setName("Test");
		assertEquals("Test", peakRatio.getName());
	}

	public void test3() {

		peakRatio.setDeviation(0.64d);
		assertEquals(0.64d, peakRatio.getDeviation());
	}

	public void test4() {

		peakRatio.setDeviationWarn(5.0d);
		assertEquals(5.0d, peakRatio.getDeviationWarn());
	}

	public void test5() {

		peakRatio.setDeviationError(15.0d);
		assertEquals(15.0d, peakRatio.getDeviationError());
	}

	public void test6() {

		peakRatio.setExpectedRatio(0.8d);
		assertEquals(0.8d, peakRatio.getExpectedRatio());
	}

	public void test7() {

		peakRatio.setRatio(0.5687d);
		assertEquals(0.5687d, peakRatio.getRatio());
	}

	public void test8() {

		peakRatio.setTestCase("104:103");
		assertEquals("104:103", peakRatio.getTestCase());
	}
}
