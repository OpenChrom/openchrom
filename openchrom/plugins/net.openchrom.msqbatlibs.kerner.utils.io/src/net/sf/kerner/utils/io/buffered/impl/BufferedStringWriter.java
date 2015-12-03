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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import net.sf.kerner.utils.io.LineWriter;
import net.sf.kerner.utils.io.ObjectWriter;
import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.AbstractBufferedWriter;
import net.sf.kerner.utils.io.buffered.CharWriter;

/**
 * A {@code BufferedStringWriter} provides the ability to write a string buffered to
 * <ul>
 * <li>a {@link java.io.File}</li>
 * <li>a {@link java.io.Writer}</li>
 * <li>an {@link java.io.OutputStream}</li>
 * </ul>
 * </p>
 * <p>
 * <b>Note:</b> writing is buffered. If you want to write quickly small files at once, consider to use
 * {@link net.sf.kerner.utils.io.lazy.LazyStringWriter LazyStringWriter} instead.
 * </p>
 * <p>
 * <b>Example:</b>
 * </p>
 * 
 * <pre>
 * &#064;Test
 * public final void exampleWriteStringToAWriter() throws IOException {
 * 
 *     // Use BufferedStringWriter to write a string buffered to a java.io.Writer
 * 
 *     StringWriter sw = new StringWriter();
 *     final BufferedStringWriter writer = new BufferedStringWriter(sw);
 * 
 *     // write first two characters
 *     writer.write(&quot;ab&quot;);
 * 
 *     // flush the writer (it's buffer)
 *     writer.flush();
 * 
 *     assertEquals(&quot;ab&quot;, sw.toString());
 *     writer.write(&quot;cd&quot;);
 * 
 *     // closing the writer will automatically flush it's buffer
 *     writer.close();
 *     assertEquals(&quot;abcd&quot;, sw.toString());
 * }
 * </pre>
 * 
 * <pre>
 * &#064;Test
 * public final void exampleWriteStringToAFile() throws IOException {
 * 
 *     // Use BufferedStringWriter to write a string buffered to a file
 * 
 *     final File file = new File(&quot;src/test/resources/smallTestFileWrite.txt&quot;);
 * 
 *     final BufferedStringWriter writer = new BufferedStringWriter(file);
 * 
 *     // write first two characters
 *     writer.write(&quot;ab&quot;);
 * 
 *     // flush the writer (it's buffer)
 *     writer.flush();
 * 
 *     assertEquals(&quot;ab&quot;, new LazyStringReader().read(file));
 *     writer.write(&quot;cd&quot;);
 * 
 *     // closing the writer will automatically flush it's buffer
 *     writer.close();
 *     assertEquals(&quot;abcd&quot;, new LazyStringReader().read(file));
 * }
 * </pre>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-09-13
 * @see java.io.File
 * @see java.io.Writer
 * @see java.io.OutputStream
 */
public class BufferedStringWriter extends AbstractBufferedWriter implements CharWriter, LineWriter,
        ObjectWriter<String> {

    // Constructor //

    /**
     * <p>
     * Construct a new {@code BufferedStringWriter} that will write a String to a file.
     * </p>
     * 
     * @param file
     *            file to which is written
     * @throws IOException
     *             if {@code file} is not accessible
     */
    public BufferedStringWriter(File file) throws IOException {
        super(file);
    }

    /**
     * <p>
     * Construct a new {@code BufferedStringWriter} that will write a String to a {@link java.io.Writer Writer}.
     * </p>
     * 
     * @param writer
     *            writer to which is written
     */
    public BufferedStringWriter(Writer writer) {
        super(writer);
    }

    /**
     * <p>
     * Construct a new {@code BufferedStringWriter} that will write a String to a {@link java.io.OutputStream
     * OutputStream}.
     * </p>
     * 
     * @param stream
     *            stream to which is written
     * @throws IOException
     */
    public BufferedStringWriter(OutputStream stream) {
        super(stream);
    }

    // Public //

    /**
     * Write the next string.
     * 
     * @param string
     *            string that is written
     * @throws IOException
     *             if anything goes wrong
     */
    public synchronized void write(String string) throws IOException {
        writer.write(string);
    }

    // Implement //

    /**
     * Write the next string and append a {@link UtilIO#NEW_LINE_STRING}.
     * 
     * @param line
     *            line that is written
     * @throws IOException
     *             if anything goes wrong
     */
    public synchronized void writeNextLine(String line) throws IOException {
        writer.write(line);
        writer.write(UtilIO.NEW_LINE_STRING);
    }

    /**
     * Write a single character.
     * 
     * @param c
     *            character that is written
     * @throws IOException
     *             if anything goes wrong
     */
    public synchronized void write(char c) throws IOException {
        writer.write(c);
    }

    /**
     * Write a an array of characters.
     * 
     * @param chars
     *            array of characters that is written
     * @throws IOException
     *             if anything goes wrong
     */
    public synchronized void write(char[] chars) throws IOException {
        writer.write(chars);
    }

}
