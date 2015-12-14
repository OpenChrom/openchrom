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
