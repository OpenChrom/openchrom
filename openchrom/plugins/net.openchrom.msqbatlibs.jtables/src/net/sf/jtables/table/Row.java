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

package net.sf.jtables.table;

import java.util.List;

/**
 *
 * A table row.
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
 * @version 2014-12-03
 *
 * @param <T>
 *            type of table element
 */
public interface Row<T> extends List<T>, Cloneable {

    T get(Object o);

    List<Object> getIdentifier();

    boolean hasColumn(int index);

    boolean hasColumn(Object o);

    boolean isEmpty();

    void setIdentifier(List<? extends Object> identifier);

    String toString(String delimiter);

}
