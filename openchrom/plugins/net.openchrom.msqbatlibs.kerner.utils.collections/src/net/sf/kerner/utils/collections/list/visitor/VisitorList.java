/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.list.visitor;

/**
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
 * @version 2012-06-22
 * @param <R>
 *            type of result
 * @param <E>
 *            type of input
 */
public interface VisitorList<R, E> {

	/**
	 * Visit object {@code element}, perform any action and return result.
	 * 
	 * @param element
	 *            element to visit
	 * @param index
	 *            of element in list
	 * @return result of visit
	 */
	R visit(E element, int index);
}
