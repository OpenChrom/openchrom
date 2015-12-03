/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
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
            while (!t.isInterrupted()) {
                synchronized (lock) {
                    final long current = getCurrentUsage();
                    if (max < current)
                        max = current;
                    average = (average + current) / 2;
                }
                try {
                    sleep(interval);
                } catch (InterruptedException e) {
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
