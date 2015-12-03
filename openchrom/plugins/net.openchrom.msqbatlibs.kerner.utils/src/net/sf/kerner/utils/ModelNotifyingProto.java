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
package net.sf.kerner.utils;

import java.util.Collection;
import java.util.LinkedHashSet;

public class ModelNotifyingProto {

    public interface Listener {

        void notifyError(Throwable t);

        void notifyMessage(String msg);

        void notifyObject(Object o);
    }

    private final Collection<Listener> listeners = new LinkedHashSet<Listener>();

    public synchronized void addListener(final Listener listener) {
        listeners.add(listener);
    }

    protected void notifyError(final Throwable t) {
        for (final Listener l : listeners) {
            l.notifyError(t);
        }
    }

    protected void notifyMessage(final String msg) {
        for (final Listener l : listeners) {
            l.notifyMessage(msg);
        }
    }

    protected void notifyObject(final Object o) {
        for (final Listener l : listeners) {
            l.notifyObject(o);
        }
    }

    public synchronized void removeListener(final Listener listener) {
        listeners.remove(listener);
    }

}
