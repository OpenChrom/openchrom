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
package net.sf.bioutils.proteomics.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.collections.map.MapList;

public class UtilSample {

    // private final static Logger log =
    // LoggerFactory.getLogger(UtilSample.class);

    public static List<Sample> clone(final List<Sample> samples) {
        final List<Sample> result = new ArrayList<Sample>(samples.size());
        for (final Sample s : samples) {
            result.add(s.clone());
        }
        return result;
    }

    public static MapList<Integer, Peak> getPeakMap(final Sample sample) {
        final MapList<Integer, Peak> result = new MapList<Integer, Peak>();
        for (final Peak p : sample.getPeaks()) {
            result.put(p.getFractionIndex(), p);
        }
        return result;
    }

    public static List<Peak> getPeaks(final Collection<? extends Sample> samples) {
        final List<Peak> result = new ArrayList<Peak>();
        for (final Sample s : samples) {
            result.addAll(s.getPeaks());
        }
        return result;
    }

    /**
     * Returns and locks.
     *
     */
    public static List<Lock> getReadLockAll(final Collection<? extends Sample> samples) {
        final List<Lock> result = new ArrayList<Lock>();
        for (final Sample s : samples) {
            final Lock l = s.getLock().readLock();
            l.lock();
            result.add(l);
        }
        return result;
    }

    public static void unlockAll(final Collection<? extends Lock> locks) {
        for (final Lock l : locks) {
            l.unlock();
        }
    }
}
