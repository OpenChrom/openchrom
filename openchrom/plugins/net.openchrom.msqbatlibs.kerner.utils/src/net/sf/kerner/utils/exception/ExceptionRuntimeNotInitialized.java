/*******************************************************************************
 * Copyright (c) 2015, 2018 Lablicate GmbH.
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

public class ExceptionRuntimeNotInitialized extends RuntimeException {

	private static final long serialVersionUID = 5014934956292054682L;

	public ExceptionRuntimeNotInitialized() {
	}

	public ExceptionRuntimeNotInitialized(final String message) {
		super(message);
	}

	public ExceptionRuntimeNotInitialized(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ExceptionRuntimeNotInitialized(final Throwable cause) {
		super(cause);
	}
}
