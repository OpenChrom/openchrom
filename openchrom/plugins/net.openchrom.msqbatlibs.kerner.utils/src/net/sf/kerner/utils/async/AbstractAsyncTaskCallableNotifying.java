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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAsyncTaskCallableNotifying<R, V> extends
        AbstractAsyncTaskCallable<R, V> {

    public static interface Listener {
        void notify(Object o);
    }

    public static enum State {
        WAITING, RUNNING, FINISHED_OK, FINISHED_BAD
    }

    private final List<Listener> listeners = new ArrayList<Listener>();

    private State state = State.WAITING;

    private final String identifier;

    public AbstractAsyncTaskCallableNotifying(final String identifier) {
        this.identifier = identifier;
    }

    public synchronized void addAllListeners(final Collection<? extends Listener> listeners) {
        for (final Listener l : listeners) {
            this.listeners.add(l);
        }
    }

    public synchronized void addListener(final Listener listener) {
        listeners.add(listener);
    }

    public void doBefore() {
        synchronized (state) {
            state = State.RUNNING;
            notifyListeners(identifier + " : " + state);
        }
    }

    public void doOnFailure(final Exception e) {
        synchronized (state) {
            state = State.FINISHED_BAD;
            notifyListeners(identifier + " : " + state);
        }
    }

    public void doOnSucess(final R result) {
        synchronized (state) {
            state = State.FINISHED_OK;
            notifyListeners(identifier + " : " + state);
        }
    }

    private void notifyListeners(final Object o) {
        for (final Listener l : listeners) {
            l.notify(o);
        }
    }
}
