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
package net.sf.kerner.utils.equal;

import net.sf.kerner.utils.pair.Pair;
import net.sf.kerner.utils.transformer.Transformer;

/**
 * An Equalator checks if two objects are equal.
 *
 * @author <a href="mailto:alexander.kerner@silico-sciences.com">Alexander
 *         Kerner</a>
 *
 * @param <T>
 *            type of object that is checked for equality
 */
public interface Equalator<T> extends Transformer<Pair<T, Object>, Boolean> {

	/**
	 * Checks if two provided objects are equal.
	 * 
	 * @param obj1
	 *            first object
	 * @param obj2
	 *            second object
	 * @return {@code true} if {@code obj1} and {@code obj2} are equal;
	 *         {@code false} otherwise
	 */
	boolean areEqual(final T obj1, Object obj2);
}
