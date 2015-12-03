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

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.Table;

/**
 * 
 * A {@link Writer} to write {@link Table Tables} row by row.
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
 * last reviewed: 2013-02-26
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-02-26
 * 
 */
public interface WriterTableBuffered {

    /**
     * Writes given rows using default column delimiter.
     * 
     * @param rows
     *            Rows that should be written
     * @return {@code this} {@code WriterTableBuffered}
     * @throws IOException
     */
    WriterTableBuffered write(List<? extends Row<? extends Object>> rows) throws IOException;

    /**
     * Writes given rows using default column delimiter.
     * 
     * @param rows
     *            Rows that should be written
     * @return {@code this} {@code WriterTableBuffered}
     * @throws IOException
     */
    WriterTableBuffered write(Row<? extends Object>... rows) throws IOException;

    /**
     * Writes given row using default column delimiter.
     * 
     * @param row
     *            Row that should be written
     * @return {@code this} {@code WriterTableBuffered}
     * @throws IOException
     */
    WriterTableBuffered write(Row<?> row) throws IOException;

    /**
     * Writes given rows using given column delimiter.
     * 
     * @param rows
     *            Rows that should be written
     * @param delimiter
     *            column delimiter that should be used
     * @return {@code this} {@code WriterTableBuffered}
     * @throws IOException
     */
    WriterTableBuffered write(String delimiter, List<? extends Row<? extends Object>> rows) throws IOException;

    /**
     * Writes given rows using given column delimiter.
     * 
     * @param rows
     *            Rows that should be written
     * @param delimiter
     *            column delimiter that should be used
     * @return {@code this} {@code WriterTableBuffered}
     * @throws IOException
     */
    WriterTableBuffered write(String delimiter, Row<? extends Object> row) throws IOException;

    /**
     * Writes given row using given column delimiter.
     * 
     * @param row
     *            Row that should be written
     * @param delimiter
     *            column delimiter that should be used
     * @return {@code this} {@code WriterTableBuffered}
     * @throws IOException
     */
    WriterTableBuffered write(String delimiter, Row<? extends Object>... rows) throws IOException;

}
