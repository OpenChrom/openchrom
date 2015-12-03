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
package net.sf.jranges.range.doublerange.impl;

import net.sf.jranges.range.RangeException;
import net.sf.jranges.range.doublerange.RangeDouble;

/**
 * 
 * Most simple implementation for
 * {@link net.sf.jranges.range.doublerange.RangeDouble DoubleRange}. Arguments
 * and operations are not checked for validity.
 * 
 * <p>
 * A {@code DummyDoubleRange} is mutable. Start and Stop may be set
 * independently.
 * </p>
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
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-02-23
 * 
 */
public class RangeDoubleDummy extends VeryAbstractDoubleRange {

	/**
	 * 
	 * Construct a new {@code DummyDoubleRange} with start and stop values
	 * initiated with {@code 0}.
	 * 
	 */
	public RangeDoubleDummy() {
		this.start = 0;
		this.stop = 0;
	}

	/**
	 * 
	 * Construct a new {@code DummyDoubleRange} with given start and stop
	 * values.
	 * 
	 */
	public RangeDoubleDummy(double start, double stop) {
		this.start = start;
		this.stop = stop;
	}

	/**
	 * 
	 */
	public RangeDouble shift(double offset) throws RangeException {
		return new RangeDoubleDummy(getStart() + offset, getStop() + offset);
	}

	/**
	 * 
	 */
	public RangeDouble expandRange(double offset) throws RangeException {
		return expandRange(offset, false);
	}

	/**
	 * 
	 */
	public RangeDouble expandRange(double offset, boolean stayWithinLimits) throws RangeException {
		double start = getStart() - offset;
		double stop = getStop() + offset;
		return new RangeDoubleDummy(start, stop);
	}

}
