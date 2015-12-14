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
package net.sf.bioutils.proteomics.sample.stats;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sf.bioutils.proteomics.sample.Sample;

public class SampleStatisticsCalculatorFuture {

	// private final static Logger log =
	// LoggerFactory.getLogger(SampleStatisticsFactory.class);
	protected final Map<Sample, Future<SampleStatistics>> result = new LinkedHashMap<Sample, Future<SampleStatistics>>();
	protected final ExecutorService exe = Executors.newSingleThreadExecutor();

	public synchronized void calculate(final List<Sample> samples) {

		for(final Sample s : samples) {
			result.put(s, exe.submit(new SampleStatisticsCallable(s)));
		}
	}

	public synchronized SampleStatistics get(final Sample sample) throws InterruptedException, ExecutionException {

		return result.get(sample).get();
	}

	public synchronized void shutdown() {

		exe.shutdown();
	}
}
