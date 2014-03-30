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
package net.chemclipse.chromatogram.fid.converter.supplier.cdf.model;

import net.chemclipse.chromatogram.fid.model.core.AbstractChromatogramFID;
import net.chemclipse.chromatogram.model.noise.ISimpleNoiseFactorCalculator;
import net.chemclipse.chromatogram.model.noise.SimpleNoiseFactorCalculator;

public class CDFChromatogramFID extends AbstractChromatogramFID implements ICDFChromatogramFID {

	private ISimpleNoiseFactorCalculator noiseFactorCalculator;

	public CDFChromatogramFID() {

		noiseFactorCalculator = new SimpleNoiseFactorCalculator();
	}

	@Override
	public void recalculateTheNoiseFactor() {

		noiseFactorCalculator.recalculate();
	}

	@Override
	public float getNoiseFactor() {

		return noiseFactorCalculator.getNoiseFactor(this);
	}

	@Override
	public String getName() {

		return extractNameFromFile("XYChromatogramFID");
	}
}
