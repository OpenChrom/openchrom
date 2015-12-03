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
package net.sf.kerner.utils.pair;

/**
 * 
 * A Pair of Objects.
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
 * <b>Threading:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * Not thread save.
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2013-12-09
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * 
 * @param <F>
 *            type of first object
 * @param <S>
 *            type of second object
 */
public interface Pair<F, S> {

    /**
     * clones this {@code ObjectPair}.
     * 
     * @return a new instance of {@code ObjectPair}, holding same {@code first}
     *         and {@code second} as this {@code ObjectPair}
     */
    Pair<F, S> clone();

    /**
     * In contrast to {@link KeyValue}, {@code ObjectPair(1,2)} equals
     * {@code ObjectPair(2,1)}.
     * 
     */
    public boolean equals(Object obj);

    /**
     * Returns first object.
     * 
     * @return first object
     */
    F getFirst();

    /**
     * Returns second object.
     * 
     * @return second object
     */
    S getSecond();

    /**
     * In contrast to {@link KeyValue}, {@code ObjectPair(1,2).hashCode()}
     * equals {@code ObjectPair(2,1).hashCode()}.
     * 
     */
    @Override
    public int hashCode();

    /**
     * Inverts this {@code ObjectPair}, returning a new {@code ObjectPair} where
     * {@code first} and {@code second} are switched.
     * 
     * @return an inverted {@code ObjectPair}
     */
    Pair<S, F> invert();

}
