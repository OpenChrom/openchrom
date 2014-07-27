/*******************************************************************************
 * Copyright (c) 2014 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.comparison.supplier.pbm.comparator;

import net.chemclipse.chromatogram.msd.comparison.processing.IMassSpectrumComparatorProcessingInfo;
import net.chemclipse.chromatogram.msd.model.core.IMassSpectrum;
import net.chemclipse.chromatogram.msd.model.core.identifier.massspectrum.IMassSpectrumComparisonResult;

public class PBMMassSpectrumComparator_5_Test extends MassSpectrumSetTestCase {

	private PBMMassSpectrumComparator comparator;
	private IMassSpectrumComparatorProcessingInfo processingInfo;
	private IMassSpectrumComparisonResult result;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		//
		IMassSpectrum unknown = noMatchA1.getMassSpectrum();
		IMassSpectrum reference = noMatchA2.getMassSpectrum();
		//
		comparator = new PBMMassSpectrumComparator();
		processingInfo = comparator.compare(unknown, reference);
		result = processingInfo.getMassSpectrumComparisonResult();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}

	public void test1() {

		assertFalse(processingInfo.hasErrorMessages());
	}

	public void test2() {

		assertEquals(0.0f, result.getMatchFactor());
	}

	public void test3() {

		assertEquals(0.0f, result.getReverseMatchFactor());
	}
}
