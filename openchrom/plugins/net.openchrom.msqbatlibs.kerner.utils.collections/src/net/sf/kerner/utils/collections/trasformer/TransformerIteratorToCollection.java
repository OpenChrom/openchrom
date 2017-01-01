/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.trasformer;

import java.util.Collection;
import java.util.Iterator;

import net.sf.kerner.utils.collections.FactoryCollection;
import net.sf.kerner.utils.collections.list.ArrayListFactory;

public class TransformerIteratorToCollection {

	public static <C> Collection<C> transform(final Iterable<? extends C> iterable) {

		return transform(iterable, new ArrayListFactory<C>());
	}

	public static <C> Collection<C> transform(final Iterable<? extends C> iterable, final FactoryCollection<C> factory) {

		final Collection<C> result = factory.createCollection();
		final Iterator<? extends C> it = iterable.iterator();
		while(it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}
}
