/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.model.quant;

import junit.framework.TestCase;

public class QuantRatio_2_Test extends TestCase {

	private QuantRatio peakRatio;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		peakRatio = new QuantRatio();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertNull(peakRatio.getPeak());
	}

	public void test2() {

		peakRatio.setName("Test");
		assertEquals("Test", peakRatio.getName());
	}

	public void test3() {

		peakRatio.setDeviation(0.64d);
		assertEquals(0.64d, peakRatio.getDeviation());
	}

	public void test4() {

		peakRatio.setDeviationWarn(5.0d);
		assertEquals(5.0d, peakRatio.getDeviationWarn());
	}

	public void test5() {

		peakRatio.setDeviationError(15.0d);
		assertEquals(15.0d, peakRatio.getDeviationError());
	}

	public void test6() {

		peakRatio.setConcentration(4.5d);
		assertEquals(4.5d, peakRatio.getConcentration());
	}

	public void test7() {

		peakRatio.setConcentrationUnit("mg/L");
		assertEquals("mg/L", peakRatio.getConcentrationUnit());
	}

	public void test8() {

		peakRatio.setExpectedConcentration(1.0d);
		assertEquals(1.0d, peakRatio.getExpectedConcentration());
	}

	public void test9() {

		peakRatio.setQuantitationName("ISTD");
		assertEquals("ISTD", peakRatio.getQuantitationName());
	}
}
