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
package net.sf.jranges.range.longrange;

import net.sf.jranges.range.Range;
import net.sf.jranges.range.RangeException;

/**
 * 
 * A {@code LongRange} is a {@link net.sf.jranges.range.Range Range} based on
 * {@code long} values.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-07
 * 
 */
public interface RangeLong extends Comparable<RangeLong>, Range {

	/**
	 * 
	 * Retrieve this {@code LongRange}'s start point.
	 * 
	 * @return this {@code LongRange}'s start point
	 */
	long getStart();

	/**
	 * 
	 * Retrieve this {@code LongRange}'s stop point.
	 * 
	 * @return this {@code LongRange}'s stop point
	 */
	long getStop();

	/**
	 * 
	 * Retrieve the number of positions covered by this {@code LongRange}, what
	 * will be typically defined as {@code {@link #getStop()} -
	 * {@link #getStart()} +1}.
	 * 
	 * @return this {@code LongRange}'s length
	 */
	long getLength();

	/**
	 * 
	 * Retrieve this {@code LongRange}'s interval.
	 * 
	 * @return this {@code LongRange}'s interval
	 */
	long getInterval();

	/**
	 * 
	 * 
	 * Shift this {@code LongRange}.
	 * <p>
	 * A shift is defined as the increment of both start and stop by given
	 * offset. <br>
	 * If {@code offset} is negative, it will result in an decrement of this
	 * {@code LongRange}'s start and stop.
	 * </p>
	 * 
	 * @param offset
	 *            offset by which is shifted
	 * @return the new, shifted {@code LongRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	RangeLong shift(long offset) throws RangeException;

	/**
	 * 
	 * 
	 * Exactly the same as {@code LongRange#expandRange(long, false)}
	 * 
	 * @param offset
	 *            offset by which is expanded
	 * @return the new, expanded {@code LongRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	RangeLong expandRange(long offset) throws RangeException;

	/**
	 * 
	 * Expand this {@code LongRange}.
	 * <p>
	 * An expansion is defined as the increment of this {@code LongRange}'s
	 * length by {@code offset * 2}. <br>
	 * If {@code offset} is negative, it will result in an decrement of this
	 * {@code LongRange}'s length.
	 * </p>
	 * 
	 * @param offset
	 *            offset by which is expanded
	 * @param stayWithinLimits
	 *            if true, this operation will not result in a RangeException
	 * @return the new, expanded {@code LongRange}
	 * @throws RangeException
	 *             if this operation resulted in an invalid range
	 */
	RangeLong expandRange(long offset, boolean stayWithinLimits) throws RangeException;

	/**
	 * Check if this {@code LongRange} contains the given position.
	 * 
	 * @param position
	 *            position that is checked
	 * @return true, if given position is covered by this {@code LongRange};
	 *         false otherwise
	 */
	boolean includes(long position);

}
