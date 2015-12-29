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
package net.sf.jfasta.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFile;
import net.sf.kerner.utils.io.UtilIO;

/**
 * 
 * TODO description
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2013-04-29
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-04-29
 * 
 */
public class FASTAFileImpl implements FASTAFile {

	private static final long serialVersionUID = 3363308756893284011L;
	volatile int lineLength = FASTAFile.DEFAULT_LINE_LENGTH;
	protected final Collection<FASTAElement> elements = new LinkedHashSet<FASTAElement>();

	public FASTAFileImpl() {
	}

	public FASTAFileImpl(final Collection<? extends FASTAElement> elements) {
		this.elements.addAll(elements);
	}

	public FASTAFileImpl(final FASTAElement element) {
		elements.add(element);
	}

	/**
	 * 
	 */
	public boolean add(final FASTAElement e) {

		synchronized(elements) {
			return elements.add(e);
		}
	}

	/**
	 * 
	 */
	public boolean addAll(final Collection<? extends FASTAElement> c) {

		synchronized(elements) {
			return elements.addAll(c);
		}
	}

	/**
	 * 
	 */
	public void clear() {

		synchronized(elements) {
			elements.clear();
		}
	}

	/**
	 * 
	 */
	public boolean contains(final Object o) {

		synchronized(elements) {
			return elements.contains(o);
		}
	}

	/**
	 * 
	 */
	public boolean containsAll(final Collection<?> c) {

		synchronized(elements) {
			return elements.containsAll(c);
		}
	}

	public boolean equals(final Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof FASTAFileImpl))
			return false;
		final FASTAFileImpl other = (FASTAFileImpl)obj;
		if(elements == null) {
			if(other.elements != null)
				return false;
		} else if(!elements.equals(other.elements))
			return false;
		return true;
	}

	public FASTAElement getElementByHeader(final String header) {

		if(header == null)
			throw new NullPointerException();
		for(final FASTAElement e : elements) {
			if(e.getHeader().equals(header)) {
				return e;
			}
		}
		throw new NoSuchElementException("no FASTA element with header \"" + header + "\"");
	}

	public FASTAElement getLargestElement() {

		if(elements.isEmpty())
			return null;
		FASTAElement result = elements.iterator().next();
		for(final FASTAElement e : elements) {
			if(e.getSequenceLength() > result.getSequenceLength()) {
				result = e;
			}
		}
		return result;
	}

	public int getLineLength() {

		return lineLength;
	}

	public boolean hasElementByHeader(final String header) {

		for(final FASTAElement e : elements) {
			if(e.getHeader().equals(header)) {
				return true;
			}
		}
		return false;
	}

	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	/**
	 * 
	 */
	public boolean isEmpty() {

		synchronized(elements) {
			return elements.isEmpty();
		}
	}

	public Iterator<FASTAElement> iterator() {

		return elements.iterator();
	}

	/**
	 * 
	 */
	public boolean remove(final Object o) {

		synchronized(elements) {
			return elements.remove(o);
		}
	}

	/**
	 * 
	 */
	public boolean removeAll(final Collection<?> c) {

		synchronized(elements) {
			return elements.removeAll(c);
		}
	}

	/**
	 * 
	 */
	public boolean retainAll(final Collection<?> c) {

		synchronized(elements) {
			return elements.retainAll(c);
		}
	}

	public void setLineLength(final int len) {

		lineLength = len;
	}

	/**
	 * 
	 */
	public int size() {

		synchronized(elements) {
			return elements.size();
		}
	}

	/**
	 * 
	 */
	public Object[] toArray() {

		synchronized(elements) {
			return elements.toArray();
		}
	}

	/**
	 * 
	 */
	public <T> T[] toArray(final T[] a) {

		synchronized(elements) {
			return elements.toArray(a);
		}
	}

	public String toString() {

		final StringBuilder sb = new StringBuilder();
		final Iterator<?> it = elements.iterator();
		while(it.hasNext()) {
			sb.append(it.next());
			if(it.hasNext()) {
				sb.append(UtilIO.NEW_LINE_STRING);
			}
		}
		return sb.toString();
	}
}
