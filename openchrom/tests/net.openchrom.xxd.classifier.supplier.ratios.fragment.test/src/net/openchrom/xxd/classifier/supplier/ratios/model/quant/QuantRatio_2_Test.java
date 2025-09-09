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
package net.openchrom.xxd.classifier.supplier.ratios.model.quant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class QuantRatio_2_Test {

	private QuantRatio peakRatio = new QuantRatio();

	@Test
	public void test1() {

		assertNull(peakRatio.getPeak());
	}

	@Test
	public void test2() {

		peakRatio.setName("Test");
		assertEquals("Test", peakRatio.getName());
	}

	@Test
	public void test3() {

		peakRatio.setDeviation(0.64d);
		assertEquals(0.64d, peakRatio.getDeviation(), 0);
	}

	@Test
	public void test4() {

		peakRatio.setDeviationWarn(5.0d);
		assertEquals(5.0d, peakRatio.getDeviationWarn(), 0);
	}

	@Test
	public void test5() {

		peakRatio.setDeviationError(15.0d);
		assertEquals(15.0d, peakRatio.getDeviationError(), 0);
	}

	@Test
	public void test6() {

		peakRatio.setConcentration(4.5d);
		assertEquals(4.5d, peakRatio.getConcentration(), 0);
	}

	@Test
	public void test7() {

		peakRatio.setConcentrationUnit("mg/L");
		assertEquals("mg/L", peakRatio.getConcentrationUnit());
	}

	@Test
	public void test8() {

		peakRatio.setExpectedConcentration(1.0d);
		assertEquals(1.0d, peakRatio.getExpectedConcentration(), 0);
	}

	@Test
	public void test9() {

		peakRatio.setQuantitationName("ISTD");
		assertEquals("ISTD", peakRatio.getQuantitationName());
	}
}
