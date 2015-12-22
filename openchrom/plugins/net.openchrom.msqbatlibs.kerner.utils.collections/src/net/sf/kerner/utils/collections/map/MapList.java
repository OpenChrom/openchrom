/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

	protected Factory<List<V>> getFactoryCollection() {

		return factory;
	}
}
