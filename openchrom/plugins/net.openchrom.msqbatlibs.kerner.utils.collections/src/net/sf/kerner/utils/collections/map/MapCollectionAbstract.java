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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.kerner.utils.Factory;
import net.sf.kerner.utils.collections.UtilCollection;

public abstract class MapCollectionAbstract<K, V, L extends Collection<V>> implements MapCollection<K, V, L> {

	protected final Map<K, L> map;

	public MapCollectionAbstract() {

		this(new LinkedHashMap<K, L>());
	}

	public MapCollectionAbstract(final Map<K, L> map) {

		this.map = map;
	}

	public void clear() {

		map.clear();
	}

	public boolean containsKey(final K key) {

		return map.containsKey(key);
	}

	public synchronized boolean containsValue(final V value) {

		for(final Collection<V> v : map.values()) {
			if(v.contains(value))
				return true;
		}
		return false;
	}

	public Set<Map.Entry<K, L>> entrySet() {

		return map.entrySet();
	}

	@Override
	public boolean equals(final Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof MapCollectionAbstract))
			return false;
		@SuppressWarnings("rawtypes")
		final MapCollectionAbstract other = (MapCollectionAbstract)obj;
		if(map == null) {
			if(other.map != null)
				return false;
		} else if(!map.equals(other.map))
			return false;
		return true;
	}

	public L get(final K key) {

		return map.get(key);
	}

	protected abstract Factory<L> getFactoryCollection();

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		return result;
	}

	public boolean isEmpty() {

		return map.isEmpty();
	}

	public boolean isEmpty(final K k) {

		if(map.containsKey(k)) {
			return map.get(k).isEmpty();
		}
		return true;
	}

	public Set<K> keySet() {

		return map.keySet();
	}

	public void put(final K key, final V value) {

		// TODO see {@link MapUtils#addToCollectionsMap()}
		L col = map.get(key);
		if(col == null) {
			col = getFactoryCollection().create();
			map.put(key, col);
		}
		col.add(value);
	}

	public synchronized void putAll(final K k, final Collection<? extends V> elements) {

		for(final V v : elements) {
			put(k, v);
		}
	};

	public synchronized void putAll(final Map<? extends K, ? extends V> m) {

		for(final java.util.Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
			this.put(e.getKey(), e.getValue());
		}
	}

	public synchronized void putAll(final MapCollection<? extends K, ? extends V, L> m) {

		for(final Entry<? extends K, L> e : m.entrySet()) {
			this.putAll(e.getKey(), e.getValue());
		}
	}

	public synchronized void remove(final K key) {

		map.remove(key);
	}

	public synchronized void removeValue(final K k, final V v) {

		map.get(k).remove(v);
	}

	public synchronized void removeValue(final V v) {

		final Iterator<Entry<K, L>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			final Entry<K, L> next = it.next();
			next.getValue().remove(v);
			if(next.getValue().isEmpty()) {
				it.remove();
			}
		}
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 */
	public int size() {

		return map.size();
	}

	/**
	 * Returns the number of elements mapped by this key.
	 */
	public int size(final K k) {

		if(map.containsKey(k)) {
			return map.get(k).size();
		}
		return 0;
	}

	@Override
	public String toString() {

		return map.toString();
	}

	public Collection<L> values() {

		return map.values();
	}

	public Collection<V> valuesAll() {

		final Collection<V> result = UtilCollection.newCollection();
		for(final L v : map.values()) {
			result.addAll(v);
		}
		return result;
	}
}
