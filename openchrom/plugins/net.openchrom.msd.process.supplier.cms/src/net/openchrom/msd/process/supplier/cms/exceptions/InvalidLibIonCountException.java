/*******************************************************************************
 * Copyright (c) 2016, 2018 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
