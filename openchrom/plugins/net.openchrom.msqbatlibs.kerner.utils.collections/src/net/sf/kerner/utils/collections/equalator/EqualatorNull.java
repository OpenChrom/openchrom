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
package net.sf.kerner.utils.collections.equalator;

import net.sf.kerner.utils.equal.Equalator;
import net.sf.kerner.utils.equal.EqualatorAbstract;

public class EqualatorNull<T> extends EqualatorAbstract<T> {

	private final Equalator<T> delegate;

	public EqualatorNull() {
		this(null);
	}

	public EqualatorNull(final Equalator<T> delegate) {
		super();
		if(delegate == null) {
			this.delegate = new EqualatorDefault<T>();
		} else {
			this.delegate = delegate;
		}
	}

	public boolean areEqual(final T o1, final Object o2) {

		if(o1 == null && o2 == null) {
			return true;
		}
		if(o1 == null) {
			return false;
		}
		if(o2 == null) {
			return false;
		}
		return delegate.areEqual(o1, o2);
	}
}
