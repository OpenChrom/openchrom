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
package net.sf.kerner.utils.time;

import net.sf.kerner.utils.time.StopWatch.AlreadyRunningException;
import net.sf.kerner.utils.time.StopWatch.NotRunningException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestStopWatch {

    private StopWatch w;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        w = new StopWatch();
    }

    @After
    public void tearDown() throws Exception {
        w = null;
    }

    @Test
    public final void testStart01() {
        w.start();
    }

    @Test(expected = AlreadyRunningException.class)
    public final void testStart02() {
        w.start();
        w.start();
    }

    @Test(expected = NotRunningException.class)
    public final void testRound01() {
        w.round();
    }

    @Test
    public final void testRound02() {
        w.start();
        w.round();
    }

    @Test(expected = NotRunningException.class)
    public final void testStop01() {
        w.stop();
    }

    @Test
    public final void testStop02() {
        w.start();
        w.stop();
    }

}
