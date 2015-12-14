/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.math;

import java.util.Random;

/**
 * {@code RandomFactory} provides static methods to generate random numbers.
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
 * @version 2012-03-06
 */
public class UtilRandom {

	private final static Random R = new Random();

	private UtilRandom() {

	}

	/**
	 * Generate a random {@code double} for which the following is true: </p> {@code random = value +- delta}.
	 * 
	 * @param value
	 * @param delta
	 * @return new random [@code double}
	 */
	public static double generateAround(final double value, final double delta) {

		final double low = value - delta;
		final double heigh = value + delta;
		return generateBetween(low, heigh);
	}

	/**
	 * Generate a random {@code double} for which the following is true: </p> {@code low <= random <= high}.
	 * 
	 * @param low
	 *            lower boundary, inclusive
	 * @param high
	 *            upper boundary, exclusive
	 * @return new random [@code double}
	 */
	public static double generateBetween(final double low, final double high) {

		return low + (high - low) * R.nextDouble();
	}

	/**
	 * Generate a random {@code int} for which the following is true: </p> {@code random = value +- delta}.
	 * 
	 * @param value
	 * @param delta
	 * @return new random [@code int}
	 */
	public static int generateAround(final int value, final int delta) {

		final int low = value - delta;
		final int heigh = value + delta;
		return generateBetween(low, heigh);
	}

	/**
	 * Generate a random {@code int} for which the following is true: </p> {@code low <= random <= high}.
	 * 
	 * @param low
	 *            lower boundary, inclusive
	 * @param high
	 *            upper boundary, inclusive
	 * @return new random [@code int}
	 */
	public static int generateBetween(final int low, final int high) {

		return low + R.nextInt(high + 1 - low);
	}

	public static boolean generateWithProbability(double probability) {

		if(probability > 1)
			return true;
		if(probability <= 0)
			return false;
		probability = (int)(probability * 100);
		int result = generateBetween(0, 100);
		// System.out.println("prob="+probability);
		// System.out.println("result="+result);
		return result <= probability;
	}

	public static boolean generate() {

		return R.nextBoolean();
	}
}
