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
package net.sf.jfasta.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import net.sf.kerner.utils.exception.ExceptionFileFormat;
import net.sf.kerner.utils.io.UtilIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-06
 * 
 */
public class TestFASTAElementHeaderReader {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    private FASTAElementHeaderReader reader;

    @Before
    public void setUp() throws Exception {
        reader = new FASTAElementHeaderReader();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for
     * {@link net.sf.jfasta.impl.FASTAElementHeaderReader#read(java.io.BufferedReader)}
     * .
     */
    @Test
    @Ignore
    public final void testReadBufferedReader() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jfasta.impl.FASTAElementHeaderReader#read(java.io.File)}.
     */
    @Test
    @Ignore
    public final void testReadFile() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jfasta.impl.FASTAElementHeaderReader#read(java.io.InputStream)}
     * .
     */
    @Test
    @Ignore
    public final void testReadInputStream() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.jfasta.impl.FASTAElementHeaderReader#read(java.io.Reader)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testReadReader() throws IOException {
        final String result = reader.read(new StringReader(">bla" + UtilIO.NEW_LINE_STRING));
        assertEquals("bla", result);
    }

    /**
     * Test method for
     * {@link net.sf.jfasta.impl.FASTAElementHeaderReader#read(java.io.Reader)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testReadReader01() throws IOException {
        final String result = reader.read(new StringReader(">bla"));
        assertEquals("bla", result);
    }

    /**
     * Test method for
     * {@link net.sf.jfasta.impl.FASTAElementHeaderReader#read(java.io.Reader)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testReadReader02() throws IOException {
        final String result = reader.read(new StringReader(">bla"));
        assertEquals("bla", result);
    }

    /**
     * Test method for
     * {@link net.sf.jfasta.impl.FASTAElementHeaderReader#read(java.io.Reader)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testReadReader03() throws IOException {
        assertNull(reader.read(new StringReader("")));
    }

    @Test(expected = ExceptionFileFormat.class)
    public final void testReadReader04() throws IOException {
        reader.read(new StringReader("bla"));
    }

}
