/*******************************************************************************
 * Copyright (c) 2016, 2017 Walter Whitlock.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;

class NoScanIonsException extends Exception {

	int j;

	NoScanIonsException(int i) {
		j = i;
	}

	public String toString() {

		return "Need at least 1 Scan ion, have " + j + ".";
	}
}
