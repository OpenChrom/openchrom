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

public class IntegratorSetting_1_Test {

	private IntegratorSetting setting = new IntegratorSetting();

	@Test
	public void testOa() {

		assertEquals("Trapezoid", IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID);
	}

	@Test
	public void testOb() {

		assertEquals("org.eclipse.chemclipse.chromatogram.xxd.integrator.supplier.trapezoid.peakIntegrator", IntegratorSetting.INTEGRATOR_ID_TRAPEZOID);
	}

	@Test
	public void testOc() {

		assertEquals("Max", IntegratorSetting.INTEGRATOR_NAME_MAX);
	}

	@Test
	public void testOd() {

		assertEquals("org.eclipse.chemclipse.chromatogram.msd.integrator.supplier.peakmax.peakIntegrator", IntegratorSetting.INTEGRATOR_ID_MAX);
	}

	@Test
	public void test1() {

		assertEquals("", setting.getIdentifier());
	}

	@Test
	public void test2() {

		assertEquals(0.0d, setting.getPositionStart(), 0);
	}

	@Test
	public void test3() {

		assertEquals(0.0d, setting.getPositionStop(), 0);
	}

	@Test
	public void test4() {

		assertEquals("", setting.getIntegrator());
	}

	@Test
	public void test5() {

		assertEquals(PositionDirective.RETENTION_TIME_MIN, setting.getPositionDirective());
	}
}