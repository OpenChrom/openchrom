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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * <p>
 * A {@code GenericWriter} provides the ability to write something to
 * <ul>
 * <li>a {@link java.io.File File}</li>
 * <li>a {@link java.io.Writer Writer}</li>
 * <li>an {@link java.io.OutputStream OutputStream}</li>
 * </ul>
 * </p>
 * <p>
 * TODO example
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-19
 * @see java.io.File File
 * @see java.io.Writer Writer
 * @see java.io.OutputStream OutputStream
 */
public interface GenericWriter {

    /**
     * <p>
     * Write something to a {@link java.io.File File}.
     * </p>
     * 
     * @param file
     *            file to which is written
     * @throws IOException
     */
    void write(File file) throws IOException;

    /**
     * <p>
     * Write something to a {@link java.io.Writer Writer}.
     * </p>
     * 
     * @param writer
     *            writer to which is written
     * @throws IOException
     */
    void write(Writer writer) throws IOException;

    /**
     * <p>
     * Write something to an {@link java.io.OutputStream OutputStream}.
     * </p>
     * 
     * @param stream
     *            stream to which is written
     * @throws IOException
     */
    void write(OutputStream stream) throws IOException;

}
