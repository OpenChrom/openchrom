/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.comparator;

import net.openchrom.chromatogram.msd.comparison.exceptions.ComparisonException;
import net.openchrom.chromatogram.msd.comparison.processing.IMassSpectrumComparatorProcessingInfo;
import net.openchrom.chromatogram.msd.comparison.processing.MassSpectrumComparatorProcessingInfo;
import net.openchrom.chromatogram.msd.comparison.spectrum.IMassSpectrumComparator;
import net.openchrom.chromatogram.msd.comparison.spectrum.IMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.model.PBMMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.xic.IIonRange;
import net.openchrom.logging.core.Logger;

/**
 * This class gives back a IMassSpectrumComparisonResult which implements the
 * PBM mass spectrum comparison algorithm.
 * 
 * @author eselmeister
 */
public class PBMMassSpectrumComparator implements IMassSpectrumComparator {

	public static final String COMPARATOR_ID = "net.openchrom.chromatogram.msd.comparison.supplier.pbm";
	private static final Logger logger = Logger.getLogger(PBMMassSpectrumComparator.class);
	private static final String DESCRIPTION = "PBM Distance Comparator";

	@Override
	public IMassSpectrumComparatorProcessingInfo compare(IMassSpectrum unknown, IMassSpectrum reference, IIonRange ionRange) {

		IMassSpectrumComparatorProcessingInfo processingInfo = new MassSpectrumComparatorProcessingInfo();
		try {
			IMassSpectrumComparisonResult massSpectrumComparisonResult = new PBMMassSpectrumComparisonResult(unknown, reference, ionRange);
			processingInfo.setMassSpectrumComparisonResult(massSpectrumComparisonResult);
		} catch(ComparisonException e) {
			logger.warn(e);
			processingInfo.addErrorMessage(DESCRIPTION, "The unknown mass spectrum couldn't be identified.");
		}
		return processingInfo;
	}
}
