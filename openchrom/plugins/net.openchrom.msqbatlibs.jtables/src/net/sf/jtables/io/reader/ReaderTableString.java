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

package net.sf.jtables.io.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.sf.jtables.table.impl.RowImpl;
import net.sf.jtables.table.impl.RowString;
import net.sf.jtables.table.impl.TableString;

/**
 *
 * {@link ReaderTable} to read a table that contains {@link String} values.
 *
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-03-13
 *
 */
public class ReaderTableString extends ReaderTableAbstract<String> {

    public ReaderTableString(final BufferedReader reader, final boolean columnIds,
            final boolean rowIds) throws IOException {
        super(reader, columnIds, rowIds);
    }

    public ReaderTableString(final BufferedReader reader, final boolean columnIds,
            final boolean rowIds, final String delim) throws IOException {
        super(reader, columnIds, rowIds, delim);
    }

    public ReaderTableString(final File file) throws IOException {
        super(file);
    }

    public ReaderTableString(final File file, final boolean columnIds, final boolean rowIds)
            throws IOException {
        super(file, columnIds, rowIds);
    }

    public ReaderTableString(final File file, final boolean columnIds, final boolean rowIds,
            final String delim) throws IOException {
        super(file, columnIds, rowIds, delim);
    }

    public ReaderTableString(final InputStream stream, final boolean columnIds, final boolean rowIds)
            throws IOException {
        super(stream, columnIds, rowIds);
    }

    public ReaderTableString(final InputStream stream, final boolean columnIds,
            final boolean rowIds, final String delim) throws IOException {
        super(stream, columnIds, rowIds, delim);
    }

    public ReaderTableString(final Reader reader, final boolean columnIds, final boolean rowIds)
            throws IOException {
        super(reader, columnIds, rowIds);
    }

    public ReaderTableString(final Reader reader, final boolean columnIds, final boolean rowIds,
            final String delim) throws IOException {
        super(reader, columnIds, rowIds, delim);
    }

    @Override
    protected TableString getInstance() {
        return new TableString();
    }

    @Override
    protected RowImpl<String> getNewRowInstance() {
        return new RowString();
    }

    @Override
    protected String parse(final String s) {
        return s;
    }

    @Override
    public TableString readTableAtOnce() throws IOException {
        return (TableString) super.readTableAtOnce();
    }
}
