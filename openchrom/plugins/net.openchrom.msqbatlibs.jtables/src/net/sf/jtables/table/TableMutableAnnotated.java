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

/**
 * 
 * A {@code MutableTable} and also a {@code AnnotatedTable}.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-01-25
 * 
 * @param <T>
 *            type of elements in {@Table}
 * 
 * @see TableMutable
 * @see TableAnnotated
 */
public interface TableMutableAnnotated<T> extends TableAnnotated<T>, TableMutable<T> {

    /**
     * 
     * Set identifiers for columns.
     * 
     * @param ids
     *            a {@link List} that contains all column identifiers
     */
    void setColumnIdentifier(List<? extends Object> ids);

    /**
     * 
     * Set identifiers for rows.
     * 
     * @param ids
     *            a {@link List} that contains all row identifiers
     */
    void setRowIdentifier(List<? extends Object> ids);

    void addRow(Object id, Row<T> row);

    void addColumn(Object id, Column<T> row);

    void addRow(Object id, Row<T> row, int index);

    void addColumn(Object id, Column<T> row, int index);

}
