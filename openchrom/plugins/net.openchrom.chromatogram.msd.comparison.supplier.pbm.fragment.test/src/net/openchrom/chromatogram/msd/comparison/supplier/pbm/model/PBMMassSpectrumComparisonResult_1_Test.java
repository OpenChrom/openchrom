/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.model;

import net.openchrom.chromatogram.msd.comparison.exceptions.ComparisonException;
import net.openchrom.chromatogram.msd.model.core.IMassFragment;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.implementation.DefaultMassFragment;
import net.openchrom.chromatogram.msd.model.implementation.DefaultMassSpectrum;
import net.openchrom.chromatogram.msd.model.xic.IMassFragmentRange;
import net.openchrom.chromatogram.msd.model.xic.MassFragmentRange;
import junit.framework.TestCase;

public class PBMMassSpectrumComparisonResult_1_Test extends TestCase {

	private PBMMassSpectrumComparisonResult result;
	private IMassSpectrum unknown;
	private IMassSpectrum reference;
	private IMassFragment massFragment;
	private IMassFragmentRange massFragmentRange;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		unknown = new DefaultMassSpectrum();
		massFragment = new DefaultMassFragment(45.5f, 500.0f);
		unknown.addMassFragment(massFragment);
		massFragment = new DefaultMassFragment(55.5f, 320.0f);
		unknown.addMassFragment(massFragment);
		massFragment = new DefaultMassFragment(85.5f, 8500.0f);
		unknown.addMassFragment(massFragment);
		massFragment = new DefaultMassFragment(95.5f, 740.0f);
		unknown.addMassFragment(massFragment);
		massFragment = new DefaultMassFragment(105.5f, 6900.0f);
		unknown.addMassFragment(massFragment);
		reference = new DefaultMassSpectrum();
		massFragment = new DefaultMassFragment(45.5f, 500.0f);
		reference.addMassFragment(massFragment);
		massFragment = new DefaultMassFragment(55.5f, 320.0f);
		reference.addMassFragment(massFragment);
		massFragment = new DefaultMassFragment(75.5f, 2800.0f);
		reference.addMassFragment(massFragment);
		massFragment = new DefaultMassFragment(105.5f, 6900.0f);
		reference.addMassFragment(massFragment);
		massFragmentRange = new MassFragmentRange(20, 120);
	}

	@Override
	protected void tearDown() throws Exception {

		unknown = null;
		reference = null;
		massFragment = null;
		massFragmentRange = null;
		super.tearDown();
	}

	public void testFitValue_1() throws ComparisonException {

		result = new PBMMassSpectrumComparisonResult(unknown, reference, massFragmentRange);
		assertEquals("FIT Value", 0.6f, result.getFitValue());
	}

	public void testReverseFitValue_1() throws ComparisonException {

		result = new PBMMassSpectrumComparisonResult(unknown, reference, massFragmentRange);
		assertEquals("ReverseFIT Value", 0.75f, result.getReverseFitValue());
	}

	public void testMatchQuality_1() throws ComparisonException {

		result = new PBMMassSpectrumComparisonResult(unknown, reference, massFragmentRange);
		assertEquals("MatchQuality", 0.698283f, result.getMatchQuality());
	}
}
