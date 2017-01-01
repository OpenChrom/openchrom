/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.peak;

import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.math.UtilMath;

public class FilterPeakByMZ implements Filter<Peak> {

	public static int DEFAULT_ACCURACY = 1;
	private final int accuracy;
	private final double mz;

	public FilterPeakByMZ(final double mz) {
		this(mz, DEFAULT_ACCURACY);
	}

	public FilterPeakByMZ(final double mz, final int accuracy) {
		this.accuracy = accuracy;
		this.mz = mz;
	}

	public boolean filter(final Peak element) {

		return UtilMath.round(element.getMz(), accuracy) == UtilMath.round(mz, accuracy);
	}

	public String toString() {

		return "mz=" + mz + ",accuracy=" + accuracy;
	}
}
