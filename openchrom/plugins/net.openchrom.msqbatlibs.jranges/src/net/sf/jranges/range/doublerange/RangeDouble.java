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
package net.sf.jranges.range.doublerange;

import net.sf.jranges.range.Range;
import net.sf.jranges.range.RangeException;

/**
 * 
 * A {@code DoubleRange} is a {@link Range} based on {@code double} values. In
 * comparison to {@link net.sf.jranges.range.RangeInteger IntegerRange}, there
 * are some differences:
 * 
 * <ul>
 * <li>
 * There is no {@code DoubleRange#getLength()} method, since the number of
 * elements covered by this range is defined by the rules of IEEE arithmetic</li>
 * 
 * </ul>
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
 * <p>
 * <b>Threading:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * Not thread save.
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2014-02-11
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * 
 */

public interface RangeDouble extends Range, Comparable<RangeDouble> {

    /**
     * 
     * 
     * The same as {@code DoubleRange#expandRange(int, false)}
     * 
     * @param offset
     *            offset by which is expanded
     * @return the new, expanded {@code DoubleRange}
     * @throws RangeException
     *             if this operation resulted in an invalid range
     */
    RangeDouble expandRange(double offset) throws RangeException;

    /**
     * 
     * Expand this {@code DoubleRange}.
     * <p>
     * DoubleRange expansion is defined as the increment of this
     * {@code DoubleRange's} length by {@code offset * 2}. <br>
     * If {@code offset} is negative, it will result in an decrement of this
     * {@code DoubleRange's} length.
     * </p>
     * 
     * @param offset
     *            offset by which is expanded
     * @param stayWithinLimits
     *            if true, this operation will not result in a RangeException
     * @return the new, expanded {@code DoubleRange}
     * @throws RangeException
     *             if this operation resulted in an invalid range
     */
    RangeDouble expandRange(double offset, boolean stayWithinLimits) throws RangeException;

    /**
     * 
     * Retrieve this {@code DoubleRange's} interval.
     * 
     * @return this {@code DoubleRange's} interval
     */
    double getInterval();

    /**
     * 
     * Retrieve the number of positions covered by this {@code  DoubleRange}. <br>
     * If {@code interval == 1}, that will be typically defined as
     * {@link #getStop()} - {@link #getStart()} +1.
     * 
     * @return this {@code  DoubleRange}'s length
     */
    int getLength();

    /**
     * 
     * Retrieve this {@code DoubleRange's} start point.
     * 
     * @return this {@code DoubleRange's} start point
     */
    double getStart();

    /**
     * 
     * Retrieve this {@code DoubleRange's} stop point.
     * 
     * @return this {@code DoubleRange's} stop point
     */
    double getStop();

    /**
     * Check if this {@code DoubleRange} contains the given position.
     * 
     * @param position
     *            position that is checked
     * @return true, if given position is covered by this {@code DoubleRange};
     *         false otherwise
     */
    boolean includes(double position);

    boolean sharesPositionsWith(RangeDouble anotherRange);

    /**
     * 
     * 
     * Shift this {@code DoubleRange}.
     * <p>
     * A shift is defined as the increment of both start and stop by given
     * offset. <br>
     * If {@code offset} is negative, it will result in an decrement of this
     * {@code DoubleRange's} start and stop.
     * </p>
     * 
     * @param offset
     *            offset by which is shifted
     * @return the new, shifted {@code DoubleRange}
     * @throws RangeException
     *             if this operation resulted in an invalid range
     */
    RangeDouble shift(double offset) throws RangeException;

}
