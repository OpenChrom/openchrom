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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.collections.comparator.ComparatorInverter;
import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.collections.list.UtilList;
import net.sf.kerner.utils.transformer.Transformer;

/**
 *
 * TODO description
 *
 * <p>
 * <b>Example:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 * <p>
 * <b>Threading:</b><br>
 *
 * </p>
 * <p>
 *
 * <pre>
 * Not thread save.
 * </pre>
 *
 * </p>
 * <p>
 * last reviewed: 2014-02-18
 * </p>
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 *
 */
public class UtilPeak {

	public static List<Peak> cast(final Collection<?> peaks) {

		return UtilList.cast(peaks);
	}

	public static List<Peak> filter(final List<? extends Peak> peaks, final Filter<Peak> filter) {

		return UtilList.filterList(peaks, filter);
	}

	public static <T extends Peak> T findClosestToMZ(final Iterable<T> peaks, final double mass) {

		T result = null;
		for(final T p : peaks) {
			if(result == null)
				result = p;
			else {
				final double massDiffNew = Math.abs(mass - p.getMz());
				// TODO cache this
				final double massDiffOld = Math.abs(mass - result.getMz());
				if(massDiffNew < massDiffOld) {
					result = p;
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param peaks
	 *            {@link Peak Peaks} to find peak with the highest intensity
	 *            from
	 * @return {@link Peak} that has the highest intensity of all given {@code peaks}
	 */
	public static <T extends Peak> T findHighestIntensity(final Collection<? extends T> peaks) {

		return Collections.max(peaks, new ComparatorPeakByIntensity());
	}

	/**
	 *
	 * @param peaks
	 *            {@link Peak Peaks} to find peak with the highest mz from
	 * @return {@link Peak} that has the highest mz of all given {@code peaks}
	 */
	public static <T extends Peak> T findHighestMZ(final Collection<? extends T> peaks) {

		return Collections.max(peaks, new ComparatorPeakByMZ());
	}

	/**
	 * Converts an PPM delta to according absolute delta.
	 *
	 * @param parent
	 * @param ppmDelta
	 * @return converted delta
	 */
	public static double getAbsDelta(final double parent, final double ppmDelta) {

		return ppmDelta * parent / 1.0E+6;
	}

	public static double getDeltaMass(final double p1, final double p2, final boolean ppm) {

		double result;
		result = Math.abs(p1 - p2);
		if(ppm) {
			result = getDeltaPpm(p1, result);
		}
		return result;
	}

	/**
	 * Converts an absolute delta to according PPM delta.
	 *
	 * @return converted delta
	 */
	public static double getDeltaPpm(final double parent, final double absDelta) {

		return 1.0E+6 * absDelta / parent;
	}

	/**
	 * @deprecated use {@link #getDeltaMass(double, boolean)
	 *
	 */
	@Deprecated
	public static double getPpmDeltaMz(final Peak p1, final Peak p2) {

		final double absDiff = Math.abs(p1.getMz() - p2.getMz());
		return getDeltaPpm(p1.getMz(), absDiff);
	}

	public static TreeMap<Double, Peak> getSortedMapMassShiftAbsSmallestFirst(final double mass, final Collection<? extends Peak> values) {

		final TreeMap<Double, Peak> result = new TreeMap<Double, Peak>();
		for(final Peak p : values) {
			result.put(Math.abs(mass - p.getMz()), p);
		}
		return result;
	}

	public static TreeMap<Double, Peak> getSortedMapMassShiftAbsSmallestFirst(final Peak key, final Collection<? extends Peak> values) {

		return getSortedMapMassShiftAbsSmallestFirst(key.getMz(), values);
	}

	public static TreeSet<Peak> getSortedSet(final Collection<? extends Peak> peaks, final Comparator<Peak> comparator) {

		final TreeSet<Peak> result = new TreeSet<Peak>(comparator);
		result.addAll(peaks);
		return result;
	}

	public static TreeSet<Peak> getSortedSetByMzLargestFirst(final Collection<? extends Peak> peaks) {

		return getSortedSet(peaks, new ComparatorInverter<Peak>(new ComparatorPeakByMZ()));
	}

	public static TreeSet<Peak> getSortedSetByMzSmallestFirst(final Collection<? extends Peak> peaks) {

		return getSortedSet(peaks, new ComparatorPeakByMZ());
	}

	@Deprecated
	public static String intensityToNoiseToString(final Peak peak) {

		return String.format("%3.0f", peak.getIntensityToNoise());
	}

	@Deprecated
	public static String intensityToString(final double intensity) {

		return String.format("%3.0f", intensity);
	}

	@Deprecated
	public static String mzToString(final double mz) {

		return String.format("%4.4f", mz);
	}

	@Deprecated
	public static String mzToString(final Peak peak) {

		return mzToString(peak.getMz());
	}

	@Deprecated
	public static String ppmToString(final double ppm) {

		return String.format("%6.2f", ppm);
	}

	public static String toStringIndices(final Collection<? extends Peak> peaks) {

		return UtilCollection.toString(new TransformerPeakToFractionNr().transformCollection(peaks));
	}

	public static String toStringNames(final Collection<? extends Peak> peaks) {

		return UtilCollection.toString(new ArrayList<Peak>(peaks), new Transformer<Peak, String>() {

			public String transform(final Peak element) {

				return element.getName();
			}
		});
	}

	private UtilPeak() {
	}
}
