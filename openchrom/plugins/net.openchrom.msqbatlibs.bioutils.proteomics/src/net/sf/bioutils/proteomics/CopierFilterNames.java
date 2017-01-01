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
package net.sf.bioutils.proteomics;

import java.util.List;

import net.sf.bioutils.proteomics.sample.Sample;
import net.sf.kerner.utils.collections.filter.Filter;

public class CopierFilterNames {

	public void copy(final Sample sample, final List<? extends Filter<?>> filters) {

		for(int i = 0; i < filters.size(); i++) {
			final String key = "filter-" + i;
			final String value = filters.get(i).toString();
			sample.getProperties().put(key, value);
		}
	}
}
