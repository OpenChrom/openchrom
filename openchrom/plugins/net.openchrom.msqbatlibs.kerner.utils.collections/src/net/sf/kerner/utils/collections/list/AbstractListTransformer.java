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

			@Override
			public synchronized Void visit(final T element, final int index) {

				setCurrentIndex(index);
				result.add(transform(element));
				return null;
			}
		});
	}

	@Override
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
