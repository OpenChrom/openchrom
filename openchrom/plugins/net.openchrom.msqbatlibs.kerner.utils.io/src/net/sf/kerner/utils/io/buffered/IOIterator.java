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
package net.sf.kerner.utils.io.buffered;

import java.io.IOException;

/**
 * An implementation of {@code IOIterator} reads elements in an iterator based manner.
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
 * @version 2010-11-27
 * @param <E>
 *            type of elements which are returned by this {@code IOIterator}
 * @see java.util.Iterator
 */
public interface IOIterator<E> extends GeneralIOIterator {

    /**
     * Retrieve the next element.
     * 
     * @return the next element
     * @throws IOException
     *             if reading fails
     */
    E next() throws IOException;

}
