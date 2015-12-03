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
package net.sf.kerner.utils.io.buffered;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;

import net.sf.kerner.utils.io.UtilIO;

/**
 * An {@code AbstractBufferedReader} provides a {@link BufferedReader} instance for buffered reading as well as
 * convenient constructor methods to instantiate this reader.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-27
 */
public abstract class AbstractBufferedReader {

    /**
     * {@link java.io.Reader} to which reading is delegated.
     */
    protected BufferedReader reader;

    /**
     * Create a new {@code AbstractBufferedReader} from a {@link java.io.BufferedReader}.
     * <p>
     * <b>Note:</b> Passed in {@code java.io.BufferedReader} is kept as a reference. Use this constructor if you want to
     * work on same {@link java.io.BufferedReader} with more than one reading-proxies.
     * </p>
     * 
     * @param reader
     *            buffered reader from which is read
     * @see java.io.BufferedReader
     */
    public AbstractBufferedReader(final BufferedReader reader) {
        synchronized (AbstractBufferedReader.class) {
            this.reader = reader;
        }
    }

    /**
     * Create a new {@code AbstractBufferedReader} from a file.
     * 
     * @param file
     *            file from which is read
     * @throws FileNotFoundException
     * @see java.io.File
     */
    public AbstractBufferedReader(final File file) throws FileNotFoundException {
        synchronized (AbstractBufferedReader.class) {
            this.reader = new BufferedReader(UtilIO.inputStreamToReader(new FileInputStream(file)));
        }
    }

    /**
     * Create a new {@code AbstractBufferedReader} from a {@link java.io.InputStream} .
     * 
     * @param stream
     *            stream from which is read
     * @see java.io.InputStream
     */
    public AbstractBufferedReader(final InputStream stream) {
        synchronized (AbstractBufferedReader.class) {
            this.reader = new BufferedReader(UtilIO.inputStreamToReader(stream));
        }
    }

    /**
     * Create a new {@code AbstractBufferedReader} from a {@link java.io.Reader} .
     * 
     * @param reader
     *            reader from which is read
     * @see java.io.Reader
     */
    public AbstractBufferedReader(final Reader reader) {
        synchronized (AbstractBufferedReader.class) {
            this.reader = new BufferedReader(reader);
        }
    }

    /**
     * Close this {@code AbstractBufferedReader}.
     */
    public void close() {
        synchronized (reader) {
            UtilIO.closeProperly(reader);
        }
    }

}
