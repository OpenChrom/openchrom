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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.sf.jtables.table.impl.TableDouble;

/**
 * 
 * {@link ReaderTable} to read a table that contains {@link Double} values.
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
public class ReaderTableDouble extends ReaderTableAbstract<Double> {

	/**
	 * 
	 * Create a new {@code DoubleTableReader}.
	 * 
	 * @param reader
	 *            {@link Reader} from which table is read
	 * @param columnIds
	 *            {@code true}, if columns have headers; {@code false} otherwise
	 * @param rowIds
	 *            {@code true}, if rows have headers; {@code false} otherwise
	 * @param delim
	 *            column delimiter to use
	 * @throws IOException
	 *             if anything goes wrong
	 */
	public ReaderTableDouble(File file, boolean columnIds, boolean rowIds, String delim) throws IOException {
		super(file, columnIds, rowIds, delim);
	}

	/**
	 * 
	 * Create a new {@code DoubleTableReader}.
	 * 
	 * @param file
	 *            {@link File} from which table is read
	 * @param columnIds
	 *            {@code true}, if columns have headers; {@code false} otherwise
	 * @param rowIds
	 *            {@code true}, if rows have headers; {@code false} otherwise
	 * 
	 * @throws IOException
	 *             if anything goes wrong
	 */
	public ReaderTableDouble(File file, boolean columnIds, boolean rowIds) throws IOException {
		super(file, columnIds, rowIds);
	}

	/**
	 * 
	 * Create a new {@code DoubleTableReader}.
	 * 
	 * @param stream
	 *            {@link InputStream} from which table is read
	 * @param columnIds
	 *            {@code true}, if columns have headers; {@code false} otherwise
	 * @param rowIds
	 *            {@code true}, if rows have headers; {@code false} otherwise
	 * @param delim
	 *            column delimiter to use
	 * @throws IOException
	 *             if anything goes wrong
	 */
	public ReaderTableDouble(InputStream stream, boolean columnIds, boolean rowIds, String delim) throws IOException {
		super(stream, columnIds, rowIds, delim);
	}

	/**
	 * 
	 * Create a new {@code DoubleTableReader}.
	 * 
	 * @param stream
	 *            {@link InputStream} from which table is read
	 * @param columnIds
	 *            {@code true}, if columns have headers; {@code false} otherwise
	 * @param rowIds
	 *            {@code true}, if rows have headers; {@code false} otherwise
	 * 
	 * @throws IOException
	 *             if anything goes wrong
	 */
	public ReaderTableDouble(InputStream stream, boolean columnIds, boolean rowIds) throws IOException {
		super(stream, columnIds, rowIds);
	}

	/**
	 * 
	 * Create a new {@code DoubleTableReader}.
	 * 
	 * @param reader
	 *            {@link Reader} from which table is read
	 * @param columnIds
	 *            {@code true}, if columns have headers; {@code false} otherwise
	 * @param rowIds
	 *            {@code true}, if rows have headers; {@code false} otherwise
	 * @param delim
	 *            column delimiter to use
	 * @throws IOException
	 *             if anything goes wrong
	 */
	public ReaderTableDouble(Reader reader, boolean columnIds, boolean rowIds, String delim) throws IOException {
		super(reader, columnIds, rowIds, delim);
	}

	/**
	 * 
	 * Create a new {@code DoubleTableReader}.
	 * 
	 * @param reader
	 *            {@link Reader} from which table is read
	 * @param columnIds
	 *            {@code true}, if columns have headers; {@code false} otherwise
	 * @param rowIds
	 *            {@code true}, if rows have headers; {@code false} otherwise
	 * 
	 * @throws IOException
	 *             if anything goes wrong
	 */
	public ReaderTableDouble(Reader reader, boolean columnIds, boolean rowIds) throws IOException {
		super(reader, columnIds, rowIds);
	}

	/**
	 * 
	 */
	protected TableDouble getInstance() {

		return new TableDouble();
	}

	/**
	 * 
	 */
	protected Double parse(String s) throws NumberFormatException {

		return Double.parseDouble(s);
	}

	public TableDouble readTableAtOnce() throws IOException {

		return (TableDouble)super.readTableAtOnce();
	}
}
