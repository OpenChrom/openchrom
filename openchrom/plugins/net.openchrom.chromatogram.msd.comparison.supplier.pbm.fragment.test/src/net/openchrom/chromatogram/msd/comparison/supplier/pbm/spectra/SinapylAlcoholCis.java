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
 * Name: Sinapylalcohol (cis), BR 109, RT 69:01
 * Formula: C11H14O4
 * MW: 210
 * ExactMass: 210.089209
 * DB#: 68
 * Num Peaks: 58
 */
public class SinapylAlcoholCis implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public SinapylAlcoholCis() throws Exception {

		massSpectrum = new DefaultMassSpectrum();
		massSpectrum.addIon(new DefaultIon(27.0d, 71.0f));
		massSpectrum.addIon(new DefaultIon(39.0d, 94.0f));
		massSpectrum.addIon(new DefaultIon(40.0d, 74.0f));
		massSpectrum.addIon(new DefaultIon(45.0d, 132.0f));
		massSpectrum.addIon(new DefaultIon(50.0d, 90.0f));
		massSpectrum.addIon(new DefaultIon(51.0d, 158.0f));
		massSpectrum.addIon(new DefaultIon(52.0d, 74.0f));
		massSpectrum.addIon(new DefaultIon(53.0d, 181.0f));
		massSpectrum.addIon(new DefaultIon(55.0d, 224.0f));
		massSpectrum.addIon(new DefaultIon(57.0d, 83.0f));
		massSpectrum.addIon(new DefaultIon(63.0d, 76.0f));
		massSpectrum.addIon(new DefaultIon(65.0d, 113.0f));
		massSpectrum.addIon(new DefaultIon(66.0d, 69.0f));
		massSpectrum.addIon(new DefaultIon(69.0d, 93.0f));
		massSpectrum.addIon(new DefaultIon(71.0d, 95.0f));
		massSpectrum.addIon(new DefaultIon(75.0d, 130.0f));
		massSpectrum.addIon(new DefaultIon(77.0d, 264.0f));
		massSpectrum.addIon(new DefaultIon(78.0d, 112.0f));
		massSpectrum.addIon(new DefaultIon(79.0d, 112.0f));
		massSpectrum.addIon(new DefaultIon(81.0d, 78.0f));
		massSpectrum.addIon(new DefaultIon(89.0d, 82.0f));
		massSpectrum.addIon(new DefaultIon(91.0d, 169.0f));
		massSpectrum.addIon(new DefaultIon(92.0d, 80.0f));
		massSpectrum.addIon(new DefaultIon(93.0d, 86.0f));
		massSpectrum.addIon(new DefaultIon(94.0d, 84.0f));
		massSpectrum.addIon(new DefaultIon(103.0d, 154.0f));
		massSpectrum.addIon(new DefaultIon(105.0d, 128.0f));
		massSpectrum.addIon(new DefaultIon(106.0d, 105.0f));
		massSpectrum.addIon(new DefaultIon(107.0d, 162.0f));
		massSpectrum.addIon(new DefaultIon(108.0d, 91.0f));
		massSpectrum.addIon(new DefaultIon(115.0d, 73.0f));
		massSpectrum.addIon(new DefaultIon(119.0d, 84.0f));
		massSpectrum.addIon(new DefaultIon(121.0d, 152.0f));
		massSpectrum.addIon(new DefaultIon(122.0d, 73.0f));
		massSpectrum.addIon(new DefaultIon(123.0d, 121.0f));
		massSpectrum.addIon(new DefaultIon(131.0d, 94.0f));
		massSpectrum.addIon(new DefaultIon(133.0d, 80.0f));
		massSpectrum.addIon(new DefaultIon(135.0d, 100.0f));
		massSpectrum.addIon(new DefaultIon(137.0d, 111.0f));
		massSpectrum.addIon(new DefaultIon(139.0d, 123.0f));
		massSpectrum.addIon(new DefaultIon(147.0d, 100.0f));
		massSpectrum.addIon(new DefaultIon(149.0d, 277.0f));
		massSpectrum.addIon(new DefaultIon(151.0d, 80.0f));
		massSpectrum.addIon(new DefaultIon(154.0d, 459.0f));
		massSpectrum.addIon(new DefaultIon(155.0d, 71.0f));
		massSpectrum.addIon(new DefaultIon(161.0d, 97.0f));
		massSpectrum.addIon(new DefaultIon(163.0d, 84.0f));
		massSpectrum.addIon(new DefaultIon(164.0d, 80.0f));
		massSpectrum.addIon(new DefaultIon(165.0d, 81.0f));
		massSpectrum.addIon(new DefaultIon(167.0d, 999.0f));
		massSpectrum.addIon(new DefaultIon(168.0d, 152.0f));
		massSpectrum.addIon(new DefaultIon(177.0d, 115.0f));
		massSpectrum.addIon(new DefaultIon(179.0d, 141.0f));
		massSpectrum.addIon(new DefaultIon(181.0d, 126.0f));
		massSpectrum.addIon(new DefaultIon(182.0d, 237.0f));
		massSpectrum.addIon(new DefaultIon(193.0d, 65.0f));
		massSpectrum.addIon(new DefaultIon(210.0d, 951.0f));
		massSpectrum.addIon(new DefaultIon(211.0d, 101.0f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
