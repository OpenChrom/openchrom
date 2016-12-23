/*******************************************************************************
 * Copyright (c) 2016 Lablicate GmbH.
 *
 * All rights reserved.
 *
 * Contributors:
 * Dr. Alexander Kerner - initial API and implementation
 *******************************************************************************/
package net.sf.kerner.utils.exception;

public class ExceptionRuntimeUnexpectedType extends RuntimeException {

	private static final long serialVersionUID = 1729348152174596405L;

	public ExceptionRuntimeUnexpectedType() {
		super();
	}

	public ExceptionRuntimeUnexpectedType(Object unexpectedType) {
		super("Unexpected type " + unexpectedType.getClass());
	}

	public ExceptionRuntimeUnexpectedType(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExceptionRuntimeUnexpectedType(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceptionRuntimeUnexpectedType(String message) {
		super(message);
	}

	public ExceptionRuntimeUnexpectedType(Throwable cause) {
		super(cause);
	}
}
