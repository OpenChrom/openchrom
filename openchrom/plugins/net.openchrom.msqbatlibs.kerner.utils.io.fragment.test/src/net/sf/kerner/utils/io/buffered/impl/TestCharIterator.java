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
 *
 */
package net.sf.kerner.utils.io.buffered.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-10-25
 */
public class TestCharIterator {

    private CharIterator it;

    StringReader input;

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
        input = null;
        it = null;
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#CharIterator(java.io.BufferedReader)}.
     */
    @Test
    @Ignore
    public final void testCharIteratorBufferedReader() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#CharIterator(java.io.File)}.
     */
    @Test
    @Ignore
    public final void testCharIteratorFile() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#CharIterator(java.io.InputStream)}.
     */
    @Test
    @Ignore
    public final void testCharIteratorInputStream() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#CharIterator(java.io.Reader, boolean)}.
     */
    @Test
    @Ignore
    public final void testCharIteratorReaderBoolean() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.io.buffered.CharIterator#CharIterator(java.io.BufferedReader, boolean)}.
     */
    @Test
    @Ignore
    public final void testCharIteratorBufferedReaderBoolean() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#CharIterator(java.io.File, boolean)}.
     */
    @Test
    @Ignore
    public final void testCharIteratorFileBoolean() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#CharIterator(java.io.InputStream, boolean)}
     * .
     */
    @Test
    @Ignore
    public final void testCharIteratorInputStreamBoolean() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#CharIterator(java.io.Reader)}.
     */
    @Test
    @Ignore
    public final void testCharIteratorReader() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#hasNext()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testHasNext() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        assertTrue(it.hasNext());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextIndex()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testNextIndex() throws IOException {
        input = new StringReader("");
        it = new CharIterator(input);
        assertEquals(1, it.nextIndex());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextIndex()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testNextIndex01() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        assertEquals(1, it.nextIndex());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextChar()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testNextOne() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        assertTrue(it.hasNext());
        assertEquals('t', it.nextChar());
        assertEquals('e', it.nextChar());
        assertEquals('s', it.nextChar());
        assertEquals('t', it.nextChar());
        assertFalse(it.hasNext());
        it.close();
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextChar()}.
     * 
     * @throws IOException
     */
    @Test(expected = NoSuchElementException.class)
    public final void testNextOne01() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        assertTrue(it.hasNext());
        assertEquals('t', it.nextChar());
        assertEquals('e', it.nextChar());
        assertEquals('s', it.nextChar());
        assertEquals('t', it.nextChar());
        it.nextChar();
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextChar()}.
     * 
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public final void testNextOne02() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        it.close();
        it.nextChar();
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextChars()}.
     * 
     * @throws IOException
     */
    @Test
    public final void testNextBunch() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        assertTrue(it.hasNext());
        assertArrayEquals(new char[] { 't', 'e', 's', 't' }, it.nextChars());
        assertFalse(it.hasNext());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextChars(int)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testNextBunchInt() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        assertTrue(it.hasNext());
        assertArrayEquals(new char[] { 't', 'e', 's', 't' }, it.nextChars(4));
        assertFalse(it.hasNext());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextChars(int)}.
     * 
     * @throws IOException
     */
    @Test
    public final void testNextBunchInt01() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        assertTrue(it.hasNext());
        assertArrayEquals(new char[] { 't', 'e', 's' }, it.nextChars(3));
        assertTrue(it.hasNext());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.CharIterator#nextChars(int)}.
     * 
     * @throws IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNextBunchInt02() throws IOException {
        input = new StringReader("test");
        it = new CharIterator(input);
        it.nextChars(-1);
    }

}
