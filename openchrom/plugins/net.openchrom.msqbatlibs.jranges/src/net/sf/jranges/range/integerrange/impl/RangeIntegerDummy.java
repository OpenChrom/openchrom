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
package net.sf.jranges.range.integerrange.impl;

import net.sf.jranges.range.RangeException;
import net.sf.jranges.range.integerrange.RangeInteger;

/**
 * 
 * Most simple implementation for {@link net.sf.jranges.range.integerrange.RangeInteger IntegerRange}. Arguments and
 * operations are not checked for validity.
 * 
 * <p>
 * A {@code DummyRange} is mutable. Start and Stop may be set independently.
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
 * @version 2010-11-18
 * 
 */
public class RangeIntegerDummy extends VeryAbstractIntegerRange implements RangeInteger {

	// Constructor //
	/**
	 * 
	 * Construct a new {@code DummyRange} with start and stop values initiated
	 * with {@code 0}.
	 * 
	 */
	public RangeIntegerDummy() {
		this.start = 0;
		this.stop = 0;
	}

	/**
	 * 
	 * Construct a new {@code DummyRange} with given start and stop values.
	 * 
	 */
	public RangeIntegerDummy(int start, int stop) {
		this.start = start;
		this.stop = stop;
	}

	// Public //
	/**
	 * 
	 * Set start position for this {@code DummyRange}.
	 * 
	 * @param start
	 *            new start position
	 */
	public void setStart(int start) {

		this.start = start;
	}

	/**
	 * 
	 * Set stop position for this {@code DummyRange}.
	 * 
	 * @param stop
	 *            new stop position
	 */
	public void setStop(int stop) {

		this.stop = stop;
	}

	// Implement //
	/**
	 * 
	 */
	public RangeInteger shift(int offset) {

		return new RangeIntegerDummy(getStart() + offset, getStop() + offset);
	}

	/**
	 * 
	 */
	public RangeInteger expandRange(int offset) throws RangeException {

		return expandRange(offset, false);
	}

	/**
	 * 
	 */
	public RangeInteger expandRange(int offset, boolean stayWithinLimits) throws RangeException {

		int start = getStart() - offset;
		int stop = getStop() + offset;
		return new RangeIntegerDummy(start, stop);
	}
}
