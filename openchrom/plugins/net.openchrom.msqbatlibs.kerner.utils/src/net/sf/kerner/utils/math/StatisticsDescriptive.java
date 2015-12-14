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

public interface StatisticsDescriptive {

	/**
	 * Get maximum of given values.
	 *
	 * @return maximum value
	 *
	 */
	double getMax();

	/**
	 * Calculate the {@code mean} of given values.
	 *
	 * @return mean of values
	 *
	 */
	double getMean();

	double getMedian();

	double getMin();

	int getN();

	double getSum();
}
