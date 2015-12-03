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
/**
 * 
 */
package net.sf.kerner.utils.math;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-08-23
 */
public class TestMathUtils {

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#round(double, int)}.
     */
    @Test
    public final void testRound() {
        assertEquals(1.0, UtilMath.round(1.0123, 1), 0);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#round(double, int)}.
     */
    @Test
    public final void testRound01() {
        assertEquals(1.00, UtilMath.round(1.00123, 2), 0);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#round(double, int)}.
     */
    @Test
    public final void testRound02() {
        assertEquals(1.000, UtilMath.round(1.000123, 3), 0);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#round(double, int)}.
     */
    @Test
    public final void testRound03() {
        assertEquals(1.0000, UtilMath.round(1.0000123, 4), 0);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#round(double, int)}.
     */
    @Test
    public final void testRound04() {
        assertEquals(10.0000, UtilMath.round(10.0000123, 4), 0);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#round(double, int)}.
     */
    @Test
    public final void testRound05() {
        assertEquals(1000000.0000, UtilMath.round(1000000.0000123, 4), 0);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#round(double, int)}.
     */
    @Test
    public final void testRound06() {
        assertEquals(10000000.0000, UtilMath.round(10000000.0000123, 4), 0);
    }

    @Test
    public final void testMedian01() {
        assertEquals(0, UtilMath.getMedian(0), 0);
    }

    @Test
    public final void testMedian02() {
        assertEquals(1, UtilMath.getMedian(1), 0);
    }

    @Test
    public final void testMedian03() {
        assertEquals(2, UtilMath.getMedian(Arrays.asList(0, 1, 2, 2, 3, 4)), 0);
    }

    @Test
    public final void testMedian04() {
        assertEquals(2.5, UtilMath.getMedian(Arrays.asList(0, 1, 3, 2, 3, 4)), 0);
    }

    @Test
    public final void testMedian05() {
        assertEquals(2.55, UtilMath.getMedian(Arrays.asList(0, 1, 3.1, 2, 3.1, 4)), 0);
    }

    @Test
    public final void testMedian06() {
        assertEquals(26, UtilMath.getMedian(Arrays.asList(21, 26, 33)), 0);
    }

}
