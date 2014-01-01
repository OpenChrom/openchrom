/*******************************************************************************
 * Copyright (c) 2008, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.comparator;

import net.openchrom.chromatogram.msd.comparison.massspectrum.MassSpectrumComparator;
import net.openchrom.chromatogram.msd.comparison.processing.IMassSpectrumComparatorProcessingInfo;
import net.openchrom.chromatogram.msd.model.core.IMassSpectrum;
import net.openchrom.chromatogram.msd.model.core.identifier.massspectrum.IMassSpectrumComparisonResult;
import net.openchrom.chromatogram.msd.model.implementation.DefaultMassSpectrum;
import net.openchrom.processing.core.exceptions.TypeCastException;

import junit.framework.TestCase;

public class PBMMassSpectrumComparator_2_Test extends TestCase {

	@SuppressWarnings("unused")
	private IMassSpectrumComparisonResult result;
	private IMassSpectrum unknown;
	private IMassSpectrum reference;

	@Override
	protected void setUp() throws Exception {

		unknown = new DefaultMassSpectrum();
		reference = new DefaultMassSpectrum();
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {

		unknown = null;
		reference = null;
		result = null;
		super.tearDown();
	}

	public void testMassSpectrumComparatorCompare_1() {

		IMassSpectrumComparatorProcessingInfo processingInfo = MassSpectrumComparator.compare(null, null, PBMMassSpectrumComparator.COMPARATOR_ID);
		assertTrue(processingInfo.hasErrorMessages());
		try {
			result = processingInfo.getMassSpectrumComparisonResult();
		} catch(TypeCastException e) {
			assertTrue("TypeCastException", true);
		}
	}

	public void testMassSpectrumComparatorCompare_2() {

		IMassSpectrumComparatorProcessingInfo processingInfo = MassSpectrumComparator.compare(unknown, null, PBMMassSpectrumComparator.COMPARATOR_ID);
		assertTrue(processingInfo.hasErrorMessages());
		try {
			result = processingInfo.getMassSpectrumComparisonResult();
		} catch(TypeCastException e) {
			assertTrue("TypeCastException", true);
		}
	}

	public void testMassSpectrumComparatorCompare_3() {

		IMassSpectrumComparatorProcessingInfo processingInfo = MassSpectrumComparator.compare(null, reference, PBMMassSpectrumComparator.COMPARATOR_ID);
		assertTrue(processingInfo.hasErrorMessages());
		try {
			result = processingInfo.getMassSpectrumComparisonResult();
		} catch(TypeCastException e) {
			assertTrue("TypeCastException", true);
		}
	}

	public void testMassSpectrumComparatorCompare_4() {

		IMassSpectrumComparatorProcessingInfo processingInfo = MassSpectrumComparator.compare(unknown, reference, PBMMassSpectrumComparator.COMPARATOR_ID);
		assertTrue(processingInfo.hasErrorMessages());
		try {
			result = processingInfo.getMassSpectrumComparisonResult();
		} catch(TypeCastException e) {
			assertTrue("TypeCastException", true);
		}
	}

	public void testMassSpectrumComparatorCompare_5() {

		IMassSpectrumComparatorProcessingInfo processingInfo = MassSpectrumComparator.compare(unknown, reference, null);
		assertTrue(processingInfo.hasErrorMessages());
		try {
			result = processingInfo.getMassSpectrumComparisonResult();
		} catch(TypeCastException e) {
			assertTrue("TypeCastException", true);
		}
	}
}
