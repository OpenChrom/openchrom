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

import org.eclipse.chemclipse.model.core.PeakType;
import org.junit.jupiter.api.Test;

public class DetectorSetting_1_Test {

	private DetectorSetting setting = new DetectorSetting();

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

		assertEquals(PeakType.VV, setting.getPeakType());
	}

	@Test
	public void test4() {

		assertEquals("", setting.getTraces());
	}

	@Test
	public void test5() {

		assertEquals(false, setting.isOptimizeRange());
	}

	@Test
	public void test6() {

		assertEquals("", setting.getReferenceIdentifier());
	}

	@Test
	public void test7() {

		assertEquals("", setting.getName());
	}

	@Test
	public void test8() {

		assertEquals(PositionDirective.RETENTION_TIME_MIN, setting.getPositionDirective());
	}
}