/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
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
		for(KeyValue<?, V> e : element) {
			result.add(transform(e));
		}
		return result;
	}
}
