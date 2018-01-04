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
package net.sf.kerner.utils.collections.set;

import java.util.Collection;
import java.util.Set;

import net.sf.kerner.utils.collections.FactoryCollection;

/**
 * {@code FactorySet} extends {@link FactoryCollection} by limiting the created {@code Collection} to be a {@link Set}.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-06-15
 * @param <E>
 *            type of elements contained by the {@code Set}
 */
public interface FactorySet<E> extends FactoryCollection<E> {

	/**
	 * Create a new {@link Set}.
	 * 
	 * @return the new {@link Set} instance
	 */
	Set<E> createCollection();

	/**
	 * Create a new {@link Set} containing all given elements.
	 * 
	 * @param template
	 *            Elements which are initially contained by new {@link Set}
	 * @return the new {@link Set} instance
	 */
	Set<E> createCollection(Collection<? extends E> template);
}
