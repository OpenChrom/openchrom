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
import net.sf.kerner.utils.math.UtilMath;

public abstract class AbstractDoubleRange extends VeryAbstractDoubleRange implements RangeDouble {

    public static final int ACCURACY = 6;

    protected static boolean inValid(final double start, final double stop, final double interval) {
        // System.out.println("diff:" + UtilMath.round(stop - start,
        // ACCURACY));
        // System.out.println("mod:" + UtilMath.round((UtilMath.round(stop -
        // start, ACCURACY)) % interval, ACCURACY));
        // System.out.println("return:" + (UtilMath.round((UtilMath.round(stop
        // - start, ACCURACY)) % interval, ACCURACY) == 0));
        return (UtilMath.round((UtilMath.round(stop - start, ACCURACY)) % interval, ACCURACY) != 0

        // double accuracy workaround
        && UtilMath.round((UtilMath.round(stop - start, ACCURACY)) % interval, ACCURACY) != interval);
    }

    /**
     * This {@code AbstractDoubleRange's} interval.
     */
    protected final double interval;

    /**
     * This {@code AbstractDoubleRange's} lower limit, which is the smallest possible position that is valid for
     * {@code start}.
     */
    protected final double limit1;

    // Constructor //

    /**
     * This {@code AbstractDoubleRange's} upper limit, which is the biggest possible position that is valid for
     * {@code stop}.
     */
    protected final double limit2;

    /**
     * Construct a new {@code AbstractDoubleRange}.
     * <p>
     * For every {@code AbstractDoubleRange} following is true: <br>
     * {@code limit1 <= start <= stop <= limit2}
     * </p>
     * 
     * @param start
     *            start point of this {@code AbstractDoubleRange}
     * @param stop
     *            stop point of this {@code AbstractDoubleRange}
     * @param limit1
     *            minimum value that is valid for this {@code AbstractDoubleRange's} start point
     * @param limit2
     *            maximum value that is valid for this {@code AbstractDoubleRange's} stop point
     * @throws RangeException
     *             if {@code limit1 > start || limit2 < stop || start > stop}
     */
    public AbstractDoubleRange(final double start, final double stop, final double limit1, final double limit2)
            throws RangeException {
        if (limit1 > start || limit2 < stop || start > stop)
            throw new RangeException("invalid range" + " start=" + start + " stop=" + stop + " limit1=" + limit1
                    + " limit2=" + limit2);
        this.interval = 1;
        this.limit1 = limit1;
        this.limit2 = limit2;
        this.stop = stop;
        this.start = start;
    }

    /**
     * Construct a new {@code AbstractDoubleRange}.
     * <p>
     * For every {@code AbstractDoubleRange} following is true: <br>
     * {@code limit1 <= start <= stop <= limit2}
     * </p>
     * 
     * @param start
     *            start point of this {@code AbstractDoubleRange}
     * @param stop
     *            stop point of this {@code AbstractDoubleRange}
     * @param limit1
     *            minimum value that is valid for this {@code AbstractDoubleRange's} start point
     * @param limit2
     *            maximum value that is valid for this {@code AbstractDoubleRange's} stop point
     * @param interval
     *            interval of this {@code AbstractDoubleRange}
     * @throws RangeException
     *             if {@code limit1 > start || limit2 < stop || start > stop || (((stop - start) % interval) != 0)}
     */
    public AbstractDoubleRange(final double start, final double stop, final double limit1, final double limit2,
            final double interval) throws RangeException {
        if (limit1 > start || limit2 < stop || start > stop || (inValid(start, stop, interval)))
            throw new RangeException("invalid range" + " start=" + start + " stop=" + stop + " limit1=" + limit1
                    + " limit2=" + limit2 + "interval=" + interval);
        this.limit1 = limit1;
        this.limit2 = limit2;
        this.interval = interval;
        this.stop = stop;
        this.start = start;
    }

    // Public //

    public RangeDouble expandRange(final double offset) throws RangeException {
        return expandRange(offset, false);
    }

    public RangeDouble expandRange(final double offset, final boolean stayWithinLimits) throws RangeException {
        double start = this.start;
        double stop = this.stop;
        if (stayWithinLimits) {
            start = getStart() - offset;
            stop = getStop() + offset;

            if (start < limit1) {
                start = limit1;
            }
            if (stop > limit2) {
                stop = limit2;
            }
            return newInstange(start, stop, getLimit1(), getLimit2());
        } else {
            start = getStart() - offset;
            stop = getStop() + offset;
            return newInstange(start, stop, getLimit1(), getLimit2());
        }
    }

    // Override //

    @Override
    public double getInterval() {
        return interval;
    }

    /**
     * Retrieve this {@code AbstractDoubleRange's} lower limit. This is the smallest possible value for {@code start}.
     * 
     * @return lower limit for this {@code AbstractDoubleRange}
     */
    public double getLimit1() {
        return limit1;
    }

    // Implement //

    /**
     * Retrieve this {@code AbstractDoubleRange's} upper limit. This is the greatest possible value for {@code stop}.
     * 
     * @return upper limit for this {@code AbstractDoubleRange}
     */
    public double getLimit2() {
        return limit2;
    }

    @Override
    public boolean includes(final double position) {
        if (interval == 1)
            return super.includes(position);
        if (position == getStart() || position == getStop())
            return true;
        return ((UtilMath.round(UtilMath.round(position - start, ACCURACY) % interval, ACCURACY) == 0) || (UtilMath
                .round(UtilMath.round(position - start, ACCURACY) % interval, ACCURACY) == interval))
                && position <= stop;
    }

    /**
     * Retrieve a new {@code AbstractDoubleRange} instance based on the given parameter.
     * 
     * @param start
     *            start point for new {@code AbstractDoubleRange}
     * @param stop
     *            stop point for new {@code AbstractDoubleRange}
     * @param limit1
     *            limit1 point for new {@code AbstractDoubleRange}
     * @param limit2
     *            limit2 point for new {@code AbstractDoubleRange}
     * @return the new {@code AbstractDoubleRange} instance
     * @throws RangeException
     */
    protected abstract AbstractDoubleRange newInstange(double start, double stop, double limit1, double limit2)
            throws RangeException;

    // Abstract //

    public RangeDouble shift(final double offset) throws RangeException {
        return newInstange(getStart() + offset, getStop() + offset, getLimit1(), getLimit2());
    }
}
