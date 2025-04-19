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

public class IntegratorSetting_1_Test extends TestCase {

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

	public void testOa() {

		assertEquals("Trapezoid", IntegratorSetting.INTEGRATOR_NAME_TRAPEZOID);
	}

	public void testOb() {

		assertEquals("org.eclipse.chemclipse.chromatogram.xxd.integrator.supplier.trapezoid.peakIntegrator", IntegratorSetting.INTEGRATOR_ID_TRAPEZOID);
	}

	public void testOc() {

		assertEquals("Max", IntegratorSetting.INTEGRATOR_NAME_MAX);
	}

	public void testOd() {

		assertEquals("org.eclipse.chemclipse.chromatogram.msd.integrator.supplier.peakmax.peakIntegrator", IntegratorSetting.INTEGRATOR_ID_MAX);
	}

	public void test1() {

		assertEquals("", setting.getIdentifier());
	}

	public void test2() {

		assertEquals(0.0d, setting.getPositionStart());
	}

	public void test3() {

		assertEquals(0.0d, setting.getPositionStop());
	}

	public void test4() {

		assertEquals("", setting.getIntegrator());
	}

	public void test5() {

		assertEquals(PositionDirective.RETENTION_TIME_MIN, setting.getPositionDirective());
	}
}