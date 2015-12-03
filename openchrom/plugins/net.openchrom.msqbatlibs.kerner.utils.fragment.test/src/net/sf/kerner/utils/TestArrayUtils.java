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
package net.sf.kerner.utils;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-10-26
 */
public class TestArrayUtils {

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
    public final void testArrayTrimChar01() {
        char[] arr = new char[4];
        assertArrayEquals(new char[] { '\0', '\0', '\0' }, UtilArray.trim(arr, 3));
    }

    @Test
    public final void testArrayTrimChar02() {
        char[] arr = new char[] {};
        assertArrayEquals(new char[] {}, UtilArray.trim(arr, 3));
    }

    @Test
    public final void testArrayTrimByte01() {
        byte[] arr = new byte[] { 1, 2, 3, 4 };
        assertArrayEquals(new byte[] { 1, 2, 3 }, UtilArray.trim(arr, 3));

    }

    @Test
    public final void testArrayTrimByte02() {
        byte[] arr = new byte[4];
        assertArrayEquals(new byte[] { 0, 0, 0 }, UtilArray.trim(arr, 3));

    }

    @Test
    public final void testArrayTrimInt01() {
        int[] arr = new int[4];
        assertArrayEquals(new int[] { 0, 0, 0 }, UtilArray.trim(arr, 3));
    }

    @Test
    public final void testArrayTrimInt02() {
        int[] arr = new int[4];
        assertArrayEquals(new int[] {}, UtilArray.trim(arr, 0));
    }

    @Test
    public final void testArrayTrimInt03() {
        int[] arr = new int[4];
        assertArrayEquals(arr, UtilArray.trim(arr, 9));
    }

    @Test
    public final void testArrayTrimInt04() {
        int[] arr = new int[4];
        assertArrayEquals(new int[] {}, UtilArray.trim(arr, -1));
    }

}
