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

public class ProblemA2 implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public ProblemA2() throws Exception {

		massSpectrum = new DefaultMassSpectrum();
		massSpectrum.addIon(new DefaultIon(53.0d, 77.79473f));
		massSpectrum.addIon(new DefaultIon(225.0d, 22.955824f));
		massSpectrum.addIon(new DefaultIon(71.0d, 16.579206f));
		massSpectrum.addIon(new DefaultIon(136.0d, 67.59215f));
		massSpectrum.addIon(new DefaultIon(121.0d, 99.475235f));
		massSpectrum.addIon(new DefaultIon(176.0d, 43.361f));
		massSpectrum.addIon(new DefaultIon(224.0d, 121.15573f));
		massSpectrum.addIon(new DefaultIon(82.0d, 51.01294f));
		massSpectrum.addIon(new DefaultIon(211.0d, 38.259705f));
		massSpectrum.addIon(new DefaultIon(105.0d, 35.709057f));
		massSpectrum.addIon(new DefaultIon(91.0d, 119.88041f));
		massSpectrum.addIon(new DefaultIon(125.0d, 126.25703f));
		massSpectrum.addIon(new DefaultIon(67.0d, 53.563587f));
		massSpectrum.addIon(new DefaultIon(168.0d, 1274.0482f));
		massSpectrum.addIon(new DefaultIon(169.0d, 154.31415f));
		massSpectrum.addIon(new DefaultIon(171.0d, 53.563587f));
		massSpectrum.addIon(new DefaultIon(78.0d, 75.24409f));
		massSpectrum.addIon(new DefaultIon(92.0d, 66.316826f));
		massSpectrum.addIon(new DefaultIon(79.0d, 86.722f));
		massSpectrum.addIon(new DefaultIon(193.0d, 63.766174f));
		massSpectrum.addIon(new DefaultIon(118.0d, 40.810352f));
		massSpectrum.addIon(new DefaultIon(95.0d, 68.86747f));
		massSpectrum.addIon(new DefaultIon(124.0d, 66.316826f));
		massSpectrum.addIon(new DefaultIon(212.0d, 947.56537f));
		massSpectrum.addIon(new DefaultIon(122.0d, 103.3012f));
		massSpectrum.addIon(new DefaultIon(74.0d, 31.883087f));
		massSpectrum.addIon(new DefaultIon(213.0d, 103.3012f));
		massSpectrum.addIon(new DefaultIon(153.0d, 251.23874f));
		massSpectrum.addIon(new DefaultIon(137.0d, 146.6622f));
		massSpectrum.addIon(new DefaultIon(167.0d, 1249.817f));
		massSpectrum.addIon(new DefaultIon(51.0d, 58.664883f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
