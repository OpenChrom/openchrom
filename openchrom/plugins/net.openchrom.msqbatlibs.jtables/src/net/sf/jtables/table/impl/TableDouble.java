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
import net.sf.jtables.table.Table;

public class TableDouble extends AnnotatedMutableTableImpl<Double> {

	public TableDouble() {
		super();
	}

	public TableDouble(final List<Row<Double>> rows) {
		super(rows);
	}

	public TableDouble(final Table<Double> template) {
		super(template);
	}
}
