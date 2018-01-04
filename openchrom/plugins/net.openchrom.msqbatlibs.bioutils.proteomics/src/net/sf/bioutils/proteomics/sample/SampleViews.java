/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.bioutils.proteomics.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jranges.range.UtilRange;
import net.sf.jranges.range.UtilRange.IntegerRangeTask;
import net.sf.jranges.range.doublerange.RangeDouble;
import net.sf.jranges.range.doublerange.impl.FactoryRangeDoubleZeroPositive;
import net.sf.jranges.range.doublerange.impl.RangeDoubleUtils;
import net.sf.jranges.range.doublerange.impl.ZeroPositiveDoubleRange;
import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.kerner.utils.collections.map.MapList;

public class SampleViews {

	public static RangeDouble findRangeMz(final Peak peak, final Collection<? extends RangeDouble> ranges) {

		for(final RangeDouble r : ranges) {
			if(r.includes(peak.getMz())) {
				return r;
			}
		}
		throw new RuntimeException("could not find valid range for " + peak);
	}

	public static MapList<RangeDouble, Peak> getBinningMz(final Collection<? extends RangeDouble> ranges, final Collection<? extends Peak> peaks) {

		final MapList<RangeDouble, Peak> result = new MapList<RangeDouble, Peak>();
		for(final Peak p : peaks) {
			result.put(findRangeMz(p, ranges), p);
		}
		return result;
	}

	public static List<Peak> getPeaksFromBinForMz(final MapList<RangeDouble, Peak> bins, final double mz) {

		for(final Entry<RangeDouble, List<Peak>> e : bins.entrySet()) {
			if(e.getKey().includes(mz)) {
				return e.getValue();
			}
		}
		return null;
	}

	public static List<Peak> getPeaksInRange(final MapList<Integer, Peak> bins, final RangeInteger fracRange) {

		if(bins == null || bins.isEmpty()) {
			throw new IllegalArgumentException();
		}
		final List<Peak> result = new ArrayList<Peak>();
		UtilRange.doForAllInRange(fracRange, new IntegerRangeTask() {

			public void call(final int i) {

				if(bins.containsKey(i)) {
					result.addAll(bins.get(i));
				} else {
					// if (log.isDebugEnabled()) {
					// log.debug("No entry for key " + i);
					// }
				}
			}
		});
		return result;
	}

	public static MapList<Integer, Peak> getViewFractions(final Collection<? extends Peak> peaks) {

		final MapList<Integer, Peak> result = new MapList<Integer, Peak>();
		for(final Peak p : peaks) {
			result.put(p.getFractionIndex(), p);
		}
		return result;
	}

	public static MapList<RangeDouble, Peak> getViewMassRanges(final Collection<? extends Peak> peaks) {

		final RangeDouble range = new ZeroPositiveDoubleRange(130.5655, 10254.6250, 1.0005);
		final ArrayList<RangeDouble> split = new ArrayList<RangeDouble>(RangeDoubleUtils.split(range, 6, new FactoryRangeDoubleZeroPositive()));
		final MapList<RangeDouble, Peak> result = getBinningMz(split, peaks);
		return result;
	}

	private SampleViews() {
	}
}
