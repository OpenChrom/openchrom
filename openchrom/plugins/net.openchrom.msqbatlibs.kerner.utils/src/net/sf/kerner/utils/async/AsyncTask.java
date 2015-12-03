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
package net.sf.kerner.utils.async;

/**
 * Create an asynchronous callback.
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
 * @version 2011-03-10
 * @param <R>
 *            type of result
 * @param <V>
 *            type of value
 */
public interface AsyncTask<R, V> {

    void doBefore();

    /**
     * Perform on failure.
     * 
     * @param e
     *            cause for this {@code AsyncCallBack}'s execution failure
     */
    void doOnFailure(Exception e);

    /**
     * Perform on success.
     * 
     * @param result
     *            result of execution
     */
    void doOnSucess(R result);

    /**
     * Do something asynchronously.
     * 
     * @param value
     *            parameter for execution
     * @return result of execution
     * @throws Exception
     *             if execution fails
     */
    R run(V value) throws Exception;

}
