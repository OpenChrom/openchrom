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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IdentifierSetting_1_Test {

	private IdentifierSetting setting = new IdentifierSetting();

	@Test
	public void test1() {

		assertEquals(0.0d, setting.getPositionStart(), 0);
	}

	@Test
	public void test2() {

		assertEquals(0.0d, setting.getPositionStop(), 0);
	}

	@Test
	public void test3() {

		assertEquals("", setting.getName());
	}

	@Test
	public void test4() {

		assertEquals("", setting.getCasNumber());
	}

	@Test
	public void test5() {

		assertEquals("", setting.getComments());
	}

	@Test
	public void test6() {

		assertEquals("", setting.getContributor());
	}

	@Test
	public void test7() {

		assertEquals("", setting.getReferenceIdentifier());
	}

	@Test
	public void test8() {

		assertEquals("", setting.getTraces());
	}

	@Test
	public void test9() {

		assertEquals("", setting.getPositionRelativePeakName());
	}

	@Test
	public void test10() {

		assertEquals(PositionDirective.RETENTION_TIME_MIN, setting.getPositionDirective());
	}
}