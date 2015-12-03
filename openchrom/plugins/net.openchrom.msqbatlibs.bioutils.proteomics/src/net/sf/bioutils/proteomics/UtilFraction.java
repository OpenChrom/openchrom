/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.bioutils.proteomics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.bioutils.proteomics.fraction.Fraction;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.collections.filter.FilterApplierProto;

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
public class UtilFraction {

    public static List<Peak> getPeaks(
            final Collection<? extends Iterable<? extends Fraction>> fractions) {
        return getPeaks(fractions, new ArrayList<Filter<Peak>>(0));
    }

    public static List<Peak> getPeaks(
            final Collection<? extends Iterable<? extends Fraction>> fractions,
            final Collection<? extends Filter<Peak>> filters) {
        final List<Peak> result = new ArrayList<Peak>();
        for (final Iterable<? extends Fraction> f : fractions) {
            result.addAll(getPeaks(f, filters));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Peak> getPeaks(
            final Collection<? extends Iterable<? extends Fraction>> fractions,
            final Filter<Peak> filter) {
        return getPeaks(fractions, Arrays.asList(filter));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Peak> getPeaks(final Fraction fraction) {
        return getPeaks(fraction, new ArrayList());
    }

    public static List<Peak> getPeaks(final Fraction fraction,
            final Collection<? extends Filter<Peak>> filters) {
        final FilterApplierProto<Peak> filter = new FilterApplierProto<Peak>();
        for (final Filter<Peak> f : filters) {
            filter.addFilter(f);
        }
        final List<Peak> result = new ArrayList<Peak>();
        for (final Peak p : fraction.getPeaks()) {
            if (filter.filter(p)) {
                result.add(p);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Peak> getPeaks(final Fraction fraction, final Filter<Peak> filter) {
        return getPeaks(fraction, Arrays.asList(filter));
    }

    public static List<Peak> getPeaks(final Iterable<? extends Fraction> fractions) {
        return getPeaks(fractions, new ArrayList<Filter<Peak>>(0));
    }

    /**
     * Gets all {@link Peak peaks} from all {@link Fraction fractions} which are
     * provided by given {@link Iterable} {@code fractions}. An array of
     * {@link Filter Filters} may be provided to <b>exclude non-matching</b>
     * {@link Peak peaks} from result.</p> Result has the same ordering as given
     * by
     * <ol>
     * <li>{@code fraction's} {@link Iterator} over {@link Peak peaks}</li>
     * <li>{@link Iterator} of provided {@code fractions}</li>
     * </ol>
     *
     * <p>
     * last reviewed: 2013-09-13
     * </p>
     *
     * @param filters
     *            {@link Filter} to filter peaks
     * @param fractions
     *            fractions, from which all peaks are returned
     * @return a {@link List}, which contains all peaks from given fractions
     */
    public static List<Peak> getPeaks(final Iterable<? extends Fraction> fractions,
            final Collection<? extends Filter<Peak>> filters) {
        final List<Peak> result = new ArrayList<Peak>();
        if (fractions == null) {
            return result;
        }
        for (final Fraction f : fractions) {
            result.addAll(getPeaks(f, filters));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static List<Peak> getPeaks(final Iterable<? extends Fraction> fractions,
            final Filter<Peak> filter) {
        return getPeaks(fractions, Arrays.asList(filter));
    }

}
