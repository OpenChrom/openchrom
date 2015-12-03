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
package net.sf.kerner.utils.io;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import net.sf.kerner.utils.Util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestFileUtils {

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
    public final void testDirCheck01() {
        assertTrue(UtilFile.dirCheck(Util.WORKING_DIR, false));
    }

    @Ignore
    @Test
    public final void testDirCheck02() {
        final File dir = new File("/mnt/autofs/exported-spectra");
        System.out.println(dir.canRead());
        System.out.println(dir.isDirectory());
        System.out.println(dir.length());

        assertTrue(UtilFile.dirCheck(new File("/mnt/autofs/exported-spectra"), false));
    }

    @Ignore
    @Test
    public final void testFileCheck01() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testDelete01() {
        fail("Not yet implemented"); // TODO
    }

    @Ignore
    @Test
    public final void testDeleteDir01() {
        fail("Not yet implemented"); // TODO
    }

}
