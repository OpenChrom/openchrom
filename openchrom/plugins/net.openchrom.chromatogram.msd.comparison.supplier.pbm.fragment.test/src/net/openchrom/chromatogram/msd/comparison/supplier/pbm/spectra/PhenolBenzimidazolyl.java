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
 * Name: Phenol, o-2-benzimidazolyl-
 * Formula: C13H10N2O
 * MW: 210
 * ExactMass: 210.079313
 * CAS#: 2963-66-8; NIST#: 258651
 * Num Peaks: 120
 */
public class PhenolBenzimidazolyl implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public PhenolBenzimidazolyl() throws Exception {

		massSpectrum = new DefaultMassSpectrum();
		massSpectrum.addIon(new DefaultIon(33.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(34.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(36.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(37.0d, 78.0f));
		massSpectrum.addIon(new DefaultIon(38.0d, 222.0f));
		massSpectrum.addIon(new DefaultIon(39.0d, 483.0f));
		massSpectrum.addIon(new DefaultIon(40.0d, 81.0f));
		massSpectrum.addIon(new DefaultIon(41.0d, 58.0f));
		massSpectrum.addIon(new DefaultIon(42.0d, 21.0f));
		massSpectrum.addIon(new DefaultIon(43.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(44.0d, 26.0f));
		massSpectrum.addIon(new DefaultIon(45.0d, 13.0f));
		massSpectrum.addIon(new DefaultIon(48.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(50.0d, 188.0f));
		massSpectrum.addIon(new DefaultIon(51.0d, 254.0f));
		massSpectrum.addIon(new DefaultIon(52.0d, 231.0f));
		massSpectrum.addIon(new DefaultIon(53.0d, 78.0f));
		massSpectrum.addIon(new DefaultIon(54.0d, 13.0f));
		massSpectrum.addIon(new DefaultIon(55.0d, 14.0f));
		massSpectrum.addIon(new DefaultIon(56.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(57.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(61.0d, 50.0f));
		massSpectrum.addIon(new DefaultIon(62.0d, 132.0f));
		massSpectrum.addIon(new DefaultIon(63.0d, 400.0f));
		massSpectrum.addIon(new DefaultIon(64.0d, 299.0f));
		massSpectrum.addIon(new DefaultIon(65.0d, 342.0f));
		massSpectrum.addIon(new DefaultIon(66.0d, 42.0f));
		massSpectrum.addIon(new DefaultIon(67.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(68.0d, 1.0f));
		massSpectrum.addIon(new DefaultIon(69.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(70.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(72.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(73.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(74.0d, 53.0f));
		massSpectrum.addIon(new DefaultIon(75.0d, 85.0f));
		massSpectrum.addIon(new DefaultIon(76.0d, 118.0f));
		massSpectrum.addIon(new DefaultIon(77.0d, 184.0f));
		massSpectrum.addIon(new DefaultIon(78.0d, 212.0f));
		massSpectrum.addIon(new DefaultIon(79.0d, 37.0f));
		massSpectrum.addIon(new DefaultIon(80.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(81.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(82.0d, 0.0f));
		massSpectrum.addIon(new DefaultIon(84.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(85.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(86.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(87.0d, 20.0f));
		massSpectrum.addIon(new DefaultIon(88.0d, 31.0f));
		massSpectrum.addIon(new DefaultIon(89.0d, 34.0f));
		massSpectrum.addIon(new DefaultIon(90.0d, 140.0f));
		massSpectrum.addIon(new DefaultIon(91.0d, 269.0f));
		massSpectrum.addIon(new DefaultIon(92.0d, 130.0f));
		massSpectrum.addIon(new DefaultIon(93.0d, 12.0f));
		massSpectrum.addIon(new DefaultIon(94.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(98.0d, 3.0f));
		massSpectrum.addIon(new DefaultIon(99.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(100.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(101.0d, 20.0f));
		massSpectrum.addIon(new DefaultIon(102.0d, 81.0f));
		massSpectrum.addIon(new DefaultIon(103.0d, 37.0f));
		massSpectrum.addIon(new DefaultIon(104.0d, 25.0f));
		massSpectrum.addIon(new DefaultIon(105.0d, 76.0f));
		massSpectrum.addIon(new DefaultIon(106.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(107.0d, 3.0f));
		massSpectrum.addIon(new DefaultIon(108.0d, 1.0f));
		massSpectrum.addIon(new DefaultIon(112.0d, 0.0f));
		massSpectrum.addIon(new DefaultIon(113.0d, 3.0f));
		massSpectrum.addIon(new DefaultIon(114.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(115.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(116.0d, 12.0f));
		massSpectrum.addIon(new DefaultIon(117.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(118.0d, 18.0f));
		massSpectrum.addIon(new DefaultIon(119.0d, 9.0f));
		massSpectrum.addIon(new DefaultIon(120.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(121.0d, 4.0f));
		massSpectrum.addIon(new DefaultIon(123.0d, 4.0f));
		massSpectrum.addIon(new DefaultIon(124.0d, 1.0f));
		massSpectrum.addIon(new DefaultIon(125.0d, 5.0f));
		massSpectrum.addIon(new DefaultIon(126.0d, 12.0f));
		massSpectrum.addIon(new DefaultIon(127.0d, 50.0f));
		massSpectrum.addIon(new DefaultIon(128.0d, 37.0f));
		massSpectrum.addIon(new DefaultIon(129.0d, 53.0f));
		massSpectrum.addIon(new DefaultIon(130.0d, 13.0f));
		massSpectrum.addIon(new DefaultIon(131.0d, 9.0f));
		massSpectrum.addIon(new DefaultIon(132.0d, 9.0f));
		massSpectrum.addIon(new DefaultIon(139.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(140.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(141.0d, 5.0f));
		massSpectrum.addIon(new DefaultIon(142.0d, 20.0f));
		massSpectrum.addIon(new DefaultIon(143.0d, 87.0f));
		massSpectrum.addIon(new DefaultIon(144.0d, 9.0f));
		massSpectrum.addIon(new DefaultIon(151.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(152.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(153.0d, 26.0f));
		massSpectrum.addIon(new DefaultIon(154.0d, 67.0f));
		massSpectrum.addIon(new DefaultIon(155.0d, 58.0f));
		massSpectrum.addIon(new DefaultIon(156.0d, 117.0f));
		massSpectrum.addIon(new DefaultIon(157.0d, 13.0f));
		massSpectrum.addIon(new DefaultIon(164.0d, 3.0f));
		massSpectrum.addIon(new DefaultIon(165.0d, 1.0f));
		massSpectrum.addIon(new DefaultIon(166.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(167.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(168.0d, 4.0f));
		massSpectrum.addIon(new DefaultIon(169.0d, 27.0f));
		massSpectrum.addIon(new DefaultIon(170.0d, 3.0f));
		massSpectrum.addIon(new DefaultIon(171.0d, 1.0f));
		massSpectrum.addIon(new DefaultIon(176.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(179.0d, 40.0f));
		massSpectrum.addIon(new DefaultIon(180.0d, 50.0f));
		massSpectrum.addIon(new DefaultIon(181.0d, 571.0f));
		massSpectrum.addIon(new DefaultIon(182.0d, 644.0f));
		massSpectrum.addIon(new DefaultIon(183.0d, 85.0f));
		massSpectrum.addIon(new DefaultIon(184.0d, 9.0f));
		massSpectrum.addIon(new DefaultIon(190.0d, 1.0f));
		massSpectrum.addIon(new DefaultIon(192.0d, 2.0f));
		massSpectrum.addIon(new DefaultIon(207.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(208.0d, 13.0f));
		massSpectrum.addIon(new DefaultIon(209.0d, 54.0f));
		massSpectrum.addIon(new DefaultIon(210.0d, 999.0f));
		massSpectrum.addIon(new DefaultIon(211.0d, 144.0f));
		massSpectrum.addIon(new DefaultIon(212.0d, 10.0f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
