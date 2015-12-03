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
package net.sf.jranges.range.integerrange.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-18
 *
 */
public class TestDummyIntegerRange {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private RangeIntegerDummy r1;

    private RangeIntegerDummy r2;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#compareTo(net.sf.kerner.RangeInteger.range.IntegerRange)}
     * .
     */
    @Test
    public final void testCompareTo() {
        r1 = new RangeIntegerDummy();
        r2 = new RangeIntegerDummy();
        assertEquals(0, r1.compareTo(r2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#compareTo(net.sf.kerner.RangeInteger.range.IntegerRange)}
     * .
     */
    @Test
    public final void testCompareTo01() {
        r1 = new RangeIntegerDummy(1, 1);
        r2 = new RangeIntegerDummy(1, 1);
        assertEquals(0, r1.compareTo(r2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#compareTo(net.sf.kerner.RangeInteger.range.IntegerRange)}
     * .
     */
    @Test
    public final void testCompareTo02() {
        r1 = new RangeIntegerDummy(1, 1);
        r2 = new RangeIntegerDummy(1, 2);
        assertEquals(0, r1.compareTo(r2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#compareTo(net.sf.kerner.RangeInteger.range.IntegerRange)}
     * .
     */
    @Test
    public final void testCompareTo03() {
        r1 = new RangeIntegerDummy(1, 1);
        r2 = new RangeIntegerDummy(2, 2);
        assertEquals(-1, r1.compareTo(r2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#compareTo(net.sf.kerner.RangeInteger.range.IntegerRange)}
     * .
     */
    @Test
    public final void testCompareTo04() {
        r1 = new RangeIntegerDummy(3, 1);
        r2 = new RangeIntegerDummy(2, 2);
        assertEquals(1, r1.compareTo(r2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#DummyRange()}
     * .
     */
    @Test
    public final void testDummyRange() {
        r1 = new RangeIntegerDummy();
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#DummyRange(int, int)}
     * .
     */
    @Test
    public final void testDummyRangeIntInt() {
        r1 = new RangeIntegerDummy(1, 2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#DummyRange(int, int)}
     * .
     */
    @Test
    public final void testDummyRangeIntInt01() {
        r1 = new RangeIntegerDummy(-1, 2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#DummyRange(int, int)}
     * .
     */
    @Test
    public final void testDummyRangeIntInt02() {
        r1 = new RangeIntegerDummy(-1, -2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#equals(java.lang.Object)}
     * .
     */
    @Test
    public final void testEqualsObject() {
        r1 = new RangeIntegerDummy();
        r2 = new RangeIntegerDummy();
        assertEquals(r1, r2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#equals(java.lang.Object)}
     * .
     */
    @Test
    public final void testEqualsObject01() {
        r1 = new RangeIntegerDummy(1, 1);
        r2 = new RangeIntegerDummy(1, 1);
        assertEquals(r1, r2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#equals(java.lang.Object)}
     * .
     */
    @Test
    public final void testEqualsObject02() {
        r1 = new RangeIntegerDummy(1, 1);
        r2 = new RangeIntegerDummy(1, 2);
        assertNotSame(r1, r2);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#expandRange(int)}
     * .
     */
    @Test
    public final void testExpandRangeInt() {
        r1 = new RangeIntegerDummy(2, 4);
        assertEquals(new RangeIntegerDummy(0, 6), r1.expandRange(2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#expandRange(int)}
     * .
     */
    @Test
    public final void testExpandRangeInt01() {
        r1 = new RangeIntegerDummy(2, 4);
        assertEquals(new RangeIntegerDummy(3, 3), r1.expandRange(-1));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#expandRange(int, boolean)}
     * .
     */
    @Test
    @Ignore("A DummyRange does not have limits")
    public final void testExpandRangeIntBoolean() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#getLength()}
     * .
     */
    @Test
    public final void testGetLength() {
        r1 = new RangeIntegerDummy(1, 2);
        assertEquals(2, r1.getLength());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#getLength()}
     * .
     */
    @Test
    public final void testGetLength01() {
        r1 = new RangeIntegerDummy(1, 20);
        assertEquals(20, r1.getLength());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#getStart()}.
     */
    @Test
    public final void testGetStart() {
        r1 = new RangeIntegerDummy(1, 1);
        assertEquals(1, r1.getStart());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#getStop()}.
     */
    @Test
    public final void testGetStop() {
        r1 = new RangeIntegerDummy(1, 2);
        assertEquals(2, r1.getStop());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#hashCode()}.
     */
    @Test
    public final void testHashCode() {
        r1 = new RangeIntegerDummy();
        r2 = new RangeIntegerDummy();
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#hashCode()}.
     */
    @Test
    public final void testHashCode01() {
        r1 = new RangeIntegerDummy(1, 2);
        r2 = new RangeIntegerDummy(1, 2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#hashCode()}.
     */
    @Test
    public final void testHashCode02() {
        r1 = new RangeIntegerDummy(1, 2);
        r2 = new RangeIntegerDummy(2, 2);
        assertNotSame(r1.hashCode(), r2.hashCode());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#includes(int)}
     * .
     */
    @Test
    public final void testIncludes() {
        r1 = new RangeIntegerDummy(1, 3);
        assertTrue(r1.includes(3));
        assertTrue(r1.includes(2));
        assertTrue(r1.includes(1));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#includes(int)}
     * .
     */
    @Test
    public final void testIncludes01() {
        r1 = new RangeIntegerDummy(3, 1);
        assertFalse(r1.includes(4));
        assertFalse(r1.includes(0));
        assertFalse(r1.includes(-1));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#setStart(int)}
     * .
     */
    @Test
    public final void testSetStart() {
        r1 = new RangeIntegerDummy(-1, -2);
        r1.setStart(1);
        assertEquals(1, r1.getStart());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#setStart(int)}
     * .
     */
    @Test
    public final void testSetStart01() {
        r1 = new RangeIntegerDummy(-1, -2);
        r1.setStart(-4);
        assertEquals(-4, r1.getStart());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#setStop(int)}
     * .
     */
    @Test
    public final void testSetStop() {
        r1 = new RangeIntegerDummy(-1, -2);
        r1.setStop(1);
        assertEquals(1, r1.getStop());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#shift(int)}.
     */
    @Test
    public final void testShift() {
        r1 = new RangeIntegerDummy(2, 4);
        assertEquals(new RangeIntegerDummy(4, 6), r1.shift(2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#shift(int)}.
     */
    @Test
    public final void testShift01() {
        r1 = new RangeIntegerDummy(2, 4);
        assertEquals(new RangeIntegerDummy(0, 2), r1.shift(-2));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.RangeIntegerDummy.range.impl.DummyRange#toString()}.
     */
    @Test
    @Ignore
    public final void testToString() {
        fail("Not yet implemented"); // TODO
    }

}
