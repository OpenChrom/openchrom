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

public class ProblemA1 implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public ProblemA1() throws Exception {

		massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(36.0d, 152.46007f));
		massSpectrum.addIon(new Ion(37.0d, 304.92014f));
		massSpectrum.addIon(new Ion(38.0d, 457.3802f));
		massSpectrum.addIon(new Ion(40.0d, 1219.6805f));
		massSpectrum.addIon(new Ion(31.0d, 1829.5208f));
		massSpectrum.addIon(new Ion(39.0d, 1829.5208f));
		massSpectrum.addIon(new Ion(41.0d, 2591.821f));
		massSpectrum.addIon(new Ion(42.0d, 4116.422f));
		massSpectrum.addIon(new Ion(30.0d, 121510.67f));
		massSpectrum.addIon(new Ion(29.0d, 152307.61f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
