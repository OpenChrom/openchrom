/*******************************************************************************
 * Copyright (c) 2013, 2016 Dr. Philip Wenig.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula;

import java.util.HashSet;
import java.util.Set;

import org.openscience.cdk.interfaces.IIsotope;

import net.openchrom.chromatogram.msd.identifier.supplier.cdk.formula.IsotopeParser;

import junit.framework.TestCase;

public class IsotopeParser_1_Test extends TestCase {

	private IsotopeParser isotopeParser;
	private Set<IIsotope> isotopes;
	private Set<String> isotopeIds;

	@Override
	protected void setUp() throws Exception {

		super.setUp();
		isotopeParser = new IsotopeParser();
		isotopes = isotopeParser.extract("1H, 12C, 13C, C, H, N");
		isotopeIds = new HashSet<String>();
		for(IIsotope isotope : isotopes) {
			isotopeIds.add(isotope.getID());
		}
	}

	@Override
	protected void tearDown() throws Exception {

		isotopeParser = null;
		isotopes = null;
		isotopeIds = null;
		super.tearDown();
	}

	public void testExtract_1() {

		assertNotNull(isotopes);
	}

	public void testExtract_2() {

		assertEquals(6, isotopes.size());
	}

	public void testExtract_3() {

		assertEquals(4, isotopeIds.size());
	}

	public void testExtract_4() {

		assertTrue(isotopeIds.contains("H1"));
	}

	public void testExtract_5() {

		assertTrue(isotopeIds.contains("C12"));
	}

	public void testExtract_6() {

		assertTrue(isotopeIds.contains("C13"));
	}

	public void testExtract_7() {

		assertTrue(isotopeIds.contains("N14"));
	}
}
