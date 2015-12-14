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

import net.sf.jtables.table.Table;
import net.sf.kerner.utils.io.lazy.LazyStringWriter;

/**
 * 
 * 
 * A {@code TableWriter} will write a {@link net.sf.jtables.table.Table Table} to
 * <ul>
 * <li>
 * a {@link java.io.File}</li>
 * <li>
 * a {@link java.io.Writer}</li>
 * <li>
 * an {@link java.io.OutputStream}</li>
 * </ul>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-03-12
 */
public class WriterTableLazy extends LazyStringWriter {

	/**
	 * 
	 * Construct a {@code TableWriter} that will write given {@link net.sf.jtables.table.Table Table}.
	 * 
	 * @param table
	 *            {@link net.sf.jtables.table.Table Table} to write
	 */
	public WriterTableLazy(Table<?> table) {

		super(table.toString());
	}
}
