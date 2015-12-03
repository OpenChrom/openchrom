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
package net.sf.kerner.utils.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorNotifying extends ThreadPoolExecutor {

    /**
     * The thread factory
     */
    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private String namePrefix;
        private String id;

        DefaultThreadFactory() {
            final SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        }

        public synchronized String getId() {
            return id;
        }

        public synchronized Thread newThread(final Runnable r) {
            namePrefix = "pool-" + poolNumber.getAndIncrement() + "-" + id + "-";
            final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }

        public synchronized void setId(final String id) {
            this.id = id;
        }
    }

    public static ThreadPoolExecutorNotifying build(final int numCPUs) {
        return new ThreadPoolExecutorNotifying(numCPUs, numCPUs, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    private String identifier;

    private final DefaultThreadFactory defaultThreadFactory = new DefaultThreadFactory();

    private final List<FutureTaskNotifying.ListenerDone> listeners = new ArrayList<FutureTaskNotifying.ListenerDone>();

    public ThreadPoolExecutorNotifying(final int corePoolSize, final int maximumPoolSize,
            final long keepAliveTime, final TimeUnit unit, final BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolExecutorNotifying(final int corePoolSize, final int maximumPoolSize,
            final long keepAliveTime, final TimeUnit unit, final BlockingQueue<Runnable> workQueue,
            final RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public synchronized <T> void addListenerDone(final FutureTaskNotifying.ListenerDone listener) {
        listeners.add(listener);
    }

    @Override
    protected void afterExecute(final Runnable r, final Throwable t) {
        // TODO Auto-generated method stub
        super.afterExecute(r, t);
    }

    @Override
    protected void beforeExecute(final Thread t, final Runnable r) {
        // TODO Auto-generated method stub
        super.beforeExecute(t, r);
    }

    public synchronized String getIdentifier() {
        return identifier;
    }

    @Override
    public synchronized ThreadFactory getThreadFactory() {
        defaultThreadFactory.setId(getIdentifier());
        return defaultThreadFactory;
    }

    @Override
    protected synchronized <T> RunnableFuture<T> newTaskFor(final Callable<T> callable) {
        if (identifier == null) {
            identifier = "n/a";
        }
        final FutureTaskNotifying<T> hannes = new FutureTaskNotifying<T>(callable, identifier);
        hannes.addAllListener(listeners);
        return hannes;
    }

    @Override
    protected synchronized <T> RunnableFuture<T> newTaskFor(final Runnable runnable, final T value) {
        if (identifier == null) {
            identifier = "n/a";
        }
        final FutureTaskNotifying<T> hannes = new FutureTaskNotifying<T>(runnable, value,
                new String(identifier));
        hannes.addAllListener(listeners);
        return hannes;
    }

    public synchronized void setIdentifier(final String identifier) {
        this.identifier = identifier;
    }

}
