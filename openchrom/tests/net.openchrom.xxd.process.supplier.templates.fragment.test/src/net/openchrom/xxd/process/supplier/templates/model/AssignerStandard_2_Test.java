/*******************************************************************************
 * Copyright (c) 2019, 2023 Lablicate GmbH.
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

		setting.setPositionStart(92.2d);
		assertEquals(92.2d, setting.getPositionStart());
	}

	public void test3() {

		setting.setPositionStop(392.4d);
		assertEquals(392.4d, setting.getPositionStop());
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

		setting.setCompensationFactor(0.98d);
		assertEquals(0.98d, setting.getCompensationFactor());
	}

	public void test7() {

		setting.setTracesIdentification("104 103");
		assertEquals("104 103", setting.getTracesIdentification());
	}

	public void test8() {

		setting.setPositionDirective(PositionDirective.RETENTION_INDEX);
		assertEquals(PositionDirective.RETENTION_INDEX, setting.getPositionDirective());
	}
}