/*******************************************************************************
 * Copyright (c) 2008, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.results;

import net.chemclipse.chromatogram.msd.model.core.identifier.massspectrum.AbstractMassSpectrumComparisonResult;

public class PBMMassSpectrumComparisonResult extends AbstractMassSpectrumComparisonResult {

	public PBMMassSpectrumComparisonResult(float matchFactor, float reverseMatchFactor) {

		super(matchFactor, reverseMatchFactor);
	}
}
