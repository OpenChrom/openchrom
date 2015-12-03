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
package net.sf.kerner.utils.collections;

import java.util.Collection;
import java.util.List;

import net.sf.kerner.utils.collections.list.ArrayListFactory;
import net.sf.kerner.utils.collections.list.FactoryList;
import net.sf.kerner.utils.pair.KeyValue;
import net.sf.kerner.utils.transformer.ViewKeyValueValue;

public class KeyValueViewValues<V> extends ViewKeyValueValue<V> implements TransformerCollection<KeyValue<?, V>, V> {

    private final FactoryList<V> factory;

    public KeyValueViewValues(FactoryList<V> factory) {
        this.factory = factory;
    }

    public KeyValueViewValues() {
        this(new ArrayListFactory<V>());
    }

    public List<V> transformCollection(Collection<? extends KeyValue<?, V>> element) {
        List<V> result = factory.createCollection();
        for (KeyValue<?, V> e : element) {
            result.add(transform(e));
        }
        return result;
    }

}
