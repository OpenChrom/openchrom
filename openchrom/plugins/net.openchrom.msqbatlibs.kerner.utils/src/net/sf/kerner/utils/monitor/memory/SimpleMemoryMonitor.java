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
package net.sf.kerner.utils.monitor.memory;

import net.sf.kerner.utils.math.LongUnit;

public class SimpleMemoryMonitor implements MemoryMonitor {

	private long max = -1;
	private long average = 0;
	private final LongUnit unit;
	private final long interval;
	private final Object lock = new Object();
	private Thread t = new Thread() {

		public void run() {

			while(!t.isInterrupted()) {
				synchronized(lock) {
					final long current = getCurrentUsage();
					if(max < current)
						max = current;
					average = (average + current) / 2;
				}
				try {
					sleep(interval);
				} catch(InterruptedException e) {
					break;
				}
			}
		};
	};

	public SimpleMemoryMonitor(LongUnit unit, long interval) {

		super();
		this.unit = unit;
		this.interval = interval;
	}

	public SimpleMemoryMonitor(long interval) {

		this(DEFAULT_UNIT, interval);
	}

	public SimpleMemoryMonitor() {

		this(DEFAULT_UNIT, 2000);
	}

	public long getCurrentUsage() {

		return getCurrentUsage(this.unit);
	}

	public long getCurrentUsage(LongUnit unit) {

		final long result = unit.convert(Runtime.getRuntime().totalMemory(), LongUnit.UNIT);
		// System.out.println("convert from " +
		// Runtime.getRuntime().totalMemory() + " to " + result);
		return result;
	}

	public synchronized long getMaxUsage() {

		return getMaxUsage(this.unit);
	}

	public synchronized long getMaxUsage(LongUnit unit) {

		return this.unit.convert(max, unit);
	}

	public synchronized long getAverageUsage() {

		return getAverageUsage(this.unit);
	}

	public synchronized long getAverageUsage(LongUnit unit) {

		return this.unit.convert(average, unit);
	}

	public void start() {

		t.start();
	}

	public synchronized long stop() {

		t.interrupt();
		return getCurrentUsage();
	}

	public LongUnit getUnit() {

		return this.unit;
	}
}
