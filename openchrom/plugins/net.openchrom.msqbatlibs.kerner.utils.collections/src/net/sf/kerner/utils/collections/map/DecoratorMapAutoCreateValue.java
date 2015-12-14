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
import java.util.Map;
import java.util.Set;

import net.sf.kerner.utils.Factory;

/**
 * A {@link Map} decorator, that creates a value to a given key, if no such value is associated to a key, when trying to
 * get associated value for a key.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version May 2, 2012
 * @param <K>
 * @param <V>
 */
public class DecoratorMapAutoCreateValue<K, V> implements Map<K, V> {

	private final Map<K, V> decorated;
	private final Factory<V> factory;

	public DecoratorMapAutoCreateValue(Map<K, V> decorated, Factory<V> factory) {

		super();
		this.decorated = decorated;
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	public V get(Object key) {

		if(decorated.containsKey(key)) {
			// ok
		} else {
			decorated.put((K)key, factory.create());
		}
		return decorated.get(key);
	}

	// Delegate //
	
	public String toString() {

		return decorated.toString();
	}

	public int size() {

		return decorated.size();
	}

	public boolean isEmpty() {

		return decorated.isEmpty();
	}

	public boolean containsKey(Object key) {

		return decorated.containsKey(key);
	}

	public boolean containsValue(Object value) {

		return decorated.containsValue(value);
	}

	public V put(K key, V value) {

		return decorated.put(key, value);
	}

	public V remove(Object key) {

		return decorated.remove(key);
	}

	public void putAll(Map<? extends K, ? extends V> m) {

		decorated.putAll(m);
	}

	public void clear() {

		decorated.clear();
	}

	public Set<K> keySet() {

		return decorated.keySet();
	}

	public Collection<V> values() {

		return decorated.values();
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {

		return decorated.entrySet();
	}

	public boolean equals(Object o) {

		return decorated.equals(o);
	}

	public int hashCode() {

		return decorated.hashCode();
	}
}
