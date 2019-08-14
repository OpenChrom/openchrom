/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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

import java.util.Collection;
import java.util.List;

public abstract class AbstractTransformingListFactory<T, V> extends AbstractListTransformer<T, V> implements FactoryList<V> {

	public AbstractTransformingListFactory() {
		super();
	}

	public AbstractTransformingListFactory(final FactoryList<V> factory) {
		super(factory);
	}

	public List<V> createCollection() {

		return factory.createCollection();
	}

	public List<V> createCollection(final Collection<? extends V> template) {

		return factory.createCollection(template);
	}
}
