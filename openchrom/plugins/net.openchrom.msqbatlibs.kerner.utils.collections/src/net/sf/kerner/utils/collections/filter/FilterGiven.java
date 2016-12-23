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
package net.sf.kerner.utils.collections.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FilterGiven implements Filter<Object> {

	private final Set<Object> set;

	public FilterGiven(final Collection<Object> objects) {
		set = new HashSet<Object>(objects);
	}

	public boolean filter(final Object e) {

		return set.contains(e);
	}
}
