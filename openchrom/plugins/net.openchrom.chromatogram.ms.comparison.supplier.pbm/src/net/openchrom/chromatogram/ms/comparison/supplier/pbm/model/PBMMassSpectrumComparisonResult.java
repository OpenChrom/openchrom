/*******************************************************************************
 * Copyright (c) 2008, 2010 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.ms.comparison.supplier.pbm.model;

import net.openchrom.chromatogram.ms.comparison.exceptions.ComparisonException;
import net.openchrom.chromatogram.ms.comparison.spectrum.AbstractMassSpectrumComparisonResult;
import net.openchrom.chromatogram.ms.model.core.IMassFragment;
import net.openchrom.chromatogram.ms.model.core.IMassSpectrum;
import net.openchrom.chromatogram.ms.model.exceptions.AbundanceLimitExceededException;
import net.openchrom.chromatogram.ms.model.exceptions.MZLimitExceededException;
import net.openchrom.chromatogram.ms.model.implementation.DefaultMassFragment;
import net.openchrom.chromatogram.ms.model.implementation.DefaultMassSpectrum;
import net.openchrom.chromatogram.ms.model.xic.IExtractedIonSignal;
import net.openchrom.chromatogram.ms.model.xic.IMassFragmentRange;
import net.openchrom.logging.core.Logger;

public class PBMMassSpectrumComparisonResult extends AbstractMassSpectrumComparisonResult {

	private static final Logger logger = Logger.getLogger(PBMMassSpectrumComparisonResult.class);
	public static final String DESCRIPTION = "PBM";
	private static final int NORMALIZATION_FACTOR = 100;
	private float matchQuality = 0.0f;

	public PBMMassSpectrumComparisonResult(IMassSpectrum unknown, IMassSpectrum reference, IMassFragmentRange massFragmentRange) throws ComparisonException {

		/*
		 * The super method checks if one of the arguments is null and throws an
		 * ComparisonException.
		 */
		super(unknown, reference, massFragmentRange);
		/*
		 * Build new mass spectra (normalized and with weighted intensity) and
		 * reassign the mass spectra to not override the original ones.
		 */
		unknown = adjustMassSpectrum(unknown, massFragmentRange);
		reference = adjustMassSpectrum(reference, massFragmentRange);
		matchQuality = calculateGeometricDistanceMatchQuality(unknown, reference, massFragmentRange);
		setDescription(DESCRIPTION);
	}

	@Override
	public float getMatchQuality() {

		return matchQuality;
	}

	// ----------------------------------------private methods
	/**
	 * This method will calculate new abundance values in the following manner:<br/>
	 * For each mass fragment the new abundance will be set to:<br/>
	 * <br/>
	 * Inew = I * MZ;<br/>
	 * <br/>
	 * Set the highest intensity value to 100 so that no problems will occur
	 * when you calculate the new abundance values. A new mass spectrum will be
	 * returned.
	 */
	private IMassSpectrum adjustMassSpectrum(IMassSpectrum massSpectrum, IMassFragmentRange massFragmentRange) {

		IMassSpectrum adjustedMassSpectrum = new DefaultMassSpectrum();
		IMassFragment adjustedMassFragment;
		/*
		 * Normalize the abundance values to a highest value of 100.
		 */
		massSpectrum.normalize(NORMALIZATION_FACTOR);
		int startMZ = massFragmentRange.getStartMassFragment();
		int stopMZ = massFragmentRange.getStopMassFragment();
		IExtractedIonSignal signal;
		signal = massSpectrum.getExtractedIonSignal(startMZ, stopMZ);
		/*
		 * Calculate the new abundance value.
		 */
		float abundance;
		for(int mz = startMZ; mz <= stopMZ; mz++) {
			abundance = signal.getAbundance(mz);
			if(abundance >= 0) {
				/*
				 * Calculate the new abundance and add the mass fragment to the
				 * fresh created mass spectrum.
				 */
				try {
					/*
					 * Inew = I * MZ
					 */
					adjustedMassFragment = new DefaultMassFragment(mz, abundance * mz);
					adjustedMassSpectrum.addMassFragment(adjustedMassFragment);
				} catch(AbundanceLimitExceededException e) {
					logger.warn(e);
				} catch(MZLimitExceededException e) {
					logger.warn(e);
				}
			}
		}
		return adjustedMassSpectrum;
	}
	// ----------------------------------------private methods
}
