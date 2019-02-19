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

		setting.setStartRetentionTime(2.10d);
		assertEquals(2.10d, setting.getStartRetentionTime());
	}

	public void test3() {

		setting.setStopRetentionTime(7.46d);
		assertEquals(7.46d, setting.getStopRetentionTime());
	}

	public void test4() {

		setting.setIntegrator("PeakMax");
		assertEquals("PeakMax", setting.getIntegrator());
	}
}
