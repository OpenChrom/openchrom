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
package net.sf.jfasta.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.sf.jfasta.FASTAFile;
import net.sf.kerner.utils.exception.ExceptionFileFormat;
import net.sf.kerner.utils.io.UtilIO;
import net.sf.kerner.utils.io.buffered.impl.BufferedStringReader;

/**
 * 
 * TODO description
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2013-05-29
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-05-29
 * 
 */
class FASTAElementHeaderReader {

    /**
     * 
     * 
     * Helper method to work on "same" {@link BufferedReader}. <br>
     * {@link BufferedReader} is not closed after return.
     * 
     * 
     * @param reader
     *            {@link BufferedReader} to use
     * @return header string
     * @throws IOException
     *             if anything goes wrong
     * @throws ExceptionFileFormat
     *             if FASTA header could not be found
     */
    public String read(final BufferedReader reader) throws IOException {
        String s = reader.readLine();
        if (s == null)
            return null;
        s = s.trim();
        if (s.startsWith(Character.toString(FASTAFile.HEADER_IDENT)))
            return s.substring(1);
        throw new ExceptionFileFormat("failed to get header from " + s);
    }

    public String read(final File file) throws IOException {
        return read(UtilIO.getInputStreamFromFile(file));
    }

    public String read(final InputStream stream) throws IOException {
        return read(UtilIO.inputStreamToReader(stream));
    }

    public String read(final Reader reader) throws IOException {
        BufferedStringReader reader2 = null;
        try {
            reader2 = new BufferedStringReader(reader);
            String s = reader2.nextLine();
            if (s == null) {
                return null;
            }
            s = s.trim();
            if (s.startsWith(Character.toString(FASTAFile.HEADER_IDENT)))
                return s.substring(1);
            throw new ExceptionFileFormat("failed to get header from " + s);
        } finally {
            if (reader2 != null)
                reader2.close();
        }
    }
}
