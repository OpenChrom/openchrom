/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import net.sf.kerner.utils.collections.map.UtilMap;
import net.sf.kerner.utils.math.UtilMath;

/**
 * Default implementation for {@link ObjectToIndexMapper}.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-06-18
 */
public class ObjectToIndexMapperImpl<T> implements ObjectToIndexMapper<T> {

	// Fields //
	/**
	 * 
	 */
	protected final Map<T, Integer> map = new LinkedHashMap<T, Integer>();

	public ObjectToIndexMapperImpl(final List<? extends T> keys) {
		final List<Integer> values = new ArrayList<Integer>();
		for(int i = 0; i < keys.size(); i++) {
			values.add(Integer.valueOf(i));
		}
		UtilMap.initMapWithValues(map, keys, values);
	}

	public ObjectToIndexMapperImpl(final T... keys) {
		this(Arrays.asList(keys));
	}

	// Private //
	// Protected //
	// Public //
	// Override //
	public String toString() {

		return map.toString();
	}

	// Implement //
	/**
	 * 
	 */
	public int get(final T key) {

		final Integer result = map.get(key);
		if(result != null) {
			return result;
		} else
			throw new NoSuchElementException("no such key [" + key + "]");
	}

	public Object getValue(final int index) {

		if(index < 0)
			throw new IllegalArgumentException("index [" + index + "]");
		for(final Entry<T, Integer> e : map.entrySet()) {
			if(e.getValue().equals(Integer.valueOf(index)))
				return e.getKey();
		}
		throw new NoSuchElementException("no such index [" + index + "]");
	}

	public boolean containsKey(final T key) {

		return map.containsKey(key);
	}

	public boolean containsValue(final int index) {

		return map.containsValue(Integer.valueOf(index));
	}

	public List<Integer> values() {

		return new ArrayList<Integer>(map.values());
	}

	public List<T> keys() {

		return new ArrayList<T>(map.keySet());
	}

	public int getMaxIndex() {

		return (int)UtilMath.getMax(map.values());
	}

	public int getSize() {

		return map.values().size();
	}

	public boolean isEmpty() {

		return getSize() == 0;
	}

	public void addMapping(final T key, final int value) {

		map.put(key, value);
	}

	public void addMapping(final T key) {

		map.put(key, getMaxIndex() + 1);
	}
}
