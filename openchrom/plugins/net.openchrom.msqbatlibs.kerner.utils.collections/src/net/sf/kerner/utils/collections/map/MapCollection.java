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

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A {@code MapCollection} represents a {@link Map}, that contains values, which
 * are itself a {@link Collection}.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 *
 *
 * </p>
 *
 *
 * <p>
 * last reviewed: 2013-08-07
 * </p>
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2013-08-07
 * @param <K>
 *            type of keys in this map
 * @param <V>
 *            type of values in this map
 * @param <L>
 *            type of implementation of {@link Collection}
 */
public interface MapCollection<K, V, L extends Collection<? extends V>> {

	void clear();

	/**
	 * Returns {@code true} if this map contains a mapping for given key. More
	 * formally, returns {@code true} if and only if this map contains a mapping
	 * for a key {@code k} such that {@code (key==null ? k==null : key.equals(k))}. (There can be at most one
	 * such mapping.)
	 *
	 * @param key
	 *            key whose presence in this map is to be tested
	 * @return {@code true} if this map contains a mapping for the specified key
	 * @throws ClassCastException
	 *             if the key is of an inappropriate type for this map
	 *             (optional)
	 * @throws NullPointerException
	 *             if the specified key is null and this map does not permit
	 *             null keys (optional)
	 */
	boolean containsKey(K k);

	/**
	 * Returns {@code true} if this map maps one or more keys to the specified
	 * value. More formally, returns {@code true} if and only if this map
	 * contains at least one mapping to a value {@code v} such that {@code (value==null ? v==null : value.equals(v))}. This operation will
	 * probably require time linear in the map size for most implementations of
	 * the {@code Map} interface.
	 *
	 * @param value
	 *            value whose presence in this map is to be tested
	 * @return {@code true} if this map maps one or more keys to the specified
	 *         value
	 * @throws ClassCastException
	 *             if the value is of an inappropriate type for this map
	 *             (optional)
	 * @throws NullPointerException
	 *             if the specified value is null and this map does not permit
	 *             null values (optional)
	 */
	boolean containsValue(V v);

	Set<Entry<K, L>> entrySet();

	L get(K k);

	boolean isEmpty();

	boolean isEmpty(K k);

	Set<K> keySet();

	/**
	 * Add another key value mapping to this {@code MapCollection}.
	 * </p>
	 * If this {@code MapCollection} already contains this key, The value is added to
	 * the {@code Collection} that is mapped by this key.
	 * </p>
	 * If the {@code MapCollection} does not contain this key, a new {@code Collection} is created, which will be associated by given key and holds initially
	 * given value.
	 */
	void put(K k, V v);

	/**
	 * Add all key value mappings to this {@code MapCollection}.
	 * </p>
	 * If this {@code MapCollection} already contains this key, All values are added to
	 * the {@code Collection} that is mapped by this key.
	 */
	void putAll(K k, Collection<? extends V> values);

	/**
	 * Add all key value mappings to this {@code MapCollection}.
	 * </p>
	 * If this {@code MapCollection} already contains any of given keys, All values are
	 * added to the {@code Collection} that are mapped by this key.
	 */
	void putAll(Map<? extends K, ? extends V> values);

	/**
	 * Add all key value mappings to this {@code MapCollection}.
	 * </p>
	 * If this {@code MapCollection} already contains any of given keys, All values are
	 * added to the {@code Collection} that are mapped by this key.
	 */
	void putAll(MapCollection<? extends K, ? extends V, L> values);

	void remove(K k);

	/**
	 * Removes all values from {@code k} which are equal to {@code v}.
	 *
	 */
	void removeValue(K k, V v);

	/**
	 * Removes all values from all keys which are equal to {@code v}.
	 *
	 */
	void removeValue(V v);

	int size();

	int size(K k);

	Collection<L> values();

	Collection<V> valuesAll();
}
