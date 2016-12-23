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
package net.sf.kerner.utils.io.buffered;

import java.io.Closeable;
import java.io.IOException;

/**
 * An instance of {@code IOIterable} provides an [@link IOIterator} for reading
 * elements in an iterator based manner.
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
 * @version 2011-11-28
 * @param <E>
 *            type of elements which are iterated
 * @see java.lang.Iterable Iterable
 */
public interface IOIterable<E> extends Closeable {

	void close();

	/**
	 * Retrieve the {@code IOIterator}.
	 * 
	 * @return the {@code IOIterator}
	 * @throws IOException
	 *             if anything goes wrong
	 */
	IOIterator<E> getIterator() throws IOException;
}
