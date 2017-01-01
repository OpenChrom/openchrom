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
package net.sf.kerner.utils.collections.filter;

import net.sf.kerner.utils.Util;

/**
 * Simple class to invert the filtering logic of another {@link Filter}.
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
 * @version 2011-10-26
 * @see Filter
 * @param <E>
 *            type of elements which are filtered
 */
public class FilterInverter<E> implements Filter<E> {

	protected final Filter<E> filter;

	public FilterInverter(final Filter<E> filter) {
		Util.checkForNull(filter);
		this.filter = filter;
	}

	public boolean filter(final E element) {

		return !filter.filter(element);
	}
}
