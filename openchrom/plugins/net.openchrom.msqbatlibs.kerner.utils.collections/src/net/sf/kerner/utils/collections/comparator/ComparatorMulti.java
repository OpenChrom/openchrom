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
package net.sf.kerner.utils.collections.comparator;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ComparatorMulti<T> implements Comparator<T> {

	private final List<Comparator<T>> cs;

	public ComparatorMulti(final Comparator<T>... cs) {
		this.cs = Arrays.asList(cs);
	}

	public ComparatorMulti(final List<Comparator<T>> cs) {
		this.cs = cs;
	}

	public int compare(final T t1, final T t2) {

		for(final Comparator<T> c : cs) {
			final int result = c.compare(t1, t2);
			if(result != 0) {
				return result;
			}
		}
		return 0;
	}
}
