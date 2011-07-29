/*******************************************************************************
 * Copyright (c) 2008, 2011 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.comparator;

import net.openchrom.chromatogram.msd.comparison.exceptions.ComparisonException;
import net.openchrom.chromatogram.msd.comparison.spectrum.IMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.comparison.spectrum.MassSpectrumComparator;
import net.openchrom.chromatogram.msd.model.core.IIon;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.implementation.DefaultIon;
import net.openchrom.chromatogram.msd.model.implementation.DefaultMassSpectrum;
import net.openchrom.chromatogram.msd.model.xic.IIonRange;
import net.openchrom.chromatogram.msd.model.xic.IonRange;
import junit.framework.TestCase;

public class PBMMassSpectrumComparator_1_Test extends TestCase {

	private IMassSpectrumComparisonResult result;
	private IMassSpectrum unknown;
	private IMassSpectrum reference;
	private IIon ion;
	private IIonRange ionRange;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
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
		reference = new DefaultMassSpectrum();
		ion = new DefaultIon(45.5f, 4000.0f);
		reference.addIon(ion);
		ion = new DefaultIon(55.5f, 4000.0f);
		reference.addIon(ion);
		ion = new DefaultIon(75.5f, 4000.0f);
		reference.addIon(ion);
		ion = new DefaultIon(105.5f, 4000.0f);
		reference.addIon(ion);
		ionRange = new IonRange(20, 120);
		result = MassSpectrumComparator.compare(unknown, reference, ionRange, PBMMassSpectrumComparator.COMPARATOR_ID);
	}

	@Override
	protected void tearDown() throws Exception {

		unknown = null;
		reference = null;
		ion = null;
		ionRange = null;
		super.tearDown();
	}

	public void testFitValue_1() throws ComparisonException {

		assertEquals("FIT Value", 0.6f, result.getFitValue());
	}

	public void testReverseFitValue_1() throws ComparisonException {

		assertEquals("ReverseFIT Value", 0.75f, result.getReverseFitValue());
	}

	public void testMatchQuality_1() throws ComparisonException {

		assertEquals("MatchQuality", 0.8162296f, result.getMatchQuality());
	}
}
