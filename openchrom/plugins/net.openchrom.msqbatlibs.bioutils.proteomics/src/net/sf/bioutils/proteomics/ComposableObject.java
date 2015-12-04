/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
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

	@Override
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
	@Override
	public List<T> getMembers() {

		return Collections.unmodifiableList(members);
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		return result;
	}

	@Override
	public String toString() {

		return members.toString();
	}
}
