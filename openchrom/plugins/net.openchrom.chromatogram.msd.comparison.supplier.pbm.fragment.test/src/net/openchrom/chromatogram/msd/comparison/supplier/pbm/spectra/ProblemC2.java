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

public class ProblemC2 implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public ProblemC2() throws Exception {

		massSpectrum = new DefaultMassSpectrum();
		massSpectrum.addIon(new DefaultIon(53.0d, 3.1257746f));
		massSpectrum.addIon(new DefaultIon(71.0d, 8.00E-006f));
		massSpectrum.addIon(new DefaultIon(81.0d, 1.63E-009f));
		massSpectrum.addIon(new DefaultIon(43.0d, 1000f));
		massSpectrum.addIon(new DefaultIon(98.0d, 0.07038388f));
		massSpectrum.addIon(new DefaultIon(36.0d, 1.0204448f));
		massSpectrum.addIon(new DefaultIon(37.0d, 3.9347918f));
		massSpectrum.addIon(new DefaultIon(15.0d, 44.28657f));
		massSpectrum.addIon(new DefaultIon(112.0d, 0.50520504f));
		massSpectrum.addIon(new DefaultIon(70.0d, 5.46E+000f));
		massSpectrum.addIon(new DefaultIon(73.0d, 39.14292f));
		massSpectrum.addIon(new DefaultIon(26.0d, 10.587521f));
		massSpectrum.addIon(new DefaultIon(39.0d, 13.496904f));
		massSpectrum.addIon(new DefaultIon(78.0d, 2.22E-016f));
		massSpectrum.addIon(new DefaultIon(25.0d, 2.4467254f));
		massSpectrum.addIon(new DefaultIon(58.0d, 2.002025f));
		massSpectrum.addIon(new DefaultIon(49.0d, 1.4375734f));
		massSpectrum.addIon(new DefaultIon(24.0d, 0.62569463f));
		massSpectrum.addIon(new DefaultIon(79.0d, 1.78E-015f));
		massSpectrum.addIon(new DefaultIon(57.0d, 7.962364f));
		massSpectrum.addIon(new DefaultIon(48.0d, 2.51E-001f));
		massSpectrum.addIon(new DefaultIon(42.0d, 61.168015f));
		massSpectrum.addIon(new DefaultIon(31.0d, 0.7742158f));
		massSpectrum.addIon(new DefaultIon(27.0d, 11.512419f));
		massSpectrum.addIon(new DefaultIon(38.0d, 2.941516f));
		massSpectrum.addIon(new DefaultIon(74.0d, 1.13E+001f));
		massSpectrum.addIon(new DefaultIon(86.0d, 169.89043f));
		massSpectrum.addIon(new DefaultIon(59.0d, 1.0088214f));
		massSpectrum.addIon(new DefaultIon(56.0d, 0.27799475f));
		massSpectrum.addIon(new DefaultIon(84.0d, 3.8121982f));
		massSpectrum.addIon(new DefaultIon(60.0d, 2.45E-008f));
		massSpectrum.addIon(new DefaultIon(30.0d, 3.0110269f));
		massSpectrum.addIon(new DefaultIon(52.0d, 0.0028204513f));
		massSpectrum.addIon(new DefaultIon(117.0d, 0.005865801f));
		massSpectrum.addIon(new DefaultIon(88.0d, 2.0097277f));
		massSpectrum.addIon(new DefaultIon(29.0d, 26.439533f));
		massSpectrum.addIon(new DefaultIon(41.0d, 22.991068f));
		massSpectrum.addIon(new DefaultIon(50.0d, 2.37E+000f));
		massSpectrum.addIon(new DefaultIon(116.0d, 56.117054f));
		massSpectrum.addIon(new DefaultIon(51.0d, 0.5656856f));
		massSpectrum.addIon(new DefaultIon(101.0d, 3.01163f));
		massSpectrum.addIon(new DefaultIon(55.0d, 0.79345727f));
		massSpectrum.addIon(new DefaultIon(69.0d, 4.6541615f));
		massSpectrum.addIon(new DefaultIon(54.0d, 0.058652434f));
		massSpectrum.addIon(new DefaultIon(45.0d, 0.1564067f));
		massSpectrum.addIon(new DefaultIon(97.0d, 7.827329f));
		massSpectrum.addIon(new DefaultIon(87.0d, 8.072788f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
