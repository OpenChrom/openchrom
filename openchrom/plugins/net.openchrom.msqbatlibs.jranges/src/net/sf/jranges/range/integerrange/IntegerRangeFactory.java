/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
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
