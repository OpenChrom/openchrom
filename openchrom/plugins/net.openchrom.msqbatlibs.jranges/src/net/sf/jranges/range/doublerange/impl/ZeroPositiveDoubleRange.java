/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.jranges.range.doublerange.impl;

import net.sf.jranges.range.RangeException;

public class ZeroPositiveDoubleRange extends AbstractDoubleRange {

	public ZeroPositiveDoubleRange(double start, double stop) throws RangeException {
		super(start, stop, 0, Double.MAX_VALUE);
	}

	public ZeroPositiveDoubleRange(double start, double stop, double interval) throws RangeException {
		super(start, stop, 0, Double.MAX_VALUE, interval);
	}

	public ZeroPositiveDoubleRange(String start, String stop, String interval) throws RangeException,
			NumberFormatException {
		this(Double.parseDouble(start.trim()), Double.parseDouble(stop.trim()), Double.parseDouble(interval.trim()));
	}

	@Override
	protected ZeroPositiveDoubleRange newInstange(double start, double stop, double limit1, double limit2)
			throws RangeException {
		return new ZeroPositiveDoubleRange(start, stop, interval);
	}

}
