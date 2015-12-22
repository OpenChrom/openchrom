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

import net.sf.jranges.range.doublerange.RangeDouble;
import net.sf.jranges.range.doublerange.impl.RangeDoubleDummy;
import net.sf.kerner.utils.collections.filter.Filter;

public class FilterPeakBySignalToNoise implements Filter<Peak> {

	public static enum TYPE {
		EXACT, MIN, MAX
	}

	private final RangeDouble range;

	public FilterPeakBySignalToNoise(final double intensity, final TYPE type) {

		switch(type) {
			case EXACT:
				range = new RangeDoubleDummy(intensity, intensity);
				break;
			case MIN:
				range = new RangeDoubleDummy(intensity, Double.MAX_VALUE);
				break;
			case MAX:
				range = new RangeDoubleDummy(Double.MIN_VALUE, intensity);
				break;
			default:
				throw new IllegalArgumentException("unknown type " + type);
		}
	}

	public FilterPeakBySignalToNoise(final RangeDouble range) {

		this.range = range;
	}

	public boolean filter(final Peak element) {

		return range.includes(element.getIntensity());
	}

	public String toString() {

		return "SN.range=" + range;
	}
}
