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
package net.sf.jranges.range.integerrange.impl;

import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.jranges.range.integerrange.IntegerRangeFactory;

/**
 * 
 * A factory that creates objects of type {@link RangeIntegerDummy}.
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
 * @version 2011-09-02
 * 
 */
public class FactoryRangeIntegerDummy implements IntegerRangeFactory<RangeInteger> {

	public RangeIntegerDummy create() {

		return new RangeIntegerDummy();
	}

	public RangeIntegerDummy create(int start, int stop) {

		return new RangeIntegerDummy(start, stop);
	}

	public RangeIntegerDummy create(RangeInteger template) {

		return new RangeIntegerDummy(template.getStart(), template.getStop());
	}
}
