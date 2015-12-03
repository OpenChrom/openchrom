/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.kerner.utils.collections.applier.Applier;
import net.sf.kerner.utils.collections.applier.ApplierAbstract;
import net.sf.kerner.utils.collections.list.UtilList;

public class FilterApplierProto<E> extends ApplierAbstract implements FilterApplier<E> {

    private List<Filter<E>> filters = UtilList.newList();

    public FilterApplierProto() {
        this(DEFAULT_FILTER_TYPE);
    }

    public FilterApplierProto(final Filter<E>... filters) {
        this(DEFAULT_FILTER_TYPE, filters);
    }

    public FilterApplierProto(final TYPE type) {
        super(type);
    }

    public FilterApplierProto(final TYPE type, final Filter<E>... filters) {
        super(type);
        for (final Filter<E> f : filters) {
            this.filters.add(f);
        }
    }

    public synchronized FilterApplierProto<E> addFilter(final Filter<E> filter) {
        filters.add(filter);
        return this;
    }

    public synchronized void clear() {
        filters.clear();
    }

    public synchronized List<E> filter(final Collection<? extends E> elements) {
        final List<E> result = UtilList.newList();
        for (final E e : elements) {
            if (filter(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public synchronized boolean filter(final E e) {
        switch (type) {
            case AND:
                for (final Filter<E> f : filters) {
                    if (f.filter(e)) {
                        // take
                    } else {
                        return false;
                    }
                }
                return true;
            case OR:
                for (final Filter<E> f : filters) {
                    if (f.filter(e)) {
                        return true;
                    } else {

                    }
                }
                return false;
            default:
                throw new RuntimeException("unknown type " + type);
        }
    }

    public synchronized List<Filter<E>> getFilters() {
        return new ArrayList<Filter<E>>(filters);
    }

    public synchronized boolean isEmpty() {
        return this.filters.isEmpty();
    }

    public synchronized void setFilters(final List<Filter<E>> filters) {
        this.filters = new ArrayList<Filter<E>>(filters);
    }

    public synchronized void setFilterType(final Applier.TYPE filterType) {
        super.type = filterType;
    }

    @Override
    public String toString() {
        return getFilters().toString();
    }
}
