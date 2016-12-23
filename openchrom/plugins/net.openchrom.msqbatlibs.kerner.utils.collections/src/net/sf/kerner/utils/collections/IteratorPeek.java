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
package net.sf.kerner.utils.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorPeek<T> implements Iterator<T> {

	private final Iterator<T> iterator;
	private T nextElement = null;
	private boolean done = false;

	public IteratorPeek(final Iterator<T> iterator) {
		super();
		this.iterator = iterator;
		performPeak();
	}

	public synchronized boolean hasNext() {

		if(done) {
			return false;
		}
		return true;
	}

	public synchronized T next() {

		if(done)
			throw new NoSuchElementException();
		final T result = nextElement;
		performPeak();
		return result;
	}

	public synchronized T peek() {

		if(done)
			throw new NoSuchElementException();
		return nextElement;
	}

	private void performPeak() {

		if(done) {
			return;
		}
		if(iterator.hasNext()) {
			nextElement = iterator.next();
		} else {
			done = true;
			nextElement = null;
		}
	}

	public synchronized void remove() {

		throw new RuntimeException("not implemented yet");
	}
}
