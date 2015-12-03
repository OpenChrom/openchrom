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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.sf.kerner.utils.UtilArray;
import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.CharReader;
import net.sf.kerner.utils.io.lazy.LazyStringReader;

/**
 * A {@code BufferedStringReader} provides the ability to read a string buffered from
 * <ul>
 * <li>a {@link java.io.File}</li>
 * <li>a {@link java.io.Writer}</li>
 * <li>an {@link java.io.InputStream}</li>
 * </ul>
 * <p>
 * <b>Note:</b> reading is buffered. If you want to read quickly small files in one piece, consider to use
 * {@link LazyStringReader} instead.
 * </p>
 * <p>
 * {@code BufferedStringReader} is thread save.
 * </p>
 * <p>
 * Performance of {@code BufferedStringReader} is near to optimal, but usage is less intuitive compared to
 * {@link CharIterator}.
 * </p>
 * <p>
 * Examples:
 * </p>
 * <p>
 * 
 * <pre>
 * &#064;Test
 * public void testBufferedReadingSpeed() throws IOException {
 *     final File in1 = new File(&quot;src/test/resources/seq.100m.txt&quot;);
 *     final StringBuilder sb = new StringBuilder();
 *     final BufferedStringReader reader1 = new BufferedStringReader(in1);
 *     long start = System.currentTimeMillis();
 *     char[] b = null;
 *     while ((b = reader1.nextChars()) != null) {
 *         sb.append(b);
 *     }
 *     reader1.close();
 *     long stop = System.currentTimeMillis();
 *     log.debug(BufferedStringReader.class.getSimpleName() + &quot; took &quot; + new TimePeriod(start, stop).getDuration() + &quot;ms&quot;);
 * }
 * </pre>
 * 
 * <pre>
 * &#064;Test
 * public void testBufferedReadingSpeed01() throws IOException {
 *     final File in1 = new File(&quot;src/test/resources/seq.100m.txt&quot;);
 *     final StringBuilder sb = new StringBuilder();
 *     final CharIterator reader1 = new CharIterator(in1);
 *     long start = System.currentTimeMillis();
 *     while (reader1.hasNext()) {
 *         sb.append(reader1.nextChars());
 *     }
 *     reader1.close();
 *     long stop = System.currentTimeMillis();
 *     log.debug(CharIterator.class.getSimpleName() + &quot; took &quot; + new TimePeriod(start, stop).getDuration() + &quot;ms&quot;);
 * }
 * </pre>
 * 
 * <pre>
 * &#064;Test
 * public void testBufferedReadingSpeed02() throws IOException {
 *     final File in1 = new File(&quot;src/test/resources/seq.100m.txt&quot;);
 *     final FileInputStream fstream = new FileInputStream(in1);
 *     final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
 *     final StringBuilder sb = new StringBuilder();
 *     long start = System.currentTimeMillis();
 *     String strLine;
 *     while ((strLine = br.readLine()) != null) {
 *         sb.append(strLine);
 *     }
 *     fstream.close();
 *     long stop = System.currentTimeMillis();
 *     log.debug(BufferedReader.class.getSimpleName() + &quot; took &quot; + new TimePeriod(start, stop).getDuration() + &quot;ms&quot;);
 * }
 * </pre>
 * 
 * </p>
 * <p>
 * Output:
 * 
 * <pre>
 *  net.sf.kerner.commons.io.buffered.TestPerformance - BufferedStringReader took 106ms
 *  net.sf.kerner.commons.io.buffered.TestPerformance - CharIterator took 916ms
 *  net.sf.kerner.commons.io.buffered.TestPerformance - BufferedReader took 150ms
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @see java.io.File
 * @see java.io.Reader
 * @see java.io.InputStream
 * @see CharReader
 * @version 2010-10-25
 */
public class BufferedStringReader implements Closeable, CharReader {

    // Fields //

    /**
     * {@link java.io.Reader} to which reading is delegated.
     */
    protected BufferedReader reader;

    // Constructor //

    /**
     * * Helper method to work on "same" BufferedReader.
     * 
     * @param reader
     */
    public BufferedStringReader(final BufferedReader reader) {
        synchronized (BufferedStringReader.class) {
            this.reader = reader;
        }
    }

    /**
     * <p>
     * Construct a new {@code BufferedStringReader} that will from a file.
     * </p>
     * 
     * @param file
     *            file that is read
     * @throws IOException
     *             if {@code file} is not accessible
     */
    public BufferedStringReader(final File file) throws IOException {
        synchronized (BufferedStringReader.class) {
            this.reader = new BufferedReader(UtilIO.inputStreamToReader(new FileInputStream(file)));
        }
    }

    /**
     * <p>
     * Construct a new {@code BufferedStringReader} that will read from a {@link java.io.InputStream}.
     * </p>
     * 
     * @param stream
     *            stream that is read
     */
    public BufferedStringReader(final InputStream stream) {
        synchronized (BufferedStringReader.class) {
            this.reader = new BufferedReader(UtilIO.inputStreamToReader(stream));
        }
    }

    /**
     * <p>
     * Construct a new {@code BufferedStringReader} that will read from a {@link java.io.Reader}.
     * </p>
     * 
     * @param reader
     *            reader that is read
     */
    public BufferedStringReader(final Reader reader) {
        synchronized (BufferedStringReader.class) {
            this.reader = new BufferedReader(reader, UtilIO.DEFAULT_BUFFER);
        }
    }

    // Public //

    /**
	 * 
	 */
    public synchronized void close() {
        UtilIO.closeProperly(reader);
    }

    /**
	 * 
	 */
    public synchronized int nextChar() throws IOException {
        return reader.read();
    }

    /**
	 * 
	 */
    public synchronized char[] nextChars() throws IOException {
        return nextChars(UtilIO.DEFAULT_BUFFER);
    }

    // Override //

    //

    // Implement //

    /**
	 * 
	 */
    public synchronized char[] nextChars(final int bufferSize) throws IOException {
        if (bufferSize < 1)
            throw new IllegalArgumentException();
        final char[] buffer = new char[bufferSize];
        final int read = reader.read(buffer);
        if (read == -1) {
            // nothing left to read
            return null;
        }
        return UtilArray.trim(buffer, read);
    }

    /**
     * Read a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return
     * ('\r'), or a carriage return followed immediately by a linefeed.
     * 
     * @return A String containing the contents of the line, not including any line-termination characters, or null if
     *         the end of the stream has been reached
     * @throws IOException
     * @see java.io.BufferedReader#readLine()
     */
    public synchronized String nextLine() throws IOException {
        return reader.readLine();
    }

    /**
     * <p>
     * Read and return the next string, using a default buffer size of {@link UtilIO#DEFAULT_BUFFER}.
     * </p>
     * <p>
     * The string that is read will have at most a length of {@link UtilIO#DEFAULT_BUFFER}.
     * </p>
     * <p>
     * If there is nothing (left) to read, {@code null} will be returned.
     * </p>
     * 
     * @return the string that was read
     * @throws IOException
     *             if anything goes wrong
     */
    public synchronized String nextString() throws IOException {
        return nextString(UtilIO.DEFAULT_BUFFER);
    }

    /**
     * <p>
     * Read and return the next string, using the given buffer size.
     * </p>
     * <p>
     * The string that is read will have at most a length of {@code bufferSize}.
     * </p>
     * <p>
     * Returns {@code null} if nothing left to read.
     * </p>
     * 
     * @param bufferSize
     *            size of buffer that is used for reading
     * @return the string that was read
     * @throws IOException
     *             if anything goes wrong
     */
    public synchronized String nextString(final int bufferSize) throws IOException {
        final char[] result = nextChars(bufferSize);
        if (result == null)
            return null;
        return String.valueOf(result);
    }

}
