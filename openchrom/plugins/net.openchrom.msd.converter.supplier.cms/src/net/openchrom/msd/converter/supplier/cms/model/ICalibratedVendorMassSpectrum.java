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

import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

public interface ICalibratedVendorMassSpectrum extends IRegularLibraryMassSpectrum, IScanMSD {
	boolean addIonMeasurement(double mz, float signal);

	boolean addIonMeasurement(IIonMeasurement ionMeasurement);

	List<String> getComments();

	double getEenergy();

	double getEtimes();

	double getIenergy();

	String getInstrumentName();

	/**
	 * This method may return null.
	 * 
	 * @param scanIndex
	 * @return {@link IIonMeasurement}
	 */
	IIonMeasurement getIonMeasurement(int scanIndex);

	List<IIonMeasurement> getIonMeasurements();

	String getScanName();

	String getSignalUnits();

	double getSourcePressure();

	String getSourcePressureUnits();

	String getTimeStamp();

	ICalibratedVendorMassSpectrum makeDeepCopy() throws CloneNotSupportedException;

	ICalibratedVendorMassSpectrum makeNoisyCopy(long seed, double relativeError) throws CloneNotSupportedException;

	boolean scale();

	void setComments(List<String> comments);

	void setEenergy(double eenergy);

	void setEtimes(double etimes);

	void setIenergy(double ienergy);

	void setInstrumentName(String instrumentName);

	void setScanName(String scanName);

	void setSignalUnits(String signalUnits);

	void setSourcePressure(double sourcePressure);

	void setSourcePressureUnits(String sourcePressureUnits);

	void setTimeStamp(String timeStamp);

	boolean unscale();

	void updateIons();

	void updateSignalLimits();

}
