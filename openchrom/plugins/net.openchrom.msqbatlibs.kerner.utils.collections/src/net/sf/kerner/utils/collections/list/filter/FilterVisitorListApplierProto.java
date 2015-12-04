/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.kerner.utils.collections.list.filter;

import java.util.List;

import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.collections.filter.FilterApplier;
import net.sf.kerner.utils.collections.filter.FilterApplierProto;
import net.sf.kerner.utils.collections.list.visitor.VisitorApplierListDefault;
import net.sf.kerner.utils.collections.list.visitor.VisitorList;
import net.sf.kerner.utils.collections.list.visitor.VisitorListApplierDefaultProto;

public class FilterVisitorListApplierProto<E> implements FilterApplier<E>, VisitorApplierListDefault<E> {

	private final FilterApplier<E> filterDelegate = new FilterApplierProto<E>();
	private final VisitorApplierListDefault<E> visitorDelegate = new VisitorListApplierDefaultProto<E>();

	public FilterVisitorListApplierProto<E> addFilter(final Filter<E> filter) {

		filterDelegate.addFilter(filter);
		return this;
	}

	public void addVisitor(final VisitorList<Void, E> visitor) {

		visitorDelegate.addVisitor(visitor);
	}

	public void clear() {

		filterDelegate.clear();
	}

	public void clearVisitors() {

		visitorDelegate.clearVisitors();
	}

	public boolean filter(final E e) {

		return filterDelegate.filter(e);
	}

	public List<Filter<E>> getFilters() {

		return filterDelegate.getFilters();
	}

	public boolean isEmpty() {

		return filterDelegate.isEmpty();
	}

	public void visit(final E e, final int index) {

		if(filter(e)) {
			visitorDelegate.visit(e, index);
		}
	}
}
