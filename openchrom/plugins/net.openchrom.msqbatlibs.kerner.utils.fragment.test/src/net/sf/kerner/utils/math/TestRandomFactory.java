/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
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
package net.sf.kerner.utils.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-02-15
 */
public class TestRandomFactory {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilRandom#generateAround(double, double)}
     * .
     */
    @Test
    public void testGenerateAroundDoubleDouble() {
        final double delta = 0.04;
        for (int i = 0; i < 1000; i++) {
            assertEquals((double) i, UtilRandom.generateAround(i, delta), delta);
        }
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilRandom#generateBetween(double, double)}
     * .
     */
    @Test
    public void testGenerateBetweenDoubleDouble() {
        final double low = 0.04;
        final double high = 0.8;
        for (int i = 0; i < 1000; i++) {
            final double r = UtilRandom.generateBetween(low, high);
            assertTrue(low <= r);
            assertTrue(r < high);
        }
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilRandom#generateAround(int, int)}.
     */
    @Test
    public void testGenerateAroundIntInt() {
        final int delta = 4;
        for (int i = 0; i < 1000; i++) {
            assertEquals((double) i, UtilRandom.generateAround(i, delta), delta);
        }
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilRandom#generateBetween(int, int)}.
     */
    @Test
    public void testGenerateBetweenIntInt() {
        final int low = 4;
        final int high = 80;
        for (int i = 0; i < 1000; i++) {
            final int r = UtilRandom.generateBetween(low, high);
            assertTrue(low <= r);
            // System.out.println(r);
            assertTrue(r <= high);
        }
    }

    @Test
    public void testGenerateWithPropability01() {
        for (int i = 0; i < 10000; i++) {
            final boolean r = UtilRandom.generateWithProbability(1);
            assertTrue(r);
        }
    }

    @Test
    public void testGenerateWithPropability02() {
        for (int i = 0; i < 10000; i++) {
            final boolean r = UtilRandom.generateWithProbability(0);
            assertFalse(r);
        }
    }

    @Ignore
    @Test
    public void testGenerateWithPropability03() {
        int happend = 0;
        double prob = 0.01;
        int multiplier = 1000;
        for (int i = 0; i < 100 * multiplier; i++) {
            final boolean r = UtilRandom.generateWithProbability(prob);
            if (r) {
                happend++;
            }
        }
        assertEquals(prob * 100, Math.round(happend / multiplier), 0);
    }

}
