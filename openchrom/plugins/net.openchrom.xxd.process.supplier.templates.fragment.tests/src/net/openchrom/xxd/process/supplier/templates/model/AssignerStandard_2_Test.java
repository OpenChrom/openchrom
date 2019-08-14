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

public class AssignerStandard_2_Test extends TestCase {

	private AssignerStandard setting;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		setting = new AssignerStandard();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertEquals("", setting.getName());
	}

	public void test2() {

		setting.setStartRetentionTimeMinutes(92.2d);
		assertEquals(92.2d, setting.getStartRetentionTimeMinutes());
	}

	public void test3() {

		setting.setStopRetentionTimeMinutes(392.4d);
		assertEquals(392.4d, setting.getStopRetentionTimeMinutes());
	}

	public void test4() {

		setting.setConcentration(2.78d);
		assertEquals(2.78d, setting.getConcentration());
	}

	public void test5() {

		setting.setConcentrationUnit("mg/L");
		assertEquals("mg/L", setting.getConcentrationUnit());
	}

	public void test6() {

		setting.setResponseFactor(0.98d);
		assertEquals(0.98d, setting.getResponseFactor());
	}
}
