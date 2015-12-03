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
package net.sf.jranges.range.integerrange;

import net.sf.kerner.utils.Factory;

/**
 * 
 * A factory that creates objects of type {@link RangeInteger}.
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
public interface IntegerRangeFactory<T extends RangeInteger> extends Factory<T> {

	/**
	 * 
	 * Create a {@link RangeInteger} with given start and stop positions.
	 * 
	 * @param start
	 *            start position of created {@code IntegerRange}
	 * @param stop
	 *            stop position of created {@code IntegerRange}
	 * @return newly created {@code IntegerRange}
	 */
	T create(int start, int stop);

	/**
	 * 
	 * Create a {@link RangeInteger} with from given template.
	 * 
	 * @param template
	 *            template that is used to create new {@code IntegerRange}
	 * @return newly created {@code IntegerRange}
	 */
	T create(RangeInteger template);

}
