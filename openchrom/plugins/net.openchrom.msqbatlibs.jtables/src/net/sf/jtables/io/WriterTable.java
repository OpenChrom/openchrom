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

import java.io.Closeable;
import java.io.IOException;

import net.sf.jtables.table.Table;

/**
 * 
 * TODO description
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
public interface WriterTable extends Closeable {

	/**
	 * Writes {@link Table} using given column delimiter.
	 * 
	 * @param delimiter
	 *            column delimiter that is used for writing
	 * @param table
	 *            {@link Table} that is written
	 * @return {@code this}
	 * @throws IOException
	 */
	WriterTable write(String delimiter, Table<? extends Object> table) throws IOException;

	/**
	 * Writes {@link Table}.
	 * 
	 * @param table
	 *            {@link Table} that is written
	 * @return {@code this}
	 * @throws IOException
	 */
	WriterTable write(Table<? extends Object> table) throws IOException;
}
