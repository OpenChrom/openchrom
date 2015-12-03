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
package net.sf.kerner.utils.collections.list.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import net.sf.kerner.utils.collections.list.UtilList;
import net.sf.kerner.utils.pair.PairSame;
import net.sf.kerner.utils.pair.PairSameImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestUtilList {

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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public final void testAllAgainstAll01() {
        final List<Integer> list = Arrays.asList(1, 2, 3);
        final List<PairSame<Integer>> result = UtilList.allAgainstAll(list);
        assertEquals(3, result.size());
        assertEquals(new PairSameImpl(1, 2), result.get(0));
        assertEquals(new PairSameImpl(1, 3), result.get(1));
        assertEquals(new PairSameImpl(2, 3), result.get(2));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public final void testAllAgainstAll02() {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4);
        final List<PairSame<Integer>> result = UtilList.allAgainstAll(list);
        assertEquals(6, result.size());
        assertEquals(new PairSameImpl(1, 2), result.get(0));
        assertEquals(new PairSameImpl(1, 3), result.get(1));
        assertEquals(new PairSameImpl(1, 4), result.get(2));
        assertEquals(new PairSameImpl(2, 3), result.get(3));
        assertEquals(new PairSameImpl(2, 4), result.get(4));
        assertEquals(new PairSameImpl(3, 4), result.get(5));
    }

}
