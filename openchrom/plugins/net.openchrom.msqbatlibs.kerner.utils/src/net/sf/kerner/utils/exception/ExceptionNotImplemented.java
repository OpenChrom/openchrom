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

public class ExceptionNotImplemented extends RuntimeException {

	private static final long serialVersionUID = 2576513716754824976L;

	public ExceptionNotImplemented() {
	}

	public ExceptionNotImplemented(final String message) {
		super(message);
	}

	public ExceptionNotImplemented(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ExceptionNotImplemented(final Throwable cause) {
		super(cause);
	}
}
