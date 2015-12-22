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
package net.sf.kerner.utils.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * <p>
 * A {@code GenericReader} provides the ability to read something from
 * <ul>
 * <li>a {@link java.io.File File}</li>
 * <li>a {@link java.io.Reader Reader}</li>
 * <li>an {@link java.io.InputStream IntputStream}</li>
 * </ul>
 * </p>
 * <p>
 * TODO example
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-11-19
 * @see java.io.File File
 * @see java.io.Reader Reader
 * @see java.io.InputStream InputStream
 */
public interface GenericReader<T> {

	/**
	 * Read something from a {@link File}.
	 * 
	 * @param file
	 *            file that is read
	 * @return something that is read from this file
	 * @throws IOException
	 */
	public T read(File file) throws IOException;

	/**
	 * Read something from a {@link Reader}.
	 * 
	 * @param reader
	 *            {@link Reader} from which is read
	 * @return something that is read from this {@link Reader}
	 * @throws IOException
	 */
	public T read(Reader reader) throws IOException;

	/**
	 * Read something from an {@link InputStream}.
	 * 
	 * @param stream
	 *            {@link InputStream} from which is read
	 * @return something that is read from this {@link InputStream}
	 * @throws IOException
	 */
	public T read(InputStream stream) throws IOException;
}
