/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.sf.kerner.utils.concurrent.FutureTaskNotifying.ListenerDone;

import org.apache.log4j.Logger;

public class ExecutorNotifying implements ExecutorService {

	private final static Logger log = Logger.getLogger(ExecutorNotifying.class);
	private ThreadPoolExecutorNotifying delegate;
	private int numCPUs;

	public ExecutorNotifying() {
	}

	public ExecutorNotifying(final int numCPUs) {
		this.numCPUs = numCPUs;
		delegate = ThreadPoolExecutorNotifying.build(numCPUs);
	}

	public synchronized void addListenerDone(final ListenerDone listener) {

		delegate.addListenerDone(listener);
	}

	public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {

		return delegate.awaitTermination(timeout, unit);
	}

	public void execute(final Runnable command) {

		delegate.execute(command);
	}

	public synchronized int getActiveCount() {

		return delegate.getActiveCount();
	}

	public ThreadPoolExecutorNotifying getDelegate() {

		return delegate;
	}

	public synchronized int getNumCPUs() {

		return numCPUs;
	}

	public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {

		return delegate.invokeAll(tasks);
	}

	public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException {

		return delegate.invokeAll(tasks, timeout, unit);
	}

	public <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {

		return delegate.invokeAny(tasks);
	}

	public <T> T invokeAny(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {

		return delegate.invokeAny(tasks, timeout, unit);
	}

	public boolean isShutdown() {

		return delegate.isShutdown();
	}

	public synchronized boolean isTerminated() {

		return delegate.isTerminated();
	}

	public synchronized void setIdentifier(final String identifier) {

		delegate.setIdentifier(identifier);
	}

	public synchronized void setNumCPUs(final int numCPUs) {

		this.numCPUs = numCPUs;
		if(delegate != null) {
			log.info("reinitiate with " + numCPUs + " threads");
			delegate.shutdown();
		}
		delegate = ThreadPoolExecutorNotifying.build(numCPUs);
	}

	public synchronized void shutdown() {

		delegate.shutdown();
	}

	public synchronized List<Runnable> shutdownNow() {

		return delegate.shutdownNow();
	}

	public <T> Future<T> submit(final Callable<T> task) {

		return delegate.submit(task);
	}

	public Future<?> submit(final Runnable task) {

		return delegate.submit(task);
	}

	public <T> Future<T> submit(final Runnable task, final T result) {

		return delegate.submit(task, result);
	}
}
