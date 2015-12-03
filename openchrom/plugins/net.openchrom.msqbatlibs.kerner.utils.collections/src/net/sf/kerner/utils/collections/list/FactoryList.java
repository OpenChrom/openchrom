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

import java.util.Collection;
import java.util.List;

import net.sf.kerner.utils.collections.FactoryCollection;

/**
 * {@code FactoryList} extends {@link FactoryCollection} by limiting the created {@code Collection} to be a {@link List}
 * .
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-06-15
 * @param <E>
 *            type of elements contained by the {@code List}
 */
public interface FactoryList<E> extends FactoryCollection<E> {

    /**
     * Create a new {@link List}.
     * 
     * @return new {@link List} instance
     */
    List<E> createCollection();

    /**
     * Create a new {@link List} containing all given elements.
     * 
     * @param template
     *            Elements which are initially contained by new {@link List}
     * @return new {@link List} instance
     */
    List<E> createCollection(Collection<? extends E> template);

}
