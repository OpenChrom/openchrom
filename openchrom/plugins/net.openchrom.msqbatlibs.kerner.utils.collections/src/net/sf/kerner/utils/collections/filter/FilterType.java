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
package net.sf.kerner.utils.collections.filter;

public class FilterType implements Filter<Object> {

	private final Class<?> clazz;

	public FilterType(final Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return {@code true}, if {@code e} is instance of {@code clazz}; {@code false} otherwise
	 */
	public boolean filter(final Object e) {

		final boolean result = clazz.isInstance(e);
		return result;
	}
}
