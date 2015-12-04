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

import net.sf.kerner.utils.collections.list.ArrayListFactory;
import net.sf.kerner.utils.transformer.Transformer;

public abstract class TransformerAbstract<T, V> implements Transformer<T, V>, TransformerCollection<T, V> {

	protected final FactoryCollection<V> factoryCollection;

	public TransformerAbstract() {

		super();
		this.factoryCollection = new ArrayListFactory<V>();
	}

	public TransformerAbstract(final FactoryCollection<V> factoryCollection) {

		super();
		this.factoryCollection = factoryCollection;
	}

	public Collection<V> transformCollection(final Collection<? extends T> element) {

		final Collection<V> result = factoryCollection.createCollection();
		for(final T t : element) {
			result.add(transform(t));
		}
		return result;
	}
}
