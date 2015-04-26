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

import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.ScanMSD;

/*
 * Name: Benzenepropanoic acid, 2,5-dimethoxy-
 * Formula: C11H14O4
 * MW: 210
 * ExactMass: 210.089209
 * CAS#: 10538-49-5; NIST#: 134802
 * Num Peaks: 111
 */
public class BenzenepropanoicAcid implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public BenzenepropanoicAcid() throws Exception {

		massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(14.0d, 31.0f));
		massSpectrum.addIon(new Ion(15.0d, 297.0f));
		massSpectrum.addIon(new Ion(16.0d, 6.0f));
		massSpectrum.addIon(new Ion(18.0d, 14.0f));
		massSpectrum.addIon(new Ion(26.0d, 41.0f));
		massSpectrum.addIon(new Ion(27.0d, 120.0f));
		massSpectrum.addIon(new Ion(28.0d, 88.0f));
		massSpectrum.addIon(new Ion(29.0d, 75.0f));
		massSpectrum.addIon(new Ion(30.0d, 7.0f));
		massSpectrum.addIon(new Ion(31.0d, 34.0f));
		massSpectrum.addIon(new Ion(32.0d, 10.0f));
		massSpectrum.addIon(new Ion(37.0d, 8.0f));
		massSpectrum.addIon(new Ion(38.0d, 44.0f));
		massSpectrum.addIon(new Ion(39.0d, 271.0f));
		massSpectrum.addIon(new Ion(40.0d, 31.0f));
		massSpectrum.addIon(new Ion(41.0d, 78.0f));
		massSpectrum.addIon(new Ion(42.0d, 40.0f));
		massSpectrum.addIon(new Ion(43.0d, 75.0f));
		massSpectrum.addIon(new Ion(44.0d, 7.0f));
		massSpectrum.addIon(new Ion(45.0d, 247.0f));
		massSpectrum.addIon(new Ion(46.0d, 8.0f));
		massSpectrum.addIon(new Ion(50.0d, 97.0f));
		massSpectrum.addIon(new Ion(51.0d, 266.0f));
		massSpectrum.addIon(new Ion(52.0d, 116.0f));
		massSpectrum.addIon(new Ion(53.0d, 219.0f));
		massSpectrum.addIon(new Ion(54.0d, 90.0f));
		massSpectrum.addIon(new Ion(55.0d, 240.0f));
		massSpectrum.addIon(new Ion(56.0d, 9.0f));
		massSpectrum.addIon(new Ion(57.0d, 8.0f));
		massSpectrum.addIon(new Ion(59.0d, 21.0f));
		massSpectrum.addIon(new Ion(60.0d, 8.0f));
		massSpectrum.addIon(new Ion(61.0d, 15.0f));
		massSpectrum.addIon(new Ion(62.0d, 53.0f));
		massSpectrum.addIon(new Ion(63.0d, 157.0f));
		massSpectrum.addIon(new Ion(64.0d, 90.0f));
		massSpectrum.addIon(new Ion(65.0d, 321.0f));
		massSpectrum.addIon(new Ion(66.0d, 55.0f));
		massSpectrum.addIon(new Ion(67.0d, 43.0f));
		massSpectrum.addIon(new Ion(68.0d, 13.0f));
		massSpectrum.addIon(new Ion(69.0d, 30.0f));
		massSpectrum.addIon(new Ion(74.0d, 37.0f));
		massSpectrum.addIon(new Ion(75.0d, 38.0f));
		massSpectrum.addIon(new Ion(77.0d, 596.0f));
		massSpectrum.addIon(new Ion(78.0d, 260.0f));
		massSpectrum.addIon(new Ion(79.0d, 285.0f));
		massSpectrum.addIon(new Ion(80.0d, 31.0f));
		massSpectrum.addIon(new Ion(81.0d, 24.0f));
		massSpectrum.addIon(new Ion(82.0d, 38.0f));
		massSpectrum.addIon(new Ion(83.0d, 11.0f));
		massSpectrum.addIon(new Ion(84.0d, 10.0f));
		massSpectrum.addIon(new Ion(85.0d, 7.0f));
		massSpectrum.addIon(new Ion(86.0d, 8.0f));
		massSpectrum.addIon(new Ion(87.0d, 8.0f));
		massSpectrum.addIon(new Ion(88.0d, 7.0f));
		massSpectrum.addIon(new Ion(89.0d, 88.0f));
		massSpectrum.addIon(new Ion(90.0d, 85.0f));
		massSpectrum.addIon(new Ion(91.0d, 519.0f));
		massSpectrum.addIon(new Ion(92.0d, 120.0f));
		massSpectrum.addIon(new Ion(93.0d, 95.0f));
		massSpectrum.addIon(new Ion(94.0d, 26.0f));
		massSpectrum.addIon(new Ion(95.0d, 27.0f));
		massSpectrum.addIon(new Ion(96.0d, 11.0f));
		massSpectrum.addIon(new Ion(98.0d, 25.0f));
		massSpectrum.addIon(new Ion(101.0d, 5.0f));
		massSpectrum.addIon(new Ion(102.0d, 18.0f));
		massSpectrum.addIon(new Ion(103.0d, 66.0f));
		massSpectrum.addIon(new Ion(104.0d, 17.0f));
		massSpectrum.addIon(new Ion(105.0d, 83.0f));
		massSpectrum.addIon(new Ion(106.0d, 76.0f));
		massSpectrum.addIon(new Ion(107.0d, 167.0f));
		massSpectrum.addIon(new Ion(108.0d, 96.0f));
		massSpectrum.addIon(new Ion(109.0d, 35.0f));
		massSpectrum.addIon(new Ion(110.0d, 23.0f));
		massSpectrum.addIon(new Ion(111.0d, 6.0f));
		massSpectrum.addIon(new Ion(117.0d, 7.0f));
		massSpectrum.addIon(new Ion(118.0d, 55.0f));
		massSpectrum.addIon(new Ion(119.0d, 40.0f));
		massSpectrum.addIon(new Ion(120.0d, 6.0f));
		massSpectrum.addIon(new Ion(121.0d, 664.0f));
		massSpectrum.addIon(new Ion(122.0d, 75.0f));
		massSpectrum.addIon(new Ion(123.0d, 122.0f));
		massSpectrum.addIon(new Ion(124.0d, 6.0f));
		massSpectrum.addIon(new Ion(125.0d, 186.0f));
		massSpectrum.addIon(new Ion(126.0d, 14.0f));
		massSpectrum.addIon(new Ion(133.0d, 119.0f));
		massSpectrum.addIon(new Ion(134.0d, 59.0f));
		massSpectrum.addIon(new Ion(135.0d, 317.0f));
		massSpectrum.addIon(new Ion(136.0d, 73.0f));
		massSpectrum.addIon(new Ion(137.0d, 52.0f));
		massSpectrum.addIon(new Ion(138.0d, 24.0f));
		massSpectrum.addIon(new Ion(139.0d, 8.0f));
		massSpectrum.addIon(new Ion(149.0d, 198.0f));
		massSpectrum.addIon(new Ion(150.0d, 119.0f));
		massSpectrum.addIon(new Ion(151.0d, 775.0f));
		massSpectrum.addIon(new Ion(152.0d, 92.0f));
		massSpectrum.addIon(new Ion(153.0d, 266.0f));
		massSpectrum.addIon(new Ion(154.0d, 24.0f));
		massSpectrum.addIon(new Ion(161.0d, 13.0f));
		massSpectrum.addIon(new Ion(162.0d, 11.0f));
		massSpectrum.addIon(new Ion(163.0d, 59.0f));
		massSpectrum.addIon(new Ion(164.0d, 140.0f));
		massSpectrum.addIon(new Ion(165.0d, 43.0f));
		massSpectrum.addIon(new Ion(166.0d, 8.0f));
		massSpectrum.addIon(new Ion(167.0d, 6.0f));
		massSpectrum.addIon(new Ion(177.0d, 287.0f));
		massSpectrum.addIon(new Ion(178.0d, 35.0f));
		massSpectrum.addIon(new Ion(179.0d, 7.0f));
		massSpectrum.addIon(new Ion(195.0d, 16.0f));
		massSpectrum.addIon(new Ion(210.0d, 999.0f));
		massSpectrum.addIon(new Ion(211.0d, 123.0f));
		massSpectrum.addIon(new Ion(212.0d, 6.0f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
