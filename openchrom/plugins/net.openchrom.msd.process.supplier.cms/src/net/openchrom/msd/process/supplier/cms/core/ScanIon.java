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

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

class ScanIon extends GenIon {

	ICalibratedVendorMassSpectrum scanRef; // this ion was found in this mass spectrum scan
	int ionIndex; // marks the position of this ion in the scan mass spectrum

	ScanIon(double mass, double abundance, ICalibratedVendorMassSpectrum scan, int index) {
		super(mass, abundance);
		scanRef = scan;
		ionIndex = index;
	}
}
