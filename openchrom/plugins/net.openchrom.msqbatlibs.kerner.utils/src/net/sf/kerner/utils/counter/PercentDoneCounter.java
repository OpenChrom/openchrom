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
 * 
 * TODO description
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2013-06-12
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-06-12
 * 
 */
public class PercentDoneCounter extends Counter {

    public static interface Listener {
        void update(double percentDone);
    }

    private final List<Listener> listeners = new ArrayList<Listener>();

    /**
     * Instantiates a new {@code PercentDoneCounter}. Interval is initially set
     * to 1%.
     * 
     * @param totalElements
     */
    public PercentDoneCounter(final int totalElements) {
        if (totalElements <= 100) {
            setInterval(1);
        } else {
            setInterval(totalElements / 100);
        }
        addRunnable(new Runnable() {
            public void run() {
                final double percent = (double) getCount() / (double) totalElements * 100;
                for (final Listener l : listeners) {
                    l.update(percent);
                }
            }
        });
    }

    public void addListener(final Listener listener) {
        listeners.add(listener);
    }

    public void clearListners() {
        listeners.clear();
    }
}
