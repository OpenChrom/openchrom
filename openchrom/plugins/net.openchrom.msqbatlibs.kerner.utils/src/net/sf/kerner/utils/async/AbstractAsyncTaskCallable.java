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
package net.sf.kerner.utils.async;

import java.util.concurrent.Callable;

public abstract class AbstractAsyncTaskCallable<R, V> extends AbstractAsyncTask<R, V> implements Callable<Void> {

	protected V value;

	/**
	 * Don't override. Override {@link #run(Object)}
	 */
	public final Void call() throws Exception {

		execute(getValue());
		return null;
	}

	public synchronized V getValue() {

		return value;
	}

	public synchronized void setValue(final V value) {

		this.value = value;
	}
}
