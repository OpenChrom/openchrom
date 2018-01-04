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
package net.sf.bioutils.proteomics.sample;

import java.util.Collection;

import net.sf.kerner.utils.collections.Selector;

public class SelectorSampleByBaseName<T extends Sample> implements Selector<T> {

	private final String name;

	public SelectorSampleByBaseName(final String name) {
		this.name = name;
	}

	public T select(final Collection<? extends T> elements) {

		for(final T s : elements) {
			if(s.getNameBase().equals(name)) {
				return s;
			}
		}
		return null;
	}
}
