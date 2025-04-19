/*******************************************************************************
 * Copyright (c) 2016, 2025 Walter Whitlock.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.exceptions;

public class NotCalibratedVendorMassSpectrumException extends Exception {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -6601698689069415800L;

	public NotCalibratedVendorMassSpectrumException() {
		super("Mass spectrum must be of type ICalibratedVendorMassSpectrum");
	}
}
