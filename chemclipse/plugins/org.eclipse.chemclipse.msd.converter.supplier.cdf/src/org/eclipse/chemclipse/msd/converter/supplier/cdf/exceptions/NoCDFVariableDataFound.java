/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.chemclipse.msd.converter.supplier.cdf.exceptions;

public class NoCDFVariableDataFound extends Exception {

	private static final long serialVersionUID = 857008084043051875L;

	public NoCDFVariableDataFound() {

		super();
	}

	public NoCDFVariableDataFound(String message) {

		super(message);
	}
}
