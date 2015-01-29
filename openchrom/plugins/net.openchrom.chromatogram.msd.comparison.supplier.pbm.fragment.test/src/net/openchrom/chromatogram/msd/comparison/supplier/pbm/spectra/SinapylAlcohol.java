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
import net.chemclipse.msd.model.implementation.ScanMSD;

/*
 * Name: Sinapyl alcohol, trans BR 110, RT 71:85
 * Formula: C11H14O4
 * MW: 210
 * ExactMass: 210.089209
 * DB#: 137
 * Num Peaks: 122
 */
public class SinapylAlcohol implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public SinapylAlcohol() throws Exception {

		massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(27.0d, 84.0f));
		massSpectrum.addIon(new Ion(38.0d, 60.0f));
		massSpectrum.addIon(new Ion(39.0d, 228.0f));
		massSpectrum.addIon(new Ion(40.0d, 7.0f));
		massSpectrum.addIon(new Ion(41.0d, 116.0f));
		massSpectrum.addIon(new Ion(42.0d, 29.0f));
		massSpectrum.addIon(new Ion(43.0d, 121.0f));
		massSpectrum.addIon(new Ion(45.0d, 49.0f));
		massSpectrum.addIon(new Ion(47.0d, 17.0f));
		massSpectrum.addIon(new Ion(49.0d, 16.0f));
		massSpectrum.addIon(new Ion(50.0d, 116.0f));
		massSpectrum.addIon(new Ion(51.0d, 224.0f));
		massSpectrum.addIon(new Ion(52.0d, 104.0f));
		massSpectrum.addIon(new Ion(53.0d, 287.0f));
		massSpectrum.addIon(new Ion(54.0d, 44.0f));
		massSpectrum.addIon(new Ion(55.0d, 338.0f));
		massSpectrum.addIon(new Ion(57.0d, 72.0f));
		massSpectrum.addIon(new Ion(59.0d, 40.0f));
		massSpectrum.addIon(new Ion(60.0d, 21.0f));
		massSpectrum.addIon(new Ion(61.0d, 34.0f));
		massSpectrum.addIon(new Ion(62.0d, 51.0f));
		massSpectrum.addIon(new Ion(63.0d, 129.0f));
		massSpectrum.addIon(new Ion(64.0d, 56.0f));
		massSpectrum.addIon(new Ion(65.0d, 232.0f));
		massSpectrum.addIon(new Ion(66.0d, 113.0f));
		massSpectrum.addIon(new Ion(67.0d, 99.0f));
		massSpectrum.addIon(new Ion(68.0d, 46.0f));
		massSpectrum.addIon(new Ion(69.0d, 80.0f));
		massSpectrum.addIon(new Ion(70.0d, 20.0f));
		massSpectrum.addIon(new Ion(71.0d, 36.0f));
		massSpectrum.addIon(new Ion(74.0d, 39.0f));
		massSpectrum.addIon(new Ion(75.0d, 39.0f));
		massSpectrum.addIon(new Ion(76.0d, 66.0f));
		massSpectrum.addIon(new Ion(77.0d, 437.0f));
		massSpectrum.addIon(new Ion(78.0d, 201.0f));
		massSpectrum.addIon(new Ion(79.0d, 216.0f));
		massSpectrum.addIon(new Ion(80.0d, 55.0f));
		massSpectrum.addIon(new Ion(81.0d, 83.0f));
		massSpectrum.addIon(new Ion(82.0d, 36.0f));
		massSpectrum.addIon(new Ion(83.0d, 42.0f));
		massSpectrum.addIon(new Ion(85.0d, 58.0f));
		massSpectrum.addIon(new Ion(86.0d, 18.0f));
		massSpectrum.addIon(new Ion(89.0d, 121.0f));
		massSpectrum.addIon(new Ion(90.0d, 66.0f));
		massSpectrum.addIon(new Ion(91.0d, 253.0f));
		massSpectrum.addIon(new Ion(92.0d, 55.0f));
		massSpectrum.addIon(new Ion(93.0d, 102.0f));
		massSpectrum.addIon(new Ion(94.0d, 58.0f));
		massSpectrum.addIon(new Ion(95.0d, 93.0f));
		massSpectrum.addIon(new Ion(96.0d, 18.0f));
		massSpectrum.addIon(new Ion(97.0d, 26.0f));
		massSpectrum.addIon(new Ion(101.0d, 18.0f));
		massSpectrum.addIon(new Ion(102.0d, 39.0f));
		massSpectrum.addIon(new Ion(103.0d, 146.0f));
		massSpectrum.addIon(new Ion(104.0d, 40.0f));
		massSpectrum.addIon(new Ion(105.0d, 128.0f));
		massSpectrum.addIon(new Ion(106.0d, 167.0f));
		massSpectrum.addIon(new Ion(107.0d, 198.0f));
		massSpectrum.addIon(new Ion(108.0d, 52.0f));
		massSpectrum.addIon(new Ion(109.0d, 48.0f));
		massSpectrum.addIon(new Ion(110.0d, 28.0f));
		massSpectrum.addIon(new Ion(111.0d, 49.0f));
		massSpectrum.addIon(new Ion(115.0d, 41.0f));
		massSpectrum.addIon(new Ion(116.0d, 22.0f));
		massSpectrum.addIon(new Ion(117.0d, 41.0f));
		massSpectrum.addIon(new Ion(118.0d, 83.0f));
		massSpectrum.addIon(new Ion(119.0d, 100.0f));
		massSpectrum.addIon(new Ion(120.0d, 46.0f));
		massSpectrum.addIon(new Ion(121.0d, 239.0f));
		massSpectrum.addIon(new Ion(122.0d, 73.0f));
		massSpectrum.addIon(new Ion(123.0d, 146.0f));
		massSpectrum.addIon(new Ion(124.0d, 51.0f));
		massSpectrum.addIon(new Ion(125.0d, 41.0f));
		massSpectrum.addIon(new Ion(126.0d, 16.0f));
		massSpectrum.addIon(new Ion(129.0d, 15.0f));
		massSpectrum.addIon(new Ion(130.0d, 17.0f));
		massSpectrum.addIon(new Ion(131.0d, 78.0f));
		massSpectrum.addIon(new Ion(132.0d, 45.0f));
		massSpectrum.addIon(new Ion(133.0d, 100.0f));
		massSpectrum.addIon(new Ion(134.0d, 77.0f));
		massSpectrum.addIon(new Ion(135.0d, 126.0f));
		massSpectrum.addIon(new Ion(136.0d, 45.0f));
		massSpectrum.addIon(new Ion(137.0d, 100.0f));
		massSpectrum.addIon(new Ion(138.0d, 42.0f));
		massSpectrum.addIon(new Ion(139.0d, 127.0f));
		massSpectrum.addIon(new Ion(140.0d, 19.0f));
		massSpectrum.addIon(new Ion(145.0d, 25.0f));
		massSpectrum.addIon(new Ion(146.0d, 27.0f));
		massSpectrum.addIon(new Ion(147.0d, 106.0f));
		massSpectrum.addIon(new Ion(148.0d, 36.0f));
		massSpectrum.addIon(new Ion(149.0d, 327.0f));
		massSpectrum.addIon(new Ion(150.0d, 64.0f));
		massSpectrum.addIon(new Ion(151.0d, 101.0f));
		massSpectrum.addIon(new Ion(152.0d, 41.0f));
		massSpectrum.addIon(new Ion(153.0d, 31.0f));
		massSpectrum.addIon(new Ion(154.0d, 399.0f));
		massSpectrum.addIon(new Ion(155.0d, 41.0f));
		massSpectrum.addIon(new Ion(161.0d, 78.0f));
		massSpectrum.addIon(new Ion(162.0d, 31.0f));
		massSpectrum.addIon(new Ion(163.0d, 59.0f));
		massSpectrum.addIon(new Ion(164.0d, 22.0f));
		massSpectrum.addIon(new Ion(165.0d, 58.0f));
		massSpectrum.addIon(new Ion(167.0d, 999.0f));
		massSpectrum.addIon(new Ion(168.0d, 123.0f));
		massSpectrum.addIon(new Ion(175.0d, 17.0f));
		massSpectrum.addIon(new Ion(177.0d, 78.0f));
		massSpectrum.addIon(new Ion(178.0d, 28.0f));
		massSpectrum.addIon(new Ion(179.0d, 89.0f));
		massSpectrum.addIon(new Ion(180.0d, 50.0f));
		massSpectrum.addIon(new Ion(181.0d, 169.0f));
		massSpectrum.addIon(new Ion(182.0d, 234.0f));
		massSpectrum.addIon(new Ion(183.0d, 37.0f));
		massSpectrum.addIon(new Ion(191.0d, 7.0f));
		massSpectrum.addIon(new Ion(192.0d, 24.0f));
		massSpectrum.addIon(new Ion(193.0d, 15.0f));
		massSpectrum.addIon(new Ion(194.0d, 53.0f));
		massSpectrum.addIon(new Ion(195.0d, 16.0f));
		massSpectrum.addIon(new Ion(208.0d, 24.0f));
		massSpectrum.addIon(new Ion(209.0d, 8.0f));
		massSpectrum.addIon(new Ion(210.0d, 872.0f));
		massSpectrum.addIon(new Ion(211.0d, 98.0f));
		massSpectrum.addIon(new Ion(212.0d, 16.0f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
