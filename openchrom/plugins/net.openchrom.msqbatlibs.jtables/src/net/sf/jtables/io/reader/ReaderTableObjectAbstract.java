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
package net.sf.jtables.io.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import net.sf.kerner.utils.io.buffered.IOIterator;

import org.apache.log4j.Logger;

public abstract class ReaderTableObjectAbstract<T> implements IOIterator<T> {

	protected final ReaderTableString reader;
	private final static Logger logger = Logger.getLogger(ReaderTableObjectAbstract.class);

	public ReaderTableObjectAbstract(final BufferedReader reader, final boolean columnIds, final boolean rowIds) throws IOException {
		this.reader = new ReaderTableString(reader, columnIds, rowIds);
	}

	public ReaderTableObjectAbstract(final BufferedReader reader, final boolean columnIds, final boolean rowIds, final String delim) throws IOException {
		this.reader = new ReaderTableString(reader, columnIds, rowIds, delim);
	}

	public ReaderTableObjectAbstract(final File file) throws IOException {
		this.reader = new ReaderTableString(file, true, false);
	}

	public ReaderTableObjectAbstract(final File file, final boolean columnIds, final boolean rowIds) throws IOException {
		this.reader = new ReaderTableString(file, columnIds, rowIds);
	}

	public ReaderTableObjectAbstract(final File file, final boolean columnIds, final boolean rowIds, final String delim) throws IOException {
		this.reader = new ReaderTableString(file, columnIds, rowIds, delim);
	}

	public ReaderTableObjectAbstract(final InputStream stream, final boolean columnIds, final boolean rowIds) throws IOException {
		this.reader = new ReaderTableString(stream, columnIds, rowIds);
	}

	public ReaderTableObjectAbstract(final InputStream stream, final boolean columnIds, final boolean rowIds, final String delim) throws IOException {
		this.reader = new ReaderTableString(stream, columnIds, rowIds, delim);
	}

	public ReaderTableObjectAbstract(final Reader reader, final boolean columnIds, final boolean rowIds) throws IOException {
		this.reader = new ReaderTableString(reader, columnIds, rowIds);
	}

	public ReaderTableObjectAbstract(final Reader reader, final boolean columnIds, final boolean rowIds, final String delim) throws IOException {
		this.reader = new ReaderTableString(reader, columnIds, rowIds, delim);
	}

	public synchronized void close() {

		reader.close();
	}

	public boolean hasNext() throws IOException {

		return reader.hasNext();
	}

	public List<T> readAll() throws IOException {

		final List<T> result = new ArrayList<T>();
		while(hasNext()) {
			final T next = next();
			if(next == null) {
				logger.debug("omit null element");
			} else {
				result.add(next);
			}
		}
		return result;
	}
}
