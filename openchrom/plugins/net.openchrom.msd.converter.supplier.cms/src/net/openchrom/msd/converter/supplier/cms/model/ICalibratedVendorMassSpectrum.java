/*******************************************************************************
 * Copyright (c) 2016 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.model;

import java.util.List;

import org.eclipse.chemclipse.msd.model.core.AbstractScanMSD;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;

import net.openchrom.msd.converter.supplier.cms.model.CMSion;

public interface ICalibratedVendorMassSpectrum extends IRegularLibraryMassSpectrum, IScanMSD {
	/*
	 * TODO WALTER
	 * Add the CMS specific fields here.
	 */
	public List<MsdPeakMeasurement> getPeaks();
	public boolean addPeak(MsdPeakMeasurement peak);
	public boolean addPeak(double mz, float signal);
	public boolean scale();
	public boolean unscale();
	//public int getNumberOfIons();
	public double getSourcep();
	public String getSPunits();
	public String getSigunits();
	public String getScanName();
	public void setSourcep(double sourcep);
	public void setSPunits(String spunits);
	public void setSigunits(String sigunits);
	public void setScanName(String name);
	public boolean updateIons();
	public ICalibratedVendorMassSpectrum makeNoiseyCopy(long l, double relativeError) throws CloneNotSupportedException;
	
	/**
	 * Returns the source.
	 * 
	 * @return String
	 */
	String getSource();

	/**
	 * Sets the source.
	 * 
	 * @param source
	 */
	void setSource(String source);
	//String getName();
}
