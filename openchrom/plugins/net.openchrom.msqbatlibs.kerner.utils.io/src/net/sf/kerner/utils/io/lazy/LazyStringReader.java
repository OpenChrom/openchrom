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
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;

import net.sf.kerner.utils.io.GenericReader;
import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.impl.BufferedStringReader;

/**
 * A {@code LazyStringReader} provides the ability to read a string quickly from
 * <ul>
 * <li>a {@link java.io.File}</li>
 * <li>a {@link java.io.Writer}</li>
 * <li>an {@link java.io.InputStream}</li>
 * </ul>
 * </p>
 * <p>
 * <b>Attention:</b> reading is not buffered! If you want to read large files, consider to use
 * {@link BufferedStringReader} instead.
 * </p>
 * <p>
 * <b>Example:</b>
 * 
 * <pre>
 * &#064;Test
 * public final void example() throws IOException {
 *     final java.io.StringReader sr = new java.io.StringReader(&quot;Hallo Welt!&quot;);
 *     assertEquals(&quot;Hallo Welt!&quot;, reader.read(sr));
 * }
 * </pre>
 * 
 * @author Alexander Kerner
 * @see java.io.File
 * @see java.io.Reader
 * @see java.io.InputStream
 * @version 2010-09-11
 */
public class LazyStringReader implements GenericReader<String> {

    public String read(File file) throws IOException {
        return read(UtilIO.getInputStreamFromFile(file));
    }

    public String read(Reader reader) throws IOException {
        if (reader == null)
            throw new NullPointerException();
        final StringWriter writer = new StringWriter();
        try {
            UtilIO.readerToWriter(reader, writer);
            return writer.toString();
        } finally {
            UtilIO.closeProperly(reader);
            UtilIO.closeProperly(writer);
        }
    }

    public String read(InputStream stream) throws IOException {
        if (stream == null)
            throw new NullPointerException();
        return read(UtilIO.inputStreamToReader(stream));
    }
}
