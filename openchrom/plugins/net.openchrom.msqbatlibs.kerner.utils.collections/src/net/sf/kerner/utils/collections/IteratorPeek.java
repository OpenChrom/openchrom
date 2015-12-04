/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
