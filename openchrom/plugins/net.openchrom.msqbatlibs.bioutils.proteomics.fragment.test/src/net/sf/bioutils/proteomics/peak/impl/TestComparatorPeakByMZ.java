/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
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

import static org.junit.Assert.assertEquals;
import net.sf.bioutils.proteomics.peak.ComparatorPeakByMZ;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.peak.PeakImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestComparatorPeakByMZ {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private ComparatorPeakByMZ c;
	private Peak p1, p2;

	@Before
	public void setUp() throws Exception {

		c = new ComparatorPeakByMZ();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public final void testCompare01() {

		p1 = new PeakImpl(1, 1);
		p2 = new PeakImpl(1, 1);
		assertEquals(0, c.compare(p1, p2));
	}

	@Test
	public final void testCompare02() {

		p1 = null;
		p2 = null;
		assertEquals(0, c.compare(p1, p2));
	}

	@Test
	public final void testCompare03() {

		p1 = new PeakImpl(1, 1);
		p2 = null;
		assertEquals(1, c.compare(p1, p2));
	}

	@Test
	public final void testCompare04() {

		p2 = new PeakImpl(1, 1);
		p1 = null;
		assertEquals(-1, c.compare(p1, p2));
	}

	@Test
	public final void testCompare05() {

		p1 = new PeakImpl(2, 1);
		p2 = new PeakImpl(1, 1);
		assertEquals(1, c.compare(p1, p2));
	}

	@Test
	public final void testCompare06() {

		p1 = new PeakImpl(2, 1);
		p2 = new PeakImpl(3, 1);
		assertEquals(-1, c.compare(p1, p2));
	}
}
