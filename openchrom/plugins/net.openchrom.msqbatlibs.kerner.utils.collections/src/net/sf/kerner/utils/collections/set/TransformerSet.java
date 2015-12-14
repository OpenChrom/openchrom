/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

import net.sf.kerner.utils.collections.TransformerCollection;

public interface TransformerSet<T, V> extends TransformerCollection<T, V> {

	/**
	 * Transforms each element contained by given {@link Set} and returns another {@link Set} which contains all
	 * transformed elements (in the same order).
	 * 
	 * @param collection
	 *            {@link Collection} that contains elements to transform
	 * @return {@link Set} containing transformed elements
	 */
	Set<V> transformCollection(Collection<? extends T> collection);
}
