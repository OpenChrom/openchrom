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
package net.sf.jranges.range.doublerange.impl;

import net.sf.jranges.range.RangeException;

public class ZeroPositiveDoubleRange extends AbstractDoubleRange {

	public ZeroPositiveDoubleRange(double start, double stop) throws RangeException {
		super(start, stop, 0, Double.MAX_VALUE);
	}

	public ZeroPositiveDoubleRange(double start, double stop, double interval) throws RangeException {
		super(start, stop, 0, Double.MAX_VALUE, interval);
	}

	public ZeroPositiveDoubleRange(String start, String stop, String interval) throws RangeException, NumberFormatException {
		this(Double.parseDouble(start.trim()), Double.parseDouble(stop.trim()), Double.parseDouble(interval.trim()));
	}

	protected ZeroPositiveDoubleRange newInstange(double start, double stop, double limit1, double limit2) throws RangeException {

		return new ZeroPositiveDoubleRange(start, stop, interval);
	}
}
