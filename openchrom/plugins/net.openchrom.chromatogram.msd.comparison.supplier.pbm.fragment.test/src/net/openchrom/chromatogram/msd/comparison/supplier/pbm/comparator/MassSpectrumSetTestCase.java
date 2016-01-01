/*******************************************************************************
 * Copyright (c) 2014, 2016 Dr. Philip Wenig.
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

import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.BenzenepropanoicAcid;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.NoMatchA1;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.NoMatchA2;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.PhenolBenzimidazolyl;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.ProblemA1;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.ProblemA2;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.ProblemB1;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.ProblemB2;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.ProblemC1;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.ProblemC2;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.SinapylAlcohol;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.SinapylAlcoholCis;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.Syringylacetone;
import net.openchrom.chromatogram.msd.comparison.supplier.pbm.spectra.ITestMassSpectrum;

import junit.framework.TestCase;

/**
 * Comparison NIST-DB 12 (MF, RMF):
 * sinapylAclohol vs sinapylAcloholCis: 79.4, 92.6
 * sinapylAclohol vs benzenepropanoicAcid: 61.9, 68.0
 * sinapylAclohol vs syringylAcetone: 59.5, 76.3
 * sinapylAclohol vs phenolBenzimidazolyl: 51.5, 57.6
 * 
 */
public class MassSpectrumSetTestCase extends TestCase {

	protected ITestMassSpectrum sinapylAclohol;
	protected ITestMassSpectrum sinapylAcloholCis;
	protected ITestMassSpectrum benzenepropanoicAcid;
	protected ITestMassSpectrum syringylAcetone;
	protected ITestMassSpectrum phenolBenzimidazolyl;
	//
	protected ITestMassSpectrum noMatchA1;
	protected ITestMassSpectrum noMatchA2;
	protected ITestMassSpectrum problemA1;
	protected ITestMassSpectrum problemA2;
	protected ITestMassSpectrum problemB1;
	protected ITestMassSpectrum problemB2;
	protected ITestMassSpectrum problemC1;
	protected ITestMassSpectrum problemC2;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		//
		sinapylAclohol = new SinapylAlcohol();
		sinapylAcloholCis = new SinapylAlcoholCis();
		benzenepropanoicAcid = new BenzenepropanoicAcid();
		syringylAcetone = new Syringylacetone();
		phenolBenzimidazolyl = new PhenolBenzimidazolyl();
		//
		noMatchA1 = new NoMatchA1();
		noMatchA2 = new NoMatchA2();
		problemA1 = new ProblemA1();
		problemA2 = new ProblemA2();
		problemB1 = new ProblemB1();
		problemB2 = new ProblemB2();
		problemC1 = new ProblemC1();
		problemC2 = new ProblemC2();
	}

	@Override
	protected void tearDown() throws Exception {

		super.tearDown();
	}
}
