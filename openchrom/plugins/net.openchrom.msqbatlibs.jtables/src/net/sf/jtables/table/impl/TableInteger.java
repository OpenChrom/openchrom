/**********************************************************************
Copyright (c) 2011-2012 Alexander Kerner. All rights reserved.
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

import java.util.List;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.TableMutableAnnotated;

/**
 * 
 * An {@link TableMutableAnnotated} with {@link Integer} elements.
 * 
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-01-25
 * 
 */
public class TableInteger extends AnnotatedMutableTableImpl<Integer> {

    /**
     * 
     * Create a empty {@code IntegerTable}.
     * 
     */
    public TableInteger() {
        super();
    }

    /**
     * 
     * Create a new {@code IntegerTable} that contains given rows.
     * 
     * @param rows
     *            rows that are initially contained by this {@code Table}
     */
    public TableInteger(List<Row<Integer>> rows) {
        super(rows);
    }

}
