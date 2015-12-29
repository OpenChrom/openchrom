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
package net.sf.kerner.utils.collections;

import java.util.Collection;

import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.equal.Equalator;

public class FilterEqual<T> implements Filter<T> {

	private final Collection<? extends T> elements;
	private final Equalator<T> equalator;

	public FilterEqual(final Collection<? extends T> elements, final Equalator<T> equalator) {
		super();
		this.elements = elements;
		this.equalator = equalator;
	}

	public boolean filter(final T e) {

		for(final T o : elements) {
			if(o.hashCode() == e.hashCode()) {
				continue;
			}
			if(equalator.areEqual(e, o)) {
				return true;
			}
		}
		return false;
	}
}
