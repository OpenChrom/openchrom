/*******************************************************************************
 * Copyright (c) 2008, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.comparator;

import net.chemclipse.chromatogram.msd.comparison.exceptions.ComparisonException;
import net.chemclipse.chromatogram.msd.comparison.massspectrum.MassSpectrumComparator;
import net.chemclipse.chromatogram.msd.comparison.processing.IMassSpectrumComparatorProcessingInfo;
import net.chemclipse.chromatogram.msd.model.core.IIon;
import net.chemclipse.chromatogram.msd.model.core.IMassSpectrum;
import net.chemclipse.chromatogram.msd.model.core.identifier.massspectrum.IMassSpectrumComparisonResult;
import net.chemclipse.chromatogram.msd.model.implementation.DefaultIon;
import net.chemclipse.chromatogram.msd.model.implementation.DefaultMassSpectrum;
import junit.framework.TestCase;

public class PBMMassSpectrumComparator_1_Test extends TestCase {

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
		ion = new DefaultIon(45.5f, 4000.0f);
		reference.addIon(ion);
		ion = new DefaultIon(55.5f, 4000.0f);
		reference.addIon(ion);
		ion = new DefaultIon(75.5f, 4000.0f);
		reference.addIon(ion);
		ion = new DefaultIon(105.5f, 4000.0f);
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

		assertEquals("MatchFactor", 56.014168f, result.getMatchFactor());
	}

	public void testGetReverseMatchFactor_1() throws ComparisonException {

		assertEquals("ReverseMatchFactor", 56.014168f, result.getReverseMatchFactor());
	}

	public void testGetProbability_1() throws ComparisonException {

		assertEquals("Probability", 0.0f, result.getProbability());
	}
}
