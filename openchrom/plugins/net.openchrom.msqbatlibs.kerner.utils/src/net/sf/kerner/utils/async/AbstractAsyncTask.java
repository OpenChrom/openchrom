/*******************************************************************************
 * Copyright (c) 2015, 2017 Lablicate GmbH.
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

public abstract class AbstractAsyncTask<R, V> implements AsyncTask<R, V> {

	public void execute(final V value) {

		doBefore();
		try {
			doOnSucess(run(value));
		} catch(final Exception e) {
			doOnFailure(e);
		}
	}
}
