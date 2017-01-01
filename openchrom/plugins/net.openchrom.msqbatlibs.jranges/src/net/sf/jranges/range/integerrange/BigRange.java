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
package net.sf.jranges.range.integerrange;

import java.math.BigInteger;

import net.sf.jranges.range.Range;
import net.sf.jranges.range.RangeException;

/**
 * 
 * A {@code BigRange} is a {@link net.sf.jranges.range.Range Range} based on {@link java.math.BigInteger BigInteger} values.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-07
 * 
 */
public interface BigRange extends Comparable<BigRange>, Range {

	/**
	 * 
	 * Retrieve this {@code BigRange}'s start point.
	 * 
	 * @return this {@code BigRange}'s start point
	 */
	BigInteger getStart();

	/**
	 * 
	 * Retrieve this {@code BigRange}'s stop point.
	 * 
	 * @return this {@code BigRange}'s stop point
	 */
	BigInteger getStop();

	/**
	 * 
	 * Retrieve the number of positions covered by this {@code BigRange}, what
	 * will be typically defined as {@code {@link #getStop()} - {@link #getStart()} +1}.
	 * 
	 * @return this {@code BigRange}'s length
	 */
	BigInteger getLength();

	/**
	 * 
	 * Retrieve this {@code BigInteger}'s interval.
	 * 
	 * @return this {@code BigInteger}'s interval
	 */
	BigInteger getInterval();

	/**
	 * 
	 * 
	 * Shift this {@code BigRange}.
	 * <p>
	 * A shift is defined as the increment of both start and stop by given offset. <br>
	 * If {@code offset} is negative, it will result in an decrement of this {@code BigRange}'s start and stop.
	 * </p>
	 * 
	 * @param offset
	 *            offset by which is shifted
	 * @return the new, shifted {@code BigRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	BigRange shift(BigInteger offset) throws RangeException;

	/**
	 * 
	 * 
	 * Exactly the same as {@code BigRange#expandRange(BigInteger, false)}
	 * 
	 * @param offset
	 *            offset by which is expanded
	 * @return the new, expanded {@code BigRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	BigRange expandRange(BigInteger offset) throws RangeException;

	/**
	 * 
	 * Expand this {@code BigRange}.
	 * <p>
	 * An expansion is defined as the increment of this {@code BigRange}'s length by {@code offset * 2}. <br>
	 * If {@code offset} is negative, it will result in an decrement of this {@code BigRange}'s length.
	 * </p>
	 * 
	 * @param offset
	 *            offset by which is expanded
	 * @param stayWithinLimits
	 *            if true, this operation will not result in a RangeException
	 * @return the new, expanded {@code BigRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	BigRange expandRange(BigInteger offset, boolean stayWithinLimits) throws RangeException;

	/**
	 * Check if this {@code BigRange} contains the given position.
	 * 
	 * @param position
	 *            position that is checked
	 * @return true, if given position is covered by this {@code BigRange};
	 *         false otherwise
	 */
	boolean includes(BigInteger position);
}
