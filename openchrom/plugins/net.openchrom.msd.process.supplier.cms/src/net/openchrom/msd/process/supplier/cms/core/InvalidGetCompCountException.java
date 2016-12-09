/*******************************************************************************
 * Copyright (c) 2016 whitlow.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * whitlow - initial API and implementation
*******************************************************************************/
package net.openchrom.msd.process.supplier.cms.core;


class InvalidGetCompCountException extends Exception {
	int j;
	InvalidGetCompCountException(int i) { j = i; }
	public String toString() {
		return "component count is invalid, value = " + j + ".";
	}
}


