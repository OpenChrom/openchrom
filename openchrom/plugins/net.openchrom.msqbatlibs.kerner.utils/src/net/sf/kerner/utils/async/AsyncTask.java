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

/**
 * Create an asynchronous callback.
 * <p>
 * <b>Example:</b><br>
 * </p>
 * <p>
 * 
 * <pre>
 * TODO example
 * </pre>
 * 
 * </p>
 * 
 * @author <a href="mailto:alex.kerner.24@googlemail.com">Alexander Kerner</a>
 * @version 2011-03-10
 * @param <R>
 *            type of result
 * @param <V>
 *            type of value
 */
public interface AsyncTask<R, V> {

	void doBefore();

	/**
	 * Perform on failure.
	 * 
	 * @param e
	 *            cause for this {@code AsyncCallBack}'s execution failure
	 */
	void doOnFailure(Exception e);

	/**
	 * Perform on success.
	 * 
	 * @param result
	 *            result of execution
	 */
	void doOnSucess(R result);

	/**
	 * Do something asynchronously.
	 * 
	 * @param value
	 *            parameter for execution
	 * @return result of execution
	 * @throws Exception
	 *             if execution fails
	 */
	R run(V value) throws Exception;
}
