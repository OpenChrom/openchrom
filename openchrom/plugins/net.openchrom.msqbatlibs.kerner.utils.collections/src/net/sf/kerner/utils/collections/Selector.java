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
package net.sf.kerner.utils.collections;

import java.util.Collection;

/**
 * A {@code Selector} selects and returns one element out of a given {@link Collection} of elements.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-05-02
 * @param <T>
 *            type of elements
 */
public interface Selector<T> {

	/**
	 * Select one element from given {@code Collection} that fits this {@code Selector's} needs.
	 *
	 * @param elements
	 *            {@link Collection} from which one element is selected
	 * @return element that was selected by this {@code Selector}
	 */
	T select(Collection<? extends T> elements);
}
