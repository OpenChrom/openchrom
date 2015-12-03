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
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestDoubleUnitGiga {

    private DoubleUnit u;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        u = DoubleUnit.GIGA;
    }

    @After
    public void tearDown() throws Exception {
        u = null;
    }

    @Test
    public final void testToPicos01() {
        assertEquals(1.0E+21, u.toPicos(1), 0);
    }

    @Test
    public final void testToNanos() {
        assertEquals(1.0E+18, u.toNanos(1), 0);
    }

    @Test
    public final void testToMicros01() {
        assertEquals(1.0E+15, u.toMicros(1), 0);
    }

    @Test
    public final void testToMillis01() {
        assertEquals(1.0E+12, u.toMillis(1), 0);
    }

    @Test
    public final void testToUnits01() {
        assertEquals(1.0E+9, u.toUnits(1), 0);
    }

    @Test
    public final void testToKilos01() {
        assertEquals(1.0E+6, u.toKilos(1), 0);
    }

    @Test
    public final void testToMegas01() {
        assertEquals(1.0E+3, u.toMegas(1), 0);
    }

    @Test
    public final void testToGigas01() {
        assertEquals(1, u.toGigas(1), 0);
    }

    @Test
    public final void testToTeras01() {
        assertEquals(1.0E-3, u.toTeras(1), 0);
    }

    @Ignore("delegate method")
    @Test
    public final void testConvert() {
        fail("Not yet implemented"); // TODO
    }

}
