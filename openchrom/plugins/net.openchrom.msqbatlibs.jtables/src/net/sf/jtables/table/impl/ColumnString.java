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
package net.sf.jtables.table.impl;

import java.util.List;

import net.sf.kerner.utils.UtilString;

public class ColumnString extends ColumnImpl<String> {

	public ColumnString() {

		super();
	}

	public ColumnString(final List<String> elements) {

		super(elements);
	}

	public ColumnString(final String... elements) {

		super(elements);
	}

	
	public boolean isEmpty() {

		return UtilString.allEmpty(implementation);
	}
}
