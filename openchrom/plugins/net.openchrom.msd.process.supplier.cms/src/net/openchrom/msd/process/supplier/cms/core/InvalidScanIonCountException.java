/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock.
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

class InvalidScanIonCountException extends Exception {

	InvalidScanIonCountException() {
	}

	public String toString() {

		return "Attempt to get getUsedScanIonCount before executing DataSet.match()";
	}
}
