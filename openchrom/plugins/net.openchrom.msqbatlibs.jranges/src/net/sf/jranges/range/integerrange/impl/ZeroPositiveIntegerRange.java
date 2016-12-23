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

import net.sf.jranges.range.RangeException;

/**
 * 
 * 
 * {@code ZeroPositiveRange} is an implementation for {@link net.sf.jranges.range.integerrange.RangeInteger IntegerRange}, for which the
 * following is true:<br>
 * {@code 0 <= start <= stop <= Integer.MAX_VALUE}
 * 
 * <p>
 * <b>Example:</b>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2010-10-19
 * 
 */
public class ZeroPositiveIntegerRange extends RangeIntegerAbstract {

	public ZeroPositiveIntegerRange(int start, int stop) throws RangeException {
		super(start, stop, 0, Integer.MAX_VALUE);
	}

	public ZeroPositiveIntegerRange(int start, int stop, int interval) throws RangeException {
		super(start, stop, 0, Integer.MAX_VALUE, interval);
	}

	protected ZeroPositiveIntegerRange newInstange(int start, int stop, int limit1, int limit2) throws RangeException {

		return new ZeroPositiveIntegerRange(start, stop, interval);
	}
}
