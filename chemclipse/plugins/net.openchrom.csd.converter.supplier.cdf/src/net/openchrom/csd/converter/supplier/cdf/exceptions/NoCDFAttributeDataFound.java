/*******************************************************************************
 * Copyright (c) 2014, 2016 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.exceptions;

public class NoCDFAttributeDataFound extends Exception {

	private static final long serialVersionUID = 6205320595764490361L;

	public NoCDFAttributeDataFound() {
		super();
	}

	public NoCDFAttributeDataFound(String message) {
		super(message);
	}
}
