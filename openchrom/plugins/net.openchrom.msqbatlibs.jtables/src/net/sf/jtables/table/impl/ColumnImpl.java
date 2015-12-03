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

import net.sf.jtables.table.Column;

/**
 * 
 * Default implementation for {@link Column}.
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
 *            type of table element
 */
public class ColumnImpl<T> extends RowImpl<T> implements Column<T> {

    public ColumnImpl() {
        // TODO Auto-generated constructor stub
    }

    public ColumnImpl(List<T> elements) {
        super(elements);
        // TODO Auto-generated constructor stub
    }

    public ColumnImpl(T... elements) {
        super(elements);
        // TODO Auto-generated constructor stub
    }

}
