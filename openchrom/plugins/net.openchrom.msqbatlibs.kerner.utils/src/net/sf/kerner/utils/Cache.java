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

import java.util.LinkedHashMap;
import java.util.Map;

public class Cache<K, V> {

    private final LinkedHashMap<K, V> map;

    private final int capacity;

    public Cache(final int capacity) {
        this.capacity = capacity;
        map = new LinkedHashMap<K, V>(capacity + 1, 1.1f, true) {
            private static final long serialVersionUID = -8152367776854430483L;

            @Override
            protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
                return size() >= capacity;
            }
        };
    }

    public synchronized void clear() {
        map.clear();
    }

    public synchronized V get(final K key) {
        return map.get(key);
    }

    public synchronized int getCapacity() {
        return capacity;
    }

    public synchronized int getSize() {
        return map.size();
    }

    public synchronized V put(final K key, final V value) {
        return map.put(key, value);
    }

    public synchronized void putAll(final Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }
}
