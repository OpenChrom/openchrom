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
package net.sf.jfasta.impl;

public class ExceptionRuntimeFASTA extends RuntimeException {

	/**
     *
     */
	private static final long serialVersionUID = -5681123013659877669L;

	public ExceptionRuntimeFASTA() {

	}

	public ExceptionRuntimeFASTA(final String message) {

		super(message);
	}

	public ExceptionRuntimeFASTA(final String message, final Throwable cause) {

		super(message, cause);
	}

	public ExceptionRuntimeFASTA(final Throwable cause) {

		super(cause);
	}
}
