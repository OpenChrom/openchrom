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

/**
 * Prototype implementation for a {@link Comparator} which adds support for {@code null} values: </br>
 * 
 * <pre>
 * 
 * 
 * 
 * 
 * 
 * public int compare(T o1, T o2) {
 * 
 * 	if(o1 == null &amp;&amp; o2 == null)
 * 		return 0;
 * 	if(o1 == null)
 * 		return -1;
 * 	if(o2 == null)
 * 		return 1;
 * 	return compareNonNull(o1, o2);
 * }
 * 
 * </pre>
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
 * @version 2011-10-26
 * @param <T>
 *            type of objects which are compared
 */
public class ComparatorNull<T> implements Comparator<T> {

	public final int compare(final T o1, final T o2) {

		if(o1 == null && o2 == null)
			return 0;
		if(o1 == null)
			return -1;
		if(o2 == null)
			return 1;
		return compareNonNull(o1, o2);
	}

	/**
	 * Delegate method to compare objects in case both are not {@code null}. By default, this method returns {@code o1#compareTo(o2)}. Override if custom behavior is desired or objects do not implement {@link Comparable}.
	 * 
	 * @param o1
	 *            first object
	 * @param o2
	 *            second object
	 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater
	 *         than the second
	 * @throws ClassCastException
	 *             if one or both objects do not implement comparable
	 */
	@SuppressWarnings("unchecked")
	public int compareNonNull(T o1, T o2) throws ClassCastException {

		return ((Comparable<T>)o1).compareTo(o2);
	}
}
