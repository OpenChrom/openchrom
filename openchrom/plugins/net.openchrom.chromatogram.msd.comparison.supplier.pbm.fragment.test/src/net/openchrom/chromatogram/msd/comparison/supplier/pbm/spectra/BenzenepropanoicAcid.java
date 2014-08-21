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
 * Name: Benzenepropanoic acid, 2,5-dimethoxy-
 * Formula: C11H14O4
 * MW: 210
 * ExactMass: 210.089209
 * CAS#: 10538-49-5; NIST#: 134802
 * Num Peaks: 111
 */
public class BenzenepropanoicAcid implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public BenzenepropanoicAcid() throws Exception {

		massSpectrum = new DefaultMassSpectrum();
		massSpectrum.addIon(new DefaultIon(14.0d, 31.0f));
		massSpectrum.addIon(new DefaultIon(15.0d, 297.0f));
		massSpectrum.addIon(new DefaultIon(16.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(18.0d, 14.0f));
		massSpectrum.addIon(new DefaultIon(26.0d, 41.0f));
		massSpectrum.addIon(new DefaultIon(27.0d, 120.0f));
		massSpectrum.addIon(new DefaultIon(28.0d, 88.0f));
		massSpectrum.addIon(new DefaultIon(29.0d, 75.0f));
		massSpectrum.addIon(new DefaultIon(30.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(31.0d, 34.0f));
		massSpectrum.addIon(new DefaultIon(32.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(37.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(38.0d, 44.0f));
		massSpectrum.addIon(new DefaultIon(39.0d, 271.0f));
		massSpectrum.addIon(new DefaultIon(40.0d, 31.0f));
		massSpectrum.addIon(new DefaultIon(41.0d, 78.0f));
		massSpectrum.addIon(new DefaultIon(42.0d, 40.0f));
		massSpectrum.addIon(new DefaultIon(43.0d, 75.0f));
		massSpectrum.addIon(new DefaultIon(44.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(45.0d, 247.0f));
		massSpectrum.addIon(new DefaultIon(46.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(50.0d, 97.0f));
		massSpectrum.addIon(new DefaultIon(51.0d, 266.0f));
		massSpectrum.addIon(new DefaultIon(52.0d, 116.0f));
		massSpectrum.addIon(new DefaultIon(53.0d, 219.0f));
		massSpectrum.addIon(new DefaultIon(54.0d, 90.0f));
		massSpectrum.addIon(new DefaultIon(55.0d, 240.0f));
		massSpectrum.addIon(new DefaultIon(56.0d, 9.0f));
		massSpectrum.addIon(new DefaultIon(57.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(59.0d, 21.0f));
		massSpectrum.addIon(new DefaultIon(60.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(61.0d, 15.0f));
		massSpectrum.addIon(new DefaultIon(62.0d, 53.0f));
		massSpectrum.addIon(new DefaultIon(63.0d, 157.0f));
		massSpectrum.addIon(new DefaultIon(64.0d, 90.0f));
		massSpectrum.addIon(new DefaultIon(65.0d, 321.0f));
		massSpectrum.addIon(new DefaultIon(66.0d, 55.0f));
		massSpectrum.addIon(new DefaultIon(67.0d, 43.0f));
		massSpectrum.addIon(new DefaultIon(68.0d, 13.0f));
		massSpectrum.addIon(new DefaultIon(69.0d, 30.0f));
		massSpectrum.addIon(new DefaultIon(74.0d, 37.0f));
		massSpectrum.addIon(new DefaultIon(75.0d, 38.0f));
		massSpectrum.addIon(new DefaultIon(77.0d, 596.0f));
		massSpectrum.addIon(new DefaultIon(78.0d, 260.0f));
		massSpectrum.addIon(new DefaultIon(79.0d, 285.0f));
		massSpectrum.addIon(new DefaultIon(80.0d, 31.0f));
		massSpectrum.addIon(new DefaultIon(81.0d, 24.0f));
		massSpectrum.addIon(new DefaultIon(82.0d, 38.0f));
		massSpectrum.addIon(new DefaultIon(83.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(84.0d, 10.0f));
		massSpectrum.addIon(new DefaultIon(85.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(86.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(87.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(88.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(89.0d, 88.0f));
		massSpectrum.addIon(new DefaultIon(90.0d, 85.0f));
		massSpectrum.addIon(new DefaultIon(91.0d, 519.0f));
		massSpectrum.addIon(new DefaultIon(92.0d, 120.0f));
		massSpectrum.addIon(new DefaultIon(93.0d, 95.0f));
		massSpectrum.addIon(new DefaultIon(94.0d, 26.0f));
		massSpectrum.addIon(new DefaultIon(95.0d, 27.0f));
		massSpectrum.addIon(new DefaultIon(96.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(98.0d, 25.0f));
		massSpectrum.addIon(new DefaultIon(101.0d, 5.0f));
		massSpectrum.addIon(new DefaultIon(102.0d, 18.0f));
		massSpectrum.addIon(new DefaultIon(103.0d, 66.0f));
		massSpectrum.addIon(new DefaultIon(104.0d, 17.0f));
		massSpectrum.addIon(new DefaultIon(105.0d, 83.0f));
		massSpectrum.addIon(new DefaultIon(106.0d, 76.0f));
		massSpectrum.addIon(new DefaultIon(107.0d, 167.0f));
		massSpectrum.addIon(new DefaultIon(108.0d, 96.0f));
		massSpectrum.addIon(new DefaultIon(109.0d, 35.0f));
		massSpectrum.addIon(new DefaultIon(110.0d, 23.0f));
		massSpectrum.addIon(new DefaultIon(111.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(117.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(118.0d, 55.0f));
		massSpectrum.addIon(new DefaultIon(119.0d, 40.0f));
		massSpectrum.addIon(new DefaultIon(120.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(121.0d, 664.0f));
		massSpectrum.addIon(new DefaultIon(122.0d, 75.0f));
		massSpectrum.addIon(new DefaultIon(123.0d, 122.0f));
		massSpectrum.addIon(new DefaultIon(124.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(125.0d, 186.0f));
		massSpectrum.addIon(new DefaultIon(126.0d, 14.0f));
		massSpectrum.addIon(new DefaultIon(133.0d, 119.0f));
		massSpectrum.addIon(new DefaultIon(134.0d, 59.0f));
		massSpectrum.addIon(new DefaultIon(135.0d, 317.0f));
		massSpectrum.addIon(new DefaultIon(136.0d, 73.0f));
		massSpectrum.addIon(new DefaultIon(137.0d, 52.0f));
		massSpectrum.addIon(new DefaultIon(138.0d, 24.0f));
		massSpectrum.addIon(new DefaultIon(139.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(149.0d, 198.0f));
		massSpectrum.addIon(new DefaultIon(150.0d, 119.0f));
		massSpectrum.addIon(new DefaultIon(151.0d, 775.0f));
		massSpectrum.addIon(new DefaultIon(152.0d, 92.0f));
		massSpectrum.addIon(new DefaultIon(153.0d, 266.0f));
		massSpectrum.addIon(new DefaultIon(154.0d, 24.0f));
		massSpectrum.addIon(new DefaultIon(161.0d, 13.0f));
		massSpectrum.addIon(new DefaultIon(162.0d, 11.0f));
		massSpectrum.addIon(new DefaultIon(163.0d, 59.0f));
		massSpectrum.addIon(new DefaultIon(164.0d, 140.0f));
		massSpectrum.addIon(new DefaultIon(165.0d, 43.0f));
		massSpectrum.addIon(new DefaultIon(166.0d, 8.0f));
		massSpectrum.addIon(new DefaultIon(167.0d, 6.0f));
		massSpectrum.addIon(new DefaultIon(177.0d, 287.0f));
		massSpectrum.addIon(new DefaultIon(178.0d, 35.0f));
		massSpectrum.addIon(new DefaultIon(179.0d, 7.0f));
		massSpectrum.addIon(new DefaultIon(195.0d, 16.0f));
		massSpectrum.addIon(new DefaultIon(210.0d, 999.0f));
		massSpectrum.addIon(new DefaultIon(211.0d, 123.0f));
		massSpectrum.addIon(new DefaultIon(212.0d, 6.0f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
