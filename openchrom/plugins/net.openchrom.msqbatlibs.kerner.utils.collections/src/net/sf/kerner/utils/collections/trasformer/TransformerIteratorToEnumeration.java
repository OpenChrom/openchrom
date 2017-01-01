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
package net.sf.kerner.utils.collections.trasformer;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import net.sf.kerner.utils.transformer.Transformer;

/**
 * Transforms an {@link Iterator} to an {@link Enumeration}.
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
 * @version 2011-07-06
 * @param <T>
 *            type of elements
 */
public class TransformerIteratorToEnumeration<T> implements Transformer<Iterator<T>, Enumeration<T>> {

	/**
	 *
	 */
	public Enumeration<T> transform(Iterator<T> iterator) {

		final Vector<T> v = new Vector<T>();
		while(iterator.hasNext())
			v.add(iterator.next());
		return v.elements();
	}
}
