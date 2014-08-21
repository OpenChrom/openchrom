/*******************************************************************************
 * Copyright (c) 2014 Dr. Philip Wenig.
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

import net.chemclipse.msd.model.core.IMassSpectrum;
import net.chemclipse.msd.model.implementation.DefaultIon;
import net.chemclipse.msd.model.implementation.DefaultMassSpectrum;

/*
 * Name: Syringylacetone, BR 105, RT 64:04
 * Formula: C11H14O4
 * MW: 210
 * ExactMass: 210.089209
 * DB#: 65
 * Num Peaks: 54
 */
public class Syringylacetone implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public Syringylacetone() throws Exception {

		massSpectrum = new DefaultMassSpectrum();
		massSpectrum.addIon(new DefaultIon(27.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(28.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(38.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(39.0d, 26.0f));
		massSpectrum.addIon(new DefaultIon(40.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(42.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(43.0d, 18.0f));
		massSpectrum.addIon(new DefaultIon(45.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(50.0d, 21.0f));
		massSpectrum.addIon(new DefaultIon(51.0d, 28.0f));
		massSpectrum.addIon(new DefaultIon(52.0d, 19.0f));
		massSpectrum.addIon(new DefaultIon(53.0d, 33.0f));
		massSpectrum.addIon(new DefaultIon(55.0d, 19.0f));
		massSpectrum.addIon(new DefaultIon(58.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(63.0d, 29.0f));
		massSpectrum.addIon(new DefaultIon(64.0d, 18.0f));
		massSpectrum.addIon(new DefaultIon(65.0d, 21.0f));
		massSpectrum.addIon(new DefaultIon(66.0d, 23.0f));
		massSpectrum.addIon(new DefaultIon(67.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(68.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(69.0d, 19.0f));
		massSpectrum.addIon(new DefaultIon(73.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(75.0d, 9.0f));
		massSpectrum.addIon(new DefaultIon(77.0d, 26.0f));
		massSpectrum.addIon(new DefaultIon(78.0d, 25.0f));
		massSpectrum.addIon(new DefaultIon(79.0d, 23.0f));
		massSpectrum.addIon(new DefaultIon(81.0d, 19.0f));
		massSpectrum.addIon(new DefaultIon(91.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(94.0d, 14.0f));
		massSpectrum.addIon(new DefaultIon(95.0d, 17.0f));
		massSpectrum.addIon(new DefaultIon(106.0d, 33.0f));
		massSpectrum.addIon(new DefaultIon(107.0d, 13.0f));
		massSpectrum.addIon(new DefaultIon(110.0d, 12.0f));
		massSpectrum.addIon(new DefaultIon(121.0d, 19.0f));
		massSpectrum.addIon(new DefaultIon(122.0d, 54.0f));
		massSpectrum.addIon(new DefaultIon(123.0d, 81.0f));
		massSpectrum.addIon(new DefaultIon(124.0d, 21.0f));
		massSpectrum.addIon(new DefaultIon(134.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(135.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(137.0d, 18.0f));
		massSpectrum.addIon(new DefaultIon(138.0d, 12.0f));
		massSpectrum.addIon(new DefaultIon(149.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(150.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(151.0d, 35.0f));
		massSpectrum.addIon(new DefaultIon(152.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(153.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(165.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(167.0d, 999.0f));
		massSpectrum.addIon(new DefaultIon(168.0d, 109.0f));
		massSpectrum.addIon(new DefaultIon(169.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(182.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(194.0d, 35.0f));
		massSpectrum.addIon(new DefaultIon(210.0d, 208.0f));
		massSpectrum.addIon(new DefaultIon(211.0d, 26.0f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
