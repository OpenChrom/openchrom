/*******************************************************************************
 * Copyright (c) 2014, 2015 Dr. Philip Wenig.
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

import net.chemclipse.msd.model.core.IScanMSD;
import net.chemclipse.msd.model.implementation.Ion;
import net.chemclipse.msd.model.implementation.MassSpectrum;

/*
 * Name: Sinapylalcohol (cis), BR 109, RT 69:01
 * Formula: C11H14O4
 * MW: 210
 * ExactMass: 210.089209
 * DB#: 68
 * Num Peaks: 58
 */
public class SinapylAlcoholCis implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public SinapylAlcoholCis() throws Exception {

		massSpectrum = new MassSpectrum();
		massSpectrum.addIon(new Ion(27.0d, 71.0f));
		massSpectrum.addIon(new Ion(39.0d, 94.0f));
		massSpectrum.addIon(new Ion(40.0d, 74.0f));
		massSpectrum.addIon(new Ion(45.0d, 132.0f));
		massSpectrum.addIon(new Ion(50.0d, 90.0f));
		massSpectrum.addIon(new Ion(51.0d, 158.0f));
		massSpectrum.addIon(new Ion(52.0d, 74.0f));
		massSpectrum.addIon(new Ion(53.0d, 181.0f));
		massSpectrum.addIon(new Ion(55.0d, 224.0f));
		massSpectrum.addIon(new Ion(57.0d, 83.0f));
		massSpectrum.addIon(new Ion(63.0d, 76.0f));
		massSpectrum.addIon(new Ion(65.0d, 113.0f));
		massSpectrum.addIon(new Ion(66.0d, 69.0f));
		massSpectrum.addIon(new Ion(69.0d, 93.0f));
		massSpectrum.addIon(new Ion(71.0d, 95.0f));
		massSpectrum.addIon(new Ion(75.0d, 130.0f));
		massSpectrum.addIon(new Ion(77.0d, 264.0f));
		massSpectrum.addIon(new Ion(78.0d, 112.0f));
		massSpectrum.addIon(new Ion(79.0d, 112.0f));
		massSpectrum.addIon(new Ion(81.0d, 78.0f));
		massSpectrum.addIon(new Ion(89.0d, 82.0f));
		massSpectrum.addIon(new Ion(91.0d, 169.0f));
		massSpectrum.addIon(new Ion(92.0d, 80.0f));
		massSpectrum.addIon(new Ion(93.0d, 86.0f));
		massSpectrum.addIon(new Ion(94.0d, 84.0f));
		massSpectrum.addIon(new Ion(103.0d, 154.0f));
		massSpectrum.addIon(new Ion(105.0d, 128.0f));
		massSpectrum.addIon(new Ion(106.0d, 105.0f));
		massSpectrum.addIon(new Ion(107.0d, 162.0f));
		massSpectrum.addIon(new Ion(108.0d, 91.0f));
		massSpectrum.addIon(new Ion(115.0d, 73.0f));
		massSpectrum.addIon(new Ion(119.0d, 84.0f));
		massSpectrum.addIon(new Ion(121.0d, 152.0f));
		massSpectrum.addIon(new Ion(122.0d, 73.0f));
		massSpectrum.addIon(new Ion(123.0d, 121.0f));
		massSpectrum.addIon(new Ion(131.0d, 94.0f));
		massSpectrum.addIon(new Ion(133.0d, 80.0f));
		massSpectrum.addIon(new Ion(135.0d, 100.0f));
		massSpectrum.addIon(new Ion(137.0d, 111.0f));
		massSpectrum.addIon(new Ion(139.0d, 123.0f));
		massSpectrum.addIon(new Ion(147.0d, 100.0f));
		massSpectrum.addIon(new Ion(149.0d, 277.0f));
		massSpectrum.addIon(new Ion(151.0d, 80.0f));
		massSpectrum.addIon(new Ion(154.0d, 459.0f));
		massSpectrum.addIon(new Ion(155.0d, 71.0f));
		massSpectrum.addIon(new Ion(161.0d, 97.0f));
		massSpectrum.addIon(new Ion(163.0d, 84.0f));
		massSpectrum.addIon(new Ion(164.0d, 80.0f));
		massSpectrum.addIon(new Ion(165.0d, 81.0f));
		massSpectrum.addIon(new Ion(167.0d, 999.0f));
		massSpectrum.addIon(new Ion(168.0d, 152.0f));
		massSpectrum.addIon(new Ion(177.0d, 115.0f));
		massSpectrum.addIon(new Ion(179.0d, 141.0f));
		massSpectrum.addIon(new Ion(181.0d, 126.0f));
		massSpectrum.addIon(new Ion(182.0d, 237.0f));
		massSpectrum.addIon(new Ion(193.0d, 65.0f));
		massSpectrum.addIon(new Ion(210.0d, 951.0f));
		massSpectrum.addIon(new Ion(211.0d, 101.0f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
