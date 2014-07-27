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

import net.chemclipse.chromatogram.msd.model.core.IMassSpectrum;
import net.chemclipse.chromatogram.msd.model.implementation.DefaultIon;
import net.chemclipse.chromatogram.msd.model.implementation.DefaultMassSpectrum;

/*
 * Name: Sinapyl alcohol, trans BR 110, RT 71:85
 * Formula: C11H14O4
 * MW: 210
 * ExactMass: 210.089209
 * DB#: 137
 * Num Peaks: 122
 */
public class SinapylAlcohol implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public SinapylAlcohol() throws Exception {

		massSpectrum = new DefaultMassSpectrum();
		massSpectrum.addIon(new DefaultIon(27.0d, 84.0f));
		massSpectrum.addIon(new DefaultIon(38.0d, 60.0f));
		massSpectrum.addIon(new DefaultIon(39.0d, 228.0f));
		massSpectrum.addIon(new DefaultIon(40.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(41.0d, 116.0f));
		massSpectrum.addIon(new DefaultIon(42.0d, 29.0f));
		massSpectrum.addIon(new DefaultIon(43.0d, 121.0f));
		massSpectrum.addIon(new DefaultIon(45.0d, 49.0f));
		massSpectrum.addIon(new DefaultIon(47.0d, 17.0f));
		massSpectrum.addIon(new DefaultIon(49.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(50.0d, 116.0f));
		massSpectrum.addIon(new DefaultIon(51.0d, 224.0f));
		massSpectrum.addIon(new DefaultIon(52.0d, 104.0f));
		massSpectrum.addIon(new DefaultIon(53.0d, 287.0f));
		massSpectrum.addIon(new DefaultIon(54.0d, 44.0f));
		massSpectrum.addIon(new DefaultIon(55.0d, 338.0f));
		massSpectrum.addIon(new DefaultIon(57.0d, 72.0f));
		massSpectrum.addIon(new DefaultIon(59.0d, 40.0f));
		massSpectrum.addIon(new DefaultIon(60.0d, 21.0f));
		massSpectrum.addIon(new DefaultIon(61.0d, 34.0f));
		massSpectrum.addIon(new DefaultIon(62.0d, 51.0f));
		massSpectrum.addIon(new DefaultIon(63.0d, 129.0f));
		massSpectrum.addIon(new DefaultIon(64.0d, 56.0f));
		massSpectrum.addIon(new DefaultIon(65.0d, 232.0f));
		massSpectrum.addIon(new DefaultIon(66.0d, 113.0f));
		massSpectrum.addIon(new DefaultIon(67.0d, 99.0f));
		massSpectrum.addIon(new DefaultIon(68.0d, 46.0f));
		massSpectrum.addIon(new DefaultIon(69.0d, 80.0f));
		massSpectrum.addIon(new DefaultIon(70.0d, 20.0f));
		massSpectrum.addIon(new DefaultIon(71.0d, 36.0f));
		massSpectrum.addIon(new DefaultIon(74.0d, 39.0f));
		massSpectrum.addIon(new DefaultIon(75.0d, 39.0f));
		massSpectrum.addIon(new DefaultIon(76.0d, 66.0f));
		massSpectrum.addIon(new DefaultIon(77.0d, 437.0f));
		massSpectrum.addIon(new DefaultIon(78.0d, 201.0f));
		massSpectrum.addIon(new DefaultIon(79.0d, 216.0f));
		massSpectrum.addIon(new DefaultIon(80.0d, 55.0f));
		massSpectrum.addIon(new DefaultIon(81.0d, 83.0f));
		massSpectrum.addIon(new DefaultIon(82.0d, 36.0f));
		massSpectrum.addIon(new DefaultIon(83.0d, 42.0f));
		massSpectrum.addIon(new DefaultIon(85.0d, 58.0f));
		massSpectrum.addIon(new DefaultIon(86.0d, 18.0f));
		massSpectrum.addIon(new DefaultIon(89.0d, 121.0f));
		massSpectrum.addIon(new DefaultIon(90.0d, 66.0f));
		massSpectrum.addIon(new DefaultIon(91.0d, 253.0f));
		massSpectrum.addIon(new DefaultIon(92.0d, 55.0f));
		massSpectrum.addIon(new DefaultIon(93.0d, 102.0f));
		massSpectrum.addIon(new DefaultIon(94.0d, 58.0f));
		massSpectrum.addIon(new DefaultIon(95.0d, 93.0f));
		massSpectrum.addIon(new DefaultIon(96.0d, 18.0f));
		massSpectrum.addIon(new DefaultIon(97.0d, 26.0f));
		massSpectrum.addIon(new DefaultIon(101.0d, 18.0f));
		massSpectrum.addIon(new DefaultIon(102.0d, 39.0f));
		massSpectrum.addIon(new DefaultIon(103.0d, 146.0f));
		massSpectrum.addIon(new DefaultIon(104.0d, 40.0f));
		massSpectrum.addIon(new DefaultIon(105.0d, 128.0f));
		massSpectrum.addIon(new DefaultIon(106.0d, 167.0f));
		massSpectrum.addIon(new DefaultIon(107.0d, 198.0f));
		massSpectrum.addIon(new DefaultIon(108.0d, 52.0f));
		massSpectrum.addIon(new DefaultIon(109.0d, 48.0f));
		massSpectrum.addIon(new DefaultIon(110.0d, 28.0f));
		massSpectrum.addIon(new DefaultIon(111.0d, 49.0f));
		massSpectrum.addIon(new DefaultIon(115.0d, 41.0f));
		massSpectrum.addIon(new DefaultIon(116.0d, 22.0f));
		massSpectrum.addIon(new DefaultIon(117.0d, 41.0f));
		massSpectrum.addIon(new DefaultIon(118.0d, 83.0f));
		massSpectrum.addIon(new DefaultIon(119.0d, 100.0f));
		massSpectrum.addIon(new DefaultIon(120.0d, 46.0f));
		massSpectrum.addIon(new DefaultIon(121.0d, 239.0f));
		massSpectrum.addIon(new DefaultIon(122.0d, 73.0f));
		massSpectrum.addIon(new DefaultIon(123.0d, 146.0f));
		massSpectrum.addIon(new DefaultIon(124.0d, 51.0f));
		massSpectrum.addIon(new DefaultIon(125.0d, 41.0f));
		massSpectrum.addIon(new DefaultIon(126.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(129.0d, 15.0f));
		massSpectrum.addIon(new DefaultIon(130.0d, 17.0f));
		massSpectrum.addIon(new DefaultIon(131.0d, 78.0f));
		massSpectrum.addIon(new DefaultIon(132.0d, 45.0f));
		massSpectrum.addIon(new DefaultIon(133.0d, 100.0f));
		massSpectrum.addIon(new DefaultIon(134.0d, 77.0f));
		massSpectrum.addIon(new DefaultIon(135.0d, 126.0f));
		massSpectrum.addIon(new DefaultIon(136.0d, 45.0f));
		massSpectrum.addIon(new DefaultIon(137.0d, 100.0f));
		massSpectrum.addIon(new DefaultIon(138.0d, 42.0f));
		massSpectrum.addIon(new DefaultIon(139.0d, 127.0f));
		massSpectrum.addIon(new DefaultIon(140.0d, 19.0f));
		massSpectrum.addIon(new DefaultIon(145.0d, 25.0f));
		massSpectrum.addIon(new DefaultIon(146.0d, 27.0f));
		massSpectrum.addIon(new DefaultIon(147.0d, 106.0f));
		massSpectrum.addIon(new DefaultIon(148.0d, 36.0f));
		massSpectrum.addIon(new DefaultIon(149.0d, 327.0f));
		massSpectrum.addIon(new DefaultIon(150.0d, 64.0f));
		massSpectrum.addIon(new DefaultIon(151.0d, 101.0f));
		massSpectrum.addIon(new DefaultIon(152.0d, 41.0f));
		massSpectrum.addIon(new DefaultIon(153.0d, 31.0f));
		massSpectrum.addIon(new DefaultIon(154.0d, 399.0f));
		massSpectrum.addIon(new DefaultIon(155.0d, 41.0f));
		massSpectrum.addIon(new DefaultIon(161.0d, 78.0f));
		massSpectrum.addIon(new DefaultIon(162.0d, 31.0f));
		massSpectrum.addIon(new DefaultIon(163.0d, 59.0f));
		massSpectrum.addIon(new DefaultIon(164.0d, 22.0f));
		massSpectrum.addIon(new DefaultIon(165.0d, 58.0f));
		massSpectrum.addIon(new DefaultIon(167.0d, 999.0f));
		massSpectrum.addIon(new DefaultIon(168.0d, 123.0f));
		massSpectrum.addIon(new DefaultIon(175.0d, 17.0f));
		massSpectrum.addIon(new DefaultIon(177.0d, 78.0f));
		massSpectrum.addIon(new DefaultIon(178.0d, 28.0f));
		massSpectrum.addIon(new DefaultIon(179.0d, 89.0f));
		massSpectrum.addIon(new DefaultIon(180.0d, 50.0f));
		massSpectrum.addIon(new DefaultIon(181.0d, 169.0f));
		massSpectrum.addIon(new DefaultIon(182.0d, 234.0f));
		massSpectrum.addIon(new DefaultIon(183.0d, 37.0f));
		massSpectrum.addIon(new DefaultIon(191.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(192.0d, 24.0f));
		massSpectrum.addIon(new DefaultIon(193.0d, 15.0f));
		massSpectrum.addIon(new DefaultIon(194.0d, 53.0f));
		massSpectrum.addIon(new DefaultIon(195.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(208.0d, 24.0f));
		massSpectrum.addIon(new DefaultIon(209.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(210.0d, 872.0f));
		massSpectrum.addIon(new DefaultIon(211.0d, 98.0f));
		massSpectrum.addIon(new DefaultIon(212.0d, 16.0f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
