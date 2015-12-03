/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.collections.filter;

import net.sf.kerner.utils.Util;

/**
 * Simple class to invert the filtering logic of another {@link Filter}.
 * <p>
 * <b>Example:</b><br>
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
 * @version 2011-10-26
 * @see Filter
 * @param <E>
 *            type of elements which are filtered
 */
public class FilterInverter<E> implements Filter<E> {

    protected final Filter<E> filter;

    public FilterInverter(final Filter<E> filter) {
        Util.checkForNull(filter);
        this.filter = filter;
    }

    public boolean filter(final E element) {
        return !filter.filter(element);
    }

}
