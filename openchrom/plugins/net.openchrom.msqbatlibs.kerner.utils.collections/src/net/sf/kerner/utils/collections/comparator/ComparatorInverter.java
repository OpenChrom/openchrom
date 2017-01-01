/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.comparator;

import java.util.Comparator;

import net.sf.kerner.utils.Util;

/**
 * Simple class to invert the comparison logic of another {@link Comparator}.
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
 * @version 2012-05-23
 * @see Comparator
 * @param <T>
 *            type of elements which are compared
 */
public class ComparatorInverter<T> implements Comparator<T> {

	protected final Comparator<T> c;

	public ComparatorInverter(final Comparator<T> c) {
		Util.checkForNull(c);
		this.c = c;
	}

	public int compare(final T o1, final T o2) {

		final int result = c.compare(o2, o1);
		return result;
	}
}
