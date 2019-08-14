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
package net.sf.kerner.utils.collections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * TODO description
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
 * @version Jun 18, 2012
 * @param <T>
 */
public class ObjectToIndexMapperProxy<T> extends ObjectToIndexMapperImpl<T> {

	protected Map<T, Object> identToIdent;

	public ObjectToIndexMapperProxy(final Map<T, Object> identToIdent) {
		super(new ArrayList<T>(identToIdent.keySet()));
		this.identToIdent = new LinkedHashMap<T, Object>(identToIdent);
	}

	public int get(final T key) {

		final List<T> keySet = new ArrayList<T>(identToIdent.keySet());
		for(int i = 0; i < keySet.size(); i++) {
			if(keySet.get(i) != null && keySet.get(i).equals(key)) {
				return i;
			}
		}
		throw new NoSuchElementException();
	}

	public Object getValue(final int index) {

		final Object o = super.getValue(index);
		for(final Object oo : identToIdent.values()) {
			if(o.equals(oo)) {
				return oo;
			}
		}
		throw new NoSuchElementException();
	}

	public boolean containsKey(final T key) {

		return identToIdent.containsKey(key);
	}

	public boolean containsValue(final int index) {

		return identToIdent.containsValue(super.getValue(index));
	}

	public List<T> keys() {

		return new ArrayList<T>(identToIdent.keySet());
	}

	public void addMapping(final T key) {

		throw new IllegalStateException();
	}

	public void addMapping(final T key, final int index) {

		throw new IllegalStateException();
	}
}
