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

// import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
// import org.eclipse.chemclipse.msd.model.core.IScanMSD;
// import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;
// import net.openchrom.msd.converter.supplier.cms.model.MsdScanMeasurement;
public class GenIon implements Comparable<GenIon> {

	private double ionMass;
	private double ionAbundance;
	private int ionRowIndex;
	private int ionCompSequence;
	private boolean mark;

	public GenIon(double mass, double abundance) {
		mark = false;
		ionRowIndex = -1;
		ionCompSequence = -1;
		ionMass = mass;
		ionAbundance = abundance;
	}

	public int compareTo(GenIon ion) {

		if(this.ionMass < ion.ionMass)
			return -1;
		else if(this.ionMass > ion.ionMass)
			return 1;
		else if(this.ionCompSequence < ion.ionCompSequence)
			return -1;
		else if(this.ionCompSequence > ion.ionCompSequence)
			return 1;
		return 0;
	}

	public boolean massEqual(double mass, double tol) {

		double diff;
		diff = this.ionMass - mass;
		if(tol > java.lang.StrictMath.abs(diff))
			return true;
		return false;
	}

	public boolean massLess(double mass, double tol) {

		double diff;
		diff = this.ionMass - mass;
		if(-tol > diff)
			return true;
		return false;
	}

	public void setMark() {

		this.mark = true;
	}

	public double getIonMass() {

		return ionMass;
	}

	public void setIonMass(double ionMass) {

		this.ionMass = ionMass;
	}

	public double getIonAbundance() {

		return ionAbundance;
	}

	public void setIonAbundance(double ionAbundance) {

		this.ionAbundance = ionAbundance;
	}

	public int getIonRowIndex() {

		return ionRowIndex;
	}

	public void setIonRowIndex(int ionRowIndex) {

		this.ionRowIndex = ionRowIndex;
	}

	public int getIonCompSequence() {

		return ionCompSequence;
	}

	public void setIonCompSequence(int ionCompSequence) {

		this.ionCompSequence = ionCompSequence;
	}

	public boolean isMark() {

		return mark;
	}

	public void setMark(boolean mark) {

		this.mark = mark;
	}
}
