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
package net.sf.jtables.table.impl;

import java.util.List;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.TableMutableAnnotated;

/**
 * 
 * An {@link TableMutableAnnotated} with {@link Integer} elements.
 * 
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-01-25
 * 
 */
public class TableInteger extends AnnotatedMutableTableImpl<Integer> {

	/**
	 * 
	 * Create a empty {@code IntegerTable}.
	 * 
	 */
	public TableInteger() {
		super();
	}

	/**
	 * 
	 * Create a new {@code IntegerTable} that contains given rows.
	 * 
	 * @param rows
	 *            rows that are initially contained by this {@code Table}
	 */
	public TableInteger(List<Row<Integer>> rows) {
		super(rows);
	}
}
