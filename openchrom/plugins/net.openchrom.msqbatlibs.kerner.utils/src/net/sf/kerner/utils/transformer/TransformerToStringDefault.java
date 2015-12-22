/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.transformer;

/**
 * Default implementation for {@link TransformerToString}. It uses {@link Object#toString()} to obtain string representation for each object.
 * </p>
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
 * @version 2013-08-01
 */
public class TransformerToStringDefault implements TransformerToString<Object> {

	/**
	 * @return {@code element.toString()} or "null" if object is {@code null}
	 */
	public String transform(final Object element) {

		if(element == null) {
			return "null";
		}
		return element.toString();
	}
}
