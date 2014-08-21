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

public class ProblemB2 implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public ProblemB2() throws Exception {

		massSpectrum = new DefaultMassSpectrum();
		massSpectrum.addIon(new DefaultIon(53.0d, 39.03904f));
		massSpectrum.addIon(new DefaultIon(71.0d, 5.005005f));
		massSpectrum.addIon(new DefaultIon(68.0d, 1000.0f));
		massSpectrum.addIon(new DefaultIon(20.0d, 0.3128128f));
		massSpectrum.addIon(new DefaultIon(36.0d, 24.086586f));
		massSpectrum.addIon(new DefaultIon(77.0d, 1.8768768f));
		massSpectrum.addIon(new DefaultIon(37.0d, 134.0403f));
		massSpectrum.addIon(new DefaultIon(40.0d, 437.71896f));
		massSpectrum.addIon(new DefaultIon(112.0d, 95.22022f));
		massSpectrum.addIon(new DefaultIon(67.0d, 31.03103f));
		massSpectrum.addIon(new DefaultIon(70.0d, 10.228979f));
		massSpectrum.addIon(new DefaultIon(26.0d, 1.001001f));
		massSpectrum.addIon(new DefaultIon(39.0d, 903.1218f));
		massSpectrum.addIon(new DefaultIon(99.0d, 9.884885f));
		massSpectrum.addIon(new DefaultIon(78.0d, 1.001001f));
		massSpectrum.addIon(new DefaultIon(25.0d, 14.201701f));
		massSpectrum.addIon(new DefaultIon(58.0d, 8.008008f));
		massSpectrum.addIon(new DefaultIon(113.0d, 4.004004f));
		massSpectrum.addIon(new DefaultIon(49.0d, 5.2552547f));
		massSpectrum.addIon(new DefaultIon(79.0d, 18.518518f));
		massSpectrum.addIon(new DefaultIon(22.0d, 0.5005005f));
		massSpectrum.addIon(new DefaultIon(34.0d, 7.695195f));
		massSpectrum.addIon(new DefaultIon(42.0d, 21.177427f));
		massSpectrum.addIon(new DefaultIon(38.0d, 194.35059f));
		massSpectrum.addIon(new DefaultIon(86.0d, 3.5035036f));
		massSpectrum.addIon(new DefaultIon(59.0d, 0.37537536f));
		massSpectrum.addIon(new DefaultIon(56.0d, 36.41141f));
		massSpectrum.addIon(new DefaultIon(41.0d, 53.303303f));
		massSpectrum.addIon(new DefaultIon(50.0d, 3.3158157f));
		massSpectrum.addIon(new DefaultIon(64.0d, 1.5015016f));
		massSpectrum.addIon(new DefaultIon(51.0d, 9.009008f));
		massSpectrum.addIon(new DefaultIon(80.0d, 1.7517517f));
		massSpectrum.addIon(new DefaultIon(69.0d, 52.114616f));
		massSpectrum.addIon(new DefaultIon(97.0d, 13.513513f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
