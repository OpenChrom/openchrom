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
package net.sf.jranges.range.integerrange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.kerner.utils.Util;
import net.sf.kerner.utils.math.ArithmeticSavety;

/**
 * Utility class for all kind of {@link net.sf.jranges.range.integerrange.RangeInteger Range} related operations.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-09-02
 */
public class UtilsRangeInteger {

    private UtilsRangeInteger() {
        // singleton
    }

    /**
     * Check whether a position is covered by at least one of the given {@code Range}s.
     * <p>
     * <b>Example:</b>
     * 
     * <pre>
     * List&lt;Range&gt; ranges = new ArrayList&lt;Range&gt;();
     * ranges.add(new DummyRange(1, 2));
     * ranges.add(new DummyRange(3, 4));
     * ranges.add(new DummyRange(5, 6));
     * ranges.add(new DummyRange(7, 8));
     * 
     * assertTrue(RangeUtils.includedByOne(ranges, 8));
     * assertFalse(RangeUtils.includedByOne(ranges, 0));
     * </pre>
     * 
     * </p>
     * 
     * @param ranges
     *            {@code List} of {@code Range}s that are checked for the coverage
     * @param position
     *            position that is checked
     * @return true, if given position is contained by at least one of the given ranges; false otherwise
     */
    public static boolean includedByOne(final List<? extends RangeInteger> ranges, final int position) {
        for (final RangeInteger r : ranges) {
            if (r.includes(position)) {
                return true;
            }
        }
        return false;
    }

    public static int numberOfAppearances(final List<? extends RangeInteger> ranges, final int position) {
        int result = 0;
        for (final RangeInteger r : ranges) {
            if (r.includes(position)) {
                result++;
            }
        }
        return result;
    }

    public static long numberOfPositions(final List<? extends RangeInteger> ranges) {
        long result = 0;
        for (final RangeInteger r : ranges) {
            result += r.getLength();
        }
        return result;
    }

    /**
     * Merge one or more {@code Range}s.
     * <p>
     * <b>Example:</b>
     * 
     * <pre>
     * List&lt;Range&gt; ranges = new ArrayList&lt;Range&gt;();
     * ranges.add(new DummyRange(1, 2));
     * ranges.add(new DummyRange(3, 4));
     * ranges.add(new DummyRange(5, 6));
     * ranges.add(new DummyRange(7, 8));
     * 
     * assertEquals(new DummyRange(1, 8), RangeUtils.merge(ranges, new DummyRangeFactory()));
     * </pre>
     * 
     * </p>
     * 
     * @param ranges
     *            {@code Range}s that are to be merged
     * @param factory
     *            {@link net.sf.jranges.range.integerrange.IntegerRangeFactory RangeFactory} that is used for retrieving a
     *            new {@code Range} -instance
     * @return a new {@code Range}, that represents the merge of the given {@code Range}s
     * @see IntegerRangeFactory
     */
    public static <T extends RangeInteger> T merge(final List<? extends RangeInteger> ranges,
            final IntegerRangeFactory<T> factory) {
        if (ranges.isEmpty())
            return factory.create();
        final RangeInteger result = factory.create(ranges.iterator().next());
        int start = result.getStart();
        int stop = result.getStop();
        for (final RangeInteger r : ranges) {
            if (r.getStart() < start)
                start = r.getStart();
            if (r.getStop() > stop)
                stop = r.getStop();
        }
        return factory.create(start, stop);
    }

    public static Map<Integer, Integer> positionFrequencies(final List<? extends RangeInteger> ranges,
            final List<? extends RangeInteger> ranges2) {
        Util.checkForNull(ranges, ranges2);

        final Map<Integer, Integer> result = new HashMap<Integer, Integer>();

        for (final RangeInteger range : ranges2) {
            for (int i = range.getStart(); i <= range.getStop(); i++) {
                final int n = numberOfAppearances(ranges, i);
                final Integer key = Integer.valueOf(i);
                final Integer value = result.get(key);
                if (value == null)
                    result.put(key, Integer.valueOf(n));
                else
                    result.put(key, ArithmeticSavety.add(value, n));
            }
        }

        return result;
    }

    public static double medianPositionFrequencies(final List<? extends RangeInteger> ranges,
            final List<? extends RangeInteger> ranges2) {

        final Map<Integer, Integer> map = positionFrequencies(ranges, ranges2);

        if (map.size() == 0)
            return 0;

        int result = 0;

        for (final Integer value : map.values()) {
            result += value.intValue();
        }

        return (double) result / (double) map.size();
    }

    public static <R extends RangeInteger> R trimmValidStart(final R range, final int minStart, final int maxStart,
            final IntegerRangeFactory<R> factory) {
        final int start = trimToBounds(range.getStart(), minStart, maxStart);
        return factory.create(start, range.getStop());
    }

    public static <R extends RangeInteger> R trimmValidStop(final R range, final int minStop, final int maxStop,
            final IntegerRangeFactory<R> factory) {
        final int stop = trimToBounds(range.getStop(), minStop, maxStop);
        return factory.create(range.getStart(), stop);
    }

    public static <R extends RangeInteger> R trimmValid(final R range, final int minStart, final int maxStop,
            final IntegerRangeFactory<? extends R> factory) {
        final int start = trimToBounds(range.getStart(), minStart, maxStop);
        final int stop = trimToBounds(range.getStop(), minStart, maxStop);
        return factory.create(start, stop);
    }

    public static int trimToBounds(int value, final int min, final int max) {
        if (value < min) {
            value = min;
        }
        if (value > max) {
            value = max;
        }
        return value;
    }

}
