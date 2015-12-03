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

import java.util.ArrayList;
import java.util.List;

import net.sf.jranges.range.doublerange.RangeDouble;
import net.sf.jranges.range.doublerange.FactoryRangeDouble;
import net.sf.kerner.utils.math.UtilMath;

public class RangeDoubleUtils {

    private static double increment(double number, final double interval, final int accuracy) {
        return UtilMath.round(number += interval, accuracy);
    }

    /**
     * <pre>
     * [0.0->1.0, 1.0->2.0, 2.0->3.0, 3.0->4.0, 4.0->5.0, 5.0->6.0, 6.0->7.0, 7.0->8.0, 8.0->9.0, 9.0->10.0]
     * </pre>
     */
    public static <R extends RangeDouble> List<R> split(final RangeDouble range, final int accuracy,
            final FactoryRangeDouble<R> factory) {

        // System.err.println("split range " + range);

        final List<R> result = new ArrayList<R>();

        double last = -1;
        for (double d = range.getStart(); d <= range.getStop(); d = increment(d, range.getInterval(), accuracy)) {
            if (last == -1) {
                // skip first
            } else {
                result.add(factory.create(last, d));
            }
            last = d;
        }

        // System.err.println("splitted " + result);

        return result;

    }

    private RangeDoubleUtils() {
    }

}
