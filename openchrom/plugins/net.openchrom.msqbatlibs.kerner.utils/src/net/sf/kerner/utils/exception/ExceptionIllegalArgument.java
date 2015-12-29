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

import java.util.Properties;

public class ExceptionIllegalArgument extends ExceptionRuntimeProperty {

	private static final long serialVersionUID = -209264429262542218L;

	public ExceptionIllegalArgument() {
		super();
	}

	public ExceptionIllegalArgument(final Properties pro) {
		super(pro);
	}

	public ExceptionIllegalArgument(final String arg0) {
		super(arg0);
	}

	public ExceptionIllegalArgument(final String arg0, final Properties pro) {
		super(arg0, pro);
	}

	public ExceptionIllegalArgument(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public ExceptionIllegalArgument(final String arg0, final Throwable arg1, final Properties pro) {
		super(arg0, arg1, pro);
	}

	public ExceptionIllegalArgument(final Throwable arg0) {
		super(arg0);
	}

	public ExceptionIllegalArgument(final Throwable arg0, final Properties pro) {
		super(arg0, pro);
	}
}
