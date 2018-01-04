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
package net.sf.jtables.table.impl;

import java.util.List;

import net.sf.jtables.table.Row;

/**
 * 
 * An {@link net.sf.jtables.table.TableMutableAnnotated AnnotatedMutableTable} with {@link java.lang.Object Object} elements.
 * 
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-12-25
 * 
 */
public class ObjectTable extends AnnotatedMutableTableImpl<Object> {

	public ObjectTable() {
		super();
	}

	public ObjectTable(List<Row<Object>> rows) {
		super(rows);
	}
}
