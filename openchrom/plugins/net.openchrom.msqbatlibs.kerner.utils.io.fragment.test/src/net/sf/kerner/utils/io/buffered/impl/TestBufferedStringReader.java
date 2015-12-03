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
package net.sf.kerner.utils.io.buffered.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.lazy.LazyStringReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-10-12
 */
public class TestBufferedStringReader {

    private String s;
    private StringReader sr;
    private BufferedStringReader reader;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        s = "test";
        sr = new StringReader(s);
        reader = new BufferedStringReader(sr);
    }

    @After
    public void tearDown() throws Exception {
        reader = null;
        sr = null;
        s = null;
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#BufferedStringReader(java.io.File)}
     * .
     * 
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public final void testBufferedStringReaderFile() throws IOException {
        new BufferedStringReader(new File("/dieses/file/kann/hoffentlich/nicht/angelegt/werden"));
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#BufferedStringReader(java.io.File)}
     * .
     * 
     * @throws IOException
     */
    @Test
    public final void testBufferedStringReaderFile01() throws IOException {
        new BufferedStringReader(new File("src/test/resources/seq.50m.txt"));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#BufferedStringReader(java.io.Reader)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testBufferedStringReaderReader() throws IOException {
        new BufferedStringReader(new StringReader("test"));
    }

    /**
     * Test method for
     * {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#BufferedStringReader(java.io.InputStream)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testBufferedStringReaderInputStream() throws IOException {
        new BufferedStringReader(new ByteArrayInputStream(new byte[2]));
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextString(int)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextStringInt() throws IOException {
        final String s2 = reader.nextString(1);
        reader.close();
        assertEquals(String.valueOf(s.charAt(0)), s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextString(int)} .
     * 
     * @throws IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNextStringInt01() throws IOException {
        reader.nextString(0);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextString(int)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextStringInt02() throws IOException {
        final String s2 = reader.nextString(4);
        reader.close();
        assertEquals(s, s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextString(int)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextStringInt03() throws IOException {
        final String s2 = reader.nextString(5);
        reader.close();
        assertEquals(s, s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextString(int)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextStringInt04() throws IOException {
        reader.nextString();
        assertNull(reader.nextString());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#close()}.
     */
    @Test
    public final void testClose() {
        reader.close();
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#close()}.
     * 
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public final void testClose01() throws IOException {
        reader.close();
        reader.nextChar();
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextChars(int)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextCharsInt() throws IOException {
        final String s2 = String.valueOf(reader.nextChars(5));
        reader.close();
        assertEquals(s, s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextChars(int)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextCharsInt01() throws IOException {
        final String s2 = String.valueOf(reader.nextChars(1));
        reader.close();
        assertEquals(String.valueOf(s.charAt(0)), s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextChars(int)} .
     * 
     * @throws IOException
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNextCharsInt02() throws IOException {
        reader.nextChars(0);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextChars(int)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextCharsInt03() throws IOException {
        final File file = new File("src/test/resources/seq.50m.txt");
        reader = new BufferedStringReader(file);
        char[] buffer;
        StringBuilder sb = new StringBuilder();
        while ((buffer = reader.nextChars(1000)) != null) {
            sb.append(buffer);
        }
        assertEquals(sb.length(), new LazyStringReader().read(file).length());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextChars(int)} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextCharsInt04() throws IOException {
        final File file = new File("src/test/resources/seq.100m.txt");
        reader = new BufferedStringReader(file);
        char[] buffer;
        StringBuilder sb = new StringBuilder();
        while ((buffer = reader.nextChars(1000)) != null) {
            sb.append(buffer);
        }
        assertEquals(sb.length(), new LazyStringReader().read(file).length());
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextChars()} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextChars() throws IOException {
        final String s2 = String.valueOf(reader.nextChars());
        reader.close();
        assertEquals(s, s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextLine()} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextLine() throws IOException {
        final String s2 = String.valueOf(reader.nextLine());
        reader.close();
        assertEquals(s, s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextLine()} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextLine01() throws IOException {
        final BufferedStringReader reader = new BufferedStringReader(new StringReader("aa" + UtilIO.NEW_LINE_STRING
                + "bb"));
        final String s2 = String.valueOf(reader.nextLine());
        reader.close();
        assertEquals("aa", s2);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextChar()} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextChar() throws IOException {
        char c = (char) reader.nextChar();
        reader.close();
        assertEquals(s.charAt(0), c);
    }

    /**
     * Test method for {@link net.sf.kerner.commons.io.buffered.BufferedStringReader#nextChar()} .
     * 
     * @throws IOException
     */
    @Test
    public final void testNextChar01() throws IOException {
        char c = (char) reader.nextChar();
        c = (char) reader.nextChar();
        reader.close();
        assertEquals(s.charAt(1), c);
    }

    @Test
    public final void exampleReadFromAReader() throws IOException {

        // Use BfferedStringReader to read a String from a java.io.Reader.

        final BufferedStringReader reader = new BufferedStringReader(new StringReader("abcd"));

        // use a buffer of 2 to read
        String string = reader.nextString(2);
        assertEquals("ab", string);

        // use a buffer of 2 to read
        string = reader.nextString(2);
        assertEquals("cd", string);
        reader.close();
    }

    @Test
    public final void exampleReadFromAFile() throws IOException {

        // Use BfferedStringReader to read a String from a file.

        final BufferedStringReader reader = new BufferedStringReader(new File("src/test/resources/smallTestFile.txt"));

        // use a buffer of 12 to read
        String string = reader.nextString(12);
        assertEquals("\"Hallo Welt\"", string);

        // use a default buffer size
        string = reader.nextString();
        assertEquals(" from a text file!", string);
        reader.close();
    }

    @Test
    public final void exampleReadFromAFileCharacterByCharacter() throws IOException {

        // Use BfferedStringReader to read a String from a java.io.Reader character by character.

        final String s = "abcd";
        final BufferedStringReader reader = new BufferedStringReader(new StringReader(s));

        int counter = 0;
        int nextChar;
        while ((nextChar = reader.nextChar()) != -1) {
            assertEquals(s.charAt(counter), (char) nextChar);
            counter++;
        }
        reader.close();
    }

}
