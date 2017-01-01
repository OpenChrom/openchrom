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
package net.sf.kerner.utils.collections.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class UtilSet {

	public static <T> T getFirstElement(final Set<T> set) {

		return set.iterator().next();
	}

	public static <T> T getLastElement(final Set<T> set) {

		return new ArrayList<T>(set).get(set.size() - 1);
	}

	public static <T> Set<T> newSet() {

		return new LinkedHashSet<T>();
	}

	public static <T> Set<T> newSet(final Collection<? extends T> col) {

		return new LinkedHashSet<T>(col);
	}
}
