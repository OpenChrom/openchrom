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
package net.sf.jtables.table;

import java.util.List;

/**
 * 
 * A {@code MutableTable} and also a {@code AnnotatedTable}.
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-01-25
 * 
 * @param <T>
 *            type of elements in {@Table}
 * 
 * @see TableMutable
 * @see TableAnnotated
 */
public interface TableMutableAnnotated<T> extends TableAnnotated<T>, TableMutable<T> {

	/**
	 * 
	 * Set identifiers for columns.
	 * 
	 * @param ids
	 *            a {@link List} that contains all column identifiers
	 */
	void setColumnIdentifier(List<? extends Object> ids);

	/**
	 * 
	 * Set identifiers for rows.
	 * 
	 * @param ids
	 *            a {@link List} that contains all row identifiers
	 */
	void setRowIdentifier(List<? extends Object> ids);

	void addRow(Object id, Row<T> row);

	void addColumn(Object id, Column<T> row);

	void addRow(Object id, Row<T> row, int index);

	void addColumn(Object id, Column<T> row, int index);
}
