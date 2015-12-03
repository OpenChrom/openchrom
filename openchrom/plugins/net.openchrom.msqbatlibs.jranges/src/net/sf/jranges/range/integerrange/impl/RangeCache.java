/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.jranges.range.integerrange.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.jranges.range.integerrange.RangeInteger;

class RangeCache implements Iterable<Collection<? extends RangeInteger>>, Iterator<Collection<? extends RangeInteger>> {

	final static int DEFAULT_ELEMENTS = 10000;

	final int elements;

	private final List<? extends RangeInteger> ranges;

	private volatile Collection<? extends RangeInteger> cache;

	private volatile int index = 0;

	RangeCache(final Collection<? extends RangeInteger> ranges, int elements) {
		this.elements = elements;
		this.ranges = new ArrayList<RangeInteger>(ranges);
		fill();
	}

	RangeCache(final Collection<? extends RangeInteger> ranges) {
		this.elements = DEFAULT_ELEMENTS;
		this.ranges = new ArrayList<RangeInteger>(ranges);
		fill();
	}

	private void fill() {
		int end = index + elements;
		if (end > ranges.size() - 1)
			end = ranges.size();
		cache = new ArrayList<RangeInteger>(ranges.subList(index, end));
		index += elements;
	}

	/** 
	 * 
	 */
	public boolean hasNext() {
		// System.err.println(index);
		return (index <= ranges.size());
	}

	/** 
	 * 
	 */
	public synchronized Collection<? extends RangeInteger> next() {
		final Collection<? extends RangeInteger> result = new ArrayList<RangeInteger>(cache);
		fill();
		return result;
	}

	/** 
	 * 
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/** 
	 * 
	 */
	public Iterator<Collection<? extends RangeInteger>> iterator() {
		return this;
	}
}
