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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.Writer;

import net.sf.kerner.utils.io.GenericWriter;
import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.impl.BufferedStringWriter;

/**
 * A {@code LazyStringWriter} provides the ability to write a string quickly to
 * <ul>
 * <li>a {@link java.io.File}</li>
 * <li>a {@link java.io.Writer}</li>
 * <li>an {@link java.io.OutputStream}</li>
 * </ul>
 * </p>
 * <p>
 * <b>Attention:</b> writing is not buffered! If you want to write large files,
 * consider to use {@link BufferedStringWriter} instead.
 * </p>
 * <p>
 * <b>Example:</b>
 * </p>
 * 
 * <pre>
 * &#064;Test
 * public final void example() throws IOException {
 *     final java.io.StringWriter wr = new java.io.StringWriter();
 *     new LazyStringWriter(&quot;Hallo Welt!&quot;).write(wr);
 *     assertEquals(&quot;Hallo Welt!&quot;, wr.toString());
 * }
 * </pre>
 * <p>
 * <b>Note:</b> It is not necessary to close a {@code LazyStringWriter}. This
 * will happen automatically.
 * </p>
 * 
 * @author Alexander Kerner
 * @see java.io.File
 * @see java.io.Writer
 * @see java.io.OutputStream
 * @version 2012-10-19
 */
public class LazyStringWriter implements GenericWriter {

    private final String string;

    /**
     * Creates a new {@code LazyStringWriter}, which will write {@code toString}
     * .
     * 
     * @param toString
     *            Object which will be written
     */
    public LazyStringWriter(final Object toString) {
        if (toString == null)
            throw new NullPointerException();
        string = toString.toString();
    }

    // Implement //

    public void write(final File file) throws IOException {
        write(new FileWriter(file));
    }

    public void write(final OutputStream stream) throws IOException {
        write(UtilIO.outputStreamToWriter(stream));
    }

    public void write(final Writer writer) throws IOException {
        StringReader reader = null;
        try {
            reader = new StringReader(string);
            UtilIO.readerToWriter(reader, writer);
        } finally {
            UtilIO.closeProperly(writer);
            UtilIO.closeProperly(reader);
        }
    }
}
