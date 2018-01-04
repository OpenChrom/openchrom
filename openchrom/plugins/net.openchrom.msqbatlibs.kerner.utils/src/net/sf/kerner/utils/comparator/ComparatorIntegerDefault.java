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
package net.sf.kerner.utils.comparator;

import java.util.Comparator;

/**
 * Default comparator for {@link Integer}.
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
 * 
 * public int compare(final Integer o1, final Integer o2) {
 * 
 * 	return o1.compareTo(o2);
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
 * @version 2012-06-18
 */
public class ComparatorIntegerDefault implements Comparator<Integer> {

	public int compare(final Integer o1, final Integer o2) {

		return o1.compareTo(o2);
	}
}
