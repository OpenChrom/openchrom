/*******************************************************************************
 * Copyright (c) 2013, 2016 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
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
