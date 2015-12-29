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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryNotificationInfo;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.ArrayList;
import java.util.Collection;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;

/**
 * TODO Description
 * 
 * @author Heinz
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-09-19
 */
public class LowMemoryNotifier {

	public static interface Listener {

		public void notice(long usedMemory, long maxMemory);
	}

	private static final MemoryPoolMXBean tenuredGenPool = findTenuredGenPool();
	private final static LowMemoryNotifier instance = new LowMemoryNotifier();
	private final Collection<Listener> listeners = new ArrayList<Listener>();

	private LowMemoryNotifier() {
		final MemoryMXBean mbean = ManagementFactory.getMemoryMXBean();
		final NotificationEmitter emitter = (NotificationEmitter)mbean;
		emitter.addNotificationListener(new NotificationListener() {

			public void handleNotification(Notification n, Object hb) {

				if(n.getType().equals(MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED)) {
					long maxMemory = tenuredGenPool.getUsage().getMax();
					long usedMemory = tenuredGenPool.getUsage().getUsed();
					for(Listener listener : listeners) {
						listener.notice(usedMemory, maxMemory);
					}
				}
			}
		}, null, null);
	}

	public synchronized boolean addListener(Listener listener) {

		return listeners.add(listener);
	}

	public synchronized boolean removeListener(Listener listener) {

		return listeners.remove(listener);
	}

	public static LowMemoryNotifier getInstance() {

		return instance;
	}

	public static void setPercentageUsageThreshold(double percentage) {

		if(percentage <= 0.0 || percentage > 1.0) {
			throw new IllegalArgumentException("Percentage not in range");
		}
		long maxMemory = tenuredGenPool.getUsage().getMax();
		long warningThreshold = (long)(maxMemory * percentage);
		tenuredGenPool.setUsageThreshold(warningThreshold);
	}

	/**
	 * Tenured Space Pool can be determined by it being of type HEAP and by it
	 * being possible to set the usage threshold.
	 */
	private static MemoryPoolMXBean findTenuredGenPool() {

		for(MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
			// I don't know whether this approach is better, or whether
			// we should rather check for the pool name "Tenured Gen"?
			if(pool.getType() == MemoryType.HEAP && pool.isUsageThresholdSupported()) {
				return pool;
			}
		}
		throw new AssertionError("Could not find tenured space");
	}
}
