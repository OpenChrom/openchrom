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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.kerner.utils.Factory;

public class MapList<K, V> extends MapCollectionAbstract<K, V, List<V>> {

    private final Factory<List<V>> factory;

    public MapList() {
        this.factory = new Factory<List<V>>() {

            public List<V> create() {
                return new ArrayList<V>();
            }
        };
    }

    public MapList(final Map<K, List<V>> map) {
        super(map);
        this.factory = new Factory<List<V>>() {

            public List<V> create() {
                return new ArrayList<V>();
            }
        };
    }

    public MapList(final Map<K, List<V>> map, final Factory<List<V>> factory) {
        super(map);
        this.factory = factory;
    }

    @Override
    protected Factory<List<V>> getFactoryCollection() {
        return factory;
    }

}
