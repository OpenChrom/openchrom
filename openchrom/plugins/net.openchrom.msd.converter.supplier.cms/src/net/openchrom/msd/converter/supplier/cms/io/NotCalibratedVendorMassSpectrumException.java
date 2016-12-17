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
package net.openchrom.msd.converter.supplier.cms.io;


public class NotCalibratedVendorMassSpectrumException extends Exception {
	NotCalibratedVendorMassSpectrumException() {
	}
	public String toString() {
		return "Mass spectrum must be of type ICalibratedVendorMassSpectrum";
	}
}
