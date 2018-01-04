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

import net.sf.kerner.utils.collections.filter.Filter;

public class FilterSampleByName implements Filter<Sample> {

	private String name;

	public FilterSampleByName() {
		name = null;
	}

	public FilterSampleByName(final String name) {
		this.name = name;
	}

	public boolean filter(final Sample e) {

		return e.getName().equals(getName());
	}

	public synchronized String getName() {

		return name;
	}

	public synchronized void setName(final String name) {

		this.name = name;
	}
}
