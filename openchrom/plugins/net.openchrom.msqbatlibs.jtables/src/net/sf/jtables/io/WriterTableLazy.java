/**********************************************************************
Copyright (c) 2009-2012 Alexander Kerner. All rights reserved.
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

import net.sf.jtables.table.Table;
import net.sf.kerner.utils.io.lazy.LazyStringWriter;

/**
 * 
 * 
 * A {@code TableWriter} will write a {@link net.sf.jtables.table.Table Table}
 * to
 * <ul>
 * <li>
 * a {@link java.io.File}</li>
 * <li>
 * a {@link java.io.Writer}</li>
 * <li>
 * an {@link java.io.OutputStream}</li>
 * </ul>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-03-12
 */
public class WriterTableLazy extends LazyStringWriter {

    /**
     * 
     * Construct a {@code TableWriter} that will write given
     * {@link net.sf.jtables.table.Table Table}.
     * 
     * @param table
     *            {@link net.sf.jtables.table.Table Table} to write
     */
    public WriterTableLazy(Table<?> table) {
        super(table.toString());
    }
}
