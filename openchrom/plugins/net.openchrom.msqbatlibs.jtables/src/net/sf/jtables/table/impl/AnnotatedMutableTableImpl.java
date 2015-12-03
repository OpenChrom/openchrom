/**********************************************************************
Copyright (c) 2009-2013 Alexander Kerner. All rights reserved.
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
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import net.sf.jtables.table.Column;
import net.sf.jtables.table.Row;
import net.sf.jtables.table.Table;
import net.sf.jtables.table.TableAnnotated;
import net.sf.jtables.table.TableMutableAnnotated;
import net.sf.kerner.utils.collections.ObjectToIndexMapper;
import net.sf.kerner.utils.collections.ObjectToIndexMapperImpl;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.collections.list.UtilList;
import net.sf.kerner.utils.io.UtilIO;

/**
 *
 * Default implementation for {@link TableMutableAnnotated}.
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
 * last reviewed: 2013-02-27
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-02-27
 *
 * @param <T>
 *            type of elements in {@code Table}
 */
public class AnnotatedMutableTableImpl<T> extends MutableTableImpl<T> implements TableMutableAnnotated<T> {

    /**
     * row mappings.
     */
    protected volatile ObjectToIndexMapper<Object> rowMapper = new ObjectToIndexMapperImpl<Object>(
            new ArrayList<Object>());

    /**
     * column mappings.
     */
    protected volatile ObjectToIndexMapper<Object> colMapper = new ObjectToIndexMapperImpl<Object>(
            new ArrayList<Object>());

    /**
     * Creates an empty {@code AnnotatedMutableTableImpl}.
     */
    public AnnotatedMutableTableImpl() {
        super();
    }

    /**
     * Creates an {@code AnnotatedMutableTableImpl} with given rows.
     *
     * @param rows
     *            rows initially contained by this {@code Table}
     */
    public AnnotatedMutableTableImpl(final List<Row<T>> rows) {
        super(rows);
    }

    public AnnotatedMutableTableImpl(final Table<T> template) {
        super(template);
        if (template instanceof TableAnnotated) {
            setColumnIdentifier(((TableAnnotated) template).getColumnIdentifier());
            setRowIdentifier(((TableAnnotated) template).getRowIdentifier());
        }
    }

    public void addColumn(final Object id, final Column<T> row) {
        this.colMapper.addMapping(id);
        super.addColumn(row);
    }

    public void addColumn(final Object id, final Column<T> row, final int index) {
        this.colMapper.addMapping(id, index);
        super.addColumn(index, row);

    }

    public void addRow(final Object id, final Row<T> row) {
        this.rowMapper.addMapping(id);
        super.addRow(row);
    }

    public void addRow(final Object id, final Row<T> row, final int index) {
        this.rowMapper.addMapping(id, index);
        super.addRow(index, row);
    }

    protected void checkColumnIndex(final Object key) {
        if (colMapper.containsKey(key)) {
            // all good
        } else
            throw new NoSuchElementException("no element for column index [" + key + "]");
    }

    protected void checkRowIndex(final Object key) {
        if (rowMapper.containsKey(key)) {
            // all good
        } else
            throw new NoSuchElementException("no element for row index [" + key + "]");
    }

    @Override
    public synchronized Column<T> getColumn(final int index) {
        final ColumnImpl<T> r;
        final Column<T> c = super.getColumn(index);
        if (c instanceof ColumnImpl) {
            r = (ColumnImpl<T>) c;
        } else {
            r = new ColumnImpl<T>(super.getColumn(index));
        }
        if (UtilCollection.notNullNotEmpty(rowMapper.keys()))
            r.setIdentifier(rowMapper.keys());
        return r;
    }

    public Column<T> getColumn(final Object key) {
        net.sf.kerner.utils.Util.checkForNull(key);
        checkColumnIndex(key);
        return getColumn(colMapper.get(key));
    }

    public List<Object> getColumnIdentifier() {
        return new ArrayList<Object>(colMapper.keys());
    }

    @Override
    public Row<T> getRow(final int index) {
        final Row<T> r = super.getRow(index);
        if (UtilCollection.notNullNotEmpty(colMapper.keys()))
            r.setIdentifier(colMapper.keys());
        return r;
    }

    public Row<T> getRow(final Object key) {
        net.sf.kerner.utils.Util.checkForNull(key);
        checkRowIndex(key);
        return getRow(rowMapper.get(key));
    }

    /**
     *
     */

    public List<Object> getRowIdentifier() {
        return new ArrayList<Object>(rowMapper.keys());
    }

    public void setColumnIdentifier(final List<? extends Object> ids) {
        this.colMapper = new ObjectToIndexMapperImpl<Object>(ids);
    }

    public void setRowIdentifier(final List<? extends Object> ids) {
        this.rowMapper = new ObjectToIndexMapperImpl<Object>(ids);
    }

    public AnnotatedMutableTableImpl<T> sortByColumnIds() {
        final List<String> sorted = new ArrayList<String>(UtilList.toStringList(getColumnIdentifier()));
        Collections.sort(sorted);
        final AnnotatedMutableTableImpl<T> result = new AnnotatedMutableTableImpl<T>();
        result.setColumnIdentifier(sorted);
        result.setRowIdentifier(getRowIdentifier());
        for (final String s : sorted) {
            result.addColumn(this.getColumn(s));
        }
        return result;
    }

    @Override
    public String toString() {
        return toString("\t");
    }

    public String toString(final String delimiter) {

        // TODO maybe a little bit more complicated?!

        final StringBuilder sb = new StringBuilder();

        // print column indices
        if (getColumnIdentifier().isEmpty()) {
            // skip
        } else {
            final List<?> r = new ArrayList<Object>(getColumnIdentifier());
            final java.util.Iterator<?> it = r.iterator();

            if (getRowIdentifier().isEmpty()) {

            } else {
                sb.append(delimiter);
            }

            while (it.hasNext()) {
                sb.append(it.next());
                if (it.hasNext())
                    sb.append(delimiter);
            }
            sb.append(UtilIO.NEW_LINE_STRING);
        }

        final Iterator<? extends List<T>> rowIt = getRowIterator();
        final Iterator<?> identIt = getRowIdentifier().iterator();

        while (rowIt.hasNext() || identIt.hasNext()) {
            if (identIt.hasNext()) {
                sb.append(identIt.next());
                sb.append(delimiter);
            }
            if (rowIt.hasNext()) {
                final Iterator<?> ii = rowIt.next().iterator();
                while (ii.hasNext()) {
                    sb.append(ii.next());
                    if (ii.hasNext())
                        sb.append(delimiter);
                }

            }
            if (rowIt.hasNext() || identIt.hasNext())
                sb.append(UtilIO.NEW_LINE_STRING);
        }
        return sb.toString();
    }
}
