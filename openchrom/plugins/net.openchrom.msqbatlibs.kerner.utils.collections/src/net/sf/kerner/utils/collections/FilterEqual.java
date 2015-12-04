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
package net.sf.kerner.utils.collections;

import java.util.Collection;

import net.sf.kerner.utils.collections.filter.Filter;
import net.sf.kerner.utils.equal.Equalator;

public class FilterEqual<T> implements Filter<T> {

	private final Collection<? extends T> elements;
	private final Equalator<T> equalator;

	public FilterEqual(final Collection<? extends T> elements, final Equalator<T> equalator) {

		super();
		this.elements = elements;
		this.equalator = equalator;
	}

	public boolean filter(final T e) {

		for(final T o : elements) {
			if(o.hashCode() == e.hashCode()) {
				continue;
			}
			if(equalator.areEqual(e, o)) {
				return true;
			}
		}
		return false;
	}
}
