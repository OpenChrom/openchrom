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
import net.chemclipse.msd.model.implementation.Ion;
import net.chemclipse.msd.model.implementation.MassSpectrum;

/*
 * No m/z values match between noMatch1 and noMatch2.
 */
public class NoMatchA1 implements ITestMassSpectrum {

	private IMassSpectrum massSpectrum;

	public NoMatchA1() throws Exception {

		massSpectrum = new MassSpectrum();
		massSpectrum.addIon(new Ion(25.0d, 102.0f));
		massSpectrum.addIon(new Ion(32.0d, 2190.0f));
		massSpectrum.addIon(new Ion(45.0d, 80.0f));
		massSpectrum.addIon(new Ion(57.0d, 22.0f));
		massSpectrum.addIon(new Ion(60.0d, 5.0f));
	}

	@Override
	public IMassSpectrum getMassSpectrum() {

		return massSpectrum;
	}
}
