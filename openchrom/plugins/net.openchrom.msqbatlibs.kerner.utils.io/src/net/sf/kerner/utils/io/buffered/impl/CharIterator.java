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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.NoSuchElementException;

import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.AbstractBufferedReader;
import net.sf.kerner.utils.io.buffered.GeneralIOIterator;
import net.sf.kerner.utils.io.buffered.IOIterator;
import net.sf.kerner.utils.math.ArithmeticSavety;

/**
 * A {@code CharIterator} provides methods for reading characters in an iterator like manner.
 * <p>
 * A {@code CharIterator} is convenient to use, but brings worse performance compared to {@link BufferedStringReader}.
 * </p>
 * <p>
 * {@code CharIterator} is thread save.
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
 * @version 2010-10-25
 * @see java.util.Iterator
 * @see IOIterator
 * @see BufferedStringReader
 * @see BufferedReader
 */
public class CharIterator extends AbstractBufferedReader implements GeneralIOIterator {

    // Field //

    /**
	 * 
	 */
    private final boolean checkIndexOverflow;

    /**
     * Peek element.
     */
    protected volatile int peek = -1;

    /**
	 * 
	 */
    private volatile long currentIndex = 0L;

    private volatile boolean neu = true;

    /**
     * Create a new {@code CharIterator} from a {@link BufferedReader}.
     * <p>
     * Use this constructor to pass {@link java.io.Reader} that is used by different proxy classes.
     * </p>
     * 
     * @param reader
     *            {@link BufferedReader} to which reading is delegated
     * @throws IOException
     *             if anything goes wrong
     */
    public CharIterator(BufferedReader reader) throws IOException {
        super(reader);
        this.checkIndexOverflow = false;
    }

    /**
     * Create a new {@code CharIterator} from a {@link File}.
     * 
     * @param file
     *            file from which is read
     * @throws IOException
     *             if anything goes wrong
     */
    public CharIterator(File file) throws IOException {
        super(file);
        this.checkIndexOverflow = false;
    }

    /**
     * Create a new {@code CharIterator} from a {@link java.io.InputStream}.
     * 
     * @param stream
     *            stream from which is read
     * @throws IOException
     *             if anything goes wrong
     */
    public CharIterator(InputStream stream) throws IOException {
        super(stream);
        this.checkIndexOverflow = false;
    }

    /**
     * TODO description
     * 
     * @param reader
     * @throws IOException
     */
    public CharIterator(Reader reader) throws IOException {
        super(reader);
        this.checkIndexOverflow = false;
    }

    /**
     * TODO description
     * 
     * @param reader
     * @param checkIndexOverflow
     * @throws IOException
     */
    public CharIterator(Reader reader, boolean checkIndexOverflow) throws IOException {
        super(reader);
        this.checkIndexOverflow = checkIndexOverflow;
    }

    /**
     * TODO description
     * 
     * @param reader
     * @param checkIndexOverflow
     * @throws IOException
     */
    public CharIterator(BufferedReader reader, boolean checkIndexOverflow) throws IOException {
        super(reader);
        this.checkIndexOverflow = checkIndexOverflow;
    }

    /**
     * TODO description
     * 
     * @param file
     * @param checkIndexOverflow
     * @throws IOException
     */
    public CharIterator(File file, boolean checkIndexOverflow) throws IOException {
        super(file);
        this.checkIndexOverflow = checkIndexOverflow;
    }

    /**
     * TODO description
     * 
     * @param stream
     * @param checkIndexOverflow
     * @throws IOException
     */
    public CharIterator(InputStream stream, boolean checkIndexOverflow) throws IOException {
        super(stream);
        this.checkIndexOverflow = checkIndexOverflow;
    }

    // Public //

    /**
     * Read next single char if there is any left to read.
     * 
     * @return next char
     * @throws IOException
     *             if anything goes wrong
     * @throws NoSuchElementException
     *             if there is nothing left to read
     */
    public synchronized char nextChar() throws IOException {
        if (hasNext()) {
            try {
                final int result = peek;
                peek = super.reader.read();
                if (result == -1)
                    return nextChar();
                return (char) result;
            } finally {
                neu = false;
            }
        }
        throw new NoSuchElementException("no more elements");
    }

    /**
     * Read next bunch of chars if there are any left to read.
     * 
     * @return next chars
     * @throws IOException
     *             if anything goes wrong
     * @throws NoSuchElementException
     *             if there is nothing left to read
     */
    public char[] nextChars() throws IOException {
        return nextChars(UtilIO.DEFAULT_BUFFER);
    }

    /**
     * Read next bunch of chars if there are any left to read.
     * 
     * @return next chars
     * @throws IOException
     *             if anything goes wrong
     * @throws NoSuchElementException
     *             if there is nothing left to read
     */
    public synchronized char[] nextChars(int buffer) throws IOException {
        if (buffer < 1)
            throw new IllegalArgumentException("invalid buffer size");
        int cnt = 0;
        final StringBuilder result = new StringBuilder();
        while (hasNext() && cnt < buffer) {
            result.append(nextChar());
            cnt++;
        }
        if (checkIndexOverflow)
            ArithmeticSavety.addLong(currentIndex, cnt);
        else
            currentIndex += cnt;
        return result.toString().toCharArray();
    }

    /**
     * close this {@code CharIterator}.
     */
    public void close() {
        UtilIO.closeProperly(super.reader);
    }

    // Implement //

    /**
	 * 
	 */
    public synchronized boolean hasNext() {
        return (peek != -1 || neu);
    }

    /**
	 * 
	 */
    public synchronized long nextIndex() {
        if (checkIndexOverflow)
            return ArithmeticSavety.addLong(Long.valueOf(currentIndex), 1L);
        return currentIndex + 1;
    }

}
