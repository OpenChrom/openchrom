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
package net.openchrom.xxd.process.supplier.templates.model;

import org.eclipse.chemclipse.model.core.PeakType;

import junit.framework.TestCase;

public class DetectorSetting_1_Test extends TestCase {

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

		assertEquals(0.0d, setting.getPositionStart());
	}

	public void test2() {

		assertEquals(0.0d, setting.getPositionStop());
	}

	public void test3() {

		assertEquals(PeakType.VV, setting.getPeakType());
	}

	public void test4() {

		assertEquals("", setting.getTraces());
	}

	public void test5() {

		assertEquals(false, setting.isOptimizeRange());
	}

	public void test6() {

		assertEquals("", setting.getReferenceIdentifier());
	}

	public void test7() {

		assertEquals("", setting.getName());
	}

	public void test8() {

		assertEquals(PositionDirective.RETENTION_TIME_MIN, setting.getPositionDirective());
	}
}