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

public class IdentifierSetting_2_Test extends TestCase {

	private IdentifierSetting setting;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		setting = new IdentifierSetting();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		setting.setStartRetentionTimeMinutes(1.23d);
		assertEquals(1.23d, setting.getStartRetentionTimeMinutes());
	}

	public void test2() {

		setting.setStopRetentionTimeMinutes(6.63d);
		assertEquals(6.63d, setting.getStopRetentionTimeMinutes());
	}

	public void test3() {

		setting.setName("Styrene");
		assertEquals("Styrene", setting.getName());
	}

	public void test4() {

		setting.setCasNumber("100-42-5");
		assertEquals("100-42-5", setting.getCasNumber());
	}

	public void test5() {

		setting.setComments("Test");
		assertEquals("Test", setting.getComments());
	}

	public void test6() {

		setting.setContributor("OpenChrom");
		assertEquals("OpenChrom", setting.getContributor());
	}

	public void test7() {

		setting.setReferenceId("REF-7");
		assertEquals("REF-7", setting.getReferenceId());
	}

	public void test8() {

		setting.setTraces("103, 104");
		assertEquals("103, 104", setting.getTraces());
	}
}
