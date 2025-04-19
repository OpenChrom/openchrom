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

import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.ResultStatus;

import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatios;

import junit.framework.TestCase;

public class PeakRatioResult_3_Test extends TestCase {

	private PeakRatioResult peakRatioResult;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		peakRatioResult = new PeakRatioResult(ResultStatus.OK, "Test", new TraceRatios());
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertNotNull(peakRatioResult.getPeakRatios());
	}

	public void test2() {

		List<? extends IPeakRatio> peakRatios = peakRatioResult.getPeakRatios();
		assertEquals(0, peakRatios.size());
	}
}