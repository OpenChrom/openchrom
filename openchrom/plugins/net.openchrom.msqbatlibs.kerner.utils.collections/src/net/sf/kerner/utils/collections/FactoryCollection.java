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
package net.sf.kerner.utils.collections;

import java.util.Collection;

/**
 * A {@code FactoryCollection} provides factory methods to retrieve a new instance of all kind of direct and indirect
 * implementations of {@link Collection}.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-04-16
 * @param E
 *            type of elements within the collection
 */
public interface FactoryCollection<E> {

	/**
	 * Get a new instance for specified {@code Collection}.
	 * 
	 * @return new {@code Collection}
	 */
	Collection<E> createCollection();

	/**
	 * Get a new instance for specified {@code Collection} containing all given elements.
	 * 
	 * @param elements
	 *            that are contained in new collection
	 * @return new {@code Collection}
	 */
	Collection<E> createCollection(Collection<? extends E> elements);
}
