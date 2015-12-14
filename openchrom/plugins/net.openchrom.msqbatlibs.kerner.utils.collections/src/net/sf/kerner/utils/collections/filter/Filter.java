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
package net.sf.kerner.utils.collections.filter;

/**
 * A {@code Filter} represents a filtering function, more generally it may be
 * understood as a <a
 * href="http://en.wikipedia.org/wiki/Predicate_%28mathematical_logic%29"
 * >Predicate</a> on {@code E}, which is a {@link Boolean}-valued function.
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-10-26
 * @param <E>
 *            type of elements which are filtered
 */
public interface Filter<E> {

	/**
	 *
	 * @param e
	 *            Element which is filtered
	 * @return {@code true}, if this {@code Filter} accepts given element {@code E}; {@code false} otherwise
	 */
	boolean filter(E e);
}
