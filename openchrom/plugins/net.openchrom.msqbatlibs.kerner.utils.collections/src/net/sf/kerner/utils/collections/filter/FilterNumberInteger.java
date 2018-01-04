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
package net.sf.kerner.utils.collections.filter;

import net.sf.jranges.range.integerrange.RangeInteger;
import net.sf.jranges.range.integerrange.impl.RangeIntegerDummy;
import net.sf.kerner.utils.transformer.Transformer;

public abstract class FilterNumberInteger<T> implements Filter<T> {

	public static enum TYPE {
		EXACT, MIN, MAX
	}

	private final RangeInteger range;

	public FilterNumberInteger(final int value, final TYPE type) {
		switch(type) {
			case EXACT:
				range = new RangeIntegerDummy(value, value);
				break;
			case MIN:
				range = new RangeIntegerDummy(value, Integer.MAX_VALUE);
				break;
			case MAX:
				range = new RangeIntegerDummy(Integer.MIN_VALUE, value);
				break;
			default:
				throw new IllegalArgumentException("unknown type " + type);
		}
	}

	public FilterNumberInteger(final RangeInteger range) {
		this.range = range;
	}

	public final boolean filter(final T e) {

		final Integer t = getTransformer().transform(e);
		return range.includes(t);
	}

	protected abstract Transformer<T, Integer> getTransformer();
}
