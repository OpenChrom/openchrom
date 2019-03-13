/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model.time;

import junit.framework.TestCase;

public class TimeRatio_1_Test extends TestCase {

	private TimeRatio peakRatio;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		peakRatio = new TimeRatio();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertNull(peakRatio.getPeak());
	}

	public void test2() {

		assertEquals("", peakRatio.getName());
	}

	public void test3() {

		assertEquals(0.0d, peakRatio.getDeviation());
	}

	public void test4() {

		assertEquals(0.0d, peakRatio.getDeviationWarn());
	}

	public void test5() {

		assertEquals(0.0d, peakRatio.getDeviationError());
	}

	public void test6() {

		assertEquals(0.0d, peakRatio.getExpectedRetentionTime());
	}
}
