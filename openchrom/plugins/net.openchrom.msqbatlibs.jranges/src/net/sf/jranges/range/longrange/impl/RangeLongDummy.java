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
package net.sf.jranges.range.longrange.impl;

import net.sf.jranges.range.RangeException;
import net.sf.jranges.range.longrange.RangeLong;

public class RangeLongDummy extends VeryAbstractLongRange implements RangeLong {

	// Constructor //

	/**
	 * 
	 * Construct a new {@code DummyLongRange} with start and stop values
	 * initiated with {@code 0}.
	 * 
	 */
	public RangeLongDummy() {
		this.start = 0;
		this.stop = 0;
	}

	/**
	 * 
	 * Construct a new {@code DummyLongRange} with given start and stop values.
	 * 
	 */
	public RangeLongDummy(long start, long stop) {
		this.start = start;
		this.stop = stop;
	}

	// Public //

	/**
	 * 
	 * Set start position for this {@code DummyLongRange}.
	 * 
	 * @param start
	 *            new start position
	 */
	public void setStart(long start) {
		this.start = start;
	}

	/**
	 * 
	 * Set stop position for this {@code DummyLongRange}.
	 * 
	 * @param stop
	 *            new stop position
	 */
	public void setStop(long stop) {
		this.stop = stop;
	}

	// Implement //

	/**
	 * 
	 */
	public RangeLong shift(long offset) {
		return new RangeLongDummy(getStart() + offset, getStop() + offset);
	}

	/**
	 * 
	 */
	public RangeLong expandRange(long offset) throws RangeException {
		return expandRange(offset, false);
	}

	/**
	 * 
	 */
	public RangeLong expandRange(long offset, boolean stayWithinLimits) throws RangeException {
		long start = getStart() - offset;
		long stop = getStop() + offset;
		return new RangeLongDummy(start, stop);
	}

}
