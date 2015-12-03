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
package net.sf.kerner.utils;

import static org.junit.Assert.assertTrue;
import net.sf.kerner.utils.comparator.ComparatorDoubleDefault;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestComparatorDoubleDefault {

    private ComparatorDoubleDefault c;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        c = new ComparatorDoubleDefault();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testCompare01() {
        assertTrue(0 > c.compare(Double.valueOf(1), Double.valueOf(2)));
    }

    @Test
    public final void testCompare02() {
        assertTrue(0 < c.compare(Double.valueOf(3), Double.valueOf(0)));
    }

    @Test
    public final void testCompare03() {
        assertTrue(0 == c.compare(Double.valueOf(3), Double.valueOf(3)));
    }

}
