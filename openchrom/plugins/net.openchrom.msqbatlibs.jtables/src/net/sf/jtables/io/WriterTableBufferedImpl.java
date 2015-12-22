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
package net.sf.jtables.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.Table;
import net.sf.jtables.table.TableAnnotated;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.io.buffered.AbstractBufferedWriter;

/**
 * 
 * Default implementation for {@link WriterTableBuffered}.
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
 * <p>
 * last reviewed: 0000-00-00
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-02-28
 * 
 */
public class WriterTableBufferedImpl extends AbstractBufferedWriter implements WriterTableBuffered, WriterTable {

	/**
	 * Default delimiter character.
	 */
	public final static String DEFAULT_DELIMITER = "\t";
	protected volatile List<? extends Object> colIds = new ArrayList<Object>();
	protected volatile boolean firstColumn = true;
	protected volatile boolean firstRow = true;
	protected volatile List<? extends Object> rowIds = new ArrayList<Object>();

	/**
	 * Creates a new {@code WriterTableBufferedImpl} that will write to given {@link File}.
	 * 
	 * @param file
	 *            {@link File} to write to
	 * @throws IOException
	 *             if file is not accessible for writing
	 */
	public WriterTableBufferedImpl(final File file) throws IOException {

		super(file);
	}

	/**
	 * Creates a new {@code WriterTableBufferedImpl} that will write to given {@link OutputStream}.
	 * 
	 * @param stream
	 *            {@link OutputStream} to write to
	 * @throws IOException
	 *             if stream is not accessible for writing
	 */
	public WriterTableBufferedImpl(final OutputStream stream) throws IOException {

		super(stream);
	}

	/**
	 * Creates a new {@code WriterTableBufferedImpl} that will write to given {@link Writer}.
	 * 
	 * @param writer
	 *            {@link Writer} to write to
	 * @throws IOException
	 *             if writer is not accessible for writing
	 */
	public WriterTableBufferedImpl(final Writer writer) throws IOException {

		super(writer);
	}

	public List<Object> getColIds() {

		return new ArrayList<Object>(colIds);
	}

	public List<Object> getRowIds() {

		return new ArrayList<Object>(rowIds);
	}

	public void setColIds(final List<? extends Object> colIds) {

		this.colIds = colIds;
	}

	public void setColIds(final Object... colIds) {

		setColIds(Arrays.asList(colIds));
	}

	public void setRowIds(final List<? extends Object> rowIds) {

		this.rowIds = rowIds;
	}

	public WriterTableBufferedImpl write(final List<? extends Row<? extends Object>> rows) throws IOException {

		return write(DEFAULT_DELIMITER, rows);
	}

	public WriterTableBufferedImpl write(final Row<? extends Object> row) throws IOException {

		return write(DEFAULT_DELIMITER, row);
	}

	public WriterTableBufferedImpl write(final Row<? extends Object>... rows) throws IOException {

		return write(DEFAULT_DELIMITER, rows);
	}

	public WriterTableBufferedImpl write(final String delimiter, final List<? extends Row<? extends Object>> rows) throws IOException {

		final Iterator<? extends Row<? extends Object>> it = rows.iterator();
		while(it.hasNext()) {
			final Row<?> r = it.next();
			write(delimiter, r);
		}
		return this;
	}

	public WriterTableBufferedImpl write(final String delimiter, final Row<? extends Object>... rows) throws IOException {

		return write(delimiter, Arrays.asList(rows));
	}

	public WriterTableBufferedImpl write(final String delimiter, final Row<?> row) throws IOException {

		if(firstRow && colIds != null && colIds.size() > 0) {
			writer.write(UtilCollection.toString(colIds, delimiter));
			writer.newLine();
			firstRow = false;
		}
		writer.write(row.toString(delimiter));
		writer.newLine();
		return this;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public WriterTable write(final String delimiter, final Table<? extends Object> table) throws IOException {

		if(table instanceof TableAnnotated) {
			setColIds(((TableAnnotated)table).getColumnIdentifier());
			setRowIds(((TableAnnotated)table).getRowIdentifier());
		}
		write(delimiter, table.getRows());
		return this;
	}

	public WriterTable write(final Table<? extends Object> table) throws IOException {

		return write(DEFAULT_DELIMITER, table);
	}
}
