/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

import java.util.Collection;
import java.util.List;

import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.kerner.utils.collections.list.UtilList;
import net.sf.kerner.utils.progress.ProgressMonitor;

public abstract class SampleHandlerAbstract<S extends SampleModifiable> implements SampleHandler<S> {

	private ProgressMonitor monitor;

	protected abstract S getEmptyResultSample(final Sample input);

	public synchronized ProgressMonitor getMonitor() {

		return monitor;
	}

	
	public List<Peak> handle(final Collection<? extends Peak> peaks) throws Exception {

		final List<Peak> result = UtilList.newList();
		for(final Peak p : peaks) {
			if(monitor != null && monitor.isCancelled()) {
				return null;
			}
			if(monitor != null)
				monitor.worked();
			result.add(handlePeak(p));
		}
		return result;
	}

	
	public synchronized S handle(final Sample sample) throws Exception {

		sample.getLock().readLock().lock();
		if(monitor != null)
			monitor.started(sample.getSize());
		try {
			final S result = getEmptyResultSample(sample);
			result.setPeaks(handle(sample.getPeaks()));
			return result;
		} finally {
			if(monitor != null)
				monitor.finished();
			sample.getLock().readLock().unlock();
		}
	}

	protected abstract Peak handlePeak(final Peak input);

	public synchronized void setMonitor(final ProgressMonitor monitor) {

		this.monitor = monitor;
	}
}
