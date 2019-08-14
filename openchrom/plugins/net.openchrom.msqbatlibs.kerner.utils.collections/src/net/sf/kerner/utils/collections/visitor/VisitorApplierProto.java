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
package net.sf.kerner.utils.collections.visitor;

import java.util.Collection;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.visitor.Visitor;
import net.sf.kerner.utils.visitor.VisitorApplier;

public class VisitorApplierProto<E> implements VisitorApplier<E> {

	protected final Collection<Visitor<E>> visitors = UtilCollection.newCollection();

	public synchronized void addVisitor(final Visitor<E> visitor) {

		visitors.add(visitor);
	}

	public synchronized void clearVisitors() {

		visitors.clear();
	}

	public synchronized Void transform(final E e) {

		for(final Visitor<E> v : visitors) {
			v.transform(e);
		}
		return null;
	}
}
