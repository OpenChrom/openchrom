/*******************************************************************************
 * Copyright (c) 2010-2014 Alexander Kerner. All rights reserved.
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
package net.sf.kerner.utils.collections.equalator;

import java.util.List;

import net.sf.kerner.utils.collections.applier.ApplierAbstract;
import net.sf.kerner.utils.collections.list.UtilList;
import net.sf.kerner.utils.equal.Equalator;
import net.sf.kerner.utils.pair.Pair;

public class EqualatorApplierProto<T> extends ApplierAbstract implements EqualatorApplier<T> {

	private final List<Equalator<T>> equalators = UtilList.newList();

	public EqualatorApplierProto() {

		super();
	}

	public EqualatorApplierProto(final TYPE type) {

		super(type);
	}

	public void addEqualator(final Equalator<T> equalator) {

		this.equalators.add(equalator);
	}

	public boolean areEqual(final T o1, final Object o2) {

		switch(type) {
			case AND:
				for(final Equalator<T> e : equalators) {
					if(e.areEqual(o1, o2)) {
						// ok
					} else {
						return false;
					}
				}
				return true;
			case OR:
				for(final Equalator<T> e : equalators) {
					if(e.areEqual(o1, o2)) {
						return true;
					} else {
						// see what others say
					}
				}
				return false;
			default:
				throw new RuntimeException("unknown type " + type);
		}
	}

	public void clear() {

		this.equalators.clear();
	}

	public List<Equalator<T>> getEqualators() {

		return this.equalators;
	}

	public Boolean transform(final Pair<T, Object> element) {

		return Boolean.valueOf(areEqual(element.getFirst(), element.getSecond()));
	}
}
