/*******************************************************************************
 * Copyright (c) 2016, 2017 Walter Whitlock, Philip Wenig.
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

	int compareTo(ICalibratedVendorMassSpectrum spectrum);

	boolean addIonMeasurement(double mz, float signal);

	boolean addIonMeasurement(IIonMeasurement ionMeasurement);

	boolean calculateSignalOffset();

	void createNewIonMeasurementList();

	/**
	 * This method may return null.
	 *
	 * @param scanIndex
	 * @return {@link IIonMeasurement}
	 */
	IIonMeasurement getIonMeasurement(int scanIndex);

	List<IIonMeasurement> getIonMeasurements();

	float getMinAbsSignal();

	String getScanName();

	float getSignalOffset();

	float getSumSignal();

	@Override
	ICalibratedVendorMassSpectrum makeDeepCopy() throws CloneNotSupportedException;

	ICalibratedVendorLibraryMassSpectrum makeNoisyCopy(long seed, double relativeError) throws CloneNotSupportedException;

	void resetMinMaxSignal();

	void resetSignalOffset();

	void resetSumSignal();

	boolean scale();

	void setScanName(String scanName);

	void setSignalOffset(float signalOffset);

	void subtractSignalOffset();

	void subtractSignalOffset(float offsetValue);

	boolean unscale();

	// void updateIons();
	void updateSignalLimits();
}
