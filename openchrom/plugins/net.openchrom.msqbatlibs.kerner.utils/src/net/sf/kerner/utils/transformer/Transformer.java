/*******************************************************************************
 * Copyright (c) 2010-2015 Alexander Kerner. All rights reserved.
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
package net.sf.kerner.utils.transformer;

/**
 * A {@code Transformer} converts one object of type {@code T} to another object
 * of type {@code V}.
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
 * @author <a href="mailto:ak@silico-sciences.com">Alexander Kerner</a>
 * @version 2015-09-05
 * @param <T>
 *            type of input element
 * @param <V>
 *            type of output element
 */
public interface Transformer<T, V> {

    /**
     * Transforms element of type {@code T} to a new element of type {@code V}.
     *
     * @param element
     *            element that is converted
     * @return resulting element
     */
    V transform(T element);

}
