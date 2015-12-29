/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.kerner.utils.collections.filter.Filter;

/**
 * A {@link Filter} that checks weather a given {@link Peak}'s fraction number
 * is within given {@link Range}.
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
 * @version 2013-10-18
 */
public class FilterPeakByFractionIndexRange implements Filter<Peak> {

	private final RangeInteger range;

	/**
	 * Create a new {@code FilterPeakByMZRange}.
	 *
	 * @param range
	 *            m/z range in which a peak's m/z must be in in order for this {@code Filter} t accept this peak
	 */
	public FilterPeakByFractionIndexRange(final RangeInteger range) {
		this.range = range;
	}

	public boolean filter(final Peak element) {

		return range.includes(element.getFractionIndex());
	}

	public String toString() {

		return "fracRange=" + range;
	}
}
