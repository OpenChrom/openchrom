/*******************************************************************************
 * Copyright (c) 2022, 2025 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.support;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.chemclipse.csd.model.core.IPeakCSD;
import org.eclipse.chemclipse.csd.model.core.IPeakModelCSD;
import org.eclipse.chemclipse.csd.model.core.IScanCSD;
import org.eclipse.chemclipse.csd.model.implementation.PeakCSD;
import org.eclipse.chemclipse.csd.model.implementation.PeakModelCSD;
import org.eclipse.chemclipse.csd.model.implementation.ScanCSD;
import org.eclipse.chemclipse.model.core.IPeakIntensityValues;
import org.eclipse.chemclipse.model.implementation.PeakIntensityValues;
import org.eclipse.chemclipse.msd.model.core.IPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IPeakMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IPeakModelMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.PeakMSD;
import org.eclipse.chemclipse.msd.model.implementation.PeakMassSpectrum;
import org.eclipse.chemclipse.msd.model.implementation.PeakModelMSD;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;
import org.eclipse.chemclipse.wsd.model.core.IPeakModelWSD;
import org.eclipse.chemclipse.wsd.model.core.IPeakWSD;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.core.implementation.PeakModelWSD;
import org.eclipse.chemclipse.wsd.model.core.implementation.PeakWSD;
import org.eclipse.chemclipse.wsd.model.core.implementation.ScanSignalWSD;
import org.eclipse.chemclipse.wsd.model.core.implementation.ScanWSD;
import org.junit.Test;

public class PeakSupport_1_Test {

	private PeakSupport peakSupport = new PeakSupport();

	@Test
	public void test1() {

		IPeakMSD peak = null;
		String traces = null;
		assertFalse(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test2() {

		IPeakMSD peak = null;
		String traces = "";
		assertFalse(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test3a() {

		IScanCSD scan = new ScanCSD(100.0f);
		IPeakCSD peak = createPeak(scan);
		String traces = null;
		assertFalse(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test3b() {

		IScanMSD scan = new ScanMSD();
		String traces = null;
		IPeakMSD peak = createPeak(scan);
		assertFalse(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test3c() {

		IScanWSD scan = new ScanWSD();
		String traces = null;
		IPeakWSD peak = createPeak(scan);
		assertFalse(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test4a() {

		IScanCSD scan = new ScanCSD(100.0f);
		IPeakCSD peak = createPeak(scan);
		String traces = "";
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test4b() {

		IScanMSD scan = new ScanMSD();
		String traces = "";
		IPeakMSD peak = createPeak(scan);
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test4c() {

		IScanWSD scan = new ScanWSD();
		String traces = "";
		IPeakWSD peak = createPeak(scan);
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test5a() {

		/*
		 * Same as 4a.
		 */
		assertTrue(true);
	}

	@Test
	public void test5b() {

		IScanMSD scan = new ScanMSD();
		scan.addIon(new Ion(18.0d, 1000.0f));
		String traces = "";
		IPeakMSD peak = createPeak(scan);
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test5c() {

		IScanWSD scan = new ScanWSD();
		scan.addScanSignal(new ScanSignalWSD(200, 1000.0f));
		String traces = "";
		IPeakWSD peak = createPeak(scan);
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test6a() {

		IScanCSD scan = new ScanCSD(100.0f);
		IPeakCSD peak = createPeak(scan);
		String traces = "18";
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test6b() {

		IScanMSD scan = new ScanMSD();
		scan.addIon(new Ion(18.0d, 1000.0f));
		String traces = "18";
		IPeakMSD peak = createPeak(scan);
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test6c() {

		IScanWSD scan = new ScanWSD();
		scan.addScanSignal(new ScanSignalWSD(200, 1000.0f));
		String traces = "200";
		IPeakWSD peak = createPeak(scan);
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test7a() {

		IScanCSD scan = new ScanCSD(100.0f);
		IPeakCSD peak = createPeak(scan);
		String traces = "18, 28";
		assertTrue(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test7b() {

		IScanMSD scan = new ScanMSD();
		scan.addIon(new Ion(18.0d, 1000.0f));
		String traces = "18, 28";
		IPeakMSD peak = createPeak(scan);
		assertFalse(peakSupport.isPeakRelevant(peak, traces));
	}

	@Test
	public void test7c() {

		IScanWSD scan = new ScanWSD();
		scan.addScanSignal(new ScanSignalWSD(200, 1000.0f));
		String traces = "200, 202";
		IPeakWSD peak = createPeak(scan);
		assertFalse(peakSupport.isPeakRelevant(peak, traces));
	}

	private IPeakCSD createPeak(IScanCSD scan) {

		IPeakModelCSD peakModelCSD = new PeakModelCSD(scan, getPeakIntensityValues());
		return new PeakCSD(peakModelCSD);
	}

	private IPeakMSD createPeak(IScanMSD scan) {

		IPeakMassSpectrum peakMaximum = new PeakMassSpectrum(scan);
		IPeakModelMSD peakModelMSD = new PeakModelMSD(peakMaximum, getPeakIntensityValues());
		return new PeakMSD(peakModelMSD);
	}

	private IPeakWSD createPeak(IScanWSD scan) {

		IPeakModelWSD peakModelWSD = new PeakModelWSD(scan, getPeakIntensityValues());
		return new PeakWSD(peakModelWSD);
	}

	private IPeakIntensityValues getPeakIntensityValues() {

		IPeakIntensityValues peakIntensityValues = new PeakIntensityValues();
		peakIntensityValues.addIntensityValue(100, 10.0f);
		peakIntensityValues.addIntensityValue(200, 100.0f);
		peakIntensityValues.addIntensityValue(300, 10.0f);

		return peakIntensityValues;
	}
}