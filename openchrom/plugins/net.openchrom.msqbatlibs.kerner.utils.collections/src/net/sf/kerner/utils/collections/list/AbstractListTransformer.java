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
package net.sf.kerner.utils.collections.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.kerner.utils.transformer.Transformer;

public abstract class AbstractListTransformer<T, V> extends ListWalkerDefault<T> implements Transformer<T, V>, TransformerList<T, V> {

	protected final FactoryList<V> factory;
	protected volatile List<V> result;
	protected volatile int currentIndex;

	public AbstractListTransformer() {
		this(new ArrayListFactory<V>());
	}

	public AbstractListTransformer(final FactoryList<V> factory) {
		this.factory = factory;
		super.addVisitor(new DefaultListVisitorImpl<T>() {

			public synchronized Void visit(final T element, final int index) {

				setCurrentIndex(index);
				result.add(transform(element));
				return null;
			}
		});
	}

	public synchronized void beforeWalk() {

		super.beforeWalk();
		result = factory.createCollection();
	}

	public synchronized List<V> getAgain() {

		return Collections.unmodifiableList(result);
	}

	protected synchronized int getCurrentIndex() {

		return currentIndex;
	}

	private synchronized void setCurrentIndex(final int currentIndex) {

		this.currentIndex = currentIndex;
	}

	/**
	 * if {@code element == null}, empty list is returned.
	 */
	public synchronized List<V> transformCollection(final Collection<? extends T> element) {

		if(element != null)
			if(element instanceof List)
				walk((List<? extends T>)element);
			else
				walk(new ArrayList<T>(element));
		return result;
	}
}
