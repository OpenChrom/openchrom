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
package net.sf.kerner.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.kerner.utils.Factory;
import net.sf.kerner.utils.Util;
import net.sf.kerner.utils.UtilString;
import net.sf.kerner.utils.collections.equalator.EqualatorDefault;
import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.collections.filter.FilterType;
import net.sf.kerner.utils.collections.list.ArrayListFactory;
import net.sf.kerner.utils.collections.list.UtilList;
import net.sf.kerner.utils.collections.list.visitor.VisitorList;
import net.sf.kerner.utils.collections.map.MapCollection;
import net.sf.kerner.utils.collections.trasformer.ToString;
import net.sf.kerner.utils.collections.trasformer.TransformerIteratorToCollection;
import net.sf.kerner.utils.equal.Equalator;
import net.sf.kerner.utils.math.UtilMath;
import net.sf.kerner.utils.pair.PairSame;
import net.sf.kerner.utils.pair.PairSameImpl;
import net.sf.kerner.utils.transformer.Transformer;
import net.sf.kerner.utils.transformer.TransformerToString;
import net.sf.kerner.utils.transformer.TransformerToStringDefault;

/**
 * Utility class for {@link Collection} related stuff.
 *
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-08-15
 */
public class UtilCollection {

	public static String DEFAULT_OBJECT_SEPARATOR = ", ";

	public final static Transformer<?, String> TRANSFORMER_TO_STRING_DEFAULT = new TransformerToStringDefault();

	/**
	 * Create a new {@link Collection}, that contains all elements contained in
	 * first given collection and in second given collection. Ordering is as
	 * given by first {@code Collection Collection's} {@link Iterator} followed
	 * by second {@code Collection Collection's} {@link Iterator}.
	 *
	 * @param <C>
	 *            Type of both {@link Collection Collections}
	 * @param c1
	 *            first {@link Collection}
	 * @param c2
	 *            second {@link Collection}
	 * @return new {@code Collection}
	 * @throws NullPointerException
	 *             if one of arguments is {@code null}
	 */
	public static <C> Collection<C> append(final Collection<? extends C> c1,
			final Collection<? extends C> c2) {
		return append(c1, c2, new ArrayListFactory<C>());
	}

	/**
	 * Create a new {@link Collection}, that contains all elements contained in
	 * first given collection and in second given collection. Ordering is as
	 * given by first {@code Collection Collection's} {@link Iterator} followed
	 * by second {@code Collection Collection's} {@link Iterator}.
	 *
	 * @param <C>
	 *            Type of both {@link Collection Collections}
	 * @param c1
	 *            first {@link Collection}
	 * @param c2
	 *            second {@link Collection}
	 * @param factory
	 *            {@link Factory} to create new {@code Collection}
	 * @return new {@code Collection}
	 * @throws NullPointerException
	 *             if one of arguments is {@code null}
	 */
	public static <C> Collection<C> append(final Collection<? extends C> c1,
			final Collection<? extends C> c2, final FactoryCollection<C> factory) {
		Util.checkForNull(c1, c2, factory);
		final Collection<C> result = factory.createCollection(c1);
		result.addAll(c2);
		return result;
	}

	public static <T> boolean areAllEqual(final Collection<? extends T> elements) {
		return areAllEqual(elements, new EqualatorDefault<T>());
	}

	public static <T> boolean areAllEqual(
			final Collection<? extends T> elements, final Equalator<T> equalator) {
		T last = null;

		for (final Iterator<? extends T> iterator = elements.iterator(); iterator
				.hasNext();) {
			final T t = iterator.next();
			if (last == null) {
				last = t;
			} else {
				if (equalator.areEqual(last, t)) {
					// ok
				} else {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Checks whether two or more elements in {@code c} have the same hashCode.
	 *
	 * @see Object#hashCode()
	 * @param c
	 *            {@link Collection}
	 * @return {@code true}, if {@code c} contains more than one element with
	 *         same hashCode; {@code false} otherwise
	 */
	public static boolean containsDuplicates(final Collection<?> c) {
		final Collection<Integer> hashes = UtilList.newList();
		for (final Object o : c) {
			if (o == null) {
				continue;
			}
			final int hash = o.hashCode();
			if (hashes.contains(hash)) {
				return true;
			}
			hashes.add(hash);
		}
		return false;
	}

	/**
	 * @return {@code true}, if any of given collection's elements is
	 *         {@code null}; {@code false} otherwise
	 */
	public static boolean containsNull(final Collection<?> c) {
		synchronized (c) {
			for (final Object o : c) {
				if (o == null)
					return true;
			}
			return false;
		}
	}

	public static <T> boolean containsType(final Collection<?> c1,
			final Class<?> clazz) {
		final FilterType f = new FilterType(clazz);
		for (final Object o : c1) {
			if (f.filter(o)) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean equalsOne(final T o1,
			final Collection<? extends T> others) {
		for (final Object o : others) {
			if (o.equals(o1)) {
				return true;
			}
		}
		return false;
	}

	public static <C> Collection<C> filterCollection(
			final Collection<? extends C> collection, final Filter<C> filter,
			final boolean isSorted) {
		boolean found = false;
		final Collection<C> result = new ArrayList<C>();
		for (final C c : collection) {
			if (filter.filter(c)) {
				result.add(c);
				found = true;
			} else {
				if (isSorted && found == true) {
					return result;
				}
				found = true;
			}
		}
		return result;
	}

	/**
	 * Filters given {@link Collection} using given {@link Filter}. Removes all
	 * <b>not</b> matching elements via {@link Iterator#remove()}. <br>
	 * Note: Synchronizes on {@code collection}.
	 *
	 * @param collection
	 * @param filter
	 * @deprecated
	 */
	@Deprecated
	public static <C> void filterCollectionRemove(
			final Collection<? extends C> collection, final Filter<C> filter) {
		synchronized (collection) {
			for (final Iterator<? extends C> i = collection.iterator(); i
					.hasNext();) {
				if (filter.filter(i.next())) {
					// OK
				} else {
					i.remove();
				}
			}
		}
	}

	/**
	 * Filters given {@link Collection} using given {@link Filter}. All
	 * <b>matching</b> elements are returned in a new {@code collection}. <br>
	 * Note: Synchronizes on {@code collection}.
	 *
	 * @param collection
	 *            {@link Collection} that is filtered
	 * @param filter
	 *            {@link Filter} which is used for filtering
	 * @return A new {@link Collection}, that contains all matching elements
	 * @deprecated
	 */
	@Deprecated
	public static <C> List<C> filterCollectionReturn(
			final Collection<? extends C> collection, final Filter<C> filter) {
		final List<C> result = UtilList.newList();
		if (collection == null) {
			throw new NullPointerException();
		}
		synchronized (collection) {
			for (final Iterator<? extends C> i = collection.iterator(); i
					.hasNext();) {
				final C next = i.next();
				if (filter.filter(next)) {
					result.add(next);
				}
			}
		}
		if (!result.isEmpty()) {
			final int k = 0;
		}
		return result;
	}

	public static <T> Set<T> findDuplicatesByHash(
			final Collection<? extends T> collection) {
		final Set<T> duplicates = new HashSet<T>();
		final Set<T> uniques = new HashSet<T>();
		for (final T t : collection) {
			if (!uniques.add(t)) {
				duplicates.add(t);
			}
		}
		return duplicates;
	}

	public static <T> Collection<T> findSame(final T key,
			final Collection<? extends T> collection) {
		return findSame(key, collection, new EqualatorDefault<T>());
	}

	public static <T> Collection<T> findSame(final T key,
			final Collection<? extends T> collection,
			final Equalator<T> equalator) {
		final Collection<T> result = newCollection();
		for (final T t : collection) {
			if (equalator.areEqual(key, t)) {
				result.add(t);
			}
		}
		return result;
	}

	public static <T> T findSameFirst(final T key,
			final Collection<T> collection) {
		return findSameFirst(key, collection, new EqualatorDefault<T>());
	}

	public static <T> T findSameFirst(final T key,
			final Collection<T> collection, final Equalator<T> equalator) {
		for (final T t : collection) {
			if (equalator.areEqual(key, t)) {
				return t;
			}
		}
		return null;
	}

	/**
	 *
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/Complement_%28set_theory%29">Set
	 *      theory</a>
	 */
	public static <C> Collection<C> getComplementRelativeLeft(
			final Collection<? extends C> c1, final Collection<? extends C> c2,
			final FactoryCollection<C> factory) {
		final Collection<C> result = factory.createCollection();
		for (final C c : c1) {
			if (!c2.contains(c)) {
				result.add(c);
			}
		}
		return result;
	}

	/**
	 *
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/Complement_%28set_theory%29">Set
	 *      theory</a>
	 */
	public static <C> Collection<C> getComplementRelativeRight(
			final Collection<? extends C> c1, final Collection<? extends C> c2,
			final FactoryCollection<C> factory) {
		final Collection<C> result = factory.createCollection();
		for (final C c : c2) {
			if (!c1.contains(c)) {
				result.add(c);
			}
		}
		return result;
	}

	/**
	 *
	 * Returns a index-to-frequency mapping for all elements in given
	 * {@link Collection}. </p> Key of returned map is element from
	 * {@code collection}, corresponding value is this element's number of
	 * occurrences in {@code collection}.
	 *
	 * <p>
	 * last reviewed: 2013-08-15
	 * </p>
	 *
	 * @param collection
	 * @return a {@link Map}, containing index-to-frequency mapping
	 */
	public static <V> Map<V, Integer> getFrequencies(
			final Collection<? extends V> collection) {
		final Map<V, Integer> result = new HashMap<V, Integer>();

		for (final V v : collection) {
			final Integer freq = result.get(v);
			if (freq == null) {
				result.put(v, Integer.valueOf(1));
			} else {
				result.put(v, UtilMath.increment(freq));
			}
		}

		return result;
	}

	public static <T> int getFrequency(final T element,
			final Collection<? extends T> collection) {
		return getFrequency(element, collection, new EqualatorDefault<T>());
	}

	public static <T> int getFrequency(final T element,
			final Collection<? extends T> collection,
			final Equalator<T> equalator) {
		int result = 0;
		for (final T t : collection) {
			if (equalator.areEqual(t, element)) {
				result++;
			}
		}
		return result;
	}

	public static <T extends Comparable<T>> T getHighest(
			final Collection<? extends T> elements) {
		return Collections.max(elements);
	}

	/**
	 * Retrieve highest element contained in a collection.
	 *
	 * @param <T>
	 *            type of elements
	 * @param elements
	 *            collection of elements from which highest is returned
	 * @param c
	 *            {@link Comparator} to find highest
	 * @return highest element
	 */
	public static <T> T getHighest(final Collection<? extends T> elements,
			final Comparator<T> c) {
		return Collections.max(elements, c);
	}

	/**
	 *
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/Intersection_%28set_theory%29">Set
	 *      theory</a>
	 */
	public static <T> Collection<T> getIntersection(final Collection<T> a,
			final Collection<T> b) {
		return getIntersection(a, b, new ArrayListFactory<T>());
	}

	/**
	 *
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/Intersection_%28set_theory%29">Set
	 *      theory</a>
	 */
	public static <T> Collection<T> getIntersection(final Collection<T> a,
			final Collection<T> b, final FactoryCollection<T> factory) {
		final Collection<T> result = factory.createCollection();
		for (final T t : a) {
			if (b.contains(t)) {
				result.add(t);
			}
		}
		return result;
	}

	public static <T extends Comparable<T>> T getLowest(
			final Collection<? extends T> elements) {
		return Collections.min(elements);
	}

	/**
	 * Retrieve lowest element contained in a collection.
	 *
	 * @param <T>
	 *            type of elements
	 * @param elements
	 *            collection of elements from which lowest is returned
	 * @param c
	 *            {@link Comparator} to find lowest
	 * @return lowest element
	 */
	public static <T> T getLowest(final Collection<? extends T> elements,
			final Comparator<T> c) {
		return Collections.min(elements, c);
	}

	public static <T> PairSame<T> getNextTwo(
			final Iterator<? extends T> iterator) {
		if (iterator == null) {
			throw new NullPointerException();
		}
		return new PairSameImpl<T>(iterator.next(), iterator.next());
	}

	public static int getNumberOfNonEmptyElements(
			final Iterable<Collection<?>> col) {
		if (col == null)
			return 0;
		int result = 0;
		for (final Collection<?> o : col) {
			if (o != null && !o.isEmpty()) {
				result++;
			}
		}
		return result;
	}

	public static int getNumberOfNonNullElements(final Collection<?> col) {
		if (col == null)
			return 0;
		int result = 0;
		for (final Object o : col) {
			if (o != null) {
				result++;
			}
		}
		return result;
	}

	public static <T> Collection<Collection<T>> getSame(
			final Collection<? extends T> c, final Equalator<T> equalator) {
		final Collection<Collection<T>> result = newCollection();
		final List<T> listCopy = new ArrayList<T>(c);
		ListIterator<T> it1 = listCopy.listIterator();
		while (it1.hasNext()) {
			final T t1 = it1.next();
			final int index = it1.nextIndex();
			final ListIterator<T> it2 = listCopy.listIterator(index);
			final Collection<T> result2 = newCollection();
			result2.add(t1);
			while (it2.hasNext()) {
				final T t2 = it2.next();
				if (equalator.areEqual(t1, t2)) {
					result2.add(t2);
					it2.remove();
					it1 = listCopy.listIterator();
				}
			}
			if (result2.size() > 1) {
				result.add(result2);
			}
		}
		return result;
	}

	public static <T> Collection<Collection<T>> getSame(final Collection<T> c) {
		return getSame(c, new EqualatorDefault<T>());
	}

	/**
	 *
	 * @see <a href="http://en.wikipedia.org/wiki/Symmetric_difference">Set
	 *      theory</a>
	 */
	public static <T> Collection<T> getSymmetricDifference(
			final Collection<? extends T> c1, final Collection<? extends T> c2) {
		final Collection<T> result = UtilCollection.newCollection();
		for (final T t : c1) {
			if (!c2.contains(t)) {
				result.add(t);
			}
		}
		for (final T t : c2) {
			if (!c1.contains(t)) {
				result.add(t);
			}
		}
		return result;
	}

	public static <C> Collection<C> getUnion(final Collection<? extends C> c1,
			final Collection<? extends C> c2) {
		return getUnion(c1, c2, new ArrayListFactory<C>());
	}

	/**
	 *
	 * @see <a href="http://en.wikipedia.org/wiki/Union_%28set_theory%29">Set
	 *      theory</a>
	 */
	public static <C> Collection<C> getUnion(final Collection<? extends C> c1,
			final Collection<? extends C> c2, final FactoryCollection<C> factory) {
		final Collection<C> result = factory.createCollection();
		result.addAll(c1);
		result.addAll(c2);
		return result;
	}

	/**
	 *
	 * @Deprecated Use {@link TransformerIteratorToCollection} instead.
	 */
	public static <C> Collection<C> iteratorToCollection(
			final Iterable<? extends C> iterable) {
		return TransformerIteratorToCollection.transform(iterable);
	}

	/**
	 *
	 * @Deprecated Use {@link TransformerIteratorToCollection} instead.
	 */
	public static <C> Collection<C> iteratorToCollection(
			final Iterable<? extends C> iterable,
			final FactoryCollection<C> factory) {
		return TransformerIteratorToCollection.transform(iterable, factory);
	}

	public static <T> Collection<T> newCollection() {
		return UtilList.newList();
	}

	public static <T> Collection<T> newCollection(
			final Collection<? extends T> template) {
		return UtilList.newList(template);
	}

	public static <T> Collection<T> newCollection(final T... template) {
		return UtilList.newList(template);
	}

	public static boolean notNullNotEmpty(final Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}

	public static boolean notNullNotEmpty(
			final MapCollection<?, ?, ?> collection) {
		return collection != null && !collection.isEmpty();
	}

	/**
	 * Check if a {@link Collection} or all of its elements is/ are {@code null}
	 * .
	 *
	 * @param col
	 *            {@link Collection} to check
	 * @return true, if given {@link Collection} or all of its elements is/ are
	 *         {@code null}; {@code false} otherwise
	 */
	public static boolean nullCollection(final Collection<?> col) {
		if (col == null)
			return true;
		for (final Object o : col) {
			if (o != null)
				return false;
		}
		return true;
	}

	public static boolean nullOrEmpty(final Collection<?> collection) {
		return !notNullNotEmpty(collection);
	}

	public static void removeNull(final Collection<?> c) {
		synchronized (c) {
			for (final Iterator<?> it = c.iterator(); it.hasNext();) {
				final Object object = it.next();
				if (object == null) {
					it.remove();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Collection removeNullReturn(final Collection c) {
		final Collection copy = new ArrayList(c);
		for (final Iterator it = copy.iterator(); it.hasNext();) {
			final Object object = it.next();
			if (object == null) {
				it.remove();
			}
		}
		return copy;
	}

	public static <T> T select(final Collection<? extends T> c,
			final Selector<T> s) {
		return s.select(c);
	}

	/**
	 *
	 * @Deprecated Use {@link ToString} instead.
	 */
	public static String toString(final Iterable<?> elements) {
		if (elements == null)
			return UtilString.NEW_LINE_STRING + "null";
		if (!elements.iterator().hasNext())
			return UtilString.NEW_LINE_STRING + " ";
		final StringBuilder b = new StringBuilder();
		b.append(UtilString.NEW_LINE_STRING);
		final Iterator<?> i = elements.iterator();
		while (i.hasNext()) {
			b.append(i.next());
			if (i.hasNext())
				b.append(UtilString.NEW_LINE_STRING);
		}
		return b.toString();
	}

	/**
	 *
	 * @Deprecated Use {@link ToString} instead.
	 */
	public static <T> String toString(final Iterable<T> it,
			final String objectSeparator) {
		return new ToString().setObjectSeparator(objectSeparator).toString(it);
		// return toString(it,
		// (Transformer<T, String>) TRANSFORMER_TO_STRING_DEFAULT,
		// objectSeparator);
	}

	/**
	 *
	 * @Deprecated Use {@link ToString} instead.
	 */
	public static <T> String toString(final Iterable<T> it,
			final Transformer<T, String> s) {
		return new ToString().toString(it, (TransformerToString<T>) s);
		// return toString(it, s, DEFAULT_OBJECT_SEPARATOR);
	}

	/**
	 *
	 * @Deprecated Use {@link ToString} instead.
	 */
	public static <T> String toString(final Iterable<T> iterable,
			final Transformer<T, String> transformer,
			final String objectSeparator) {
		return new ToString().setObjectSeparator(objectSeparator).toString(
				iterable, (TransformerToString<T>) transformer);
	}

	/**
	 *
	 * @Deprecated Use {@link ToString} instead.
	 */
	public static <T> String toString(final ListIterator<T> it,
			final VisitorList<String, T> s) {
		return new ToString().toString(it, s);
		// return toString(it, s, DEFAULT_OBJECT_SEPARATOR);
	}

	/**
	 *
	 * @Deprecated Use {@link ToString} instead.
	 */
	public static <T> String toString(final ListIterator<T> it,
			final VisitorList<String, T> visitor, final String objectSeparator) {
		return new ToString().setObjectSeparator(objectSeparator).toString(it,
				visitor);
	}

	private UtilCollection() {
		// Singleton
	}
}
