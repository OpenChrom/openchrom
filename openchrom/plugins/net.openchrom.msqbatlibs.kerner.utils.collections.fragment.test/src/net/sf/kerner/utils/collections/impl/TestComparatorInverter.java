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

import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import net.sf.kerner.utils.collections.ComparatorInverter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestComparatorInverter {

    private ComparatorInverter c;

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
    public final void testCompare01() {
        c = new ComparatorInverter<Integer>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        final int i = c.compare(Integer.valueOf(1), Integer.valueOf(2));
        // System.out.println(i);
        final boolean b = (i > 0);
        assertTrue(b);
    }

    @Test
    public final void testCompare02() {
        c = new ComparatorInverter<Integer>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        final int i = c.compare(Integer.valueOf(2), Integer.valueOf(1));
        // System.out.println(i);
        final boolean b = (i < 0);
        assertTrue(b);
    }

    @Test
    public final void testCompare03() {
        c = new ComparatorInverter<Integer>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        final int i = c.compare(Integer.valueOf(1), Integer.valueOf(1));
        // System.out.println(i);
        final boolean b = (i == 0);
        assertTrue(b);
    }

}
