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
package net.sf.kerner.utils.time;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-22
 */
public class TestTimePeriod {

    private TimePeriod tp;

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

    /**
     * Test method for
     * {@link net.sf.kerner.utils.time.TimePeriod#TimePeriod(long, long, java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public final void testTimePeriodLongLongTimeUnit() {
        tp = new TimePeriod(1, 2, TimeUnit.SECONDS);
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.time.TimePeriod#TimePeriod(long, long)}.
     */
    @Test
    public final void testTimePeriodLongLong() {
        tp = new TimePeriod(1, 2);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.time.TimePeriod#toString()}.
     */
    @Test
    public final void testToString() {
        tp = new TimePeriod(1, 2);
        assertEquals(Long.toString(tp.getDuration()), tp.toString());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.time.TimePeriod#getStart()}.
     */
    @Test
    public final void testGetStart() {
        tp = new TimePeriod(1, 2, TimeUnit.SECONDS);
        assertEquals(1, tp.getStart());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.time.TimePeriod#getStart()}.
     */
    @Test
    public final void testGetStart01() {
        tp = new TimePeriod(1, 2);
        assertEquals(1, tp.getStart());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.time.TimePeriod#getStop()}.
     */
    @Test
    public final void testGetStop() {
        tp = new TimePeriod(1, 2, TimeUnit.SECONDS);
        assertEquals(2, tp.getStop());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.time.TimePeriod#getStop()}.
     */
    @Test
    public final void testGetStop01() {
        tp = new TimePeriod(1, 2);
        assertEquals(2, tp.getStop());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.time.TimePeriod#getStart(java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public final void testGetStartTimeUnit() {
        tp = new TimePeriod(1, 2, TimeUnit.SECONDS);
        assertEquals(1000, tp.getStart(TimeUnit.MILLISECONDS));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.time.TimePeriod#getStop(java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public final void testGetStopTimeUnit() {
        tp = new TimePeriod(1, 2, TimeUnit.SECONDS);
        assertEquals(2000, tp.getStop(TimeUnit.MILLISECONDS));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.time.TimePeriod#getStop(java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public final void testGetStopTimeUnit01() {
        tp = new TimePeriod(1000, 2000);
        assertEquals(2, tp.getStop(TimeUnit.SECONDS));
    }

    /**
     * Test method for {@link net.sf.kerner.utils.time.TimePeriod#getDuration()}
     * .
     */
    @Test
    public final void testGetDuration() {
        tp = new TimePeriod(1, 2, TimeUnit.SECONDS);
        assertEquals(1, tp.getDuration());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.time.TimePeriod#getDuration()}
     * .
     */
    @Test
    public final void testGetDuration01() {
        tp = new TimePeriod(1, 2, TimeUnit.MILLISECONDS);
        assertEquals(1, tp.getDuration());
    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.time.TimePeriod#getDuration(java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public final void testGetDurationTimeUnit() {
        tp = new TimePeriod(1, 2, TimeUnit.SECONDS);
        assertEquals(1000, tp.getDuration(TimeUnit.MILLISECONDS));

    }

    /**
     * Test method for
     * {@link net.sf.kerner.utils.time.TimePeriod#getDuration(java.util.concurrent.TimeUnit)}
     * .
     */
    @Test
    public final void testGetDurationTimeUnit01() {
        tp = new TimePeriod(1, 2);
        assertEquals(1, tp.getDuration(TimeUnit.MILLISECONDS));

    }

}
