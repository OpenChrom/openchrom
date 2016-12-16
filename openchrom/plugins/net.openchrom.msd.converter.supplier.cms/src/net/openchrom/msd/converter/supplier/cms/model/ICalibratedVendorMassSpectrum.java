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

import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

public interface ICalibratedVendorMassSpectrum extends IRegularLibraryMassSpectrum, IScanMSD {

	List<IMsdPeakMeasurement> getPeaks();

	boolean addPeak(IMsdPeakMeasurement peak);

	boolean addPeak(double mz, float signal);

	boolean scale();

	boolean unscale();

	IMsdPeakMeasurement getPeak(int scanPeakIndex);

	double getSourcep();

	String getSPunits();

	String getSigunits();

	String getScanName();

	String getTstamp();

	double getEtimes();

	double getEenergy();

	double getIenergy();

	String getIname(String iname);

	void setSourcep(double sourcep);

	void setSPunits(String spunits);

	void setSigunits(String sigunits);

	void setTstamp(String tstamp);

	void setEtimes(double etimes);

	void setEenergy(double eenergy);

	void setIenergy(double ienergy);

	void setIname(String iname);

	void setScanName(String name);

	void updateIons();

	void updateSignalLimits();

	ICalibratedVendorMassSpectrum makeNoisyCopy(long l, double relativeError) throws CloneNotSupportedException;

	List<IIon> getIons();

	String getSource();

	void setSource(String source);

	ICalibratedVendorMassSpectrum makeDeepCopy() throws CloneNotSupportedException;
}
