/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class PropertiesSorted implements SortedMap<String, Object>, Serializable {

	private static final long serialVersionUID = -7867760517665815796L;

	public static PropertiesSorted fromProperties(final Properties properties) {

		final PropertiesSorted result = new PropertiesSorted();
		for(final java.util.Map.Entry<Object, Object> e : properties.entrySet()) {
			result.put(e.getKey().toString(), e.getValue());
		}
		return result;
	}

	private final SortedMap<String, Object> delegate;

	public PropertiesSorted() {
		delegate = new TreeMap<String, Object>();
	}

	public PropertiesSorted(final Comparator<? super String> c) {
		delegate = new TreeMap<String, Object>(c);
	}

	public PropertiesSorted(final Properties properties) {
		delegate = fromProperties(properties);
	}

	public PropertiesSorted(final SortedMap<String, Object> delegate) {
		this.delegate = delegate;
	}

	public void clear() {

		delegate.clear();
	}

	public Comparator<? super String> comparator() {

		return delegate.comparator();
	}

	public boolean containsKey(final Object key) {

		return delegate.containsKey(key);
	}

	public boolean containsValue(final Object value) {

		return delegate.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {

		return delegate.entrySet();
	}

	public boolean equals(final Object o) {

		return delegate.equals(o);
	}

	public String firstKey() {

		return delegate.firstKey();
	}

	public Object get(final Object key) {

		return delegate.get(key);
	}

	public int hashCode() {

		return delegate.hashCode();
	}

	public SortedMap<String, Object> headMap(final String toKey) {

		return delegate.headMap(toKey);
	}

	public boolean isEmpty() {

		return delegate.isEmpty();
	}

	public Set<String> keySet() {

		return delegate.keySet();
	}

	public String lastKey() {

		return delegate.lastKey();
	}

	public Object put(final String key, final Object value) {

		return delegate.put(key, value);
	}

	public void putAll(final Map<? extends String, ? extends Object> m) {

		delegate.putAll(m);
	}

	public Object remove(final Object key) {

		return delegate.remove(key);
	}

	public int size() {

		return delegate.size();
	}

	public SortedMap<String, Object> subMap(final String fromKey, final String toKey) {

		return delegate.subMap(fromKey, toKey);
	}

	public SortedMap<String, Object> tailMap(final String fromKey) {

		return delegate.tailMap(fromKey);
	}

	public Properties toProperties() {

		final Properties p = new Properties();
		p.putAll(delegate);
		return p;
	}

	public String toString() {

		return UtilCollection.toString(delegate.entrySet());
	}

	public Collection<Object> values() {

		return delegate.values();
	}
}
