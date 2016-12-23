/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * {@code ObjectToIndexMapper} establishes a mapping between any collection of objects and a {@link List List's} integer
 * indices.
 * <p>
 * Given a collection of objects, e.g. following set of strings:
 * </p>
 * 
 * <pre>
 * {one, two, three, four}
 * </pre>
 * 
 * And a given list with following elements:
 * </p>
 * 
 * <pre>
 * {blue, red, black, green}
 * </pre>
 * 
 * Access to this list's elements can be more intuitive accessing the elements by an object-based index:
 * </p>
 * 
 * <pre>
 * List colors = new ArrayList(){{blue, red, black, green}};
 * List indices = new ArrayList(){{one, two, three, four}};
 * ObjectToIndexMapper mapper = new ObjectToIndexMapperImpl(indices);
 * assertEquals(blue, colors.get(mapper.get(one)));
 * ...
 * </pre>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2012-06-18
 * @see Collection
 * @see Map
 */
public interface ObjectToIndexMapper<T> {

	/**
	 * Check weather given object maps to any index.
	 * 
	 * @param key
	 *            object that is checked to be a valid key in this {@code ObjectToIndexMapper}
	 * @return {@code true}, if given object is a valid key; {@code false} otherwise
	 */
	boolean containsKey(T key);

	/**
	 * Check weather given index is mapped by any object.
	 * 
	 * @param index
	 *            index that is checked to be a valid value in this {@code ObjectToIndexMapper}
	 * @return {@code true}, if given index is a valid value; {@code false} otherwise
	 */
	boolean containsValue(int index);

	/**
	 * Get index that is mapped by given object identifier.
	 * 
	 * @param key
	 *            object that maps to index
	 * @return index that is mapped
	 * @throws NoSuchElementException
	 *             if key is not registered to this {@code ObjectToIndexMapper}
	 */
	int get(T key) throws NoSuchElementException;

	/**
	 * Get object identifier that maps to given index.
	 * 
	 * @param index
	 *            index for which mapping object is returned
	 * @return object that maps to given index
	 * @throws NoSuchElementException
	 *             if index is not mapped by any object
	 */
	Object getValue(int index) throws NoSuchElementException;

	/**
	 * Get a {@link List} that contains all values of this {@code ObjectToIndexMapper}, meaning all indices that are
	 * mapped by objects.
	 * 
	 * @return a {@link List} that contains all values of this {@code ObjectToIndexMapper}
	 */
	List<Integer> values();

	/**
	 * Get a {@link List} that contains all keys of this {@code ObjectToIndexMapper}, meaning all objects that map to
	 * indices.
	 * 
	 * @return a {@code List} that contains all keys of this {@code ObjectToIndexMapper}
	 */
	List<T> keys();

	int getSize();

	int getMaxIndex();

	boolean isEmpty();

	void addMapping(T key);

	void addMapping(T key, int index);
}
