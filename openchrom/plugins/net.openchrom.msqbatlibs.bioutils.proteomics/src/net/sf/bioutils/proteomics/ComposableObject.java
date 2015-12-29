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
package net.sf.bioutils.proteomics;

import java.util.Collections;
import java.util.List;

import net.sf.kerner.utils.collections.list.UtilList;

/**
 * 
 * {@code ComposableObject} the project-default implementation for {@link ComposableElement}.
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
 * <b>Threading:</b><br>
 * 
 * </p>
 * <p>
 * 
 * <pre>
 * Not thread save.
 * </pre>
 * 
 * </p>
 * <p>
 * last reviewed: 0000-00-00
 * </p>
 * 
 * @author <a href="mailto:alexanderkerner24@gmail.com">Alexander Kerner</a>
 * 
 * @param <T>
 */
public class ComposableObject<T> implements ComposableElement<T> {

	protected List<T> members;

	public ComposableObject(final List<? extends T> members) {
		if(members == null || members.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.members = UtilList.newList(members);
	}

	public boolean equals(final Object obj) {

		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(!(obj instanceof ComposableObject))
			return false;
		@SuppressWarnings("rawtypes")
		final ComposableObject other = (ComposableObject)obj;
		if(members == null) {
			if(other.members != null)
				return false;
		} else if(!members.equals(other.members))
			return false;
		return true;
	}

	/**
	 * @return an unmodifiable {@link List} that contains all members.
	 */
	public List<T> getMembers() {

		return Collections.unmodifiableList(members);
	}

	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		return result;
	}

	public String toString() {

		return members.toString();
	}
}
