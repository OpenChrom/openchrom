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
package net.sf.kerner.utils;

import java.util.Arrays;
import java.util.Collection;

/**
 * Utility class for array stuff.
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-11-20
 */
public class UtilArray {

    public static byte[] copy(final byte[] array) {
        final byte[] result = new byte[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static double[] copy(final double[] array) {
        final double[] result = new double[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static int[] copy(final int[] array) {
        final int[] result = new int[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static Object[] copy(final Object[] array) {
        final Object[] result = new Object[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
    }

    public static boolean emptyArray(final double[] arr) {
        if (arr == null)
            return true;
        if (arr.length < 1)
            return true;
        return false;
    }

    public static boolean emptyArray(final int[] arr) {
        if (arr == null)
            return true;
        if (arr.length < 1)
            return true;
        return false;
    }

    /**
     * Check weather an array is {@code null} or contains no elements.
     *
     * @param arr
     *            array to check
     * @return true, if {@code arr} is {@code null} or contains no elements
     */
    public static boolean emptyArray(final Object[] arr) {
        if (arr == null)
            return true;
        if (arr.length < 1)
            return true;
        return false;
    }

    public static <T> T[] fill(final T[] array, final int size) {
        if (array.length >= size) {
            return array;
        }
        final T[] result = Arrays.copyOf(array, size);
        return result;
    }

    /**
     * Determines if this Class object represents an array class.
     *
     * @param o
     *            Object to ckeck
     * @return {@code true}, if this object represents an array; {@code false}
     *         otherwise
     */
    public static boolean isArray(final Object o) {
        return o.getClass().isArray();
    }

    /**
     * Check if an array
     * <ul>
     * <li>is {@code null}</li>
     * <li>is empty or</li>
     * <li>contains only {@code null} elements.</li>
     * </ul>
     *
     * @param arr
     *            array to check
     * @return true, if {@code arr} is {@code null}, empty or if it contains
     *         only {@code null} elements; {@code false} otherwise
     */
    public static boolean nullArray(final Object[] arr) {
        if (emptyArray(arr))
            return true;
        for (final Object o : arr) {
            if (o != null) {
                if (isArray(o))
                    return nullArray((Object[]) o);
                else
                    return false;
            }
        }
        return true;
    }

    public static byte[] toBytesFromASCII(final char[] chars) {
        final byte[] b = new byte[chars.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) chars[i];
        }
        return b;
    }

    public static Double[] toDouble(final Collection<? extends Number> numbers) {
        return toDouble(numbers.toArray(new Number[numbers.size()]));
    }

    public static Double[] toDouble(final Number... numbers) {
        final Double[] result = new Double[numbers.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Double(numbers[i].doubleValue());
        }
        return result;
    }

    public static Byte toObject(final byte i) {
        return Byte.valueOf(i);
    }

    public static Byte[] toObject(final byte[] arr) {
        final Byte[] result = new Byte[arr.length];
        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i];
        return result;
    }

    public static Double toObject(final double d) {
        return Double.valueOf(d);
    }

    public static Double[] toObject(final double[] arr) {
        final Double[] result = new Double[arr.length];
        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i];
        return result;
    }

    public static Integer toObject(final int i) {
        return Integer.valueOf(i);
    }

    public static Integer[] toObject(final int[] arr) {
        final Integer[] result = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i];
        return result;
    }

    /**
     *
     * Returns a {@code double} array containing the {@code double}
     * representation of given {@link Double} array. </p> Note:
     * {@code null values} in {@code arr} are ignored! Length of returned array
     * may be less than length of input array therefore.
     *
     * @param arr
     *            {@link Double} array to convert to {@code double} array
     * @return a {@code double} array containing the {@code double}
     *         representation of given {@link Double} array
     */
    public static double[] toPrimitive(final Double[] arr) {
        final double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i];
        return result;
    }

    /**
     *
     * Returns a {@code int} array containing the {@code int} representation of
     * given {@link Integer} array. </p> Note: {@code null values} in
     * {@code arr} are ignored! Length of returned array may be less than length
     * of input array therefore.
     *
     * @param arr
     *            {@link Integer} array to convert to {@code int} array
     * @return a {@code int} array containing the {@code int} representation of
     *         given {@link Integer} array
     */
    public static int[] toPrimitive(final Integer[] arr) {
        final int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            if (arr[i] != null) {
                result[i] = arr[i];
            }
        return result;
    }

    /**
     *
     * Returns a {@code double} array containing the {@code double}
     * representation of given {@link Number} array. </p> Note:
     * {@code null values} in {@code arr} are ignored! Length of returned array
     * may be less than length of input array therefore.
     *
     * @param arr
     *            {@link Number} array to convert to {@code double} array
     * @return a {@code double} array containing the {@code double}
     *         representation of given {@link Number} array
     */
    public static double[] toPrimitive(final Number[] arr) {
        final double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                result[i] = arr[i].doubleValue();
            }
        }
        return result;
    }

    /**
     * Trim an array to given size. All elements with {@code index > size} will
     * be discarded.
     *
     * @param array
     *            array that is trimmed
     * @param size
     *            the new length of the array
     * @return the trimmed array
     */
    public static byte[] trim(final byte[] array, final int size) {
        if (size < 0) {
            return new byte[0];
        }
        if (array.length <= size)
            return array;
        final byte[] result = new byte[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    /**
     * Trim an array to given size. All elements with {@code index > size} will
     * be discarded.
     *
     * @param array
     *            array that is trimmed
     * @param size
     *            the new length of the array
     * @return the trimmed array
     */
    public static char[] trim(final char[] array, final int size) {
        if (size < 0) {
            return new char[0];
        }
        if (array.length <= size)
            return array;
        final char[] result = new char[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    /**
     * Trim an array to given size. All elements with {@code index > size} will
     * be discarded.
     *
     * @param array
     *            array that is trimmed
     * @param size
     *            the new length of the array
     * @return the trimmed array
     */
    public static int[] trim(final int[] array, final int size) {
        if (size < 0) {
            return new int[0];
        }
        if (array.length <= size)
            return array;
        final int[] result = new int[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    private UtilArray() {

    }

}
