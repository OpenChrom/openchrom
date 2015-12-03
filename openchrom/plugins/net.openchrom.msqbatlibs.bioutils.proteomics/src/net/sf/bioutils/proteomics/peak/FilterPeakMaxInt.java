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
package net.sf.bioutils.proteomics.peak;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sf.bioutils.proteomics.comparator.ComparatorIntensity;
import net.sf.bioutils.proteomics.provider.ProviderIntensity;
import net.sf.kerner.utils.collections.ComparatorInverter;
import net.sf.kerner.utils.collections.filter.Filter;

public class FilterPeakMaxInt implements Filter<ProviderIntensity> {

    private Set<ProviderIntensity> set;

    private final int numElements;

    private volatile int cnt;

    public FilterPeakMaxInt(final Collection<? extends ProviderIntensity> peaks, final int numElements) {
        this.numElements = numElements;
        if (peaks == null || peaks.isEmpty()) {
            // filter only on numElements
        } else {
            if (peaks.size() <= numElements) {
                set = new HashSet<ProviderIntensity>(peaks);
            } else {
                final TreeSet<ProviderIntensity> sett = new TreeSet<ProviderIntensity>(
                        new ComparatorInverter<ProviderIntensity>(new ComparatorIntensity()));
                sett.addAll(peaks);
                final List<ProviderIntensity> list = new ArrayList<ProviderIntensity>(sett);
                try {
                    set = new HashSet<ProviderIntensity>(list.subList(0, numElements));
                } catch (final Exception e) {
                    e.printStackTrace();
                    set = new HashSet<ProviderIntensity>(peaks);
                }
            }
        }
    }

    @Override
    public boolean filter(final ProviderIntensity e) {
        if (set == null) {
            if (cnt < numElements) {
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
