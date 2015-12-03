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
package net.sf.kerner.utils.collections.set;

import java.util.Collection;
import java.util.Set;

import net.sf.kerner.utils.collections.TransformerCollection;

public interface TransformerSet<T, V> extends TransformerCollection<T, V> {

    /**
     * Transforms each element contained by given {@link Set} and returns another {@link Set} which contains all
     * transformed elements (in the same order).
     * 
     * @param collection
     *            {@link Collection} that contains elements to transform
     * @return {@link Set} containing transformed elements
     */
    Set<V> transformCollection(Collection<? extends T> collection);

}
