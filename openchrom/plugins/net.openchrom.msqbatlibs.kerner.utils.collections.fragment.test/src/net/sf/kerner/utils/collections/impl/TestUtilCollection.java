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
package net.sf.kerner.utils.collections.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.comparator.ComparatorIntegerDefault;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUtilCollection {

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

    @Test
    public final void testGetHighest01() {
        final List<Integer> input = Arrays.asList(1, 2, 1);
        assertEquals(Integer.valueOf(2), UtilCollection.getHighest(input, new ComparatorIntegerDefault()));
    }

    @Test
    public final void testGetHighest02() {
        final List<Integer> input = Arrays.asList(1, 2, 3);
        assertEquals(Integer.valueOf(3), UtilCollection.getHighest(input, new ComparatorIntegerDefault()));
    }

}
