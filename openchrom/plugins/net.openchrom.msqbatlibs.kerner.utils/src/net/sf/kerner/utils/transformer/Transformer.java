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
package net.sf.kerner.utils.transformer;

/**
 * A {@code Transformer} converts one object of type {@code T} to another object
 * of type {@code V}.
 * 
 * @author <a href="mailto:ak@silico-sciences.com">Alexander Kerner</a>
 * @version 2015-09-05
 * 
 * @param <T>
 *            type of input element
 * @param <V>
 *            type of output element
 */
public interface Transformer<T, V> {

	/**
	 * Transforms element of type {@code T} to a new element of type {@code V}.
	 *
	 * @param element
	 *            element that is converted
	 * @return resulting element
	 */
	V transform(T element);
}
