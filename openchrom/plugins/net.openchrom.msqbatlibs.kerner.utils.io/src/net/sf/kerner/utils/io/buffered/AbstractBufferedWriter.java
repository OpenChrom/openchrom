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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import net.sf.kerner.utils.io.UtilFile;
import net.sf.kerner.utils.io.UtilIO;

/**
 * An {@code AbstractBufferedWriter} provides a prototype implementation for {@link GenericBufferedWriter} with
 * convenient constructor methods to instantiate this writer.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-27
 * @see java.io.File File
 * @see java.io.Writer Writer
 * @see java.io.OutputStream OutputStream
 */
public abstract class AbstractBufferedWriter implements GenericBufferedWriter {

    /**
     * {@link java.io.Writer} to which writing is delegated.
     */
    protected BufferedWriter writer;

    /**
     * Create a new {@code AbstractBufferedWriter} that will write from given file.
     * 
     * @param file
     *            file to write to
     * @throws IOException
     *             if opening file for writing fails
     */
    public AbstractBufferedWriter(final File file) throws IOException {
        synchronized (AbstractBufferedWriter.class) {
            UtilFile.fileCheck(file, true);
            writer = new BufferedWriter(new FileWriter(file));
        }
    }

    /**
     * Create a new {@code AbstractBufferedWriter} that will write to given OutputStream.
     * 
     * @param stream
     *            OutputStream to write to
     */
    public AbstractBufferedWriter(final OutputStream stream) {
        synchronized (AbstractBufferedWriter.class) {
            writer = new BufferedWriter(UtilIO.outputStreamToWriter(stream));
        }
    }

    /**
     * Create a new {@code AbstractBufferedWriter} that will write to given writer.
     * 
     * @param writer
     *            writer to write to
     */
    public AbstractBufferedWriter(final Writer writer) {
        synchronized (AbstractBufferedWriter.class) {
            this.writer = new BufferedWriter(writer);
        }
    }

    /**
     * Close this {@code AbstractBufferedWriter}.
     */
    public void close() {
        synchronized (writer) {
            UtilIO.closeProperly(writer);
        }
    }

    /**
     * Flush this {@code AbstractBufferedWriter}.
     */
    public void flush() throws IOException {
        synchronized (writer) {
            writer.flush();
        }
    }

}
