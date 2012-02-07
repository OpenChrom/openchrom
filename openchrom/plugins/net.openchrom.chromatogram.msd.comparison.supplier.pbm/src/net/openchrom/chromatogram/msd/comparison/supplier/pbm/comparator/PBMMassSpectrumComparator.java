/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.comparator;

import net.openchrom.chromatogram.msd.comparison.massspectrum.AbstractMassSpectrumComparator;
import net.openchrom.chromatogram.msd.comparison.massspectrum.IMassSpectrumComparator;
import net.openchrom.chromatogram.msd.comparison.processing.IMassSpectrumComparatorProcessingInfo;
import net.openchrom.chromatogram.msd.comparison.processing.MassSpectrumComparatorProcessingInfo;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.results.PBMMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.model.core.IIon;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.identifier.massspectrum.IMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.model.exceptions.IonLimitExceededException;
import net.openchrom.chromatogram.msd.model.implementation.DefaultIon;
import net.openchrom.chromatogram.msd.model.implementation.DefaultMassSpectrum;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignal;
import net.openchrom.chromatogram.msd.model.xic.IIonRange;
import net.openchrom.logging.core.Logger;
import net.openchrom.processing.core.IProcessingInfo;

/**
 * This class gives back a IMassSpectrumComparisonResult which implements the
 * PBM mass spectrum comparison algorithm.
 * 
 * @author eselmeister
 */
public class PBMMassSpectrumComparator extends AbstractMassSpectrumComparator implements IMassSpectrumComparator {

	public static final String COMPARATOR_ID = "net.openchrom.chromatogram.msd.comparison.supplier.pbm";
	private static final Logger logger = Logger.getLogger(PBMMassSpectrumComparator.class);
	private static final int NORMALIZATION_FACTOR = 100;

	@Override
	public IMassSpectrumComparatorProcessingInfo compare(IMassSpectrum unknown, IMassSpectrum reference, IIonRange ionRange) {

		IMassSpectrumComparatorProcessingInfo processingInfo = new MassSpectrumComparatorProcessingInfo();
		IProcessingInfo processingInfoValidate = super.validate(unknown, reference, ionRange);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			IMassSpectrum unknownAdjusted = adjustMassSpectrum(unknown, ionRange);
			IMassSpectrum referenceAdjusted = adjustMassSpectrum(reference, ionRange);
			float matchFactor = calculateGeometricDistanceMatchQuality(unknownAdjusted, referenceAdjusted, ionRange) * 100; // internally it's normalized to 1, but a percentage value is used normally
			float reverseMatchFactor = calculateGeometricDistanceMatchQuality(referenceAdjusted, unknownAdjusted, ionRange) * 100; // internally it's normalized to 1, but a percentage value is used normally
			IMassSpectrumComparisonResult massSpectrumComparisonResult = new PBMMassSpectrumComparisonResult(matchFactor, reverseMatchFactor);
			processingInfo.setMassSpectrumComparisonResult(massSpectrumComparisonResult);
		}
		return processingInfo;
	}

	/**
	 * This method will calculate new abundance values in the following manner:<br/>
	 * For each ion the new abundance will be set to:<br/>
	 * <br/>
	 * Inew = I * Ion;<br/>
	 * <br/>
	 * Set the highest intensity value to 100 so that no problems will occur
	 * when you calculate the new abundance values. A new mass spectrum will be
	 * returned.
	 */
	private IMassSpectrum adjustMassSpectrum(IMassSpectrum massSpectrum, IIonRange ionRange) {

		IMassSpectrum adjustedMassSpectrum = new DefaultMassSpectrum();
		IIon adjustedIon;
		/*
		 * Normalize the abundance values to a highest value of 100.
		 */
		massSpectrum.normalize(NORMALIZATION_FACTOR);
		int startIon = ionRange.getStartIon();
		int stopIon = ionRange.getStopIon();
		IExtractedIonSignal signal;
		signal = massSpectrum.getExtractedIonSignal(startIon, stopIon);
		/*
		 * Calculate the new abundance value.
		 */
		float abundance;
		for(int ion = startIon; ion <= stopIon; ion++) {
			abundance = signal.getAbundance(ion);
			if(abundance >= 0) {
				/*
				 * Calculate the new abundance and add the ion to the
				 * fresh created mass spectrum.
				 */
				try {
					/*
					 * Inew = I * Ion
					 */
					adjustedIon = new DefaultIon(ion, abundance * ion);
					adjustedMassSpectrum.addIon(adjustedIon);
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				} catch(IonLimitExceededException e) {
					logger.warn(e);
				}
			}
		}
		return adjustedMassSpectrum;
	}
}
