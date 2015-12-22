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
