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
package net.openchrom.msd.process.supplier.cms.exceptions;

public class InvalidGetCompCountException extends Exception {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 2062976813264402607L;

	public InvalidGetCompCountException(int i) {
		super("component count is invalid, value = " + i + ".");
	}
}
