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

public class ProblemC2 implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public ProblemC2() throws Exception {

		massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(53.0d, 3.1257746f));
		massSpectrum.addIon(new Ion(71.0d, 8.00E-006f));
		massSpectrum.addIon(new Ion(81.0d, 1.63E-009f));
		massSpectrum.addIon(new Ion(43.0d, 1000f));
		massSpectrum.addIon(new Ion(98.0d, 0.07038388f));
		massSpectrum.addIon(new Ion(36.0d, 1.0204448f));
		massSpectrum.addIon(new Ion(37.0d, 3.9347918f));
		massSpectrum.addIon(new Ion(15.0d, 44.28657f));
		massSpectrum.addIon(new Ion(112.0d, 0.50520504f));
		massSpectrum.addIon(new Ion(70.0d, 5.46E+000f));
		massSpectrum.addIon(new Ion(73.0d, 39.14292f));
		massSpectrum.addIon(new Ion(26.0d, 10.587521f));
		massSpectrum.addIon(new Ion(39.0d, 13.496904f));
		massSpectrum.addIon(new Ion(78.0d, 2.22E-016f));
		massSpectrum.addIon(new Ion(25.0d, 2.4467254f));
		massSpectrum.addIon(new Ion(58.0d, 2.002025f));
		massSpectrum.addIon(new Ion(49.0d, 1.4375734f));
		massSpectrum.addIon(new Ion(24.0d, 0.62569463f));
		massSpectrum.addIon(new Ion(79.0d, 1.78E-015f));
		massSpectrum.addIon(new Ion(57.0d, 7.962364f));
		massSpectrum.addIon(new Ion(48.0d, 2.51E-001f));
		massSpectrum.addIon(new Ion(42.0d, 61.168015f));
		massSpectrum.addIon(new Ion(31.0d, 0.7742158f));
		massSpectrum.addIon(new Ion(27.0d, 11.512419f));
		massSpectrum.addIon(new Ion(38.0d, 2.941516f));
		massSpectrum.addIon(new Ion(74.0d, 1.13E+001f));
		massSpectrum.addIon(new Ion(86.0d, 169.89043f));
		massSpectrum.addIon(new Ion(59.0d, 1.0088214f));
		massSpectrum.addIon(new Ion(56.0d, 0.27799475f));
		massSpectrum.addIon(new Ion(84.0d, 3.8121982f));
		massSpectrum.addIon(new Ion(60.0d, 2.45E-008f));
		massSpectrum.addIon(new Ion(30.0d, 3.0110269f));
		massSpectrum.addIon(new Ion(52.0d, 0.0028204513f));
		massSpectrum.addIon(new Ion(117.0d, 0.005865801f));
		massSpectrum.addIon(new Ion(88.0d, 2.0097277f));
		massSpectrum.addIon(new Ion(29.0d, 26.439533f));
		massSpectrum.addIon(new Ion(41.0d, 22.991068f));
		massSpectrum.addIon(new Ion(50.0d, 2.37E+000f));
		massSpectrum.addIon(new Ion(116.0d, 56.117054f));
		massSpectrum.addIon(new Ion(51.0d, 0.5656856f));
		massSpectrum.addIon(new Ion(101.0d, 3.01163f));
		massSpectrum.addIon(new Ion(55.0d, 0.79345727f));
		massSpectrum.addIon(new Ion(69.0d, 4.6541615f));
		massSpectrum.addIon(new Ion(54.0d, 0.058652434f));
		massSpectrum.addIon(new Ion(45.0d, 0.1564067f));
		massSpectrum.addIon(new Ion(97.0d, 7.827329f));
		massSpectrum.addIon(new Ion(87.0d, 8.072788f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
