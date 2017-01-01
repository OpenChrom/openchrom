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
package net.sf.kerner.utils.io.buffered;

import java.io.IOException;

/**
 * An implementation of {@code IOIterator} reads elements in an iterator based manner.
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
 * @version 2010-11-27
 * @param <E>
 *            type of elements which are returned by this {@code IOIterator}
 * @see java.util.Iterator
 */
public interface IOIterator<E> extends GeneralIOIterator {

	/**
	 * Retrieve the next element.
	 * 
	 * @return the next element
	 * @throws IOException
	 *             if reading fails
	 */
	E next() throws IOException;
}
