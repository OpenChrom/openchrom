/*******************************************************************************
 * Copyright (c) 2008, 2013 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.comparator;

import net.openchrom.chromatogram.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.msd.comparison.massspectrum.AbstractMassSpectrumComparator;
import net.openchrom.chromatogram.msd.comparison.massspectrum.IMassSpectrumComparator;
import net.openchrom.chromatogram.msd.comparison.math.GeometricDistanceCalculator;
import net.openchrom.chromatogram.msd.comparison.math.IDistanceCalculator;
import net.openchrom.chromatogram.msd.comparison.processing.IMassSpectrumComparatorProcessingInfo;
import net.openchrom.chromatogram.msd.comparison.processing.MassSpectrumComparatorProcessingInfo;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.results.PBMMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.model.core.IIon;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.identifier.massspectrum.IMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.model.exceptions.IonLimitExceededException;
import net.openchrom.chromatogram.msd.model.implementation.DefaultIon;
import net.openchrom.chromatogram.msd.model.implementation.DefaultMassSpectrum;
import net.openchrom.chromatogram.msd.model.xic.IExtractedIonSignal;
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
	public IMassSpectrumComparatorProcessingInfo compare(IMassSpectrum unknown, IMassSpectrum reference) {

		IMassSpectrumComparatorProcessingInfo processingInfo = new MassSpectrumComparatorProcessingInfo();
		IProcessingInfo processingInfoValidate = super.validate(unknown, reference);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			IExtractedIonSignal signal;
			IDistanceCalculator geometricDistanceCalculator = new GeometricDistanceCalculator();
			IMassSpectrum unknownAdjusted = adjustMassSpectrum(unknown);
			IMassSpectrum referenceAdjusted = adjustMassSpectrum(reference);
			/*
			 * Match Factor
			 */
			signal = unknownAdjusted.getExtractedIonSignal();
			float matchFactor = geometricDistanceCalculator.calculate(unknownAdjusted, referenceAdjusted, signal.getIonRange()) * 100; // internally it's normalized to 1, but a percentage value is used normally
			/*
			 * Reverse Match Factor
			 */
			signal = referenceAdjusted.getExtractedIonSignal();
			float reverseMatchFactor = geometricDistanceCalculator.calculate(referenceAdjusted, unknownAdjusted, signal.getIonRange()) * 100; // internally it's normalized to 1, but a percentage value is used normally
			/*
			 * Result
			 */
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
	private IMassSpectrum adjustMassSpectrum(IMassSpectrum massSpectrum) {

		IMassSpectrum adjustedMassSpectrum = new DefaultMassSpectrum();
		IIon adjustedIon;
		/*
		 * Normalize the abundance values to a highest value of 100.
		 */
		try {
			/*
			 * Make a deep copy to not destroy the original mass spectrum.
			 */
			IMassSpectrum massSpectrumNormalized = massSpectrum.makeDeepCopy();
			/*
			 * Normalize the copied mass spectrum.
			 */
			massSpectrumNormalized.normalize(NORMALIZATION_FACTOR);
			IExtractedIonSignal signal;
			signal = massSpectrumNormalized.getExtractedIonSignal();
			/*
			 * Calculate the new abundance value.
			 */
			float abundance;
			int startIon = signal.getStartIon();
			int stopIon = signal.getStopIon();
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
		} catch(CloneNotSupportedException e1) {
			logger.warn(e1);
		}
		return adjustedMassSpectrum;
	}
}
