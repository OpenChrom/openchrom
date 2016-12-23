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
package net.sf.kerner.utils.async;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AsyncBatchMonitor<I, R> {

	public static class AlreadyRegisteredException extends RuntimeException {

		private static final long serialVersionUID = -3723305005162834457L;

		public AlreadyRegisteredException() {
		}

		public AlreadyRegisteredException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public AlreadyRegisteredException(String arg0) {
			super(arg0);
		}

		public AlreadyRegisteredException(Throwable arg0) {
			super(arg0);
		}
	}

	public static class NotRegisteredException extends RuntimeException {

		private static final long serialVersionUID = 9099067750620713811L;

		public NotRegisteredException() {
		}

		public NotRegisteredException(String message, Throwable cause) {
			super(message, cause);
		}

		public NotRegisteredException(String message) {
			super(message);
		}

		public NotRegisteredException(Throwable cause) {
			super(cause);
		}
	}

	private final Map<I, R> identifier2Result = new HashMap<I, R>();
	private final Map<I, Exception> itendifier2Exception = new HashMap<I, Exception>();
	private final Collection<BatchListener<I>> listeners = new ArrayList<BatchListener<I>>();

	public AsyncBatchMonitor(List<? extends I> identifiers) {
		init(identifiers);
	}

	private void init(final List<? extends I> identifiers) {

		for(I i : identifiers) {
			identifier2Result.put(i, null);
			itendifier2Exception.put(i, null);
		}
	}

	protected void allDone() {

		boolean error = false;
		for(Exception e : itendifier2Exception.values()) {
			if(e != null) {
				error = true;
				break;
			}
		}
		for(BatchListener<?> l : listeners) {
			l.allDone(error);
		}
	}

	protected boolean isDone(I ident) {

		if(identifier2Result.get(ident) != null)
			return true;
		if(itendifier2Exception.get(ident) != null)
			return true;
		return false;
	}

	protected boolean finished() {

		for(I i : identifier2Result.keySet()) {
			if(isDone(i)) {
				// ok
			} else {
				return false;
			}
		}
		return true;
	}

	public void reportResult(I identifier, R result) {

		synchronized(identifier2Result) {
			if(identifier2Result.containsKey(identifier)) {
				identifier2Result.put(identifier, result);
				if(finished())
					allDone();
			} else
				throw new NotRegisteredException("identifier unknown [" + identifier + "]");
		}
	}

	public void reportError(I identifier, Exception error) {

		synchronized(itendifier2Exception) {
			if(itendifier2Exception.containsKey(identifier)) {
				itendifier2Exception.put(identifier, error);
				for(BatchListener<I> l : listeners) {
					l.errorOccured(identifier, error);
				}
				if(finished())
					allDone();
			} else
				throw new NotRegisteredException("identifier unknown [" + identifier + "]");
		}
	}

	public void registerListener(BatchListener<I> l) {

		synchronized(listeners) {
			listeners.add(l);
		}
	}

	public List<Exception> getExceptions() {

		return new ArrayList<Exception>(itendifier2Exception.values());
	}

	public List<R> getResults() {

		return new ArrayList<R>(identifier2Result.values());
	}

	public Set<I> getIdentifiers() {

		return new TreeSet<I>(identifier2Result.keySet());
	}
}
