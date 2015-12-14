/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
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

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.Table;

/**
 * 
 * A {@link Writer} to write {@link Table Tables} row by row.
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
 * last reviewed: 2013-02-26
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-02-26
 * 
 */
public interface WriterTableBuffered {

	/**
	 * Writes given rows using default column delimiter.
	 * 
	 * @param rows
	 *            Rows that should be written
	 * @return {@code this} {@code WriterTableBuffered}
	 * @throws IOException
	 */
	WriterTableBuffered write(List<? extends Row<? extends Object>> rows) throws IOException;

	/**
	 * Writes given rows using default column delimiter.
	 * 
	 * @param rows
	 *            Rows that should be written
	 * @return {@code this} {@code WriterTableBuffered}
	 * @throws IOException
	 */
	WriterTableBuffered write(Row<? extends Object>... rows) throws IOException;

	/**
	 * Writes given row using default column delimiter.
	 * 
	 * @param row
	 *            Row that should be written
	 * @return {@code this} {@code WriterTableBuffered}
	 * @throws IOException
	 */
	WriterTableBuffered write(Row<?> row) throws IOException;

	/**
	 * Writes given rows using given column delimiter.
	 * 
	 * @param rows
	 *            Rows that should be written
	 * @param delimiter
	 *            column delimiter that should be used
	 * @return {@code this} {@code WriterTableBuffered}
	 * @throws IOException
	 */
	WriterTableBuffered write(String delimiter, List<? extends Row<? extends Object>> rows) throws IOException;

	/**
	 * Writes given rows using given column delimiter.
	 * 
	 * @param rows
	 *            Rows that should be written
	 * @param delimiter
	 *            column delimiter that should be used
	 * @return {@code this} {@code WriterTableBuffered}
	 * @throws IOException
	 */
	WriterTableBuffered write(String delimiter, Row<? extends Object> row) throws IOException;

	/**
	 * Writes given row using given column delimiter.
	 * 
	 * @param row
	 *            Row that should be written
	 * @param delimiter
	 *            column delimiter that should be used
	 * @return {@code this} {@code WriterTableBuffered}
	 * @throws IOException
	 */
	WriterTableBuffered write(String delimiter, Row<? extends Object>... rows) throws IOException;
}
