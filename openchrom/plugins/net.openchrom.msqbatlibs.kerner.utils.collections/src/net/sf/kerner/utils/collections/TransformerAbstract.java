/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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
