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
package net.sf.kerner.utils.collections;

import java.util.Collection;

/**
 * A {@code FactoryCollection} provides factory methods to retrieve a new instance of all kind of direct and indirect
 * implementations of {@link Collection}.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-04-16
 * @param E
 *            type of elements within the collection
 */
public interface FactoryCollection<E> {

    /**
     * Get a new instance for specified {@code Collection}.
     * 
     * @return new {@code Collection}
     */
    Collection<E> createCollection();

    /**
     * Get a new instance for specified {@code Collection} containing all given elements.
     * 
     * @param elements
     *            that are contained in new collection
     * @return new {@code Collection}
     */
    Collection<E> createCollection(Collection<? extends E> elements);

}
