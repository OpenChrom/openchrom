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

import net.sf.kerner.utils.collections.filter.FilterVisitorApplierProto;

public class CollectionWalkerImpl<E> extends FilterVisitorApplierProto<E> implements CollectionWalker<E> {

	public void afterWalk() {

		// do nothing by default
	}

	public void beforeWalk() {

		// do nothing by default
	}

	public void walk(final Collection<? extends E> list) {

		beforeWalk();
		for(final E e : list) {
			if(filter(e)) {
				transform(e);
			}
		}
		afterWalk();
	}
}
