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

public class DetectorSetting_2_Test {

	private DetectorSetting setting = new DetectorSetting();

	@Test
	public void test1() {

		setting.setPositionStart(0.78d);
		assertEquals(0.78d, setting.getPositionStart(), 0);
	}

	@Test
	public void test2() {

		setting.setPositionStop(1.28d);
		assertEquals(1.28d, setting.getPositionStop(), 0);
	}

	@Test
	public void test4() {

		setting.setTraces("103, 104");
		assertEquals("103, 104", setting.getTraces());
	}

	@Test
	public void test5() {

		setting.setOptimizeRange(true);
		assertEquals(true, setting.isOptimizeRange());
	}

	@Test
	public void test6() {

		setting.setReferenceIdentifier("Styrene");
		assertEquals("Styrene", setting.getReferenceIdentifier());
	}

	@Test
	public void test7() {

		setting.setName("Benzene");
		assertEquals("Benzene", setting.getName());
	}

	@Test
	public void test8() {

		setting.setPositionDirective(PositionDirective.RETENTION_INDEX);
		assertEquals(PositionDirective.RETENTION_INDEX, setting.getPositionDirective());
	}
}