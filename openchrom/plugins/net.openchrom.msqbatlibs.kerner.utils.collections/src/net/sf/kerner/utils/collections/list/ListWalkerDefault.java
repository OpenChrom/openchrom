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
package net.sf.kerner.utils.collections.list;

import java.util.List;
import java.util.ListIterator;

import net.sf.kerner.utils.collections.list.filter.FilterVisitorListApplierProto;

public class ListWalkerDefault<E> extends FilterVisitorListApplierProto<E> implements ListWalker<E> {

	public void afterWalk() {

		// do nothing by default
	}

	public void beforeWalk() {

		// do nothing by default
	}

	public void walk(final List<? extends E> list) {

		synchronized(list) {
			beforeWalk();
			for(final ListIterator<? extends E> it = list.listIterator(); it.hasNext();) {
				final int index = it.nextIndex();
				final E e = it.next();
				visit(e, index);
			}
			afterWalk();
		}
	}
}
