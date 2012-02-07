/*******************************************************************************
 * Copyright (c) 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.comparator;

import net.openchrom.chromatogram.msd.comparison.exceptions.ComparisonException;
import net.openchrom.chromatogram.msd.comparison.massspectrum.MassSpectrumComparator;
import net.openchrom.chromatogram.msd.comparison.processing.IMassSpectrumComparatorProcessingInfo;
import net.openchrom.chromatogram.msd.model.core.IIon;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.identifier.massspectrum.IMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.model.implementation.DefaultIon;
import net.openchrom.chromatogram.msd.model.implementation.DefaultMassSpectrum;
import junit.framework.TestCase;

public class PBMMassSpectrumComparator_3_Test extends TestCase {

	private IMassSpectrumComparisonResult result;
	private IMassSpectrum unknown;
	private IMassSpectrum reference;
	private IIon ion;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		/*
		 * Unknown
		 */
		unknown = new DefaultMassSpectrum();
		ion = new DefaultIon(45.5f, 4000.0f);
		unknown.addIon(ion);
		ion = new DefaultIon(55.5f, 4000.0f);
		unknown.addIon(ion);
		ion = new DefaultIon(85.5f, 4000.0f);
		unknown.addIon(ion);
		ion = new DefaultIon(95.5f, 4000.0f);
		unknown.addIon(ion);
		ion = new DefaultIon(105.5f, 4000.0f);
		unknown.addIon(ion);
		/*
		 * Reference
		 */
		reference = new DefaultMassSpectrum();
		ion = new DefaultIon(55.5f, 4000.0f);
		reference.addIon(ion);
		ion = new DefaultIon(75.5f, 4000.0f);
		reference.addIon(ion);
		IMassSpectrumComparatorProcessingInfo processingInfo = MassSpectrumComparator.compare(unknown, reference, PBMMassSpectrumComparator.COMPARATOR_ID);
		result = processingInfo.getMassSpectrumComparisonResult();
	}

	@Override
	protected void tearDown() throws Exception {

		unknown = null;
		reference = null;
		ion = null;
		super.tearDown();
	}

	public void testGetMatchFactor_1() throws ComparisonException {

		assertEquals("MatchFactor", 37.953224f, result.getMatchFactor());
	}

	public void testGetReverseMatchFactor_1() throws ComparisonException {

		assertEquals("ReverseMatchFactor", 55.138893f, result.getReverseMatchFactor());
	}

	public void testGetProbability_1() throws ComparisonException {

		assertEquals("Probability", 0.0f, result.getProbability());
	}
}
