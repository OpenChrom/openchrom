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
package net.sf.jtables.table;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * A {@code AnnotatedTable} extends {@link Table} by providing object
 * identifiers for rows and columns. <br>
 * This makes it possible to access rows and columns not only by their integer
 * index, but also by an additional object mapping.
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
 * @version 2012-01-25
 * 
 * @param <T>
 *            type of elements in {@code Table}
 */
public interface TableAnnotated<T> extends Table<T> {

	/**
	 * 
	 * Retrieve column that is associated to given object key.
	 * 
	 * @param key
	 *            identifier that maps to returned column
	 * @return column that is mapped by given identifier
	 * @throws NoSuchElementException
	 *             if there is no column mapped by given identifier
	 */
	Column<T> getColumn(Object key) throws NoSuchElementException;

	/**
	 * 
	 * Retrieve a {@link List} that contains all column identifiers in proper
	 * order.
	 * 
	 * @return all column identifiers
	 */
	List<Object> getColumnIdentifier();

	/**
	 * 
	 * Retrieve row that is associated to given object key.
	 * 
	 * @param key
	 *            identifier that maps to returned row
	 * @return row that is mapped by given identifier
	 * @throws NoSuchElementException
	 *             if there is no row mapped by given identifier
	 */
	Row<T> getRow(Object key) throws NoSuchElementException;

	/**
	 * 
	 * Retrieve a {@link List} that contains all row identifiers in proper
	 * order.
	 * 
	 * @return all row identifiers
	 */
	List<Object> getRowIdentifier();

	TableAnnotated<T> sortByColumnIds();
}
