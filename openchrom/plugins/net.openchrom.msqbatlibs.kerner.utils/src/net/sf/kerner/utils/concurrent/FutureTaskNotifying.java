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
package net.sf.kerner.utils.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskNotifying<V> extends FutureTask<V> {

    public interface ListenerDone {
        void isDone(FutureTaskNotifying<?> task);
    }

    private final String identifier;

    private final List<ListenerDone> listeners = new ArrayList<ListenerDone>();

    public FutureTaskNotifying(final Callable<V> callable) {
        super(callable);
        this.identifier = null;
    }

    public FutureTaskNotifying(final Callable<V> callable, final String identifier) {
        super(callable);
        this.identifier = identifier;
    }

    public FutureTaskNotifying(final Runnable runnable, final V result) {
        super(runnable, result);
        this.identifier = null;
    }

    public FutureTaskNotifying(final Runnable runnable, final V result, final String identifier) {
        super(runnable, result);
        this.identifier = identifier;
    }

    public synchronized void addAllListener(final List<ListenerDone> listeners) {
        for (final ListenerDone l : listeners) {
            addListener(l);
        }
    }

    public synchronized void addListener(final ListenerDone listener) {
        listeners.add(listener);
    }

    @Override
    protected synchronized void done() {
        super.done();
        for (final ListenerDone listener : listeners) {
            listener.isDone(this);
        }
    }

    public String getIdentifier() {
        return identifier;
    }
}
