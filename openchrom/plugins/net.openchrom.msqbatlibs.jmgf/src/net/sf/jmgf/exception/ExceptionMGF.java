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
package net.sf.jmgf.exception;

public class ExceptionMGF extends Exception {

	private static final long serialVersionUID = -8221476999104581714L;

	public ExceptionMGF() {
	}

	public ExceptionMGF(final String message) {
		super(message);
	}

	public ExceptionMGF(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ExceptionMGF(final Throwable cause) {
		super(cause);
	}
}
