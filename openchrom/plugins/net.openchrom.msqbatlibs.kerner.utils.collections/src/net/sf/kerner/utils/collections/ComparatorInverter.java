/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.collections;

import java.util.Comparator;

import net.sf.kerner.utils.Util;

/**
 * Simple class to invert the comparison logic of another {@link Comparator}.
 * <p>
 * <b>Example:</b><br>
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
 * @version 2012-05-23
 * @see Comparator
 * @param <T>
 *            type of elements which are compared
 */
public class ComparatorInverter<T> implements Comparator<T> {

	protected final Comparator<T> c;

	public ComparatorInverter(final Comparator<T> c) {

		Util.checkForNull(c);
		this.c = c;
	}

	public int compare(final T o1, final T o2) {

		final int result = c.compare(o2, o1);
		return result;
	}
}
