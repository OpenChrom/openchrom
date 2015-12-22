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
package net.sf.kerner.utils;

public class SetterOnlyOnce<T> {

	private final boolean strict;
	private T t = null;

	public SetterOnlyOnce() {

		this(false);
	}

	public SetterOnlyOnce(final boolean strict) {

		this.strict = strict;
	}

	public T get() {

		return t;
	}

	public void set(final T t) {

		if(this.t == null || (!strict && this.t.equals(t))) {
			this.t = t;
		} else {
			throw new IllegalStateException("refuse to override " + this.t + " with " + t);
		}
	}
}
