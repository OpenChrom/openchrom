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
package net.sf.kerner.utils.collections;

public class ExceptionRuntimeDuplicateElement extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8323720771155415135L;

	public ExceptionRuntimeDuplicateElement() {
	}

	public ExceptionRuntimeDuplicateElement(final String message) {
		super(message);
	}

	public ExceptionRuntimeDuplicateElement(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ExceptionRuntimeDuplicateElement(final Throwable cause) {
		super(cause);
	}
}
