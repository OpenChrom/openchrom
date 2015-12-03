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

package net.sf.jtables.table;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * A {@code Table} is a collection of elements that is organized in rows and
 * columns.<br>
 * A {@code Table} is immutable.
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
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-01-25
 * 
 * @param <T>
 *            type of elements in {@code Table}
 */
public interface Table<T> extends Cloneable, Iterable<T> {

    /**
     * 
     * Get row at given index.
     * 
     * @param index
     *            index of row to return
     * @return row at given index
     */
    Row<T> getRow(int index);

    /**
     * 
     * Get all rows.
     * 
     * @return all rows
     */
    List<Row<T>> getRows();

    /**
     * 
     * Get column at given index.
     * 
     * @param index
     *            index of column to return
     * @return column at given index
     */
    Column<T> getColumn(int index);

    /**
     * 
     * Get all columns.
     * 
     * @return all columns
     */
    List<Column<T>> getColumns();

    /**
     * 
     * Get element at given row and column index.
     * 
     * @param i
     *            index of row
     * @param j
     *            index of column
     * @return element at given row and column index
     */
    T get(int i, int j);

    /**
     * 
     * Get number of elements in row at given index.
     * 
     * @param index
     *            index of row
     * @return number of elements
     */
    int getRowSize(int index);

    /**
     * 
     * Get number of elements in column at given index.
     * 
     * @param index
     *            index of column
     * @return number of elements
     */
    int getColumnSize(int index);

    /**
     * 
     * Get number of elements in row that has the most elements.
     * 
     * @return number of elements
     */
    int getMaxRowSize();

    /**
     * 
     * Get number of elements in column that has the most elements.
     * 
     * @return number of elements
     */
    int getMaxColumnSize();

    /**
     * 
     * Get the number of rows.
     * 
     * @return number of rows
     */
    int getNumberOfRows();

    /**
     * 
     * Get the number of columns.
     * 
     * @return number of rows
     */
    int getNumberOfColumns();

    /**
     * 
     * Check weather this {@code Table} contains given element.
     * 
     * @param element
     *            element that is checked
     * @return {@code true}, if this {@code Table} contains given element;
     *         {@code false} otherwise
     */
    boolean contains(T element);

    /**
     * 
     * Retrieve a {@code List} view of all elements contained in this
     * {@code Table}.
     * 
     * @return a {@link List} that contains all elements
     */
    List<T> getAllElements();

    /**
     * 
     * Retrieve an {@link Iterator} over rows of this {@code Table}.
     * 
     * @return row iterator
     */
    Iterator<Row<T>> getRowIterator();

    /**
     * 
     * Retrieve an {@link java.util.Iterator Iterator} over columns of this
     * {@code Table}.
     * 
     * @return column iterator
     */
    Iterator<Column<T>> getColumnIterator();

}
