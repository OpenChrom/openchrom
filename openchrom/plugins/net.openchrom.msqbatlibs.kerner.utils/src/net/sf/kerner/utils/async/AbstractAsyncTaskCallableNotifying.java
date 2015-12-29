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
package net.sf.kerner.utils.async;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAsyncTaskCallableNotifying<R, V> extends AbstractAsyncTaskCallable<R, V> {

	public static interface Listener {

		void notify(Object o);
	}

	public static enum State {
		WAITING, RUNNING, FINISHED_OK, FINISHED_BAD
	}

	private final List<Listener> listeners = new ArrayList<Listener>();
	private State state = State.WAITING;
	private final String identifier;

	public AbstractAsyncTaskCallableNotifying(final String identifier) {
		this.identifier = identifier;
	}

	public synchronized void addAllListeners(final Collection<? extends Listener> listeners) {

		for(final Listener l : listeners) {
			this.listeners.add(l);
		}
	}

	public synchronized void addListener(final Listener listener) {

		listeners.add(listener);
	}

	public void doBefore() {

		synchronized(state) {
			state = State.RUNNING;
			notifyListeners(identifier + " : " + state);
		}
	}

	public void doOnFailure(final Exception e) {

		synchronized(state) {
			state = State.FINISHED_BAD;
			notifyListeners(identifier + " : " + state);
		}
	}

	public void doOnSucess(final R result) {

		synchronized(state) {
			state = State.FINISHED_OK;
			notifyListeners(identifier + " : " + state);
		}
	}

	private void notifyListeners(final Object o) {

		for(final Listener l : listeners) {
			l.notify(o);
		}
	}
}
