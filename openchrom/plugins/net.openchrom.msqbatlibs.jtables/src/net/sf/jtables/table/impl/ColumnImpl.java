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

import net.sf.jtables.table.Column;

/**
 * 
 * Default implementation for {@link Column}.
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
 *            type of table element
 */
public class ColumnImpl<T> extends RowImpl<T> implements Column<T> {

	public ColumnImpl() {

		// TODO Auto-generated constructor stub
	}

	public ColumnImpl(List<T> elements) {

		super(elements);
		// TODO Auto-generated constructor stub
	}

	public ColumnImpl(T... elements) {

		super(elements);
		// TODO Auto-generated constructor stub
	}
}
