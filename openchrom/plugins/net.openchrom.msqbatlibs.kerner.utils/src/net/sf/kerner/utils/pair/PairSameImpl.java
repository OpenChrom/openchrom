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
package net.sf.kerner.utils.pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Default implementation for {@link PairSame}.
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
public class PairSameImpl<T> extends PairImpl<T, T> implements PairSame<T> {

	public PairSameImpl() {

		super();
	}

	public PairSameImpl(final Pair<? extends T, ? extends T> template) {

		super(template);
	}

	public PairSameImpl(final T first, final T second) {

		super(first, second);
	}

	public List<T> asList() {

		final T f = getFirst();
		final T s = getSecond();
		final List<T> result = new ArrayList<T>();
		if(f != null) {
			result.add(f);
		}
		if(s != null) {
			result.add(s);
		}
		return result;
	}

	@Override
	public PairSameImpl<T> clone() {

		return new PairSameImpl<T>(this);
	}

	@Override
	public PairSameImpl<T> invert() {

		return new PairSameImpl<T>(getSecond(), getFirst());
	}

	public Iterator<T> iterator() {

		return asList().iterator();
	}
}
