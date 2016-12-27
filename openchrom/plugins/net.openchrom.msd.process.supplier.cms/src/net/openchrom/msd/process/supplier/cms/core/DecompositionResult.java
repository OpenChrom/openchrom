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

import java.util.ArrayList;

import org.eclipse.chemclipse.logging.core.Logger;

import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorLibraryMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class DecompositionResult {

	private static final Logger logger = Logger.getLogger(MassSpectraDecomposition.class);
	private ICalibratedVendorMassSpectrum residualSpectrum;
	private ArrayList<ICalibratedVendorLibraryMassSpectrum> libraryComponents; // only need some of the info in CalibratedVendorLibraryMassSpectrum, but take it all for now
	private ArrayList<Double> xComp; // for library component i = fraction of library ion current spectrum which was found in scan ion current spectrum
	private double sumOfSquaresError;
	private double weightedSumOfSquaresError;

	public DecompositionResult(double ssErr, double wssErr) {
		sumOfSquaresError = ssErr;
		weightedSumOfSquaresError = wssErr;
		libraryComponents = new ArrayList<ICalibratedVendorLibraryMassSpectrum>();
		xComp = new ArrayList<Double>();
	}

	public void setResidualSpectrum(ICalibratedVendorMassSpectrum spec) {

		if(null != spec)
			residualSpectrum = spec;
	}

	public void addComponent(double x, ICalibratedVendorLibraryMassSpectrum iCalibratedVendorLibraryMassSpectrum) {

		if((null != xComp) && (null != libraryComponents)) {
			xComp.add(x);
			libraryComponents.add(iCalibratedVendorLibraryMassSpectrum);
		}
	}

	public double getSumOfSquaresError() {

		return sumOfSquaresError;
	}

	public double getWeightedSumOfSquaresError() {

		return weightedSumOfSquaresError;
	}
}
