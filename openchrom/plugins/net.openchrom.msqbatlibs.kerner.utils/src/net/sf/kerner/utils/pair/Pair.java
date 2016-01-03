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
package net.sf.kerner.utils.pair;

/**
 * 
 * A Pair of Objects.
 * 
 * <p>
 * last reviewed: 2013-12-09
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * 
 * @param <F>
 *            type of first object
 * @param <S>
 *            type of second object
 */
public interface Pair<F, S> {

	/**
	 * clones this {@code ObjectPair}.
	 * 
	 * @return a new instance of {@code ObjectPair}, holding same {@code first}
	 *         and {@code second} as this {@code ObjectPair}
	 */
	Pair<F, S> clone();

	/**
	 * In contrast to {@link KeyValue}, {@code ObjectPair(1,2)} equals
	 * {@code ObjectPair(2,1)}.
	 * 
	 */
	public boolean equals(Object obj);

	/**
	 * Returns first object.
	 * 
	 * @return first object
	 */
	F getFirst();

	/**
	 * Returns second object.
	 * 
	 * @return second object
	 */
	S getSecond();

	/**
	 * In contrast to {@link KeyValue}, {@code ObjectPair(1,2).hashCode()}
	 * equals {@code ObjectPair(2,1).hashCode()}.
	 * 
	 */
	public int hashCode();

	/**
	 * Inverts this {@code ObjectPair}, returning a new {@code ObjectPair} where
	 * {@code first} and {@code second} are switched.
	 * 
	 * @return an inverted {@code ObjectPair}
	 */
	Pair<S, F> invert();
}
