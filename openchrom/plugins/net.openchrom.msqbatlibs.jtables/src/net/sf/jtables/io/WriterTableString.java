/**********************************************************************
Copyright (c) 2013 Alexander Kerner. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ***********************************************************************/

package net.sf.jtables.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * 
 * Convenience class. Just extends {@link WriterTableBufferedImpl}.
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
 * last reviewed: 2013-02-28
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-02-28
 * 
 */
public class WriterTableString extends WriterTableBufferedImpl {

    /**
     * Creates a new {@code WriterTableString} that will write to given
     * {@link File}.
     * 
     * @param file
     *            {@link File} to write to
     * @throws IOException
     *             if file is not accessible for writing
     */
    public WriterTableString(final File file) throws IOException {
        super(file);
    }

    /**
     * Creates a new {@code WriterTableString} that will write to given
     * {@link OutputStream}.
     * 
     * @param stream
     *            {@link OutputStream} to write to
     * @throws IOException
     *             if stream is not accessible for writing
     */
    public WriterTableString(final OutputStream stream) throws IOException {
        super(stream);
    }

    /**
     * Creates a new {@code WriterTableString} that will write to given
     * {@link Writer}.
     * 
     * @param writer
     *            {@link Writer} to write to
     * @throws IOException
     *             if writer is not accessible for writing
     */
    public WriterTableString(final Writer writer) throws IOException {
        super(writer);
    }
}
