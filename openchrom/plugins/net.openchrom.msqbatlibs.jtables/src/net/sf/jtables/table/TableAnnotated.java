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

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * A {@code AnnotatedTable} extends {@link Table} by providing object
 * identifiers for rows and columns. <br>
 * This makes it possible to access rows and columns not only by their integer
 * index, but also by an additional object mapping.
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
public interface TableAnnotated<T> extends Table<T> {

    /**
     * 
     * Retrieve column that is associated to given object key.
     * 
     * @param key
     *            identifier that maps to returned column
     * @return column that is mapped by given identifier
     * @throws NoSuchElementException
     *             if there is no column mapped by given identifier
     */
    Column<T> getColumn(Object key) throws NoSuchElementException;

    /**
     * 
     * Retrieve a {@link List} that contains all column identifiers in proper
     * order.
     * 
     * @return all column identifiers
     */
    List<Object> getColumnIdentifier();

    /**
     * 
     * Retrieve row that is associated to given object key.
     * 
     * @param key
     *            identifier that maps to returned row
     * @return row that is mapped by given identifier
     * @throws NoSuchElementException
     *             if there is no row mapped by given identifier
     */
    Row<T> getRow(Object key) throws NoSuchElementException;

    /**
     * 
     * Retrieve a {@link List} that contains all row identifiers in proper
     * order.
     * 
     * @return all row identifiers
     */
    List<Object> getRowIdentifier();

    TableAnnotated<T> sortByColumnIds();

}
