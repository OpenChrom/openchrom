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

//import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
//import org.eclipse.chemclipse.msd.model.core.IScanMSD;

//import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
//import net.openchrom.msd.converter.supplier.cms.model.MsdScanMeasurement;

class GenIon implements Comparable<GenIon> {
	double ionMass;
	double ionAbundance;
	int ionRowIndex;
	int ionCompSequence;
	boolean mark;
	
	GenIon(double mass, double abundance) {
		mark = false;
		ionRowIndex = -1;
		ionCompSequence = -1;
		ionMass = mass;
		ionAbundance = abundance;
	}
	
	public int compareTo(GenIon ion) {
		if (this.ionMass < ion.ionMass) return -1;
		else if (this.ionMass > ion.ionMass) return 1;
		else if (this.ionCompSequence < ion.ionCompSequence) return -1;
		else if (this.ionCompSequence > ion.ionCompSequence) return 1;
		return 0;
	}
	
	boolean massEqual(double mass, double tol) {
		double diff;
		
		diff = this.ionMass - mass;
		if (tol > java.lang.StrictMath.abs(diff)) return true;
		return false;
	}
	
	boolean massLess(double mass, double tol) {
		double diff;
		
		diff = this.ionMass - mass;
		if (-tol > diff) return true;
		return false;
	}
	
	void setMark() {
		this.mark = true;
	}
}


