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
package net.sf.bioutils.proteomics.exception;

import java.util.Properties;

import net.sf.kerner.utils.exception.ExceptionRuntimeProperty;

public class ExceptionRuntimeProteomics extends ExceptionRuntimeProperty {

	private static final long serialVersionUID = -7186253440348928800L;

	public ExceptionRuntimeProteomics() {
		super();
	}

	public ExceptionRuntimeProteomics(final Properties pro) {
		super(pro);
	}

	public ExceptionRuntimeProteomics(final String arg0) {
		super(arg0);
	}

	public ExceptionRuntimeProteomics(final String arg0, final Properties pro) {
		super(arg0, pro);
	}

	public ExceptionRuntimeProteomics(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

	public ExceptionRuntimeProteomics(final String arg0, final Throwable arg1, final Properties pro) {
		super(arg0, arg1, pro);
	}

	public ExceptionRuntimeProteomics(final Throwable arg0) {
		super(arg0);
	}

	public ExceptionRuntimeProteomics(final Throwable arg0, final Properties pro) {
		super(arg0, pro);
	}
}
