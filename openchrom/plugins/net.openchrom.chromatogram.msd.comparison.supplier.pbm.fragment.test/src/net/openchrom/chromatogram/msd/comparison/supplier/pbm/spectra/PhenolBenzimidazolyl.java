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
 * Name: Phenol, o-2-benzimidazolyl-
 * Formula: C13H10N2O
 * MW: 210
 * ExactMass: 210.079313
 * CAS#: 2963-66-8; NIST#: 258651
 * Num Peaks: 120
 */
public class PhenolBenzimidazolyl implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public PhenolBenzimidazolyl() throws Exception {

		massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(33.0d, 2.0f));
		massSpectrum.addIon(new Ion(34.0d, 2.0f));
		massSpectrum.addIon(new Ion(36.0d, 7.0f));
		massSpectrum.addIon(new Ion(37.0d, 78.0f));
		massSpectrum.addIon(new Ion(38.0d, 222.0f));
		massSpectrum.addIon(new Ion(39.0d, 483.0f));
		massSpectrum.addIon(new Ion(40.0d, 81.0f));
		massSpectrum.addIon(new Ion(41.0d, 58.0f));
		massSpectrum.addIon(new Ion(42.0d, 21.0f));
		massSpectrum.addIon(new Ion(43.0d, 16.0f));
		massSpectrum.addIon(new Ion(44.0d, 26.0f));
		massSpectrum.addIon(new Ion(45.0d, 13.0f));
		massSpectrum.addIon(new Ion(48.0d, 2.0f));
		massSpectrum.addIon(new Ion(50.0d, 188.0f));
		massSpectrum.addIon(new Ion(51.0d, 254.0f));
		massSpectrum.addIon(new Ion(52.0d, 231.0f));
		massSpectrum.addIon(new Ion(53.0d, 78.0f));
		massSpectrum.addIon(new Ion(54.0d, 13.0f));
		massSpectrum.addIon(new Ion(55.0d, 14.0f));
		massSpectrum.addIon(new Ion(56.0d, 6.0f));
		massSpectrum.addIon(new Ion(57.0d, 8.0f));
		massSpectrum.addIon(new Ion(61.0d, 50.0f));
		massSpectrum.addIon(new Ion(62.0d, 132.0f));
		massSpectrum.addIon(new Ion(63.0d, 400.0f));
		massSpectrum.addIon(new Ion(64.0d, 299.0f));
		massSpectrum.addIon(new Ion(65.0d, 342.0f));
		massSpectrum.addIon(new Ion(66.0d, 42.0f));
		massSpectrum.addIon(new Ion(67.0d, 7.0f));
		massSpectrum.addIon(new Ion(68.0d, 1.0f));
		massSpectrum.addIon(new Ion(69.0d, 2.0f));
		massSpectrum.addIon(new Ion(70.0d, 2.0f));
		massSpectrum.addIon(new Ion(72.0d, 11.0f));
		massSpectrum.addIon(new Ion(73.0d, 8.0f));
		massSpectrum.addIon(new Ion(74.0d, 53.0f));
		massSpectrum.addIon(new Ion(75.0d, 85.0f));
		massSpectrum.addIon(new Ion(76.0d, 118.0f));
		massSpectrum.addIon(new Ion(77.0d, 184.0f));
		massSpectrum.addIon(new Ion(78.0d, 212.0f));
		massSpectrum.addIon(new Ion(79.0d, 37.0f));
		massSpectrum.addIon(new Ion(80.0d, 8.0f));
		massSpectrum.addIon(new Ion(81.0d, 2.0f));
		massSpectrum.addIon(new Ion(82.0d, 0.0f));
		massSpectrum.addIon(new Ion(84.0d, 6.0f));
		massSpectrum.addIon(new Ion(85.0d, 2.0f));
		massSpectrum.addIon(new Ion(86.0d, 10.0f));
		massSpectrum.addIon(new Ion(87.0d, 20.0f));
		massSpectrum.addIon(new Ion(88.0d, 31.0f));
		massSpectrum.addIon(new Ion(89.0d, 34.0f));
		massSpectrum.addIon(new Ion(90.0d, 140.0f));
		massSpectrum.addIon(new Ion(91.0d, 269.0f));
		massSpectrum.addIon(new Ion(92.0d, 130.0f));
		massSpectrum.addIon(new Ion(93.0d, 12.0f));
		massSpectrum.addIon(new Ion(94.0d, 7.0f));
		massSpectrum.addIon(new Ion(98.0d, 3.0f));
		massSpectrum.addIon(new Ion(99.0d, 8.0f));
		massSpectrum.addIon(new Ion(100.0d, 11.0f));
		massSpectrum.addIon(new Ion(101.0d, 20.0f));
		massSpectrum.addIon(new Ion(102.0d, 81.0f));
		massSpectrum.addIon(new Ion(103.0d, 37.0f));
		massSpectrum.addIon(new Ion(104.0d, 25.0f));
		massSpectrum.addIon(new Ion(105.0d, 76.0f));
		massSpectrum.addIon(new Ion(106.0d, 11.0f));
		massSpectrum.addIon(new Ion(107.0d, 3.0f));
		massSpectrum.addIon(new Ion(108.0d, 1.0f));
		massSpectrum.addIon(new Ion(112.0d, 0.0f));
		massSpectrum.addIon(new Ion(113.0d, 3.0f));
		massSpectrum.addIon(new Ion(114.0d, 8.0f));
		massSpectrum.addIon(new Ion(115.0d, 16.0f));
		massSpectrum.addIon(new Ion(116.0d, 12.0f));
		massSpectrum.addIon(new Ion(117.0d, 7.0f));
		massSpectrum.addIon(new Ion(118.0d, 18.0f));
		massSpectrum.addIon(new Ion(119.0d, 9.0f));
		massSpectrum.addIon(new Ion(120.0d, 7.0f));
		massSpectrum.addIon(new Ion(121.0d, 4.0f));
		massSpectrum.addIon(new Ion(123.0d, 4.0f));
		massSpectrum.addIon(new Ion(124.0d, 1.0f));
		massSpectrum.addIon(new Ion(125.0d, 5.0f));
		massSpectrum.addIon(new Ion(126.0d, 12.0f));
		massSpectrum.addIon(new Ion(127.0d, 50.0f));
		massSpectrum.addIon(new Ion(128.0d, 37.0f));
		massSpectrum.addIon(new Ion(129.0d, 53.0f));
		massSpectrum.addIon(new Ion(130.0d, 13.0f));
		massSpectrum.addIon(new Ion(131.0d, 9.0f));
		massSpectrum.addIon(new Ion(132.0d, 9.0f));
		massSpectrum.addIon(new Ion(139.0d, 2.0f));
		massSpectrum.addIon(new Ion(140.0d, 8.0f));
		massSpectrum.addIon(new Ion(141.0d, 5.0f));
		massSpectrum.addIon(new Ion(142.0d, 20.0f));
		massSpectrum.addIon(new Ion(143.0d, 87.0f));
		massSpectrum.addIon(new Ion(144.0d, 9.0f));
		massSpectrum.addIon(new Ion(151.0d, 6.0f));
		massSpectrum.addIon(new Ion(152.0d, 16.0f));
		massSpectrum.addIon(new Ion(153.0d, 26.0f));
		massSpectrum.addIon(new Ion(154.0d, 67.0f));
		massSpectrum.addIon(new Ion(155.0d, 58.0f));
		massSpectrum.addIon(new Ion(156.0d, 117.0f));
		massSpectrum.addIon(new Ion(157.0d, 13.0f));
		massSpectrum.addIon(new Ion(164.0d, 3.0f));
		massSpectrum.addIon(new Ion(165.0d, 1.0f));
		massSpectrum.addIon(new Ion(166.0d, 2.0f));
		massSpectrum.addIon(new Ion(167.0d, 6.0f));
		massSpectrum.addIon(new Ion(168.0d, 4.0f));
		massSpectrum.addIon(new Ion(169.0d, 27.0f));
		massSpectrum.addIon(new Ion(170.0d, 3.0f));
		massSpectrum.addIon(new Ion(171.0d, 1.0f));
		massSpectrum.addIon(new Ion(176.0d, 2.0f));
		massSpectrum.addIon(new Ion(179.0d, 40.0f));
		massSpectrum.addIon(new Ion(180.0d, 50.0f));
		massSpectrum.addIon(new Ion(181.0d, 571.0f));
		massSpectrum.addIon(new Ion(182.0d, 644.0f));
		massSpectrum.addIon(new Ion(183.0d, 85.0f));
		massSpectrum.addIon(new Ion(184.0d, 9.0f));
		massSpectrum.addIon(new Ion(190.0d, 1.0f));
		massSpectrum.addIon(new Ion(192.0d, 2.0f));
		massSpectrum.addIon(new Ion(207.0d, 7.0f));
		massSpectrum.addIon(new Ion(208.0d, 13.0f));
		massSpectrum.addIon(new Ion(209.0d, 54.0f));
		massSpectrum.addIon(new Ion(210.0d, 999.0f));
		massSpectrum.addIon(new Ion(211.0d, 144.0f));
		massSpectrum.addIon(new Ion(212.0d, 10.0f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
