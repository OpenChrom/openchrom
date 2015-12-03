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
package net.sf.kerner.utils.collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.kerner.utils.Cloneable;
import net.sf.kerner.utils.Factory;
import net.sf.kerner.utils.UtilArray;
import net.sf.kerner.utils.collections.UtilCollection;
import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.collections.list.filter.FilterList;
import net.sf.kerner.utils.collections.list.filter.FilterNull;
import net.sf.kerner.utils.pair.PairSame;
import net.sf.kerner.utils.pair.PairSameImpl;
import net.sf.kerner.utils.transformer.TransformerToString;
import net.sf.kerner.utils.transformer.TransformerToStringDefault;

/**
 * Utility class for {@link List} related stuff.
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-05-03
 */
public class UtilList {

    public final static TransformerToString<?> TRANSFORMER_TO_STRING_DEFAULT = new TransformerToStringDefault();

    public static <L> List<PairSame<L>> allAgainstAll(final List<L> list) {
        final List<PairSame<L>> result = new ArrayList<PairSame<L>>();
        final ListIterator<L> it1 = list.listIterator();
        while (it1.hasNext()) {
            final int index = it1.nextIndex();
            final L next = it1.next();
            final ListIterator<L> it2 = list.listIterator(index + 1);
            while (it2.hasNext()) {
                final L next2 = it2.next();
                result.add(new PairSameImpl<L>(next, next2));
            }
        }
        return result;
    }

    public static <L> List<L> append(final Collection<? extends L> c1,
            final Collection<? extends L> c2) {
        return (List<L>) UtilCollection.append(c1, c2, new ArrayListFactory<L>());
    }

    public static <T> void append(final List<T> list, final int numElements, final T element) {
        for (int i = 0; i < numElements; i++) {
            list.add(element);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> asList(final Collection<? extends T> collection) {
        List<T> result;
        if (collection instanceof List) {
            result = (List<T>) collection;
        } else {
            result = newList(collection);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <L> List<L> cast(final Collection<? extends Object> list) {
        if (list instanceof List) {
            return (List<L>) list;
        } else {
            return (List<L>) newList(list);
        }
    }

    public static <L> List<L> castDeep(final Collection<? extends Object> list, final Class<L> clazz) {
        final List<L> result = UtilList.newList();
        for (final Object o : list) {
            result.add(clazz.cast(o));
        }
        return result;
    }

    public static <T extends Cloneable<T>> List<T> clone(final List<T> list) {
        final List<T> result = new ArrayList<T>();
        for (final T t : list) {
            result.add(t.clone());
        }
        return result;
    }

    public static <L> List<? extends L> cutToSize(final List<? extends L> list, final int size) {
        if (list.size() <= size) {
            return list;
        }
        return list.subList(0, size);
    }

    public static <E> void fill(final List<E> list, final int numElements,
            final Factory<? extends E> factory) {
        if (numElements < list.size())
            return;
        final int iterations = numElements - list.size();
        for (int i = 0; i < iterations; i++) {
            list.add(factory.create());
        }
    }

    public static <E> void fillElement(final List<E> list, final int numElements, final E e) {
        if (numElements < list.size())
            return;
        final int iterations = numElements - list.size();
        for (int i = 0; i < iterations; i++) {
            list.add(e);
        }
    }

    public static <E> void fillNull(final List<E> list, final int numElements) {
        if (numElements < list.size())
            return;
        final int iterations = numElements - list.size();
        for (int i = 0; i < iterations; i++) {
            list.add(null);
        }
    }

    public static <C> List<C> filterList(final List<? extends C> collection, final Filter<C> filter) {
        return filterList(collection, filter, new ArrayListFactory<C>());
    }

    public static <C> List<C> filterList(final List<? extends C> collection,
            final Filter<C> filter, final FactoryList<C> factory) {
        final List<C> result = factory.createCollection();
        for (int i = 0; i < collection.size(); i++) {
            final C c = collection.get(i);
            if (filter instanceof FilterList) {
                if (((FilterList<C>) filter).filter(c, i)) {
                    result.add(c);
                }
            } else {
                if (filter.filter(c)) {
                    result.add(c);
                }
            }
        }
        return result;
    }

    public static <T> List<T> filterNull(final List<T> list) {
        return filterList(list, new FilterNull<T>());
    }

    public static <T> T getFirstElement(final List<T> list) {
        if (list.size() < 1) {
            throw new IllegalArgumentException("list is empty");
        }
        return list.get(0);
    }

    public static int getFirstNonEmptyIndex(final List<Collection<?>> list) {
        for (int i = 0; i < list.size(); i++) {
            final Collection<?> o = list.get(i);
            if (o != null && !o.isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public static int getFirstNonNullIndex(final List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            final Object o = list.get(i);
            if (o != null) {
                return i;
            }
        }
        return -1;
    }

    public static <T> T getLastElement(final List<T> list) {
        if (list.size() < 1) {
            throw new IllegalArgumentException("list is empty");
        }
        return list.get(list.size() - 1);
    }

    public static int getLastNonNullIndex(final List<?> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            final Object o = list.get(i);
            if (o != null) {
                return i;
            }
        }
        return -1;
    }

    public static <C> List<C> meld(final List<? extends C> c1, final List<? extends C> c2) {
        if (c1 == null && c2 == null)
            return new ArrayListFactory<C>().createCollection();
        if (c1 == null)
            return new ArrayListFactory<C>().createCollection(c2);
        if (c2 == null)
            return new ArrayListFactory<C>().createCollection(c1);
        return meld(c1, c2, new ArrayListFactory<C>());
    }

    /**
     * Combine two {@link java.util.List Lists} into one. </p> Elements, that
     * have same position in both lists and are equal to each other are filtered
     * out. </p> {@code null equals null}. {@code null} is kept in list only, if
     * at this position in both list elemnt is {@code null} or other list does
     * not have this position. </p> Resulting list size is always equal to size
     * of longer list.
     *
     * @param <C>
     * @param c1
     *            first list
     * @param c2
     *            second list
     * @param factory
     *            {@link net.sf.kerner.utils.collections.list.FactoryList
     *            ListFactory} that provides instance of returning list
     * @return a new {@link java.util.List List}
     */
    public static <C> List<C> meld(final List<? extends C> c1, final List<? extends C> c2,
            final FactoryList<C> factory) {
        final List<C> result = factory.createCollection();
        final Iterator<? extends C> i1 = c1.iterator();
        final Iterator<? extends C> i2 = c2.iterator();

        while (i1.hasNext()) {
            final C e1 = i1.next();
            if (i2.hasNext()) {
                final C e2 = i2.next();

                if (e1 == null && e2 == null)
                    result.add(null);

                else if (e1 == null)
                    result.add(e2);

                else if (e2 == null)
                    result.add(e1);

                else if (e1.equals(e2))
                    result.add(e1);

                else {
                    result.add(e1);
                    result.add(e2);
                }

            } else
                result.add(e1);
        }

        while (i2.hasNext()) {
            final C e2 = i2.next();
            result.add(e2);
        }
        return result;
    }

    public static <T> List<T> newList() {
        return new ArrayList<T>();
    }

    public static <T> List<T> newList(final Collection<? extends T> elements) {
        return new ArrayList<T>(elements);
    }

    public static <T> List<T> newList(final Collection<? extends T>... elements) {
        final List<T> result = newList();
        for (final Collection<? extends T> c : elements) {
            result.addAll(c);
        }
        return result;
    }

    public static <T> List<T> newList(final T... elements) {
        return Arrays.asList(elements);
    }

    public static <T> void prepend(final List<T> list, final int numElements, final T element) {
        for (int i = 0; i < list.size(); i++) {
            list.add(0, element);
        }
    }

    public static void removeAll(final List<?> list, final Collection<Integer> indices) {
        final ListIterator<?> it = list.listIterator();
        while (it.hasNext()) {
            final int index = it.nextIndex();
            it.next();
            if (indices.contains(index)) {
                it.remove();
            }
        }
    }

    public static <T> List<T> removeAll(final List<T> list, final List<T> values) {
        final List<T> copy = new ArrayList<T>(list);
        copy.removeAll(values);
        return copy;
    }

    public static <T> List<T> removeAll2(final List<T> list, final Collection<Integer> indices) {
        return removeAll2(list, indices, new ArrayListFactory<T>());
    }

    public static <T> List<T> removeAll2(final List<T> list, final Collection<Integer> indices,
            final FactoryList<T> factory) {
        final List<T> copy = factory.createCollection(list);
        removeAll(copy, indices);
        return copy;
    }

    public static void retainAll(final List<?> list, final Collection<Integer> indices) {
        final ListIterator<?> it = list.listIterator();
        while (it.hasNext()) {
            final int index = it.nextIndex();
            it.next();
            if (indices.contains(index)) {
                // ok
            } else {
                it.remove();
            }
        }
    }

    public static <T> List<T> retainAll2(final List<T> list, final Collection<Integer> indices) {
        return retainAll2(list, indices, new ArrayListFactory<T>());
    }

    public static <T> List<T> retainAll2(final List<T> list, final Collection<Integer> indices,
            final FactoryList<T> factory) {
        final List<T> copy = factory.createCollection(list);
        retainAll(copy, indices);
        return copy;
    }

    public static <T> void setAll(final List<T> parent, final List<T> sublist, final int index) {
        if (parent.size() < sublist.size())
            throw new IllegalArgumentException("parent too small for child");
        if (parent.size() < sublist.size() + index)
            throw new IllegalArgumentException("parent too small for index");
        if (index < 0)
            throw new IllegalArgumentException("invalid index " + index);
        for (int i = 0; i < sublist.size(); i++) {
            parent.set(i + index, sublist.get(i));
        }
    }

    public static <T> List<List<T>> split(final List<T> list, final int index) {
        return split(list, index, new ArrayListFactory<T>());
    }

    public static <T> List<List<T>> split(final List<T> list, final int index,
            final FactoryList<T> factory) {
        final List<List<T>> result = newList();
        result.add(factory.createCollection(list.subList(0, index)));
        result.add(factory.createCollection(list.subList(index, list.size())));
        return result;
    }

    public static <T> List<List<T>> split(final List<T> list, final int[] indices) {
        return split(list, indices, new ArrayListFactory<T>());
    }

    public static <T> List<List<T>> split(final List<T> list, final int[] indices,
            final FactoryList<T> factory) {
        final List<List<T>> result = newList();
        if (UtilArray.emptyArray(indices)) {
            result.add(list);
            return result;
        }
        for (final int index : indices) {
            result.addAll(split(list, index));
        }
        return result;
    }

    public static <T> List<List<T>> splitMaxElements(final List<? extends T> list,
            final int maxElements) {
        return splitMaxElements(list, maxElements, new ArrayListFactory<T>());
    }

    public static <T> List<List<T>> splitMaxElements(final List<? extends T> list,
            final int maxElements, final FactoryList<T> factory) {
        final List<List<T>> result = newList();
        List<T> newList = factory.createCollection();
        for (int i = 0; i < list.size(); i++) {
            if (i % maxElements == 0 && newList.size() > 0) {
                result.add(factory.createCollection(newList));
                newList = factory.createCollection();
            }
            newList.add(list.get(i));
        }
        if (newList().size() > 0) {
            result.add(newList);
        }
        return result;
    }

    public static List<Object> toObjectList(final Collection<? extends Object> elements) {
        return newList(elements);
    }

    public static <E> List<String> toStringList(final Collection<E> elements) {
        final List<String> result = new TransformerToStringCollection<E>()
                .transformCollection(new ArrayList<E>(elements));
        return result;
    }

    public static <E> List<String> toStringList(final TransformerToString<E> strategy,
            final Collection<E> elements) {
        return new TransformerToStringCollection<E>(strategy).transformCollection(elements);
    }

    public static <V> List<V> trimm(final List<? extends V> list, final FactoryList<V> factory) {
        final List<V> result = factory.createCollection();
        for (final V o : list) {
            if (o != null)
                result.add(o);
        }
        return result;
    }

    private UtilList() {
        // Singleton
    }
}
