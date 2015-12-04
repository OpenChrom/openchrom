/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
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
import net.sf.kerner.utils.transformer.ViewKeyValueKey;

public class KeyValueViewKeys<K> extends ViewKeyValueKey<K> implements TransformerCollection<KeyValue<K, ?>, K> {

	private final FactoryList<K> factory;

	public KeyValueViewKeys(FactoryList<K> factory) {

		this.factory = factory;
	}

	public KeyValueViewKeys() {

		this(new ArrayListFactory<K>());
	}

	public List<K> transformCollection(Collection<? extends KeyValue<K, ?>> element) {

		List<K> result = factory.createCollection();
		for(KeyValue<K, ?> e : element) {
			result.add(transform(e));
		}
		return result;
	}
}
