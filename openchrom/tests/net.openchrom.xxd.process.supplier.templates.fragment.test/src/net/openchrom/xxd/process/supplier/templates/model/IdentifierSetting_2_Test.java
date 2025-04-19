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

		setting.setPositionStart(1.23d);
		assertEquals(1.23d, setting.getPositionStart());
	}

	public void test2() {

		setting.setPositionStop(6.63d);
		assertEquals(6.63d, setting.getPositionStop());
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

		setting.setReference("REF-7");
		assertEquals("REF-7", setting.getReference());
	}

	public void test8() {

		setting.setTraces("103, 104");
		assertEquals("103, 104", setting.getTraces());
	}

	public void test9() {

		setting.setReferenceIdentifier("Styrene");
		assertEquals("Styrene", setting.getReferenceIdentifier());
	}

	public void test10() {

		setting.setPositionDirective(PositionDirective.RETENTION_INDEX);
		assertEquals(PositionDirective.RETENTION_INDEX, setting.getPositionDirective());
	}
}