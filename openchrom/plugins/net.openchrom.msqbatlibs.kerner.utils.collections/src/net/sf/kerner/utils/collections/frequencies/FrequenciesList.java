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
package net.sf.kerner.utils.collections.frequencies;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.collections.list.FactoryList;
import net.sf.kerner.utils.collections.list.UtilList;

public class FrequenciesList<T extends Frequency> implements Frequencies<T>, List<T> {

	private final List<T> delegate;

	public FrequenciesList() {

		this.delegate = UtilList.newList();
	}

	public FrequenciesList(final Collection<T> template) {

		this.delegate = UtilList.newList();
		addAll(template);
	}

	public FrequenciesList(final FactoryList<T> delegateFactory) {

		this.delegate = delegateFactory.createCollection();
	}

	public synchronized void add(final int index, final T element) {

		setFreq(element);
		delegate.add(index, element);
	}

	/**
	 * @return {@code true}
	 */
	public synchronized boolean add(final T e) {

		setFreq(e);
		delegate.add(e);
		return true;
	}

	/**
	 * @return {@code true}
	 */
	public synchronized boolean addAll(final Collection<? extends T> c) {

		for(final T t : c) {
			add(t);
		}
		return true;
	}

	/**
	 * 
	 * @return {@code true}
	 */
	public boolean addAll(final int index, final Collection<? extends T> c) {

		for(final T t : c) {
			add(index, t);
		}
		return true;
	}

	public void clear() {

		delegate.clear();
	}

	public boolean contains(final Object o) {

		return delegate.contains(o);
	}

	public boolean containsAll(final Collection<?> c) {

		return delegate.containsAll(c);
	}

	public boolean equals(final Object o) {

		return delegate.equals(o);
	}

	public T get(final int index) {

		return delegate.get(index);
	}

	public int hashCode() {

		return delegate.hashCode();
	}

	public int indexOf(final Object o) {

		return delegate.indexOf(o);
	}

	public boolean isEmpty() {

		return delegate.isEmpty();
	}

	public Iterator<T> iterator() {

		return delegate.iterator();
	}

	public int lastIndexOf(final Object o) {

		return delegate.lastIndexOf(o);
	}

	public ListIterator<T> listIterator() {

		return delegate.listIterator();
	}

	public ListIterator<T> listIterator(final int index) {

		return delegate.listIterator(index);
	}

	public T remove(final int index) {

		throw new RuntimeException("not implemented");
	}

	public boolean remove(final Object o) {

		throw new RuntimeException("not implemented");
	}

	public boolean removeAll(final Collection<?> c) {

		throw new RuntimeException("not implemented");
	}

	public boolean retainAll(final Collection<?> c) {

		throw new RuntimeException("not implemented");
	}

	public synchronized T set(final int index, final T element) {

		setFreq(element);
		final T result = delegate.set(index, element);
		return result;
	}

	private void setFreq(final T element) {

		final Collection<T> same = UtilCollection.findSame(element, this);
		if(same.isEmpty()) {
			element.setFrequency(1);
		} else {
			element.setFrequency(same.size() + 1);
		}
		for(final T t : same) {
			t.setFrequency(element.getFrequency());
		}
	}

	public int size() {

		return delegate.size();
	}

	public List<T> subList(final int fromIndex, final int toIndex) {

		return delegate.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {

		return delegate.toArray();
	}

	public <T> T[] toArray(final T[] a) {

		return delegate.toArray(a);
	}

	public String toString() {

		return delegate.toString();
	}
}
