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
package net.sf.kerner.utils.counter;

import java.util.ArrayList;
import java.util.List;

import net.sf.kerner.utils.time.StopWatch;
import net.sf.kerner.utils.time.TimePeriod;

public class PercentDoneCounterWatch extends PercentDoneCounter implements PercentDoneCounter.Listener {

	public static interface Listener {

		void update(double percentDone, TimePeriod period);
	}

	private final List<Listener> listeners = new ArrayList<Listener>();
	private double percentDone;
	private final StopWatch watch = new StopWatch();

	public PercentDoneCounterWatch(final int totalElements) {

		super(totalElements);
		addListener(this);
		addRunnable(new Runnable() {

			public void run() {

				final TimePeriod period = watch.stop();
				for(final Listener l : listeners) {
					l.update(percentDone, period);
				}
				watch.start();
			}
		});
	}

	public void addListener(final Listener listener) {

		listeners.add(listener);
	}

	public void clearListners() {

		listeners.clear();
	}

	public void start() {

		watch.start();
	}

	public TimePeriod stop() {

		return watch.stop();
	}

	public void update(final double percentDone) {

		this.percentDone = percentDone;
	}
}
