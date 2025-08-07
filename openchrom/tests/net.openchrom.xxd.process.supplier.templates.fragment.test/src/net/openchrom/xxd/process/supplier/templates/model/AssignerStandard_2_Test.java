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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AssignerStandard_2_Test {

	private AssignerStandard setting = new AssignerStandard();

	@Test
	public void test1() {

		assertEquals("", setting.getName());
	}

	@Test
	public void test2() {

		setting.setPositionStart(92.2d);
		assertEquals(92.2d, setting.getPositionStart(), 0);
	}

	@Test
	public void test3() {

		setting.setPositionStop(392.4d);
		assertEquals(392.4d, setting.getPositionStop(), 0);
	}

	@Test
	public void test4() {

		setting.setConcentration(2.78d);
		assertEquals(2.78d, setting.getConcentration(), 0);
	}

	@Test
	public void test5() {

		setting.setConcentrationUnit("mg/L");
		assertEquals("mg/L", setting.getConcentrationUnit());
	}

	@Test
	public void test6() {

		setting.setCompensationFactor(0.98d);
		assertEquals(0.98d, setting.getCompensationFactor(), 0);
	}

	@Test
	public void test7() {

		setting.setTracesIdentification("104 103");
		assertEquals("104 103", setting.getTracesIdentification());
	}

	@Test
	public void test8() {

		setting.setPositionDirective(PositionDirective.RETENTION_INDEX);
		assertEquals(PositionDirective.RETENTION_INDEX, setting.getPositionDirective());
	}
}