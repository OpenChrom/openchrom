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
package net.sf.jtables.io.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.sf.jtables.table.impl.TableInteger;

/**
 * 
 * {@link ReaderTable} to read a table that contains {@link Integer} values.
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
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-03-13
 * 
 */
public class ReaderTableInteger extends ReaderTableAbstract<Integer> {

	public ReaderTableInteger(BufferedReader reader, boolean columnIds, boolean rowIds, String delim) throws IOException {
		super(reader, columnIds, rowIds, delim);
	}

	public ReaderTableInteger(BufferedReader reader, boolean columnIds, boolean rowIds) throws IOException {
		super(reader, columnIds, rowIds);
	}

	public ReaderTableInteger(File file, boolean columnIds, boolean rowIds, String delim) throws IOException {
		super(file, columnIds, rowIds, delim);
	}

	public ReaderTableInteger(File file, boolean columnIds, boolean rowIds) throws IOException {
		super(file, columnIds, rowIds);
	}

	public ReaderTableInteger(InputStream stream, boolean columnIds, boolean rowIds, String delim) throws IOException {
		super(stream, columnIds, rowIds, delim);
	}

	public ReaderTableInteger(InputStream stream, boolean columnIds, boolean rowIds) throws IOException {
		super(stream, columnIds, rowIds);
	}

	public ReaderTableInteger(Reader reader, boolean columnIds, boolean rowIds, String delim) throws IOException {
		super(reader, columnIds, rowIds, delim);
	}

	public ReaderTableInteger(Reader reader, boolean columnIds, boolean rowIds) throws IOException {
		super(reader, columnIds, rowIds);
	}

	protected TableInteger getInstance() {

		return new TableInteger();
	}

	protected Integer parse(String s) throws NumberFormatException {

		return Integer.parseInt(s);
	}

	public TableInteger readTableAtOnce() throws IOException {

		return (TableInteger)super.readTableAtOnce();
	}
}
