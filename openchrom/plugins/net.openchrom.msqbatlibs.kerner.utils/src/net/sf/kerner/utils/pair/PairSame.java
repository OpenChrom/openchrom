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

import java.util.List;

/**
 * 
 * {@code ObjectPairSame} is an {@link Pair}, where both {@code first} and
 * {@code second} object are of same type.
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
 * last reviewed: 2013-04-03
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-04-03
 * 
 * @param <T>
 *            type of objects
 */
public interface PairSame<T> extends Pair<T, T>, Iterable<T> {

    /**
     * Returns a {@link List}, which contains two elements,
     * {@link PairSame#getFirst()} and {@link PairSame#getSecond()},
     * in this order.
     * 
     * @return a {@link List}, which contains {@link PairSame#getFirst()}
     *         and {@link PairSame#getSecond()}
     */
    List<T> asList();

}
