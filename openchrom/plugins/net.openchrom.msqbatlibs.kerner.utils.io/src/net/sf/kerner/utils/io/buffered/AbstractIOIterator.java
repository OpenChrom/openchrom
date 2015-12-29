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
package net.sf.kerner.utils.io.buffered;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.NoSuchElementException;

/**
 * Prototype implementation for {@link IOIterator}.
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
 * @version 2012-03-13
 * @param <E>
 *            type of elements which are iterated / read by this {@code AbstractIOIterator}
 */
public abstract class AbstractIOIterator<E> extends AbstractBufferedReader implements IOIterator<E> {

	/**
	 * Peek element.
	 */
	protected volatile E peek = null;
	protected volatile boolean needToPeek = true;

	/**
	 * Create a new {@code AbstractIOIterator} that reads from given {@link java.io.BufferedReader BufferedReader}.
	 * <p>
	 * <b>Note:</b> Passed in {@code java.io.BufferedReader} is kept as a reference. Use this constructor if you want to work on same {@link java.io.BufferedReader} which more than one reading-proxies.
	 * </p>
	 * 
	 * @param reader
	 *            {@code BufferedReader} to read from
	 * @throws IOException
	 */
	public AbstractIOIterator(BufferedReader reader) throws IOException {
		super(reader);
	}

	/**
	 * Create a new {@code AbstractIOIterator} that reads from given file.
	 * 
	 * @param file
	 *            file to read from
	 * @throws IOException
	 */
	public AbstractIOIterator(File file) throws IOException {
		super(file);
	}

	/**
	 * Create a new {@code AbstractIOIterator} that reads from given {@link java.io.InputStream InputStream}.
	 * 
	 * @param stream
	 *            {@code InputStream} to read from
	 * @throws IOException
	 *             if reading fails
	 */
	public AbstractIOIterator(InputStream stream) throws IOException {
		super(stream);
	}

	/**
	 * Create a new {@code AbstractIOIterator} that reads from given {@link java.io.Reader Reader}.
	 * 
	 * @param reader
	 *            reader to read from
	 * @throws IOException
	 *             if reading fails
	 */
	public AbstractIOIterator(Reader reader) throws IOException {
		super(reader);
	}

	private synchronized void peek() throws IOException {

		peek = doRead();
		needToPeek = false;
	}

	public synchronized boolean hasNext() throws IOException {

		if(needToPeek) {
			peek();
		}
		return (peek != null);
	}

	/**
	 * 
	 */
	public synchronized E next() throws IOException {

		if(hasNext()) {
			needToPeek = true;
			return peek;
		}
		throw new NoSuchElementException("no more elements");
	}

	/**
	 * Perform the read operation.
	 * 
	 * @return the read element of type {@code E}
	 * @throws IOException
	 *             if reading fails
	 */
	protected abstract E doRead() throws IOException;
}
