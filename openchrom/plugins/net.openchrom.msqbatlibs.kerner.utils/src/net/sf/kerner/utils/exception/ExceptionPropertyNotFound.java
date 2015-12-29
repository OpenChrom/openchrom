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
package net.sf.kerner.utils.exception;

/**
 * A {@link RuntimeException} to indicate a missing property.
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
 * @version 2012-06-18
 */
public class ExceptionPropertyNotFound extends RuntimeException {

	private static final long serialVersionUID = -4880504253227652735L;

	public ExceptionPropertyNotFound() {
	}

	public ExceptionPropertyNotFound(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public ExceptionPropertyNotFound(final String arg0) {
		super(arg0);
	}

	public ExceptionPropertyNotFound(final Throwable arg0) {
		super(arg0);
	}
}
