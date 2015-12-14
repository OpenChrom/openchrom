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
package net.sf.kerner.utils.async;

public abstract class AbstractAsyncTaskDelayed<O, I, K> extends AbstractAsyncTask<O, I> implements BatchListener<K> {

	private volatile O result;

	public void allDone(final boolean error) {

		doOnSucess(result);
	}

	public void errorOccured(final K identifier, final Exception error) {

		doOnFailure(error);
	}

	/**
	 * Execute this {@code AbstractAsyncCallBack}. <br>
	 * Don't override. Override {@link #run(Object)}
	 * 
	 * 
	 * @param value
	 *            parameter for this {@code AbstractAsyncCallBack}
	 */
	@Override
	public final void execute(final I value) {

		try {
			result = run(value);
		} catch(final Exception e) {
			doOnFailure(e);
		}
	};
}
