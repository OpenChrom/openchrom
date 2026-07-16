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

public class InvalidLibIonCountException extends Exception {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 5032358108155945622L;

	public InvalidLibIonCountException() {
		super("Attempt to getUsedLibIonCount() before executing DataSet.match()");
	}
}
