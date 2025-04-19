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

public class NotEnoughScanDataStored extends Exception {

	private static final long serialVersionUID = 3474027294427724564L;

	public NotEnoughScanDataStored() {
		super();
	}

	public NotEnoughScanDataStored(String message) {
		super(message);
	}
}
