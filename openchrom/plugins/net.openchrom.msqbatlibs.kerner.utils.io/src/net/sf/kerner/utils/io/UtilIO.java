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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;

import net.sf.kerner.utils.UtilString;
import net.sf.kerner.utils.io.buffered.impl.BufferedStringReader;

/**
 * <p>
 * Utility class for commonly used Input/ Output operations.
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@googlemail.com">Alexander
 *         Kerner</a>
 * @version 2014-04-17
 */
public class UtilIO {

    /**
     * The JCL book specifies the default buffer size as 8K characters.
     */
    public static final int DEFAULT_BUFFER = 8192;

    /**
     *
     */
    public final static char NEW_LINE_CHAR = UtilString.NEW_LINE_STRING.charAt(0);

    /**
     *
     */
    public final static String NEW_LINE_STRING = UtilString.NEW_LINE_STRING;

    /**
     *
     */
    public final static char NULL_CHAR = '\u0000';

    /**
     *
     * @deprecated Use {@link CloserProperly} instead.
     */
    @Deprecated
    public static void closeProperly(final Closeable closable) {
        if (closable != null)
            try {
                closable.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }

    }

    public static <V> V deepCopy(final Class<V> c, final Serializable s) throws IOException, ClassNotFoundException {
        if (c == null || s == null)
            throw new NullPointerException();
        final ByteArrayOutputStream bs = new ByteArrayOutputStream();
        new ObjectOutputStream(bs).writeObject(s);
        final ByteArrayInputStream bi = new ByteArrayInputStream(bs.toByteArray());
        final V v = c.cast(new ObjectInputStream(bi).readObject());
        bs.close();
        bi.close();
        return v;
    }

    /**
     * Reads a file an returns an <code>BufferedInputStream</code> from it.
     *
     * @param file
     *            <code>File</code> from which the
     *            <code>BufferedInputStream</code> is created.
     * @return the <code>BufferedInputStream</code>.
     * @throws IOException
     */
    public static BufferedInputStream getBufferedInputStreamFromFile(final File file) throws IOException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    /**
     * Reads a file an returns an <code>BufferedOutputStream</code> from it.
     *
     * @param file
     *            <code>File</code> from which the
     *            <code>BufferedOutputStream</code> is created.
     * @return the <code>BufferedOutputStream</code>.
     * @throws IOException
     */
    public static BufferedOutputStream getBufferedOutputStreamForFile(final File file) throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    /**
     * Reads a file an returns an <code>InputStream</code> from it.
     *
     * @param file
     *            <code>File</code> from which the <code>InputStream</code> is
     *            created.
     * @return the <code>InputStream</code>.
     * @throws IOException
     */
    public static InputStream getInputStreamFromFile(final File file) throws IOException {
        return new FileInputStream(file);
    }

    public static long getNumberOfTotalLines(final File file) throws IOException {
        final InputStream is = new BufferedInputStream(new FileInputStream(file));
        try {
            final byte[] c = new byte[1024];
            long count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == UtilIO.NEW_LINE_CHAR) {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static long getOccource(final char c, final File file) throws IOException {
        return getOccource(c, new FileInputStream(file));
    }

    public static long getOccource(final char c, final InputStream stream) throws IOException {
        return getOccource(c, UtilIO.inputStreamToReader(stream));
    }

    public static long getOccource(final char c, final Reader reader) throws IOException {
        final BufferedStringReader r = new BufferedStringReader(reader);
        long result = 0;
        char[] line;
        while ((line = r.nextChars()) != null) {
            for (final char l : line) {
                if (l == c) {
                    result++;
                }
            }
        }
        ;
        r.close();
        return result;
    }

    /**
     * Reads a file an returns an <code>OutputStream</code> from it.
     *
     * @param file
     *            <code>File</code> from which the <code>OutputStream</code> is
     *            created.
     * @return the <code>OutputStream</code>.
     * @throws IOException
     */
    public static OutputStream getOutputStreamForFile(final File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    public static long InputStreamToFile(final InputStream stream, final File file) throws IOException {
        final FileWriter w = new FileWriter(file);
        final InputStreamReader r = new InputStreamReader(stream);
        final long result = readerToWriter(new InputStreamReader(stream), w);
        closeProperly(w);
        closeProperly(r);
        return result;
    }

    /**
     * Copies the content of an <code>InputStream</code> to an
     * <code>OutputStream</code>. Will flush the <code>OutputStream</code>, but
     * won't close <code>InputStream</code> or <code>OutputStream</code>.
     *
     * @param in
     *            <code>InputStream</code> from which data is read.
     * @param out
     *            <code>OutputStream</code> to which data is written.
     * @return number of bytes read/written.
     * @throws IOException
     */
    public static long inputStreamToOutputStream(final InputStream in, final OutputStream out) throws IOException {
        return inputStreamToOutputStream(in, out, DEFAULT_BUFFER);
    }

    /**
     * Copies the content of an <code>InputStream</code> to an
     * <code>OutputStream</code>. Will flush the <code>OutputStream</code>, but
     * won't close <code>InputStream</code> or <code>OutputStream</code>.
     *
     * @param in
     *            <code>InputStream</code> from which data is read.
     * @param out
     *            <code>OutputStream</code> to which data is written.
     * @param buffer
     *            the number of bytes to buffer reading.
     * @return number of bytes read/written.
     * @throws IOException
     */
    public static long inputStreamToOutputStream(final InputStream in, final OutputStream out, int buffer)
            throws IOException {
        if (buffer < 1)
            buffer = DEFAULT_BUFFER;
        final byte[] byteBuffer = new byte[buffer];
        long count = 0;
        int n = 0;
        while ((n = in.read(byteBuffer)) != -1) {
            out.write(byteBuffer, 0, n);
            count += n;
        }
        out.flush();
        return count;
    }

    /**
     * Copies the content of an <code>InputStream</code> to a
     * <code>Reader</code>. Will flush the <code>InputStream</code>, but won't
     * close <code>Reader</code> or <code>InputStream</code>.
     *
     * @param in
     *            <code>InputStream</code> from which data is read.
     * @return a new <code>Reader</code>
     * @throws IOException
     */
    public static Reader inputStreamToReader(final InputStream in) {
        return new InputStreamReader(in);
    }

    /**
     * Copies the content of an <code>InputStream</code> to a
     * <code>Writer</code>. Will flush the <code>Writer</code>, but won't close
     * <code>InputStream</code> or <code>Writer</code>.
     *
     * @param in
     *            <code>InputStream</code> from which data is read.
     * @param out
     *            <code>Writer</code> to which data is written.
     * @return number of bytes read/written.
     * @throws IOException
     */
    public static long inputStreamToWriter(final InputStream in, final Writer out) throws IOException {
        final InputStreamReader inr = new InputStreamReader(in);
        return readerToWriter(inr, out);
    }

    /**
     * Copies the content of an <code>InputStream</code> to a
     * <code>Writer</code>. Will flush the <code>Writer</code>, but won't close
     * <code>InputStream</code> or <code>Writer</code>.
     *
     * @param in
     *            <code>InputStream</code> from which data is read.
     * @param out
     *            <code>Writer</code> to which data is written.
     * @param buffer
     *            the number of bytes to buffer reading.
     * @return number of bytes read/written.
     * @throws IOException
     */
    public static long inputStreamToWriter(final InputStream in, final Writer out, final int buffer) throws IOException {
        final InputStreamReader inr = new InputStreamReader(in);
        return readerToWriter(inr, out, buffer);
    }

    /**
     * <p>
     * Write an {@code Object} that implements {@link Serializable} to a file.
     * </p>
     * <p>
     * Serialization is buffered: Internally a {@link BufferedOutputStream} is
     * used.
     * </p>
     *
     * @see Serializable
     * @param s
     *            {@code Object} that will be serialized
     * @param file
     *            file to write to
     * @throws IOException
     *             if anything goes wrong
     */
    public static void objectToFile(final Serializable s, final File file) throws IOException {
        if (s == null || file == null)
            throw new NullPointerException();
        objectToStream(s, new FileOutputStream(file));
    }

    /**
     * <p>
     * Write an {@code Object} that implements {@link Serializable} to an
     * {@link OutputStream}.
     * </p>
     * <p>
     * Serialization is buffered: Internally a {@link BufferedOutputStream} is
     * used.
     * </p>
     *
     * @see Serializable
     * @param s
     *            {@code Object} that will be serialized
     * @param stream
     *            stream to write to
     * @throws IOException
     *             if anything goes wrong
     */
    public static void objectToStream(final Serializable s, final OutputStream stream) throws IOException {
        if (s == null || stream == null)
            throw new NullPointerException();
        ObjectOutputStream outStream = null;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(stream);
            outStream = new ObjectOutputStream(bos);
            outStream.writeObject(s);
        } finally {
            if (outStream != null)
                outStream.close();
        }
    }

    /**
     * Copies the content of an <code>OutputStream</code> to a
     * <code>Reader</code>. Will flush the <code>OutputStream</code>, but won't
     * close <code>Reader</code> or <code>OutputStream</code>.
     *
     * @param out
     *            <code>OutputStream</code> from which data is read.
     * @param reader
     *            <code>Reader</code> to which data is written.
     * @return number of bytes read/written.
     * @throws IOException
     */
    public static long outputStreamToReader(final OutputStream out, final Reader reader) throws IOException {
        final OutputStreamWriter outw = new OutputStreamWriter(out);
        return readerToWriter(reader, outw);
    }

    /**
     * Copies the content of an <code>OutputStream</code> to a
     * <code>Writer</code>. Will flush the <code>OutputStream</code>, but won't
     * close <code>Writer</code> or <code>OutputStream</code>.
     *
     * @param out
     *            <code>OutputStream</code> from which data is read.
     * @return a new <code>Writer</code>
     * @throws IOException
     */
    public static Writer outputStreamToWriter(final OutputStream out) {
        return new OutputStreamWriter(out);
    }

    /**
     * Copies the content of a <code>Reader</code> to a <code>Writer</code>.
     * Will flush the <code>Writer</code>, but won't close <code>Reader</code>
     * or <code>Writer</code>.
     *
     * @param reader
     *            <code>Reader</code> from which data is read.
     * @param writer
     *            <code>Writer</code> to which data is written.
     * @return number of bytes read/ written.
     * @throws IOException
     */
    public static long readerToWriter(final Reader reader, final Writer writer) throws IOException {
        return readerToWriter(reader, writer, 0);
    }

    /**
     * Copies the content of a <code>Reader</code> to a <code>Writer</code>.
     * Will flush the <code>Writer</code>, but won't close <code>Reader</code>
     * or <code>Writer</code>.
     *
     * @param reader
     *            <code>Reader</code> from which data is read.
     * @param writer
     *            <code>Writer</code> to which data is written.
     * @param buffer
     *            the number of bytes to buffer reading.
     * @return number of bytes read/written.
     * @throws IOException
     */
    public static long readerToWriter(final Reader reader, final Writer writer, int buffer) throws IOException {
        if (buffer < 1)
            buffer = DEFAULT_BUFFER;
        final char[] charBuffer = new char[buffer];
        long count = 0;
        int n = 0;
        while ((n = reader.read(charBuffer)) != -1) {
            writer.write(charBuffer, 0, n);
            count += n;
        }
        writer.flush();
        return count;
    }

    /**
     * TODO description
     */
    private UtilIO() {

    }
}
