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

/**
 * A {@code Filter} represents a filtering function, more generally it may be
 * understood as a <a
 * href="http://en.wikipedia.org/wiki/Predicate_%28mathematical_logic%29"
 * >Predicate</a> on {@code E}, which is a {@link Boolean}-valued function.
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-10-26
 * @param <E>
 *            type of elements which are filtered
 */
public interface Filter<E> {

    /**
     *
     * @param e
     *            Element which is filtered
     * @return {@code true}, if this {@code Filter} accepts given element
     *         {@code E}; {@code false} otherwise
     */
    boolean filter(E e);

}
