/*******************************************************************************
 * Copyright (c) 2016, 2026 Walter Whitlock.
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

import org.eclipse.chemclipse.msd.model.core.IScanMSD;

public class InvalidScanException extends Exception {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = -3947508833918904114L;

	public InvalidScanException(IScanMSD scan) {
		super("An unrecoverable error occurred when processing this mass spec scan " + scan + ".");
	}
}
