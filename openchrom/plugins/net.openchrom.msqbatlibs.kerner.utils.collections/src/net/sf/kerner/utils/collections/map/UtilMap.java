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
package net.sf.kerner.utils.collections.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.kerner.utils.Factory;
import net.sf.kerner.utils.Util;
import net.sf.kerner.utils.UtilString;
import net.sf.kerner.utils.collections.FactoryCollection;
import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.collections.list.ArrayListFactory;
import net.sf.kerner.utils.counter.Counter;
import net.sf.kerner.utils.transformer.TransformerToString;
import net.sf.kerner.utils.transformer.TransformerToStringDefault;

/**
 *
 * Utility class for {@link Map} and {@link MapCollection} related stuff.
 *
 * <p>
 * <b>Example:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 * <p>
 * <b>Threading:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * Not thread save.
 * </pre>
 *
 * </p>
 * <p>
 * last reviewed: 0000-00-00
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class UtilMap {

	public static final String DEFAULT_ELEMENT_SEPARATOR = UtilString.NEW_LINE_STRING;
	public static final String DEFAULT_ENTRY_SEPARATOR = " = ";
	public static final TransformerToString DEFAULT_KEY_VALUE_TO_STRING = new TransformerToStringDefault();

	/**
	 * Given a {@link java.util.Map Map} of collections, add the given element
	 * to the {@link java.util.Collection Collection} associated with given key.
	 * If for this key is no {@code Collection} registered, a new one will be
	 * created. The given element will be then added to this new {@code Collection}. The new {@code Collection} will be associated with
	 * given key.
	 * <p>
	 * Element that is added may be null, if underlying collection allows null elements.
	 * </p>
	 *
	 * @param <M>
	 *            type of keys in given {@code Map}
	 * @param <E>
	 *            type of values in {@code Collection}s contained in given {@code Map}
	 * @param map
	 *            {@code Map} that contains {@code Collection} to which element
	 *            is added
	 * @param key
	 *            key for {@code Collection} to which element is added
	 * @param element
	 *            element that is added
	 * @see java.util.Map Map
	 * @see java.util.Collection Collection
	 * @throws NullPointerException
	 *             if {@code map} or {@code key} is null
	 */
	public static <M, E> void addToCollectionsMap(final Map<M, Collection<E>> map, final M key, final E element) {

		Util.checkForNull(map, key);
		addToCollectionsMap(map, key, element, new ArrayListFactory<E>());
	}

	/**
	 * Given a {@link java.util.Map Map} of collections, add the given element
	 * to the {@link java.util.Collection Collection} associated with given key.
	 * If for this key is no {@code Collection} registered, a new one will be
	 * created. The given element will be then added to this new {@code Collection}. The new {@code Collection} will be associated with
	 * given key.
	 * <p>
	 * Element that is added may be null, if underlying collection allows null elements.
	 * </p>
	 *
	 * @param <M>
	 *            type of keys in given {@code Map}
	 * @param <E>
	 *            type of values in {@code Collection}s contained in given {@code Map}
	 * @param map
	 *            {@code Map} that contains {@code Collection} to which element
	 *            is added
	 * @param key
	 *            key for {@code Collection} to which element is added
	 * @param element
	 *            element that is added
	 * @param factory
	 *            {@code CollectionFactory} that is used to create a new {@code Collection} if there is no value for given key yet
	 * @see java.util.Map Map
	 * @see java.util.Collection Collection
	 * @see net.sf.kerner.utils.Util.collections.FactoryCollection
	 *      CollectionFactory
	 * @throws NullPointerException
	 *             if {@code map} or {@code key} is null
	 */
	public static <M, E> void addToCollectionsMap(final Map<M, Collection<E>> map, final M key, final E element, final FactoryCollection<E> factory) {

		Util.checkForNull(map, factory);
		Collection<E> col = map.get(key);
		if(col == null) {
			col = factory.createCollection();
			map.put(key, col);
		}
		col.add(element);
	}

	public static <T, K, V> void addToMapMap(final Map<T, Map<K, V>> map, final T key1, final K key2, final V value, final FactoryMap<K, V> factory) {

		Util.checkForNull(map, factory);
		Map<K, V> m1 = map.get(key1);
		if(m1 == null) {
			m1 = factory.create();
			map.put(key1, m1);
		}
		m1.put(key2, value);
	}

	public static <K, V> Map<K, V> filterByKey(final Map<K, V> map, final Filter<K> filter) {

		return filterByKey(map, filter, new FactoryLinkedHashMap<K, V>());
	}

	public static <K, V> Map<K, V> filterByKey(final Map<K, V> map, final Filter<K> filter, final FactoryMap<K, V> factory) {

		final Map<K, V> result = factory.create();
		for(final Entry<K, V> e : map.entrySet()) {
			if(filter.filter(e.getKey())) {
				result.put(e.getKey(), e.getValue());
			}
		}
		return result;
	}

	public static <K, V> Map<K, V> filterByValue(final Map<K, V> map, final Filter<V> filter) {

		return filterByValue(map, filter, new FactoryLinkedHashMap<K, V>());
	}

	public static <K, V> Map<K, V> filterByValue(final Map<K, V> map, final Filter<V> filter, final FactoryMap<K, V> factory) {

		final Map<K, V> result = factory.create();
		for(final Entry<K, V> e : map.entrySet()) {
			if(filter.filter(e.getValue())) {
				result.put(e.getKey(), e.getValue());
			}
		}
		return result;
	}

	/**
	 * Retrieve first key from given map that maps to given value.
	 *
	 * @param <K>
	 *            type of {@code key}
	 * @param <V>
	 *            type of {@code value}
	 * @param map
	 *            {@link Map} to retrieve key from
	 * @param value
	 *            value, for which key is needed
	 * @return key for given value (first occurrence), or {@code null} if there
	 *         is no match
	 */
	public static <K, V> K getKeyForValue(final Map<K, V> map, final V value) {

		for(final Entry<K, V> e : map.entrySet()) {
			if(e.getValue().equals(value))
				return e.getKey();
		}
		return null;
	}

	/**
	 * Retrieve first key from given map that maps to given value.
	 *
	 * @param <K>
	 *            type of {@code key}
	 * @param <V>
	 *            type of {@code value}
	 * @param map
	 *            {@link Map} to retrieve key from
	 * @param value
	 *            value, for which key is needed
	 * @return key for given value (first occurrence), or {@code null} if there
	 *         is no match
	 */
	public static <K, V, L extends Collection<V>> K getKeyForValue(final MapCollection<K, V, L> map, final V value) {

		for(final Entry<K, L> e : map.entrySet()) {
			final L l = e.getValue();
			for(final V v : l) {
				if(v.equals(value)) {
					return e.getKey();
				}
			}
		}
		return null;
	}

	public static <K, V> void initMapWithValue(final Map<K, V> map, final Collection<? extends K> keys, final V value) {

		initMapWithValue(map, keys, value, true);
	}

	public static <K, V> void initMapWithValue(final Map<K, V> map, final Collection<? extends K> keys, final V value, final boolean clean) {

		if(clean)
			map.clear();
		for(final K k : keys) {
			map.put(k, value);
		}
	}

	/**
	 * Initialize given {@code Map} with given keys and given values. <br>
	 * After initialization, {@code Map} will contain all given keys. If {@code number of values >= number of keys}, every given key will map to
	 * one specific value. <br>
	 * Mapping will happen index-based, which means that {@code keys[i]} is
	 * mapped to {@code values[i]}.<br>
	 * If there are more keys than values, left-over keys will map to a null
	 * value, if underlying map allows this.
	 *
	 * @param <K>
	 *            type of keys
	 * @param <V>
	 *            type of values
	 * @param map
	 *            {@code Map} to initialize
	 * @param keys
	 *            keys which will map to values
	 * @param values
	 *            values which are mapped by keys
	 * @param clean
	 *            if {@code true}, clear given {@code Map} before initializing
	 * @see java.util.Map
	 * @see java.util.Collection
	 */
	public static <K, V> void initMapWithValues(final Map<K, V> map, final Collection<? extends K> keys, final Collection<? extends V> values, final boolean clean) {

		if(clean)
			map.clear();
		final Iterator<? extends V> valIt = values.iterator();
		for(final K k : keys) {
			if(valIt.hasNext())
				map.put(k, valIt.next());
			else
				map.put(k, null);
		}
	}

	/**
	 * The same as {@code #initMapWithValues(map, keys, values, true)}
	 */
	public static <M, V> void initMapWithValues(final Map<M, V> map, final Collection<? extends M> keys, final Collection<? extends V> values) {

		initMapWithValues(map, keys, values, true);
	}

	public static <K, V> Map<V, K> invert(final Map<K, V> map, final FactoryMap<V, K> factory) {

		Util.checkForNull(map, factory);
		final Map<V, K> result = factory.create();
		for(final Entry<K, V> e : map.entrySet()) {
			result.put(e.getValue(), e.getKey());
		}
		return result;
	}

	public static <K, V> Map<K, V> newMap() {

		return new LinkedHashMap<K, V>();
	}

	public static <K, V> Map<K, V> newMap(final Collection<? extends K> keys) {

		return newMap(keys, new FactoryLinkedHashMap<K, V>());
	}

	public static <K, V> Map<K, V> newMap(final Collection<? extends K> keys, final FactoryMap<K, V> factoryMap) {

		final Map<K, V> result = factoryMap.create();
		for(final K k : keys) {
			result.put(k, null);
		}
		return result;
	}

	public static <K, V> Map<K, V> sort(final Map<K, V> map, final Comparator<Map.Entry<K, V>> c) {

		final List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, c);
		final Map<K, V> result = new LinkedHashMap<K, V>();
		for(final Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <K, V, L extends Collection<V>> MapCollection<K, V, L> sort(final MapCollection<K, V, L> map, final Comparator<Map.Entry<K, L>> c, final Factory<? extends MapCollection<K, V, L>> factory) {

		final List<Map.Entry<K, L>> list = new ArrayList<Map.Entry<K, L>>(map.entrySet());
		Collections.sort(list, c);
		final MapCollection<K, V, L> result = factory.create();
		for(final Map.Entry<K, L> entry : list) {
			result.putAll(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <K, V> Map<K, V> sortByKey(final Map<K, V> map) {

		return sort(map, new Comparator<Entry<K, V>>() {

			@SuppressWarnings("unchecked")
			public int compare(final Entry<K, V> o1, final Entry<K, V> o2) {

				return ((Comparable<K>)o1.getKey()).compareTo(o2.getKey());
			}
		});
	}

	public static <K, V> Map<K, V> sortByKey(final Map<K, V> map, final Comparator<? super K> c) {

		return sort(map, new Comparator<Entry<K, V>>() {

			public int compare(final Entry<K, V> o1, final Entry<K, V> o2) {

				return c.compare(o1.getKey(), o2.getKey());
			}
		});
	}

	public static <K, V, L extends Collection<V>> MapCollection<K, V, L> sortByKey(final MapCollection<K, V, L> map, final Comparator<? super K> c, final Factory<? extends MapCollection<K, V, L>> factory) {

		return sort(map, new Comparator<Entry<K, L>>() {

			public int compare(final Entry<K, L> o1, final Entry<K, L> o2) {

				return c.compare(o1.getKey(), o2.getKey());
			}
		}, factory);
	}

	public static <K, V> Map<K, V> sortByValue(final Map<K, V> map) {

		return sort(map, new Comparator<Entry<K, V>>() {

			@SuppressWarnings("unchecked")
			public int compare(final Entry<K, V> o1, final Entry<K, V> o2) {

				return ((Comparable<V>)o1.getValue()).compareTo(o2.getValue());
			}
		});
	}

	public static <K, V> Map<K, V> sortByValue(final Map<K, V> map, final Comparator<? super V> c) {

		return sort(map, new Comparator<Entry<K, V>>() {

			public int compare(final Entry<K, V> o1, final Entry<K, V> o2) {

				return c.compare(o1.getValue(), o2.getValue());
			}
		});
	}

	public static <K, V> String toString(final Map<K, V> map) {

		return toString(map, DEFAULT_KEY_VALUE_TO_STRING, DEFAULT_KEY_VALUE_TO_STRING, DEFAULT_ELEMENT_SEPARATOR, DEFAULT_ENTRY_SEPARATOR);
	}

	public static <K, V> String toString(final Map<K, V> map, final TransformerToString keyValueToString) {

		return toString(map, keyValueToString, keyValueToString, DEFAULT_ELEMENT_SEPARATOR, DEFAULT_ENTRY_SEPARATOR);
	}

	public static <K, V> String toString(final Map<K, V> map, final TransformerToString<K> keyToString, final TransformerToString<V> valueToString) {

		return toString(map, keyToString, valueToString, DEFAULT_ELEMENT_SEPARATOR, DEFAULT_ENTRY_SEPARATOR);
	}

	public static <K, V> String toString(final Map<K, V> map, final TransformerToString<K> keyToString, final TransformerToString<V> valueToString, final String elementSeparator) {

		return toString(map, keyToString, valueToString, elementSeparator, DEFAULT_ENTRY_SEPARATOR);
	}

	public static <K, V> String toString(final Map<K, V> map, final TransformerToString<K> keyToString, final TransformerToString<V> valueToString, final String elementSeparator, final String entrySteparator) {

		final StringBuilder sb = new StringBuilder();
		final Iterator<Entry<K, V>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			final Entry<K, V> next = it.next();
			sb.append(keyToString.transform(next.getKey()));
			sb.append(entrySteparator);
			sb.append(valueToString.transform(next.getValue()));
			if(it.hasNext())
				sb.append(elementSeparator);
		}
		return sb.toString();
	}

	/**
	 * Reduce the number of elements in given {@link Map} to at most given size.
	 *
	 * @param <K>
	 *            type of keys in given {@code Map}
	 * @param <V>
	 *            type of values in given {@code Map}
	 * @param map
	 *            {@code Map} that is trimmed
	 * @param factory
	 *            {@link Factory} that is used to instantiate returning {@code Map}
	 * @param size
	 *            number of elements returning [@code Map} contains (at most)
	 * @return the new {@code Map} that has been trimmed
	 */
	public static <K, V> Map<K, V> trimMapToSize(final Map<K, V> map, final FactoryMap<K, V> factory, final int size) {

		Util.checkForNull(map, factory);
		if(size < 1 || map.size() <= size)
			return map;
		final Map<K, V> result = factory.create();
		final Counter c = new Counter();
		for(final Entry<K, V> e : map.entrySet()) {
			if(c.getCount() > size) {
				break;
			}
			result.put(e.getKey(), e.getValue());
			c.count();
		}
		return result;
	}

	private UtilMap() {
	}
}
