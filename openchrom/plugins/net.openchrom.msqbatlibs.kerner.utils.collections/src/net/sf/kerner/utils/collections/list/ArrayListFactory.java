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
package net.sf.kerner.utils.collections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * A {@link FactoryList} that returns instances of {@link ArrayList}.
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
 * last reviewed: 2013-10-16
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * 
 * @param <E>
 */
public class ArrayListFactory<E> implements FactoryList<E> {

    private final int size;

    public ArrayListFactory() {
        this.size = -1;
    }

    public ArrayListFactory(final int size) {
        this.size = size;
    }

    public synchronized List<E> createCollection() {
        if (size > -1) {
            return new ArrayList<E>(size);
        }
        return new ArrayList<E>();
    }

    public synchronized List<E> createCollection(final Collection<? extends E> template) {
        return new ArrayList<E>(template);
    }

    public synchronized int getSize() {
        return size;
    }

}
