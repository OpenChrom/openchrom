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

import java.io.Serializable;

public class ExceptionUnknownType extends RuntimeException {

	private static final long serialVersionUID = -1661770337806543702L;
	private final Serializable unknownType;

	public ExceptionUnknownType(final Serializable unknownType) {
		super();
		this.unknownType = unknownType;
	}

	public ExceptionUnknownType(final Serializable unknownType, final String message) {
		super(message);
		this.unknownType = unknownType;
	}

	public ExceptionUnknownType(final Serializable unknownType, final String message, final Throwable cause) {
		super(message, cause);
		this.unknownType = unknownType;
	}

	public ExceptionUnknownType(final Serializable unknownType, final Throwable cause) {
		super(cause);
		this.unknownType = unknownType;
	}

	public String toString() {

		return "unknown type: " + unknownType + ", " + super.toString();
	}
}
