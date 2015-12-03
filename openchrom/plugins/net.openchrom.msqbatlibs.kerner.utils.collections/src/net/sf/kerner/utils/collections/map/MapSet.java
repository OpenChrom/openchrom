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
package net.sf.kerner.utils.collections.map;

import java.util.Map;
import java.util.Set;

import net.sf.kerner.utils.Factory;
import net.sf.kerner.utils.collections.set.FactoryLinkedHashSet;

public class MapSet<K, V> extends MapCollectionAbstract<K, V, Set<V>> {

    private final Factory<Set<V>> factory;

    public MapSet() {
        this.factory = new FactoryLinkedHashSet<V>();
    }

    public MapSet(final Map<K, Set<V>> map) {
        super(map);
        this.factory = new FactoryLinkedHashSet<V>();
    }

    public MapSet(final Map<K, Set<V>> map, final Factory<Set<V>> factory) {
        super(map);
        this.factory = factory;
    }

    public MapSet(final Map<K, Set<V>> map, final MapCollectionAbstract<K, V, Set<V>> template) {
        super(map);
        this.factory = new FactoryLinkedHashSet<V>();
        putAll(template);
    }

    public MapSet(final MapCollectionAbstract<K, V, Set<V>> template) {
        this.factory = new FactoryLinkedHashSet<V>();
        putAll(template);
    }

    @Override
    protected Factory<Set<V>> getFactoryCollection() {
        return factory;
    }

}
