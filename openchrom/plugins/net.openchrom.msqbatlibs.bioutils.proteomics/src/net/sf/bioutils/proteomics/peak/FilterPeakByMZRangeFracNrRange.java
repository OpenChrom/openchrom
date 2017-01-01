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

import net.sf.jranges.range.doublerange.RangeDouble;
import net.sf.kerner.utils.collections.filter.Filter;

public class FilterPeakByMZRangeFracNrRange implements Filter<Peak> {

	private final RangeDouble fracRange;
	private final RangeDouble mzRange;

	public FilterPeakByMZRangeFracNrRange(final RangeDouble fracRange, final RangeDouble mzRange) {
		this.fracRange = fracRange;
		this.mzRange = mzRange;
	}

	public boolean filter(final Peak e) {

		return fracRange.includes(e.getFractionIndex()) && mzRange.includes(e.getMz());
	}
}
