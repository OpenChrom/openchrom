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
