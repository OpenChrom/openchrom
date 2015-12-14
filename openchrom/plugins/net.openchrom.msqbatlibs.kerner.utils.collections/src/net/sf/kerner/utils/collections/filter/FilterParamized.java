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
package net.sf.kerner.utils.collections.filter;

public abstract class FilterParamized<T, P> implements Filter<T> {

	private P param;

	public FilterParamized(final P param) {

		this.param = param;
	}

	public synchronized P getParam() {

		return param;
	}

	public synchronized void setParam(final P param) {

		this.param = param;
	}

	@Override
	public String toString() {

		return getClass().getSimpleName() + " " + getParam();
	}
}
