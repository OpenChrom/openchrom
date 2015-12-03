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
package net.sf.kerner.utils.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.kerner.utils.UtilArray;

/**
 * Provides static helper methods.
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-03-06
 */
public class UtilMath {

    /**
     * Add one {@link Integer} to another.
     *
     * @param integer1
     *            first {@link Integer}
     * @param integer2
     *            second {@link Integer}
     * @return sum of {@code integer1} and {@code integer2}
     */
    public static Integer add(final Integer integer1, final Integer integer2) {
        return Integer.valueOf(integer1.intValue() + integer2.intValue());
    }

    public static double assertAboveEqual(final double number, final double value) {
        if (number < value) {
            return value;
        }
        return number;
    }

    public static double assertBelowEqual(final double number, final double value) {
        if (number > value) {
            return value;
        }
        return number;
    }

    public static boolean equals(final double d1, final double d2, final int decimalPlace) {
        final double d11 = round(d1, decimalPlace);
        final double d22 = round(d2, decimalPlace);
        return Double.valueOf(d11).equals(Double.valueOf(d22));
    }

    public static BigInteger factorial(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("not defined for numbers < 0");
        }
        BigInteger result = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static double getClosest(final double number, final Collection<? extends Number> values) {
        return getClosest(number, UtilArray.toPrimitive(values.toArray(new Number[values.size()])));
    }

    public static double getClosest(final double number, final double... values) {
        if (values == null || values.length < 1)
            throw new IllegalArgumentException();
        if (values.length == 1) {
            return values[0];
        }
        final double[] arr = UtilArray.copy(values);
        Arrays.sort(arr);
        double result = arr[0];
        double diff = Math.abs(arr[0] - number);
        for (int i = 1; i < arr.length; i++) {
            final double diff2 = Math.abs(arr[i] - number);
            if (diff2 < diff) {
                diff = diff2;
                result = arr[i];
            }
        }
        return result;
    }

    public static double getCV(final Collection<? extends Number> values) {
        return getCV(UtilArray.toPrimitive(values.toArray(new Number[values.size()])));
    }

    public static double getCV(final double[] values) {
        return new StatisticsDescriptiveGaussianDistributionImpl(values)
                .getCoefficientOfVariation();
    }

    public static double getHigher(final double d1, final double d2) {
        if (d1 > d2)
            return d1;
        else
            return d2;
    }

    public static double getLower(final double d1, final double d2) {
        if (d1 < d2)
            return d1;
        else
            return d2;
    }

    /**
     * Get maximum of given values.
     *
     * @param values
     *            values to get maximum from
     * @return maximum value
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getMax(final Collection<? extends Number> values) {
        return getMax(UtilArray.toPrimitive(values.toArray(new Number[values.size()])));
    }

    /**
     * Get maximum of given values.
     *
     * @param values
     *            values to get maximum from
     * @return maximum value
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getMax(final double... values) {
        return new StatisticsDescriptiveGaussianDistributionImpl(values).getMax();
    }

    /**
     * Calculate the {@code mean} of given values.
     *
     * @param values
     *            values to calculate the mean
     * @return mean of values
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getMean(final Collection<? extends Number> values) {
        return getMean(UtilArray.toPrimitive(values.toArray(new Number[values.size()])));
    }

    /**
     * Calculate the {@code mean} of given values.
     *
     * @param values
     *            values to calculate the mean
     * @return mean of values
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getMean(final double... values) {
        return new StatisticsDescriptiveGaussianDistributionImpl(values).getMean();
    }

    /**
     * Calculates the <a href="http://de.wikipedia.org/wiki/Visitor">median</a>
     * of a collection of numbers.
     *
     * @param values
     *            numbers to calculate median from
     * @return median for given numbers
     */
    public static double getMedian(final Collection<? extends Number> values) {
        return getMedian(UtilArray.toPrimitive(values.toArray(new Number[values.size()])));
    }

    public static double getMedian(final double... values) {
        return new StatisticsDescriptiveGaussianDistributionImpl(values).getMedian();
    }

    /**
     * Get minimum of given values.
     *
     * @param values
     *            values to get minimum from
     * @return minimum value
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getMin(final Collection<? extends Number> values) {
        return getMin(UtilArray.toPrimitive(values.toArray(new Number[values.size()])));
    }

    /**
     * Get minimum of given values.
     *
     * @param values
     *            values to get minimum from
     * @return minimum value
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getMin(final double... values) {
        return new StatisticsDescriptiveGaussianDistributionImpl(values).getMin();
    }

    /**
     * Calculate the {@code standard deviation} of given values.
     *
     * @param values
     *            values to calculate the standard deviation
     * @return the {@code standard deviation} of given values
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getStdDev(final Collection<? extends Number> values) {
        return getStdDev(UtilArray.toPrimitive(values.toArray(new Number[values.size()])));
    }

    /**
     * Calculate the {@code standard deviation} of given values.
     *
     * @param values
     *            values to calculate the standard deviation for
     * @return the {@code standard deviation} of given values
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getStdDev(final double... values) {
        return Math.sqrt(getVariance(values));
    }

    /**
     *
     * @param values
     *            to calculate the variance for
     * @return the variance of given values
     *
     * @throws IllegalArgumentException
     *             if {@code values.length < 1} or {@code values == null}
     */
    public static double getVariance(final double... values) {
        return new StatisticsDescriptiveGaussianDistributionImpl(values).getVariance();
    }

    /**
     * Increment given {@link Integer} by {@code 1}.
     *
     * @param integer
     *            {@link Integer} to increment
     * @return incremented {@link Integer}
     */
    public static Integer increment(final Integer integer) {
        return increment(integer, 1);
    }

    /**
     * Increment given {@link Integer} by {i}.
     *
     * @param integer
     *            {@link Integer} to increment
     * @return incremented {@link Integer}
     */
    public static Integer increment(final Integer integer, final int i) {
        return Integer.valueOf(integer.intValue() + i);
    }

    /**
     *
     * @param number
     *            number of logarithm
     * @param base
     *            base of logarithm
     * @return the logarithm with base {@code base} of {@code number}
     */
    public static double log(final double number, final double base) {
        return Math.log(number) / Math.log(base);
    }

    public static double log10(final double number) {
        return log(number, 10);
    }

    public static double log2(final double number) {
        return log(number, 2);
    }

    /**
     * Round a floating point number ({@code double}) with an accuracy up to
     * given decimal place.
     *
     * @param number
     *            {@code double} that is rounded to given decimal place
     * @param decimalPlace
     *            decimal place to which given {@code double} is rounded
     * @return rounded {@code double}
     */
    public static double round(final double number, final int decimalPlaces) {
        if (Double.isNaN(number) || Double.isInfinite(number)) {
            return number;
        }
        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Integer substract(final Integer integer, final int i) {
        return Integer.valueOf(integer - i);
    }

    public static List<Integer> substract(final List<Integer> integers, final int i) {
        final List<Integer> result = new ArrayList<Integer>();
        for (final Integer integer : integers) {
            result.add(substract(integer, i));
        }
        return result;
    }

    public static double sum(final Collection<? extends Number> values) {
        return sum(UtilArray.toPrimitive(values.toArray(new Number[values.size()])));
    }

    /**
     * Calculate the {@code sum} of given values.
     *
     * @param values
     *            values to calculate the sum
     * @return sum of values * @throws IllegalArgumentException if
     *         {@code values.length < 1} or {@code values == null}
     */
    public static double sum(final double... values) {
        return new StatisticsDescriptiveGaussianDistributionImpl(values).getSum();
    }

    public static BigDecimal sumBig(final Collection<? extends BigDecimal> values) {
        final BigDecimal result = BigDecimal.ZERO;
        for (final BigDecimal n : values) {
            result.add(n);
        }
        return result;
    }

    private UtilMath() {
    }

    public int getIndexOfMax(final List<? extends Number> values) {
        Double result = values.iterator().next().doubleValue();
        for (final Iterator<? extends Number> iterator = values.iterator(); iterator.hasNext();) {
            final Double number = iterator.next().doubleValue();
            if (number.compareTo(result) > 0) {
                result = number;
            }
        }
        return values.indexOf(result);
    }

    public int getIndexOfMin(final List<? extends Number> values) {
        Double result = values.iterator().next().doubleValue();
        for (final Iterator<? extends Number> iterator = values.iterator(); iterator.hasNext();) {
            final Double number = iterator.next().doubleValue();
            if (number.compareTo(result) < 0) {
                result = number;
            }
        }
        return values.indexOf(result);
    }

}
