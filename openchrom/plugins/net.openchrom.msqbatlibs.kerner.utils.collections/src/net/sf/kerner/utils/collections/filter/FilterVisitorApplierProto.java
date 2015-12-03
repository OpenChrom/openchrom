/*******************************************************************************
 * Copyright (c) 2010-2015 Alexander Kerner. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.collections.filter;

import java.util.List;

import net.sf.kerner.utils.collections.applier.Applier;
import net.sf.kerner.utils.collections.visitor.VisitorApplierProto;
import net.sf.kerner.utils.visitor.Visitor;
import net.sf.kerner.utils.visitor.VisitorApplier;

public class FilterVisitorApplierProto<E> implements FilterApplier<E>,
VisitorApplier<E> {

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
		if (b) {
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
		if (filter(e)) {
			visitorDelegate.transform(e);
		}
		return null;
	}
}
