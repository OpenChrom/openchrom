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
package net.sf.kerner.utils.collections.list.visitor;

import java.util.Collection;

import net.sf.kerner.utils.collections.UtilCollection;

public class VisitorListApplierDefaultProto<E> implements VisitorApplierListDefault<E> {

	protected final Collection<VisitorList<Void, E>> visitors = UtilCollection.newCollection();

	public synchronized void addVisitor(final VisitorList<Void, E> visitor) {

		visitors.add(visitor);
	}

	public synchronized void clearVisitors() {

		visitors.clear();
	}

	public void visit(final E e, final int index) {

		for(final VisitorList<Void, E> v : visitors) {
			v.visit(e, index);
		}
	};
}
