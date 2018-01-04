/*******************************************************************************
 * Copyright (c) 2014, 2018 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra;

import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;

/*
 * Name: Syringylacetone, BR 105, RT 64:04
 * Formula: C11H14O4
 * MW: 210
 * ExactMass: 210.089209
 * DB#: 65
 * Num Peaks: 54
 */
public class Syringylacetone implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public Syringylacetone() throws Exception {
		massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(27.0d, 11.0f));
		massSpectrum.addIon(new Ion(28.0d, 8.0f));
		massSpectrum.addIon(new Ion(38.0d, 11.0f));
		massSpectrum.addIon(new Ion(39.0d, 26.0f));
		massSpectrum.addIon(new Ion(40.0d, 11.0f));
		massSpectrum.addIon(new Ion(42.0d, 10.0f));
		massSpectrum.addIon(new Ion(43.0d, 18.0f));
		massSpectrum.addIon(new Ion(45.0d, 10.0f));
		massSpectrum.addIon(new Ion(50.0d, 21.0f));
		massSpectrum.addIon(new Ion(51.0d, 28.0f));
		massSpectrum.addIon(new Ion(52.0d, 19.0f));
		massSpectrum.addIon(new Ion(53.0d, 33.0f));
		massSpectrum.addIon(new Ion(55.0d, 19.0f));
		massSpectrum.addIon(new Ion(58.0d, 11.0f));
		massSpectrum.addIon(new Ion(63.0d, 29.0f));
		massSpectrum.addIon(new Ion(64.0d, 18.0f));
		massSpectrum.addIon(new Ion(65.0d, 21.0f));
		massSpectrum.addIon(new Ion(66.0d, 23.0f));
		massSpectrum.addIon(new Ion(67.0d, 16.0f));
		massSpectrum.addIon(new Ion(68.0d, 10.0f));
		massSpectrum.addIon(new Ion(69.0d, 19.0f));
		massSpectrum.addIon(new Ion(73.0d, 2.0f));
		massSpectrum.addIon(new Ion(75.0d, 9.0f));
		massSpectrum.addIon(new Ion(77.0d, 26.0f));
		massSpectrum.addIon(new Ion(78.0d, 25.0f));
		massSpectrum.addIon(new Ion(79.0d, 23.0f));
		massSpectrum.addIon(new Ion(81.0d, 19.0f));
		massSpectrum.addIon(new Ion(91.0d, 10.0f));
		massSpectrum.addIon(new Ion(94.0d, 14.0f));
		massSpectrum.addIon(new Ion(95.0d, 17.0f));
		massSpectrum.addIon(new Ion(106.0d, 33.0f));
		massSpectrum.addIon(new Ion(107.0d, 13.0f));
		massSpectrum.addIon(new Ion(110.0d, 12.0f));
		massSpectrum.addIon(new Ion(121.0d, 19.0f));
		massSpectrum.addIon(new Ion(122.0d, 54.0f));
		massSpectrum.addIon(new Ion(123.0d, 81.0f));
		massSpectrum.addIon(new Ion(124.0d, 21.0f));
		massSpectrum.addIon(new Ion(134.0d, 11.0f));
		massSpectrum.addIon(new Ion(135.0d, 10.0f));
		massSpectrum.addIon(new Ion(137.0d, 18.0f));
		massSpectrum.addIon(new Ion(138.0d, 12.0f));
		massSpectrum.addIon(new Ion(149.0d, 10.0f));
		massSpectrum.addIon(new Ion(150.0d, 10.0f));
		massSpectrum.addIon(new Ion(151.0d, 35.0f));
		massSpectrum.addIon(new Ion(152.0d, 11.0f));
		massSpectrum.addIon(new Ion(153.0d, 16.0f));
		massSpectrum.addIon(new Ion(165.0d, 11.0f));
		massSpectrum.addIon(new Ion(167.0d, 999.0f));
		massSpectrum.addIon(new Ion(168.0d, 109.0f));
		massSpectrum.addIon(new Ion(169.0d, 11.0f));
		massSpectrum.addIon(new Ion(182.0d, 11.0f));
		massSpectrum.addIon(new Ion(194.0d, 35.0f));
		massSpectrum.addIon(new Ion(210.0d, 208.0f));
		massSpectrum.addIon(new Ion(211.0d, 26.0f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
