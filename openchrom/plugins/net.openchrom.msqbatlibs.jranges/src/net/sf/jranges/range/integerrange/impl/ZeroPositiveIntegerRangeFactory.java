/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.jranges.range.integerrange.impl;

import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.jranges.range.integerrange.IntegerRangeFactory;

public class ZeroPositiveIntegerRangeFactory implements IntegerRangeFactory<ZeroPositiveIntegerRange> {

	public ZeroPositiveIntegerRange create() {

		return new ZeroPositiveIntegerRange(0, 0);
	}

	public ZeroPositiveIntegerRange create(int start, int stop) {

		return new ZeroPositiveIntegerRange(start, stop);
	}

	public ZeroPositiveIntegerRange create(RangeInteger template) {

		return create(template.getStart(), template.getStop());
	}
}
