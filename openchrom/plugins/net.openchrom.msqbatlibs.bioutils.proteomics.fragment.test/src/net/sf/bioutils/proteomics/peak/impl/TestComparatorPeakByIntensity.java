/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.bioutils.proteomics.peak.impl;

import static org.junit.Assert.assertEquals;
import net.sf.bioutils.proteomics.comparator.ComparatorIntensity;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.peak.PeakImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestComparatorPeakByIntensity {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	private ComparatorIntensity c;
	private Peak p1, p2;

	@Before
	public void setUp() throws Exception {

		c = new ComparatorIntensity();
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

		p1 = new PeakImpl(1, 2);
		p2 = new PeakImpl(1, 1);
		assertEquals(1, c.compare(p1, p2));
	}

	@Test
	public final void testCompare06() {

		p1 = new PeakImpl(1, 2);
		p2 = new PeakImpl(1, 3);
		assertEquals(-1, c.compare(p1, p2));
	}
}
