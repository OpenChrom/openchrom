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
package net.openchrom.xxd.classifier.supplier.ratios.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.ResultStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatios;

@TestInstance(Lifecycle.PER_CLASS)
public class PeakRatioResult_1_Test {

	private PeakRatioResult peakRatioResult;

	@BeforeAll
	public void setUp() {

		peakRatioResult = new PeakRatioResult(ResultStatus.OK, "Test", new QuantRatios());
	}

	@Test
	public void test1() {

		assertNotNull(peakRatioResult.getPeakRatios());
	}

	@Test
	public void test2() {

		List<? extends IPeakRatio> peakRatios = peakRatioResult.getPeakRatios();
		assertEquals(0, peakRatios.size());
	}
}