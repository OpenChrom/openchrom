/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.comparator;

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
