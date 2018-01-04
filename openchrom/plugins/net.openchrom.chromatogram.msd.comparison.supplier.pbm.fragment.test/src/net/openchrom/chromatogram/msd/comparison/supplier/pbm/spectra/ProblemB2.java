/*******************************************************************************
 * Copyright (c) 2014, 2018 Lablicate GmbH.
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

public class ProblemB2 implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public ProblemB2() throws Exception {
		massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(53.0d, 39.03904f));
		massSpectrum.addIon(new Ion(71.0d, 5.005005f));
		massSpectrum.addIon(new Ion(68.0d, 1000.0f));
		massSpectrum.addIon(new Ion(20.0d, 0.3128128f));
		massSpectrum.addIon(new Ion(36.0d, 24.086586f));
		massSpectrum.addIon(new Ion(77.0d, 1.8768768f));
		massSpectrum.addIon(new Ion(37.0d, 134.0403f));
		massSpectrum.addIon(new Ion(40.0d, 437.71896f));
		massSpectrum.addIon(new Ion(112.0d, 95.22022f));
		massSpectrum.addIon(new Ion(67.0d, 31.03103f));
		massSpectrum.addIon(new Ion(70.0d, 10.228979f));
		massSpectrum.addIon(new Ion(26.0d, 1.001001f));
		massSpectrum.addIon(new Ion(39.0d, 903.1218f));
		massSpectrum.addIon(new Ion(99.0d, 9.884885f));
		massSpectrum.addIon(new Ion(78.0d, 1.001001f));
		massSpectrum.addIon(new Ion(25.0d, 14.201701f));
		massSpectrum.addIon(new Ion(58.0d, 8.008008f));
		massSpectrum.addIon(new Ion(113.0d, 4.004004f));
		massSpectrum.addIon(new Ion(49.0d, 5.2552547f));
		massSpectrum.addIon(new Ion(79.0d, 18.518518f));
		massSpectrum.addIon(new Ion(22.0d, 0.5005005f));
		massSpectrum.addIon(new Ion(34.0d, 7.695195f));
		massSpectrum.addIon(new Ion(42.0d, 21.177427f));
		massSpectrum.addIon(new Ion(38.0d, 194.35059f));
		massSpectrum.addIon(new Ion(86.0d, 3.5035036f));
		massSpectrum.addIon(new Ion(59.0d, 0.37537536f));
		massSpectrum.addIon(new Ion(56.0d, 36.41141f));
		massSpectrum.addIon(new Ion(41.0d, 53.303303f));
		massSpectrum.addIon(new Ion(50.0d, 3.3158157f));
		massSpectrum.addIon(new Ion(64.0d, 1.5015016f));
		massSpectrum.addIon(new Ion(51.0d, 9.009008f));
		massSpectrum.addIon(new Ion(80.0d, 1.7517517f));
		massSpectrum.addIon(new Ion(69.0d, 52.114616f));
		massSpectrum.addIon(new Ion(97.0d, 13.513513f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
