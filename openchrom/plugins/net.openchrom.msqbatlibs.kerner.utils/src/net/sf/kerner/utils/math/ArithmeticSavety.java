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

/**
 * Perform arithmetic operations with number over- and underflow check.
 * <p>
 * <b>Example:</b><br>
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
 * @version 2011-07-07
 */
public class ArithmeticSavety {

    public static final BigInteger BIG_MAX_INT = BigInteger.valueOf(Integer.MAX_VALUE);
    public static final BigInteger BIG_MIN_INT = BigInteger.valueOf(Integer.MIN_VALUE);

    public static final BigInteger BIG_MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
    public static final BigInteger BIG_MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);

    public static final BigDecimal BIG_MAX_DECIMAL = BigDecimal.valueOf(Double.MAX_VALUE);
    public static final BigDecimal BIG_MIN_DECIMAL = BigDecimal.valueOf(Double.MIN_VALUE);

    /**
     * Check if {@code {@link Integer#MIN_VALUE} <= b <=
     * {@link Integer#MAX_VALUE} .
     * 
     * @param b
     *            {@code BigInteger} to check if in range
     * @see BigInteger
     */
    public static void rangeCheckInteger(BigInteger b) {
        if (b.compareTo(BIG_MAX_INT) == 1 || b.compareTo(BIG_MIN_INT) == -1)
            throw new ArithmeticException("Integer overflow");
    }

    /**
     * Check if {@code {@link Long#MIN_VALUE} <= b <= {@link Long#MAX_VALUE} .
     * 
     * @param b
     *            {@code BigInteger} to check if in range
     * @see BigInteger
     */
    public static void rangeCheckLong(BigInteger b) {
        if (b.compareTo(BIG_MAX_LONG) == 1 || b.compareTo(BIG_MIN_LONG) == -1)
            throw new ArithmeticException("Double overflow");
    }

    public static void rangeCheckDouble(BigDecimal b) {
        if (b.compareTo(BIG_MAX_DECIMAL) == 1 || b.compareTo(BIG_MIN_DECIMAL) == -1)
            throw new ArithmeticException("Double overflow");
    }

    /**
     * Perform addition of two {@code int} values with arithmetic safety. if
     * resulting {@code int} would over- or underflow an exception will be
     * thrown.
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return {@code a + b}
     */
    public static int addInt(int a, int b) {
        final BigInteger bb = BigInteger.valueOf(a).add(BigInteger.valueOf(b));
        rangeCheckInteger(bb);
        return bb.intValue();
    }

    /**
     * Perform addition of two {@code long} values with arithmetic safety. if
     * resulting {@code long} would over- or underflow an exception will be
     * thrown.
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return {@code a + b}
     */
    public static long addLong(long a, long b) {
        final BigInteger bb = BigInteger.valueOf(a).add(BigInteger.valueOf(b));
        rangeCheckLong(bb);
        return bb.longValue();
    }

    /**
     * Perform multiplication of two {@code long} values with arithmetic safety.
     * if resulting {@code long} would over- or underflow an exception will be
     * thrown.
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return {@code a * b}
     */
    public static int multiply(int a, int b) {
        final BigInteger bb = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
        rangeCheckInteger(bb);
        return bb.intValue();
    }

    /**
     * Perform multiplication of two {@code long} values with arithmetic safety.
     * if resulting {@code long} would over- or underflow an exception will be
     * thrown.
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return {@code a * b}
     */
    public static long multiply(long a, long b) {
        final BigInteger bb = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
        rangeCheckLong(bb);
        return bb.longValue();
    }

    public static double multiply(double a, double b) {
        final BigDecimal bb = BigDecimal.valueOf(a).multiply(BigDecimal.valueOf(b));
        rangeCheckDouble(bb);
        return bb.doubleValue();
    }

    /**
     * Increment a {@code BigInteger} with arithmetic safety.
     * 
     * @param integer
     *            {@code BigInteger} to increment
     * @return incremented {@code BigInteger}
     */
    public static Integer increment(Integer integer) {
        return Integer.valueOf(addInt(integer.intValue(), 1));
    }

    /**
     * Add one {@link Integer} to another {@link Integer} with arithmetic
     * safety.
     * 
     * @param integer1
     *            first value
     * @param integer2
     *            second value
     * @return {@code a + b}
     */
    public static Integer add(Integer integer1, Integer integer2) {
        return Integer.valueOf(addInt(integer1.intValue(), integer2.intValue()));
    }

}
