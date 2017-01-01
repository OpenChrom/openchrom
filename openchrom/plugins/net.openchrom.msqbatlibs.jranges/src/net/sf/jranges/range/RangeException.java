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
package net.sf.jranges.range;

public class RangeException extends IllegalArgumentException {

	private static final long serialVersionUID = -6555274736122521475L;

	public RangeException() {
	}

	public RangeException(String message) {
		super(message);
	}

	public RangeException(Throwable cause) {
		super(cause);
	}

	public RangeException(String message, Throwable cause) {
		super(message, cause);
	}
}
