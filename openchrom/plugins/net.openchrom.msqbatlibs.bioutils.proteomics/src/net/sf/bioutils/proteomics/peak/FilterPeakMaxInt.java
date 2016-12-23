/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sf.bioutils.proteomics.comparator.ComparatorIntensity;
import net.sf.bioutils.proteomics.provider.ProviderIntensity;
import net.sf.kerner.utils.collections.comparator.ComparatorInverter;
import net.sf.kerner.utils.collections.filter.Filter;

public class FilterPeakMaxInt implements Filter<ProviderIntensity> {

	private Set<ProviderIntensity> set;
	private final int numElements;
	private volatile int cnt;

	public FilterPeakMaxInt(final Collection<? extends ProviderIntensity> peaks, final int numElements) {
		this.numElements = numElements;
		if(peaks == null || peaks.isEmpty()) {
			// filter only on numElements
		} else {
			if(peaks.size() <= numElements) {
				set = new HashSet<ProviderIntensity>(peaks);
			} else {
				final TreeSet<ProviderIntensity> sett = new TreeSet<ProviderIntensity>(new ComparatorInverter<ProviderIntensity>(new ComparatorIntensity()));
				sett.addAll(peaks);
				final List<ProviderIntensity> list = new ArrayList<ProviderIntensity>(sett);
				try {
					set = new HashSet<ProviderIntensity>(list.subList(0, numElements));
				} catch(final Exception e) {
					e.printStackTrace();
					set = new HashSet<ProviderIntensity>(peaks);
				}
			}
		}
	}

	public boolean filter(final ProviderIntensity e) {

		if(set == null) {
			if(cnt < numElements) {
				cnt++;
				return true;
			} else {
				return false;
			}
		} else {
			return set.contains(e);
		}
	}
}
