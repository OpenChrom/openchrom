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
package net.sf.kerner.utils.pair;

import java.util.List;

/**
 * 
 * {@code ObjectPairSame} is an {@link Pair}, where both {@code first} and {@code second} object are of same type.
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2013-04-03
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-04-03
 * 
 * @param <T>
 *            type of objects
 */
public interface PairSame<T> extends Pair<T, T>, Iterable<T> {

	/**
	 * Returns a {@link List}, which contains two elements, {@link PairSame#getFirst()} and {@link PairSame#getSecond()},
	 * in this order.
	 * 
	 * @return a {@link List}, which contains {@link PairSame#getFirst()} and {@link PairSame#getSecond()}
	 */
	List<T> asList();
}
