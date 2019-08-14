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
package net.sf.jtables.io.transformer;

import net.sf.jtables.table.Row;
import net.sf.jtables.table.impl.RowImpl;
import net.sf.kerner.utils.collections.list.AbstractTransformingListFactory;

public class TransformerRowStringToRowDouble extends AbstractTransformingListFactory<Row<String>, Row<Double>> {

	public Row<Double> transform(final Row<String> element) {

		final Row<Double> result = new RowImpl<Double>();
		for(final String s : element) {
			result.add(Double.parseDouble(s));
		}
		return result;
	}
}
