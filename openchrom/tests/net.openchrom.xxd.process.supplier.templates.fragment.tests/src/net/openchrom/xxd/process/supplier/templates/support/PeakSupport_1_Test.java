/*******************************************************************************
 * Copyright (c) 2022, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.support;

import java.util.HashSet;
import java.util.Set;

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

import junit.framework.TestCase;

public class PeakSupport_1_Test extends TestCase {

	@Override
	protected void setUp() throws Exception {

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		IPeakMSD peak = null;
		Set<Integer> traces = null;
		assertFalse(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test2() {

		IPeakMSD peak = null;
		Set<Integer> traces = new HashSet<>();
		assertFalse(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test3a() {

		IScanCSD scan = new ScanCSD(100.0f);
		IPeakCSD peak = createPeak(scan);
		Set<Integer> traces = null;
		assertFalse(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test3b() {

		IScanMSD scan = new ScanMSD();
		Set<Integer> traces = null;
		IPeakMSD peak = createPeak(scan);
		assertFalse(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test3c() {

		IScanWSD scan = new ScanWSD();
		Set<Integer> traces = null;
		IPeakWSD peak = createPeak(scan);
		assertFalse(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test4a() {

		IScanCSD scan = new ScanCSD(100.0f);
		IPeakCSD peak = createPeak(scan);
		Set<Integer> traces = new HashSet<>();
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test4b() {

		IScanMSD scan = new ScanMSD();
		Set<Integer> traces = new HashSet<>();
		IPeakMSD peak = createPeak(scan);
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test4c() {

		IScanWSD scan = new ScanWSD();
		Set<Integer> traces = new HashSet<>();
		IPeakWSD peak = createPeak(scan);
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test5a() {

		/*
		 * Same as 4a.
		 */
		assertTrue(true);
	}

	public void test5b() {

		IScanMSD scan = new ScanMSD();
		scan.addIon(new Ion(18.0d, 1000.0f));
		Set<Integer> traces = new HashSet<>();
		IPeakMSD peak = createPeak(scan);
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test5c() {

		IScanWSD scan = new ScanWSD();
		scan.addScanSignal(new ScanSignalWSD(200, 1000.0f));
		Set<Integer> traces = new HashSet<>();
		IPeakWSD peak = createPeak(scan);
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test6a() {

		IScanCSD scan = new ScanCSD(100.0f);
		IPeakCSD peak = createPeak(scan);
		Set<Integer> traces = new HashSet<>();
		traces.add(18);
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test6b() {

		IScanMSD scan = new ScanMSD();
		scan.addIon(new Ion(18.0d, 1000.0f));
		Set<Integer> traces = new HashSet<>();
		traces.add(18);
		IPeakMSD peak = createPeak(scan);
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test6c() {

		IScanWSD scan = new ScanWSD();
		scan.addScanSignal(new ScanSignalWSD(200, 1000.0f));
		Set<Integer> traces = new HashSet<>();
		traces.add(200);
		IPeakWSD peak = createPeak(scan);
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test7a() {

		IScanCSD scan = new ScanCSD(100.0f);
		IPeakCSD peak = createPeak(scan);
		Set<Integer> traces = new HashSet<>();
		traces.add(18);
		traces.add(28);
		assertTrue(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test7b() {

		IScanMSD scan = new ScanMSD();
		scan.addIon(new Ion(18.0d, 1000.0f));
		Set<Integer> traces = new HashSet<>();
		traces.add(18);
		traces.add(28);
		IPeakMSD peak = createPeak(scan);
		assertFalse(PeakSupport.isPeakRelevant(peak, traces));
	}

	public void test7c() {

		IScanWSD scan = new ScanWSD();
		scan.addScanSignal(new ScanSignalWSD(200, 1000.0f));
		Set<Integer> traces = new HashSet<>();
		traces.add(200);
		traces.add(202);
		IPeakWSD peak = createPeak(scan);
		assertFalse(PeakSupport.isPeakRelevant(peak, traces));
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
		//
		return peakIntensityValues;
	}
}