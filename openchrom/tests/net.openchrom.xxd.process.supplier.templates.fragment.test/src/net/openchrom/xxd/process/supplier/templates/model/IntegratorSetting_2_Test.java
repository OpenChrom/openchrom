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

import junit.framework.TestCase;

public class IntegratorSetting_2_Test extends TestCase {

	private IntegratorSetting setting;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		setting = new IntegratorSetting();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		setting.setIdentifier("Test");
		assertEquals("Test", setting.getIdentifier());
	}

	public void test2() {

		setting.setPositionStart(2.10d);
		assertEquals(2.10d, setting.getPositionStart());
	}

	public void test3() {

		setting.setPositionStop(7.46d);
		assertEquals(7.46d, setting.getPositionStop());
	}

	public void test4() {

		setting.setIntegrator("PeakMax");
		assertEquals("PeakMax", setting.getIntegrator());
	}

	public void test5() {

		setting.setPositionDirective(PositionDirective.RETENTION_INDEX);
		assertEquals(PositionDirective.RETENTION_INDEX, setting.getPositionDirective());
	}
}