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
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-10-25
 */
public class TestMathUtils2 {

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

    @Test(expected = IllegalArgumentException.class)
    public final void testMin01() {
        UtilMath.getMin(new double[] {});
    }

    @Test
    public final void testMin02() {
        assertEquals(2, UtilMath.getMin(new double[] { 2, 3, 4 }), 0);
    }

    @Test
    public final void testMin03() {
        assertEquals(0, UtilMath.getMin(new double[] { 0, 1 }), 0);
    }

    @Test
    public final void testMin04() {
        assertEquals(-1, UtilMath.getMin(new double[] { 0, -1 }), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testMax01() {
        UtilMath.getMax(new double[] {});
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public final void testMax02() {
        // MathUtils.max(null);
    }

    @Test
    public final void testMax03() {
        assertEquals(1, UtilMath.getMax(new double[] { 0, 1 }), 0);
    }

    @Test
    public final void testMax04() {
        assertEquals(0, UtilMath.getMax(new double[] { 0, -1 }), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testMean01() {
        UtilMath.getMean(new double[] {});
    }

    @Test
    public final void testMean03() {
        assertEquals(0.5, UtilMath.getMean(new double[] { 0, 1 }), 0);
    }

    @Test
    public final void testMean04() {
        assertEquals(-1, UtilMath.getMean(new double[] { -1, -1 }), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testSum01() {
        UtilMath.sum(new double[] {});
    }

    @Test
    public final void testSum03() {
        assertEquals(1, UtilMath.sum(new double[] { 0, 1 }), 0);
    }

    @Test
    public final void testSum04() {
        assertEquals(-2, UtilMath.sum(new double[] { -1, -1 }), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testStdDev01() {
        UtilMath.getStdDev(new double[] {});
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#increment(java.lang.Integer)}.
     */
    @Ignore
    @Test
    public final void testIncrementInteger() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#increment(java.lang.Integer, int)}
     * .
     */
    @Ignore
    @Test
    public final void testIncrementIntegerInt() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.math.UtilMath#add(java.lang.Integer, java.lang.Integer)}
     * .
     */
    @Ignore
    @Test
    public final void testAdd() {
        fail("Not yet implemented"); // TODO
    }

}
