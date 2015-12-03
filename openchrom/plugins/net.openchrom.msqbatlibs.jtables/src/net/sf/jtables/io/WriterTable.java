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

import java.io.Closeable;
import java.io.IOException;

import net.sf.jtables.table.Table;

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
 * last reviewed: 0000-00-00
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-02-28
 * 
 */
public interface WriterTable extends Closeable {

    /**
     * Writes {@link Table} using given column delimiter.
     * 
     * @param delimiter
     *            column delimiter that is used for writing
     * @param table
     *            {@link Table} that is written
     * @return {@code this}
     * @throws IOException
     */
    WriterTable write(String delimiter, Table<? extends Object> table) throws IOException;

    /**
     * Writes {@link Table}.
     * 
     * @param table
     *            {@link Table} that is written
     * @return {@code this}
     * @throws IOException
     */
    WriterTable write(Table<? extends Object> table) throws IOException;

}
