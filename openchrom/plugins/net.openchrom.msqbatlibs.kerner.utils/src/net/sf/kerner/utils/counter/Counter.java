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
package net.sf.kerner.utils.counter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@code Counter} can be used to monitor program behavior or to keep track of
 * some progress, e.g. how many loop cycles have already been performed.
 * </p>
 * <p>
 * A {@code Counter} can be initialized with a {@link Runnable}, which will be
 * executed with given interval.<br>
 * Per default the interval is {@code 1}, which means given {@link Runnable}
 * will be executed (synchronously) with every count.
 * </p>
 * <p>
 * Per default, a {@code Counter} will execute nothing with an interval of
 * {@code 1}.
 * </p>
 * <p>
 * This class is fully threadsave.
 * </p>
 * <p>
 * Example:
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-10-06
 */
public class Counter {

    // Field //

    private final int initCnt;
    private volatile int cnt = 0;
    private volatile int interval = 1;
    private volatile int intervalHelper = 0;
    private volatile boolean performed = false;
    private volatile List<Runnable> runner = new ArrayList<Runnable>();

    // Constructor //

    /**
     * Create a new {@code Counter} that has initially performed zero counts and
     * will do nothing on call of {@link Counter#perform()}.
     */
    public Counter() {
        // all zero
        initCnt = 0;
    }

    /**
     * Create a new {@code Counter} that has initially performed zero counts and
     * will execute [@code runner} on call of {@link Counter#perform()}.
     */
    public Counter(Runnable runner) {
        // all zero
        initCnt = 0;
        this.runner.add(runner);
    }

    /**
     * <p>
     * Construct a new {@code Counter} with has initially performed
     * {@code count} counts and will do nothing on call of
     * {@link Counter#perform()}. {@link Counter#getCount()} will return
     * {@code count}.
     * </p>
     * 
     * @param count
     *            Initial counting for this {@code Counter}.
     */
    public Counter(int count) {
        synchronized (Counter.class) {
            this.cnt = count;
            this.initCnt = count;
        }
    }

    /**
     * <p>
     * Construct a new {@code Counter} with has initially performed
     * {@code count} counts and will execute [@code runner} on call of
     * {@link Counter#perform()}. {@link Counter#getCount()} will return
     * {@code count}.
     * </p>
     * 
     * @param count
     *            Initial counting for this {@code Counter}.
     */
    public Counter(int count, Runnable runner) {
        synchronized (Counter.class) {
            this.cnt = count;
            this.initCnt = count;
            this.runner.add(runner);
        }
    }

    // Private //

    private void checkPerform() {
        // System.err.println("intervalHelper=" + intervalHelper);
        // System.err.println("interval=" + interval);
        // System.err.println("count=" + cnt);
        if (intervalHelper == interval) {
            perform();
            performed = true;
            intervalHelper = 0;
        } else {
            performed = false;
        }
    }

    // Protected //

    // Default //

    // Public //

    /**
     * <p>
     * Increase counter by 1.
     * </p>
     */
    public synchronized void count() {
        cnt++;
        intervalHelper++;
        checkPerform();
    }

    /**
     * <p>
     * Resets this {@code Counter} and calls {@link Counter#perform()} if there
     * has been any counts after last call of {@link Counter#perform()}.
     * </p>
     */
    public synchronized void finish() {
        if (performed) {

        } else
            perform();
        intervalHelper = 0;
        cnt = initCnt;
        interval = 1;
    }

    /**
     * <p>
     * Sets {@code interval} for this {@code Counter}.
     * </p>
     * 
     * @param interval
     *            number of counts {@link Counter#count()} has to be called
     *            before {@link Counter#perform()} is called.
     * @throws NumberFormatException
     *             if {@code interval} < 1
     */
    public synchronized void setInterval(int interval) {
        if (interval < 1)
            throw new NumberFormatException("interval must be >= 1");
        this.interval = interval;
    }

    /**
     * @return current interval for this {@code Counter}.
     */
    public int getInterval() {
        return interval;
    }

    /**
     * <p>
     * Returns the number of {@link Counter#count()} has been called.
     * </p>
     * 
     * @return current counts
     */
    public int getCount() {
        return cnt;
    }

    /**
     * <p>
     * Returns the init value of {@link Counter#cnt}.
     * </p>
     * 
     * @return init count
     */
    public int getInitCount() {
        return initCnt;
    }

    /**
     * <p>
     * Set {@code count}.
     * </p>
     * 
     * @param count
     *            {@code count} to set
     */
    public synchronized void setCount(int count) {
        this.cnt = count;
        this.intervalHelper = 0;
    }

    // Override //

    @Override
    public String toString() {
        return Integer.toString(getCount());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cnt;
        result = prime * result + interval;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Counter))
            return false;
        Counter other = (Counter) obj;
        if (cnt != other.cnt)
            return false;
        if (interval != other.interval)
            return false;
        return true;
    }

    /**
     * Set this {@code Counter} {@link Runnable}.
     * 
     * @param runner
     *            {@link Runnable} that is run every interval
     */
    public synchronized void addRunnable(Runnable runner) {
        this.runner.add(runner);
    }

    public synchronized void clearRunnables() {
        this.runner.clear();
    }

    /**
     * <p>
     * Performs action.
     * </p>
     */
    public synchronized void perform() {
        for (Runnable r : runner) {
            r.run();
        }
    }

}
