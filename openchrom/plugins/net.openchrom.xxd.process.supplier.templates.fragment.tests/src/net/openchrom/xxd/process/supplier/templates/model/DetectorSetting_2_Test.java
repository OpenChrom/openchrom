/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.model;

import junit.framework.TestCase;

public class DetectorSetting_2_Test extends TestCase {

	private DetectorSetting setting;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		setting = new DetectorSetting();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		setting.setStartRetentionTimeMinutes(0.78d);
		assertEquals(0.78d, setting.getStartRetentionTimeMinutes());
	}

	public void test2() {

		setting.setStopRetentionTimeMinutes(1.28d);
		assertEquals(1.28d, setting.getStopRetentionTimeMinutes());
	}

	public void test3() {

		setting.setDetectorType(DetectorSetting.DETECTOR_TYPE_BB);
		assertEquals("BB", setting.getDetectorType());
		assertFalse(setting.isIncludeBackground());
		//
		setting.setDetectorType(DetectorSetting.DETECTOR_TYPE_VV);
		assertEquals("VV", setting.getDetectorType());
		assertTrue(setting.isIncludeBackground());
	}

	public void test4() {

		setting.setTraces("103, 104");
		assertEquals("103, 104", setting.getTraces());
	}

	public void test5() {

		setting.setOptimizeRange(true);
		assertEquals(true, setting.isOptimizeRange());
	}

	public void test6() {

		setting.setReferenceIdentifier("Styrene");
		assertEquals("Styrene", setting.getReferenceIdentifier());
	}
}
