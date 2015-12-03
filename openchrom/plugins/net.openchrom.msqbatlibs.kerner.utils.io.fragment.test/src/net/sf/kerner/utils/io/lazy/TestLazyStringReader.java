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
package net.sf.kerner.utils.io.lazy;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-22
 */
public class TestLazyStringReader {

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
     * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringReader#LazyStringReader()}.
     */
    @Test
    public final void testLazyStringReader() {
        new LazyStringReader();
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringReader#read(java.io.Reader)}
     * 
     * @throws IOException
     */
    @Test
    public final void testReadReader() throws IOException {
        final java.io.StringReader sr = new java.io.StringReader("test");
        final String s2 = new LazyStringReader().read(sr);
        assertEquals("test", s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringReader#read(java.io.Reader)}
     * 
     * @throws IOException
     */
    @Test(expected = NullPointerException.class)
    public final void testReadReader01() throws IOException {
        new LazyStringReader().read((Reader) null);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringReader#read(java.io.File)}
     * 
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public final void testReadFile() throws IOException {
        new LazyStringReader().read(new File("/dieses/file/kann/hoffentlich/nicht/angelegt/werden"));
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringReader#read(java.io.InputStream)}.
     * 
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public final void testReadInputStream() throws IOException {
        new LazyStringReader()
                .read(new FileInputStream(new File("/dieses/file/kann/hoffentlich/nicht/angelegt/werden")));
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringReader#read(java.io.InputStream)}.
     * 
     * @throws IOException
     */
    @Test(expected = NullPointerException.class)
    public final void testReadInputStream01() throws IOException {
        new LazyStringReader().read((InputStream) null);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.lazy.LazyStringReader#read(java.io.Reader)}
     * 
     * @throws IOException
     */
    @Test
    public final void example() throws IOException {
        final LazyStringReader reader = new LazyStringReader();
        final java.io.StringReader sr = new java.io.StringReader("Hallo Welt!");
        assertEquals("Hallo Welt!", reader.read(sr));
    }

}
