/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
	@Override
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
