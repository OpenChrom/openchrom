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

	boolean addIonMeasurement(IIonMeasurement ionMeasurement);

	boolean addIonMeasurement(double mz, float signal);

	void updateIons();

	boolean scale();

	boolean unscale();

	List<IIonMeasurement> getIonMeasurements();

	/**
	 * This method may return null.
	 * 
	 * @param scanIndex
	 * @return {@link IIonMeasurement}
	 */
	IIonMeasurement getIonMeasurement(int scanIndex);

	double getSourcePressure();

	void setSourcePressure(double sourcePressure);

	String getSourcePressureUnits();

	void setSourcePressureUnits(String sourcePressureUnits);

	String getSignalUnits();

	void setSignalUnits(String signalUnits);

	String getScanName();

	void setScanName(String scanName);

	String getTimeStamp();

	void setTimeStamp(String timeStamp);

	String getInstrumentName();

	void setInstrumentName(String instrumentName);

	double getEtimes();

	double getEenergy();

	double getIenergy();

	void setEtimes(double etimes);

	void setEenergy(double eenergy);

	void setIenergy(double ienergy);

	void updateSignalLimits();

	ICalibratedVendorMassSpectrum makeNoisyCopy(long seed, double relativeError) throws CloneNotSupportedException;
}
