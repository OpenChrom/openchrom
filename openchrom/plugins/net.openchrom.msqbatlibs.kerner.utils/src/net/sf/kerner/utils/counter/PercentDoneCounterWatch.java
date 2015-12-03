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

import net.sf.kerner.utils.time.StopWatch;
import net.sf.kerner.utils.time.TimePeriod;

public class PercentDoneCounterWatch extends PercentDoneCounter implements
        PercentDoneCounter.Listener {

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
                for (final Listener l : listeners) {
                    l.update(percentDone, period);
                }
                watch.start();
            }
        });
    }

    public void addListener(final Listener listener) {
        listeners.add(listener);
    }

    @Override
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
