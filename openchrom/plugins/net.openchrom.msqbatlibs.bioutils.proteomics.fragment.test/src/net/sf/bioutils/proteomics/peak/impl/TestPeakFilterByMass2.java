/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.bioutils.proteomics.peak.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.bioutils.proteomics.peak.FilterPeakByMzRange;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.bioutils.proteomics.peak.PeakImpl;
import net.sf.jranges.range.doublerange.impl.RangeDoubleDummy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test PeakFilterByMass(DoubleRange).
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-08-01
 */
public class TestPeakFilterByMass2 {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private FilterPeakByMzRange f;

    private Peak p1;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testVisit01() {
        p1 = new PeakImpl(1, 1);
        f = new FilterPeakByMzRange(new RangeDoubleDummy(0.999, 1.001));
        assertTrue(f.filter(p1));
    }

    @Test
    public final void testVisit02() {
        p1 = new PeakImpl(1.0011, 1);
        f = new FilterPeakByMzRange(new RangeDoubleDummy(0.999, 1.001));
        assertFalse(f.filter(p1));
    }

}
