/*******************************************************************************
 * Copyright (c) 2013, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.msd.converter.supplier.cdf.exceptions;

public class NoSuchScanStored extends Exception {

	private static final long serialVersionUID = 646083054947955595L;

	public NoSuchScanStored() {

		super();
	}

	public NoSuchScanStored(String message) {

		super(message);
	}
}
