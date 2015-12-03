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

public abstract class AbstractAsyncTaskDelayed<O, I, K> extends AbstractAsyncTask<O, I> implements
        BatchListener<K> {

    private volatile O result;

    public void allDone(final boolean error) {
        doOnSucess(result);
    }

    public void errorOccured(final K identifier, final Exception error) {
        doOnFailure(error);
    }

    /**
     * Execute this {@code AbstractAsyncCallBack}. <br>
     * Don't override. Override {@link #run(Object)}
     * 
     * 
     * @param value
     *            parameter for this {@code AbstractAsyncCallBack}
     */
    @Override
    public final void execute(final I value) {

        try {
            result = run(value);
        } catch (final Exception e) {
            doOnFailure(e);
        }
    };
}
