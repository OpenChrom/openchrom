/**********************************************************************
Copyright (c) 2011-2012 Alexander Kerner. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 ***********************************************************************/

package net.sf.jtables.table.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.jtables.table.Row;
import net.sf.kerner.utils.UtilString;
import net.sf.kerner.utils.collections.ObjectToIndexMapper;
import net.sf.kerner.utils.collections.ObjectToIndexMapperImpl;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.collections.list.UtilList;

/**
 * Default implementation for {@link Row}.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 *
 * <pre>
 * TODO example
 * </pre>
 *
 * </p>
 *
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-01-25
 * @param <T>
 *            type of table elements
 */
public class RowImpl<T> implements Row<T> {

    public final static String DEFAULT_DELIMITER = "\t";

    protected final List<T> implementation = new ArrayList<T>();

    protected volatile ObjectToIndexMapper<Object> mapper = new ObjectToIndexMapperImpl<Object>(new ArrayList<Object>());

    public RowImpl() {
    }

    public RowImpl(final List<T> elements) {
        implementation.addAll(elements);
    }

    public RowImpl(final Row<T> template) {
        implementation.addAll(template);
        mapper = new ObjectToIndexMapperImpl<Object>(template.getIdentifier());
    }

    public RowImpl(final T... elements) {
        implementation.addAll(Arrays.asList(elements));
    }

    public void add(final int index, final T element) {
        implementation.add(index, element);
    }

    // Implement //

    public boolean add(final T e) {
        return implementation.add(e);
    }

    public boolean addAll(final Collection<? extends T> c) {
        return implementation.addAll(c);
    }

    public boolean addAll(final int index, final Collection<? extends T> c) {
        return implementation.addAll(index, c);
    }

    public void clear() {
        implementation.clear();
    }

    public boolean contains(final Object o) {
        return implementation.contains(o);
    }

    // Delegates //

    public boolean containsAll(final Collection<?> c) {
        return implementation.containsAll(c);
    }

    @Override
    public boolean equals(final Object o) {
        return implementation.equals(o);
    }

    public T get(final int index) {
        if (implementation.size() <= index) {
            throw new IllegalArgumentException("No index " + index + " in row " + implementation);
        }
        return implementation.get(index);
    }

    public T get(final Object indentifier) {
        return get(mapper.get(indentifier));
    }

    public List<Object> getIdentifier() {
        return new ArrayList<Object>(mapper.keys());
    }

    public ObjectToIndexMapper<Object> getObjectToIndexMapper() {
        return new ObjectToIndexMapperImpl<Object>(mapper.keys());
    }

    public boolean hasColumn(final int index) {
        return index < implementation.size() && index >= 0;
    }

    public boolean hasColumn(final Object o) {
        if (mapper.containsKey(o)) {
            final int index = mapper.get(o);
            return hasColumn(index);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return implementation.hashCode();
    }

    public int indexOf(final Object o) {
        return implementation.indexOf(o);
    }

    public boolean isEmpty() {
        return implementation.isEmpty();
    }

    public Iterator<T> iterator() {
        return implementation.iterator();
    }

    public int lastIndexOf(final Object o) {
        return implementation.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return implementation.listIterator();
    }

    public ListIterator<T> listIterator(final int index) {
        return implementation.listIterator(index);
    }

    public T remove(final int index) {
        return implementation.remove(index);
    }

    public boolean remove(final Object o) {
        return implementation.remove(o);
    }

    public boolean removeAll(final Collection<?> c) {
        return implementation.removeAll(c);
    }

    public boolean retainAll(final Collection<?> c) {
        return implementation.retainAll(c);
    }

    public T set(final int index, final T element) {
        return implementation.set(index, element);
    }

    public T set(final Object indentifier, final T newElement) {
        return set(mapper.get(indentifier), newElement);
    }

    public void setIdentifier(final List<? extends Object> ids) {
        if (UtilString.allEmpty(UtilList.toStringList(ids))) {
            throw new IllegalArgumentException("Invalid identifier: " + ids);
        }
        this.mapper = new ObjectToIndexMapperImpl<Object>(ids);
    }

    public int size() {
        return implementation.size();
    }

    public List<T> subList(final int fromIndex, final int toIndex) {
        return implementation.subList(fromIndex, toIndex);
    }

    public Object[] toArray() {
        return implementation.toArray();
    }

    @SuppressWarnings("hiding")
    public <T> T[] toArray(final T[] a) {
        return implementation.toArray(a);
    }

    @Override
    public String toString() {
        return toString(DEFAULT_DELIMITER);
    }

    public String toString(final String delimiter) {
        return UtilCollection.toString(this, delimiter);
    }

}
