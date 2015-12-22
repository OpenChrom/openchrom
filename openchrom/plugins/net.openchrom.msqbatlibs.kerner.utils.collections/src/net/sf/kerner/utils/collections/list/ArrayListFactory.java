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
package net.sf.kerner.utils.collections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * A {@link FactoryList} that returns instances of {@link ArrayList}.
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
 * last reviewed: 2013-10-16
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * 
 * @param <E>
 */
public class ArrayListFactory<E> implements FactoryList<E> {

	private final int size;

	public ArrayListFactory() {

		this.size = -1;
	}

	public ArrayListFactory(final int size) {

		this.size = size;
	}

	public synchronized List<E> createCollection() {

		if(size > -1) {
			return new ArrayList<E>(size);
		}
		return new ArrayList<E>();
	}

	public synchronized List<E> createCollection(final Collection<? extends E> template) {

		return new ArrayList<E>(template);
	}

	public synchronized int getSize() {

		return size;
	}
}
