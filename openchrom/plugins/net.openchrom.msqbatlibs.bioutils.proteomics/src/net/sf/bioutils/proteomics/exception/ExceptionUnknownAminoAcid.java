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
package net.sf.bioutils.proteomics.exception;

import java.util.Properties;

public class ExceptionUnknownAminoAcid extends ExceptionRuntimeProteomics {

	private static final long serialVersionUID = 6342610022514210243L;

	public ExceptionUnknownAminoAcid() {

		super();
	}

	public ExceptionUnknownAminoAcid(final Properties pro) {

		super(pro);
	}

	public ExceptionUnknownAminoAcid(final String arg0) {

		super(arg0);
	}

	public ExceptionUnknownAminoAcid(final String arg0, final Properties pro) {

		super(arg0, pro);
	}

	public ExceptionUnknownAminoAcid(final String arg0, final Throwable arg1) {

		super(arg0, arg1);
	}

	public ExceptionUnknownAminoAcid(final String arg0, final Throwable arg1, final Properties pro) {

		super(arg0, arg1, pro);
	}

	public ExceptionUnknownAminoAcid(final Throwable arg0) {

		super(arg0);
	}

	public ExceptionUnknownAminoAcid(final Throwable arg0, final Properties pro) {

		super(arg0, pro);
	}
}
