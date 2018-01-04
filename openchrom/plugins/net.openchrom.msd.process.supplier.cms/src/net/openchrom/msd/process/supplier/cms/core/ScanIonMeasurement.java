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
package net.openchrom.msd.process.supplier.cms.core;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class ScanIonMeasurement extends GenIon {

	private ICalibratedVendorMassSpectrum scanRef; // this ion was found in this mass spectrum scan
	private int ionIndex; // marks the position of this ion in the scan mass spectrum

	public ScanIonMeasurement(double mass, double abundance, ICalibratedVendorMassSpectrum scan, int index) {
		super(mass, abundance);
		scanRef = scan;
		ionIndex = index;
	}

	public ICalibratedVendorMassSpectrum getScanRef() {

		return scanRef;
	}

	public void setScanRef(ICalibratedVendorMassSpectrum scanRef) {

		this.scanRef = scanRef;
	}

	public int getIonIndex() {

		return ionIndex;
	}

	public void setIonIndex(int ionIndex) {

		this.ionIndex = ionIndex;
	}
}
