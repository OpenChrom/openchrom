/*******************************************************************************
 * Copyright (c) 2014, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
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
