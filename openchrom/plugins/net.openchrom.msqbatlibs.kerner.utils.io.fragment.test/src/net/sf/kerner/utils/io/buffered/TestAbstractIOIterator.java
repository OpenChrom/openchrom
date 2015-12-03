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
package net.sf.kerner.utils.io.buffered;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.NoSuchElementException;

import net.sf.kerner.utils.io.buffered.impl.BufferedStringReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-03-13
 */
public class TestAbstractIOIterator {

    private static class My extends AbstractIOIterator<String> {

        protected final BufferedStringReader reader2 = new BufferedStringReader(super.reader);

        public My(Reader reader) throws IOException {
            super(reader);
        }

        @Override
        protected String doRead() throws IOException {
            String s = reader2.nextString();
            return s;
        }
    }

    private String in;
    private AbstractIOIterator<String> it;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        in = "input001";
        it = new My(new StringReader(in));
    }

    @After
    public void tearDown() throws Exception {
        it = null;
        in = null;
    }

    /**
     * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#hasNext()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testHasNext() throws IOException {
        assertTrue(it.hasNext());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#hasNext()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testHasNext01() throws IOException {
        it.next();
        assertFalse(it.hasNext());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#hasNext()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testHasNext02() throws IOException {
        in = "";
        it = new My(new StringReader(in));
        assertFalse(it.hasNext());
    }

    /**
     * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#next()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testNext() throws IOException {
        String s = it.next();
        assertEquals(in, s);
    }

    /**
     * Test method for {@link net.sf.kerner.utils.io.buffered.AbstractIOIterator#next()}.
     * 
     * @throws IOException
     */
    @Test(expected = NoSuchElementException.class)
    public final void testNext01() throws IOException {
        it.next();
        it.next();
    }

    @Test
    public final void testhasNext01() throws IOException {
        for (int i = 0; i < 100; i++) {
            assertTrue(it.hasNext());
        }
        it.next();
        assertFalse(it.hasNext());
    }

}
