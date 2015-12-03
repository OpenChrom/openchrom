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
