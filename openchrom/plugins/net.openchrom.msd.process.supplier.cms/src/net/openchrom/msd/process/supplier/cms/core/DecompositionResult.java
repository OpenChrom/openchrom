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

import net.openchrom.msd.converter.supplier.cms.model.CalibratedVendorMassSpectrum;
import net.openchrom.msd.converter.supplier.cms.model.ICalibratedVendorMassSpectrum;

public class DecompositionResult {
	private ICalibratedVendorMassSpectrum residualSpectrum;
	private ArrayList<ICalibratedVendorMassSpectrum> libraryComponents; // only need some of the info in CalibratedVendorMassSpectrum, but take it all for now
	private ArrayList<Double> xComp; // for library component i = (partial pressure of i in decomposed scan) / (total pressure of decomposed scan)
	private double sumOfSquaresError;
	private double weightedSumOfSquaresError;
	
	public DecompositionResult(double ssErr, double wssErr) {
		libraryComponents = new ArrayList<ICalibratedVendorMassSpectrum>();
		xComp = new ArrayList<Double>();
		sumOfSquaresError = ssErr;
		weightedSumOfSquaresError = wssErr;
	}
	
	public void setResidualSpectrum(ICalibratedVendorMassSpectrum spec) {
		if (null != spec) residualSpectrum = spec;
	}
	
	public void addComponent(double x, ICalibratedVendorMassSpectrum spec) {
		if ((null != xComp) && (null != libraryComponents)) {
			xComp.add(x);
			libraryComponents.add(spec);
		}
	}
}
