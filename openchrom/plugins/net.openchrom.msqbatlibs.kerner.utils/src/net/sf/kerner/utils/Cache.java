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
