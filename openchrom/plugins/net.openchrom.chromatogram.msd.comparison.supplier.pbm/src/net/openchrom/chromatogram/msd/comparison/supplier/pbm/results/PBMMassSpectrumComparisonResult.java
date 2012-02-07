/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.results;

import net.openchrom.chromatogram.msd.model.core.identifier.massspectrum.AbstractMassSpectrumComparisonResult;

public class PBMMassSpectrumComparisonResult extends AbstractMassSpectrumComparisonResult {

	public PBMMassSpectrumComparisonResult(float matchFactor, float reverseMatchFactor) {

		super(matchFactor, reverseMatchFactor);
	}
}
