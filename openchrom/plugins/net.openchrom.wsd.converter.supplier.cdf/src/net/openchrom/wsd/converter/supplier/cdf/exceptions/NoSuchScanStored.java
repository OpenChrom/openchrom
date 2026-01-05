/*******************************************************************************
 * Copyright (c) 2014, 2026 Lablicate GmbH.
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
package net.openchrom.wsd.converter.supplier.cdf.exceptions;

public class NoSuchScanStored extends Exception {

	private static final long serialVersionUID = 1494271997895128977L;

	public NoSuchScanStored() {
		super();
	}

	public NoSuchScanStored(String message) {
		super(message);
	}
}
