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

/**
 * 
 * {@code MutableTable} extends {@link Table} by mutability.
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
public interface TableMutable<T> extends Table<T> {

    /**
     * 
     * Add a row to this {@code MutableTable}.
     * 
     * @param row
     *            row to add
     * @param ident
     *            row identifier, may be {@code null}
     */
    void addRow(Row<T> row);

    /**
     * 
     * Add a row to this {@code MutableTable} at given index.
     * 
     * @param row
     *            row to add
     * @param ident
     *            row identifier, may be {@code null}
     * @param index
     *            index at which row is added
     * 
     * @throws IllegalArgumentException
     *             if {@code index < 0 || index > {@link #getRows().size()}
     */
    void addRow(int index, Row<T> row);

    /**
     * 
     * Add a column to this {@code MutableTable}.
     * 
     * @param column
     *            column to add
     * @param ident
     *            column identifier, may be {@code null}
     */
    void addColumn(Column<T> column);

    /**
     * 
     * Add a column to this {@code MutableTable} at given index.
     * 
     * @param column
     *            column to add
     * @param index
     *            index at which column is added
     * @param ident
     *            column identifier, may be {@code null}
     */
    void addColumn(int index, Column<T> column);

    /**
     * 
     * Replace row at given index with given row.
     * 
     * @param index
     *            index of row to be replaced
     * @param row
     *            new row at given index
     * @param ident
     *            row identifier, may be {@code null}
     */
    void setRow(int index, Row<T> row);

    /**
     * 
     * Replace column at given index with given column.
     * 
     * @param index
     *            index of column to be replaced
     * @param column
     *            new column at given index
     * @param ident
     *            column identifier, may be {@code null}
     */
    void setColumn(int index, Column<T> column);

    /**
     * 
     * Remove row at given index.
     * 
     * @param index
     *            index of row to be removed
     */
    void removeRow(int index);

    /**
     * 
     * Remove column at given index.
     * 
     * @param index
     *            index of column to be removed
     */
    void removeColumn(int index);

    /**
     * 
     * Set element at given row and column index.
     * 
     * @param i
     *            index of row
     * @param j
     *            index of column
     * @param element
     *            element to set
     */
    void set(int i, int j, T element);

    /**
     * 
     * Remove element at given row and column index.
     * 
     * @param i
     *            index of row
     * @param j
     *            index of column
     */
    void remove(int i, int j);

    /**
     * 
     * Set all elements that match {@code row index <= i} or
     * {@code column index <= i} to {@code element}.
     * 
     * @param i
     *            index to which elements are filled
     * @param element
     *            element that is used to fill rows and columns
     */
    void fill(int i, T element);

    /**
     * 
     * Set all elements that match {@code row index <= i} to {@code element}.
     * 
     * @param i
     *            index to which elements are filled
     * @param element
     *            element that is used to fill rows
     */
    void fillRows(int i, T element);

    /**
     * 
     * Set all elements that match {@code column index <= i} to {@code element}.
     * 
     * @param i
     *            index to which elements are filled
     * @param element
     *            element that is used to fill columns
     */
    void fillColumns(int i, T element);

    /**
     * 
     * Remove all elements from this {@code MutableTable}.
     * 
     */
    void clear();

}
