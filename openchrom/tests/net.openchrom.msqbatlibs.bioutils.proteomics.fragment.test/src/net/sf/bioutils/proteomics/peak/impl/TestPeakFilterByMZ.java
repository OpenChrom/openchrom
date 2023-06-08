/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.peak.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.bioutils.proteomics.peak.FilterPeakByMZ;
import net.sf.bioutils.proteomics.peak.FilterPeakByMzRange;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.peak.PeakImpl;
import net.sf.jranges.range.doublerange.impl.RangeDoubleDummy;
import net.sf.kerner.utils.collections.filter.Filter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPeakFilterByMZ {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private Filter<Peak> filter;
	private PeakImpl p1;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testVisit01() {

		filter = new FilterPeakByMZ(0.001);
		p1 = new PeakImpl(0.001, 1);
		assertTrue(filter.filter(p1));
	}

	@Test(expected = NullPointerException.class)
	public final void testVisit03() {

		filter = new FilterPeakByMZ(0.001);
		filter.filter(null);
	}

	@Test
	public final void testVisit04() {

		filter = new FilterPeakByMzRange(new RangeDoubleDummy(0.001, 0.002));
		p1 = new PeakImpl(0.001, 1);
		assertTrue(filter.filter(p1));
	}

	@Test
	public final void testVisit05() {

		filter = new FilterPeakByMzRange(new RangeDoubleDummy(0.001, 0.002));
		p1 = new PeakImpl(0.002, 1);
		assertTrue(filter.filter(p1));
	}

	@Test
	public final void testVisit06() {

		filter = new FilterPeakByMzRange(new RangeDoubleDummy(0.001, 0.002));
		p1 = new PeakImpl(0.0015, 1);
		assertTrue(filter.filter(p1));
	}

	@Test
	public final void testVisit07() {

		filter = new FilterPeakByMzRange(new RangeDoubleDummy(0.001, 0.002));
		p1 = new PeakImpl(0.0009, 1);
		assertFalse(filter.filter(p1));
	}

	@Test
	public final void testVisit08() {

		filter = new FilterPeakByMzRange(new RangeDoubleDummy(0.001, 0.002));
		p1 = new PeakImpl(0.0021, 1);
		assertFalse(filter.filter(p1));
	}
}
