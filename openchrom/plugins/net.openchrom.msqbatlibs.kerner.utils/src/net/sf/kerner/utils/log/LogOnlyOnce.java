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
package net.sf.kerner.utils.log;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

public class LogOnlyOnce {

    private final Set<String> logged = new HashSet<String>();

    private final Logger log;

    public LogOnlyOnce(final Logger log) {
        this.log = log;
    }

    public synchronized void debug(final String msg) {
        if (logged.contains(msg)) {
            return;
        }
        log.debug(msg);
        logged.add(msg);
    }

    public synchronized void debug(final String msg, final Exception exception) {
        if (logged.contains(msg)) {
            return;
        }
        log.debug(msg, exception);
        logged.add(msg);

    }

    public synchronized void info(final String msg) {
        if (logged.contains(msg)) {
            return;
        }
        log.info(msg);
        logged.add(msg);
    }

    public synchronized void reset() {
        logged.clear();
    }

    public synchronized void warn(final String msg) {
        if (logged.contains(msg)) {
            return;
        }
        log.warn(msg);
        logged.add(msg);
    }

}
