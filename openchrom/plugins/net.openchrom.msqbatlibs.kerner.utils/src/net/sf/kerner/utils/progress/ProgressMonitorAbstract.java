/**
 * *****************************************************************************
 * Copyright (c) 2010-2015 Alexander Kerner. All rights reserved.
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
 *****************************************************************************
 */
package net.sf.kerner.utils.progress;

public class ProgressMonitorAbstract implements ProgressMonitor {

    private boolean cancelled;

    private String taskName;

    protected int totalWorkload = UNKNOWN_WORKLOAD;

    protected int worked;

    public void worked() {
        worked(1);
    }

    
    public void finished() {
        totalWorkload = UNKNOWN_WORKLOAD;
    }

    
    public void notifySubtask(String name) {

    }

    public void worked(int i) {
        worked += i;
    }

    public void started(int totalWorkload) {
        this.totalWorkload = totalWorkload;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public ProgressMonitorAbstract() {

    }

    public synchronized boolean isCancelled() {
        return cancelled;
    }

    public synchronized void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

}
