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
package net.sf.kerner.utils.collections.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.kerner.utils.math.UtilMath;

public class MapCount<T> implements Map<T, Integer> {

	private final Map<T, Integer> delegate;
	private T cacheMax;
	private T cacheMin;

	public MapCount() {
		this.delegate = new HashMap<T, Integer>();
	}

	public MapCount(final Map<T, Integer> delegate) {
		this.delegate = delegate;
	}

	public synchronized void clear() {

		delegate.clear();
	}

	public synchronized boolean containsKey(final Object key) {

		return delegate.containsKey(key);
	}

	public synchronized boolean containsValue(final Object value) {

		return delegate.containsValue(value);
	}

	public synchronized Set<java.util.Map.Entry<T, Integer>> entrySet() {

		return delegate.entrySet();
	}

	public synchronized boolean equals(final Object o) {

		return delegate.equals(o);
	}

	public synchronized Integer get(final Object key) {

		return delegate.get(key);
	}

	public synchronized T getMax() {

		T result = cacheMax;
		if(result == null) {
			Integer min = null;
			for(final java.util.Map.Entry<T, Integer> e : delegate.entrySet()) {
				final Integer cnt = e.getValue();
				if(min == null || cnt.compareTo(min) > 0) {
					min = cnt;
					result = e.getKey();
				}
			}
			cacheMax = result;
		}
		return result;
	}

	public synchronized T getMin() {

		T result = cacheMin;
		if(result == null) {
			Integer min = null;
			for(final java.util.Map.Entry<T, Integer> e : delegate.entrySet()) {
				final Integer cnt = e.getValue();
				if(min == null || cnt.compareTo(min) < 0) {
					min = cnt;
					result = e.getKey();
				}
			}
			cacheMin = result;
		}
		return result;
	}

	public synchronized int hashCode() {

		return delegate.hashCode();
	}

	public synchronized boolean hasHighest() {

		final T t = getMax();
		final Integer value = get(t);
		if(value.compareTo(Integer.valueOf(1)) > 0) {
			return true;
		}
		return false;
	}

	public synchronized boolean hasLowest() {

		final T t = getMax();
		final Integer value = get(t);
		if(value.compareTo(Integer.valueOf(1)) > 0) {
			return true;
		}
		return false;
	}

	public synchronized boolean isEmpty() {

		return delegate.isEmpty();
	}

	public synchronized Set<T> keySet() {

		return delegate.keySet();
	}

	public synchronized Integer put(final T key) {

		final Integer value = delegate.get(key);
		if(value != null) {
			return delegate.put(key, UtilMath.increment(value));
		} else {
			return delegate.put(key, Integer.valueOf(1));
		}
	}

	public synchronized Integer put(final T key, final Integer value) {

		if(value.intValue() <= 1) {
			throw new IllegalArgumentException();
		}
		return delegate.put(key, value);
	}

	public synchronized void putAll(final Collection<? extends T> values) {

		for(final T t : values) {
			put(t);
		}
	}

	public synchronized void putAll(final Map<? extends T, ? extends Integer> m) {

		delegate.putAll(m);
	}

	public synchronized Integer remove(final Object key) {

		return delegate.remove(key);
	}

	public synchronized int size() {

		return delegate.size();
	}

	public synchronized String toString() {

		return delegate.toString();
	}

	public synchronized Collection<Integer> values() {

		return delegate.values();
	}
}
