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
package net.sf.kerner.utils.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.kerner.utils.UtilString;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestUtilString {

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
    public final void testEmptyString01() {
        assertTrue(UtilString.emptyString(null));
    }

    @Test
    public final void testEmptyString02() {
        assertTrue(UtilString.emptyString(""));
    }

    @Test
    public final void testEmptyString03() {
        assertTrue(UtilString.emptyString("  "));
    }

    @Test
    public final void testEmptyString04() {
        assertFalse(UtilString.emptyString("e"));
    }

    @Ignore
    @Test
    public final void testReplaceAllNewLine() {
        fail("Not yet implemented");
    }

    @Test
    public final void testTrimmMultiWhiteSpaceToOne01() {
        final String input = "  x  ";
        assertEquals("x", UtilString.trimAndReduceWhiteSpace(input));
    }

    @Test
    public final void testTrimmMultiWhiteSpaceToOne02() {
        final String input = "  x\t\t";
        assertEquals("x", UtilString.trimAndReduceWhiteSpace(input));
    }

    @Test
    public final void testTrimmMultiWhiteSpaceToOne03() {
        final String input = "  x\t";
        assertEquals("x", UtilString.trimAndReduceWhiteSpace(input));
    }

    @Test
    public final void testTrimmMultiWhiteSpaceToOne04() {
        final String input = "  1   2   3  ";
        assertEquals("1 2 3", UtilString.trimAndReduceWhiteSpace(input));
    }

    @Test
    public final void testTrimmMultiWhiteSpaceToOne05() {
        final String input = "  1\t2\t3  ";
        assertEquals("1 2 3", UtilString.trimAndReduceWhiteSpace(input));
    }

    @Test
    public final void testTrimmMultiWhiteSpaceToOne06() {
        final String input = "  1\t\t2\t\t3  ";
        assertEquals("1 2 3", UtilString.trimAndReduceWhiteSpace(input));
    }

}
