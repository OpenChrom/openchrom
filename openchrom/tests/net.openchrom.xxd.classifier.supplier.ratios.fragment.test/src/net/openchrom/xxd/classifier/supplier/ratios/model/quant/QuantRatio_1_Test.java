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
public class QuantRatio_1_Test {

	private QuantRatio peakRatio = new QuantRatio();

	@Test
	public void test1() {

		assertNull(peakRatio.getPeak());
	}

	@Test
	public void test2() {

		assertEquals("", peakRatio.getName());
	}

	@Test
	public void test3() {

		assertEquals(0.0d, peakRatio.getDeviation(), 0);
	}

	@Test
	public void test4() {

		assertEquals(0.0d, peakRatio.getDeviationWarn(), 0);
	}

	@Test
	public void test5() {

		assertEquals(0.0d, peakRatio.getDeviationError(), 0);
	}

	@Test
	public void test6() {

		assertEquals(0.0d, peakRatio.getConcentration(), 0);
	}

	@Test
	public void test7() {

		assertEquals("", peakRatio.getConcentrationUnit());
	}

	@Test
	public void test8() {

		assertEquals(0.0d, peakRatio.getExpectedConcentration(), 0);
	}

	@Test
	public void test9() {

		assertEquals("", peakRatio.getQuantitationName());
	}
}
