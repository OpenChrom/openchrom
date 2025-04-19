/*******************************************************************************
 * Copyright (c) 2013, 2025 Lablicate GmbH.
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
package net.openchrom.msd.converter.supplier.cdf.exceptions;

public class NoSuchScanStored extends Exception {

	private static final long serialVersionUID = 646083054947955595L;

	public NoSuchScanStored() {
		super();
	}

	public NoSuchScanStored(String message) {
		super(message);
	}
}
