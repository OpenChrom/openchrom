/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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

import net.sf.jtables.table.impl.RowImpl;
import net.sf.jtables.table.impl.RowString;
import net.sf.jtables.table.impl.TableString;

/**
 *
 * {@link ReaderTable} to read a table that contains {@link String} values.
 *
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-03-13
 *
 */
public class ReaderTableString extends ReaderTableAbstract<String> {

	public ReaderTableString(final BufferedReader reader, final boolean columnIds, final boolean rowIds) throws IOException {
		super(reader, columnIds, rowIds);
	}

	public ReaderTableString(final BufferedReader reader, final boolean columnIds, final boolean rowIds, final String delim) throws IOException {
		super(reader, columnIds, rowIds, delim);
	}

	public ReaderTableString(final File file) throws IOException {
		super(file);
	}

	public ReaderTableString(final File file, final boolean columnIds, final boolean rowIds) throws IOException {
		super(file, columnIds, rowIds);
	}

	public ReaderTableString(final File file, final boolean columnIds, final boolean rowIds, final String delim) throws IOException {
		super(file, columnIds, rowIds, delim);
	}

	public ReaderTableString(final InputStream stream, final boolean columnIds, final boolean rowIds) throws IOException {
		super(stream, columnIds, rowIds);
	}

	public ReaderTableString(final InputStream stream, final boolean columnIds, final boolean rowIds, final String delim) throws IOException {
		super(stream, columnIds, rowIds, delim);
	}

	public ReaderTableString(final Reader reader, final boolean columnIds, final boolean rowIds) throws IOException {
		super(reader, columnIds, rowIds);
	}

	public ReaderTableString(final Reader reader, final boolean columnIds, final boolean rowIds, final String delim) throws IOException {
		super(reader, columnIds, rowIds, delim);
	}

	protected TableString getInstance() {

		return new TableString();
	}

	protected RowImpl<String> getNewRowInstance() {

		return new RowString();
	}

	protected String parse(final String s) {

		return s;
	}

	public TableString readTableAtOnce() throws IOException {

		return (TableString)super.readTableAtOnce();
	}
}
