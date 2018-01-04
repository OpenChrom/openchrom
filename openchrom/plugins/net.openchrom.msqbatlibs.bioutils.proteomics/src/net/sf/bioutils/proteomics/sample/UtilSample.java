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
import java.util.concurrent.locks.Lock;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.collections.map.MapList;

public class UtilSample {

	// private final static Logger log =
	// LoggerFactory.getLogger(UtilSample.class);
	public static List<Sample> clone(final List<Sample> samples) {

		final List<Sample> result = new ArrayList<Sample>(samples.size());
		for(final Sample s : samples) {
			result.add(s.clone());
		}
		return result;
	}

	public static MapList<Integer, Peak> getPeakMap(final Sample sample) {

		final MapList<Integer, Peak> result = new MapList<Integer, Peak>();
		for(final Peak p : sample.getPeaks()) {
			result.put(p.getFractionIndex(), p);
		}
		return result;
	}

	public static List<Peak> getPeaks(final Collection<? extends Sample> samples) {

		final List<Peak> result = new ArrayList<Peak>();
		for(final Sample s : samples) {
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
		for(final Sample s : samples) {
			final Lock l = s.getLock().readLock();
			l.lock();
			result.add(l);
		}
		return result;
	}

	public static void unlockAll(final Collection<? extends Lock> locks) {

		for(final Lock l : locks) {
			l.unlock();
		}
	}
}
