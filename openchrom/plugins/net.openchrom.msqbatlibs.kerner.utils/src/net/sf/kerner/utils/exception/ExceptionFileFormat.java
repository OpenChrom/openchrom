/*******************************************************************************
 * Copyright (c) 2015, 2016 Lablicate GmbH.
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

import java.io.IOException;

public class ExceptionFileFormat extends IOException {

	private static final long serialVersionUID = 4379612301932722343L;

	public ExceptionFileFormat() {
		super();
	}

	public ExceptionFileFormat(final String message) {
		super(message);
	}

	public ExceptionFileFormat(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ExceptionFileFormat(final Throwable cause) {
		super(cause);
	}
}
