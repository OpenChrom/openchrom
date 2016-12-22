/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

import java.util.List;

public interface ICalibratedVendorMassSpectrum extends ICalibratedVendorLibraryMassSpectrum {
	
	boolean addIonMeasurement(double mz, float signal);

	boolean addIonMeasurement(IIonMeasurement ionMeasurement);
	
	void createNewIonMeasurementList();

	/**
	 * This method may return null.
	 * 
	 * @param scanIndex
	 * @return {@link IIonMeasurement}
	 */
	IIonMeasurement getIonMeasurement(int scanIndex);

	List<IIonMeasurement> getIonMeasurements();

	String getScanName();
	
	double getSumSignal();

	ICalibratedVendorMassSpectrum makeDeepCopy() throws CloneNotSupportedException;

	ICalibratedVendorLibraryMassSpectrum makeNoisyCopy(long seed, double relativeError) throws CloneNotSupportedException;
	
	void resetMinMaxSignal();
	
	void resetSumSignal();

	boolean scale();

	void setScanName(String scanName);

	boolean unscale();

	// void updateIons();

	void updateSignalLimits();

}
