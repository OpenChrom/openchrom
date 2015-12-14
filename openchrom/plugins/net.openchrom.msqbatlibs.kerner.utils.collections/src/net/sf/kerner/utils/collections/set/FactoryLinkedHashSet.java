/*******************************************************************************
 *  Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.collections.set;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import net.sf.kerner.utils.Factory;

/**
 * 
 * A {@link FactorySet} to create instances of {@link LinkedHashSet}.
 * 
 * <p>
 * <b>Example:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 2013-08-07
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * @version 2013-08-07
 * 
 * @param <E>
 */
public class FactoryLinkedHashSet<E> implements FactorySet<E>, Factory<Set<E>> {

	/**
     * 
     */
	public Set<E> create() {

		return createCollection();
	}

	/** 
	 * 
	 */
	public Set<E> createCollection() {

		return new LinkedHashSet<E>();
	}

	/**
	 * 
	 */
	public Set<E> createCollection(final Collection<? extends E> template) {

		return new LinkedHashSet<E>(template);
	}
}
