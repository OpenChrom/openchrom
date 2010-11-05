/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.ms.comparison.supplier.pbm.comparator;

import net.openchrom.chromatogram.ms.comparison.exceptions.ComparisonException;
import net.openchrom.chromatogram.ms.comparison.spectrum.IMassSpectrumComparator;
import net.openchrom.chromatogram.ms.comparison.spectrum.IMassSpectrumComparisonResult;
import net.openchrom.chromatogram.ms.comparison.supplier.pbm.model.PBMMassSpectrumComparisonResult;
import net.openchrom.chromatogram.ms.model.core.IMassSpectrum;
import net.openchrom.chromatogram.ms.model.xic.IMassFragmentRange;
import net.openchrom.logging.core.Logger;

/**
 * This class gives back a IMassSpectrumComparisonResult which implements the
 * PBM mass spectrum comparison algorithm.
 * 
 * @author eselmeister
 */
public class PBMMassSpectrumComparator implements IMassSpectrumComparator {

	public static final String COMPARATOR_ID = "net.openchrom.chromatogram.ms.comparison.supplier.pbm";
	private static final Logger logger = Logger.getLogger(PBMMassSpectrumComparator.class);

	@Override
	public IMassSpectrumComparisonResult compare(IMassSpectrum unknown, IMassSpectrum reference, IMassFragmentRange massFragmentRange) {

		PBMMassSpectrumComparisonResult result = null;
		try {
			result = new PBMMassSpectrumComparisonResult(unknown, reference, massFragmentRange);
		} catch(ComparisonException e) {
			logger.warn(e);
			return null;
		}
		return result;
	}
}
