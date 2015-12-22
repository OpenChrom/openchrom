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
package net.sf.kerner.utils.collections.filter;

import java.util.List;

import net.sf.kerner.utils.collections.applier.Applier;
import net.sf.kerner.utils.collections.visitor.VisitorApplierProto;
import net.sf.kerner.utils.visitor.Visitor;
import net.sf.kerner.utils.visitor.VisitorApplier;

public class FilterVisitorApplierProto<E> implements FilterApplier<E>, VisitorApplier<E> {

	private final FilterApplierProto<E> filterDelegate = new FilterApplierProto<E>();
	private final VisitorApplierProto<E> visitorDelegate = new VisitorApplierProto<E>();

	public FilterVisitorApplierProto<E> addFilter(final Filter<E> filter) {

		filterDelegate.addFilter(filter);
		return this;
	}

	public void addVisitor(final Visitor<E> visitor) {

		visitorDelegate.addVisitor(visitor);
	}

	public void clear() {

		filterDelegate.clear();
	}

	public void clearVisitors() {

		visitorDelegate.clearVisitors();
	}

	public boolean filter(final E e) {

		final boolean b = filterDelegate.filter(e);
		if(b) {
			visitorDelegate.transform(e);
		}
		return b;
	}

	public List<Filter<E>> getFilters() {

		return filterDelegate.getFilters();
	}

	public boolean isEmpty() {

		return filterDelegate.isEmpty();
	}

	public void setFilterType(final Applier.TYPE filterType) {

		this.filterDelegate.setFilterType(filterType);
	}

	public Void transform(final E e) {

		if(filter(e)) {
			visitorDelegate.transform(e);
		}
		return null;
	}
}
