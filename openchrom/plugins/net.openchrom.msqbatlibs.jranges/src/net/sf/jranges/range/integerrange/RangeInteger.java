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
package net.sf.jranges.range.integerrange;

import java.util.List;

import net.sf.jranges.range.Range;
import net.sf.jranges.range.RangeException;

/**
 * 
 * An {@code IntegerRange} is a {@link Range} based on {@code int} values.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-12-06
 * 
 */
public interface RangeInteger extends Range, Comparable<RangeInteger> {

	List<Integer> asList();

	/**
	 * 
	 * 
	 * The same as {@code IntegerRange#expandRange(int, false)}
	 * 
	 * @param offset
	 *            offset by which is expanded
	 * @return the new, expanded {@code IntegerRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	RangeInteger expandRange(int offset) throws RangeException;

	/**
	 * 
	 * Expand this {@code IntegerRange}.
	 * <p>
	 * IntegerRange expansion is defined as the increment of this {@code IntegerRange}'s length by {@code offset * 2}. <br>
	 * If {@code offset} is negative, it will result in an decrement of this {@code IntegerRange}'s length.
	 * </p>
	 * 
	 * @param offset
	 *            offset by which is expanded
	 * @param stayWithinLimits
	 *            if true, this operation will not result in a RangeException
	 * @return the new, expanded {@code IntegerRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	RangeInteger expandRange(int offset, boolean stayWithinLimits) throws RangeException;

	/**
	 * 
	 * Retrieve this {@code IntegerRange}'s interval.
	 * 
	 * @return this {@code IntegerRange}'s interval
	 */
	int getInterval();

	/**
	 * 
	 * Retrieve the number of positions covered by this {@code IntegerRange}. <br>
	 * If {@code interval == 1}, that will be typically defined as {@code
	 * 
	 * @link #getStop()} - {@link #getStart()} +1}.
	 * 
	 * @return this {@code IntegerRange}'s length
	 */
	int getLength();

	/**
	 * 
	 * Retrieve this {@code IntegerRange}'s start point.
	 * 
	 * @return this {@code IntegerRange}'s start point
	 */
	int getStart();

	/**
	 * 
	 * Retrieve this {@code IntegerRange}'s stop point.
	 * 
	 * @return this {@code IntegerRange}'s stop point
	 */
	int getStop();

	/**
	 * Check if this {@code IntegerRange} contains the given position.
	 * 
	 * @param position
	 *            position that is checked
	 * @return true, if given position is covered by this {@code IntegerRange};
	 *         false otherwise
	 */
	boolean includes(int position);

	/**
	 * 
	 * 
	 * Shift this {@code IntegerRange}.
	 * <p>
	 * A shift is defined as the increment of both start and stop by given offset. <br>
	 * If {@code offset} is negative, it will result in an decrement of this {@code IntegerRange}'s start and stop.
	 * </p>
	 * 
	 * @param offset
	 *            offset by which is shifted
	 * @return the new, shifted {@code IntegerRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	RangeInteger shift(int offset) throws RangeException;
}
