/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskNotifying<V> extends FutureTask<V> {

	public interface ListenerDone {

		void isDone(FutureTaskNotifying<?> task);
	}

	private final String identifier;
	private final List<ListenerDone> listeners = new ArrayList<ListenerDone>();

	public FutureTaskNotifying(final Callable<V> callable) {

		super(callable);
		this.identifier = null;
	}

	public FutureTaskNotifying(final Callable<V> callable, final String identifier) {

		super(callable);
		this.identifier = identifier;
	}

	public FutureTaskNotifying(final Runnable runnable, final V result) {

		super(runnable, result);
		this.identifier = null;
	}

	public FutureTaskNotifying(final Runnable runnable, final V result, final String identifier) {

		super(runnable, result);
		this.identifier = identifier;
	}

	public synchronized void addAllListener(final List<ListenerDone> listeners) {

		for(final ListenerDone l : listeners) {
			addListener(l);
		}
	}

	public synchronized void addListener(final ListenerDone listener) {

		listeners.add(listener);
	}

	@Override
	protected synchronized void done() {

		super.done();
		for(final ListenerDone listener : listeners) {
			listener.isDone(this);
		}
	}

	public String getIdentifier() {

		return identifier;
	}
}
