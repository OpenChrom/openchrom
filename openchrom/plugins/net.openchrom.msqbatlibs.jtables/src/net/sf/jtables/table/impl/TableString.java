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

package net.sf.jtables.table.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.jtables.table.Column;
import net.sf.jtables.table.Row;
import net.sf.kerner.utils.collections.list.UtilList;

/**
 *
 * An {@link net.sf.jtables.table.TableMutableAnnotated AnnotatedMutableTable}
 * with {@link java.lang.String String} elements.
 *
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-12-25
 *
 */
public class TableString extends AnnotatedMutableTableImpl<String> {

    public TableString() {
        super();
    }

    public TableString(final List<Row<String>> rows) {
        super(rows);
    }

    @Override
    public TableImpl<String> clone() throws CloneNotSupportedException {
        final List<Row<String>> rows = new ArrayList<Row<String>>();
        for (final Row<String> row : this.rows) {
            final Column<String> columns = new ColumnImpl<String>();
            for (final String element : row) {
                columns.add(element);
            }
            rows.add(columns);
        }
        return new TableString(rows);
    }

    public List<ColumnString> getColumns(final String idPattern) {
        final List<ColumnString> result = new ArrayList<ColumnString>();

        for (final Object s : getColumnIdentifier()) {
            if (s.toString().matches(idPattern)) {
                result.add((ColumnString) getColumn(s));
            }
        }

        return result;
    }

    public List<RowString> getRows(final String idPattern) {
        final List<RowString> result = new ArrayList<RowString>();

        for (final Object s : getRowIdentifier()) {
            if (s.toString().matches(idPattern)) {
                result.add((RowString) getRow(s));
            }
        }

        return result;
    }

    @Override
    public TableString sortByColumnIds() {
        final List<String> sorted = new ArrayList<String>(
                UtilList.toStringList(getColumnIdentifier()));
        Collections.sort(sorted);
        final TableString result = new TableString();
        result.setColumnIdentifier(sorted);
        result.setRowIdentifier(getRowIdentifier());
        for (final String s : sorted) {
            result.addColumn(this.getColumn(s));
        }
        return result;
    }

}
