/*******************************************************************************
 * Copyright (c) 2014, 2016 Dr. Philip Wenig.
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
 * No m/z values match between noMatch1 and noMatch2.
 */
public class NoMatchA2 implements ITestMassSpectrum {

	private IScanMSD massSpectrum;

	public NoMatchA2() throws Exception {
		massSpectrum = new ScanMSD();
		massSpectrum.addIon(new Ion(65.0d, 1017.0f));
		massSpectrum.addIon(new Ion(70.0d, 202891.0f));
		massSpectrum.addIon(new Ion(72.0d, 22.0f));
		massSpectrum.addIon(new Ion(80.0d, 84.0f));
		massSpectrum.addIon(new Ion(95.0d, 40.0f));
		massSpectrum.addIon(new Ion(97.0d, 7.0f));
	}

	@Override
	public IScanMSD getMassSpectrum() {

		return massSpectrum;
	}
}
