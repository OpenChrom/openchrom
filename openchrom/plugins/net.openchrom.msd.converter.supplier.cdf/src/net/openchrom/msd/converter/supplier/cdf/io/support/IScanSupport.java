/*******************************************************************************
 * Copyright (c) 2013, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cdf.io.support;

public interface IScanSupport {

	/**
	 * Returns the scan index for the given scan.
	 * 
	 * @param scan
	 * @return int
	 */
	int getScanIndex(int scan);

	/**
	 * Returns the point count (number of ions) for the given scan.
	 * 
	 * @param scan
	 * @return int
	 */
	int getPointCount(int scan);

	/**
	 * Returns the min ion of the given scan.
	 * 
	 * @param scan
	 * @return double
	 */
	double getMinIon(int scan);

	/**
	 * Returns the max ion of the given scan.
	 * 
	 * @param scan
	 * @return double
	 */
	double getMaxIon(int scan);
}
