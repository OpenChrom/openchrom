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
package net.openchrom.msd.converter.supplier.cms.model;

import java.util.List;

public interface IMsdScanMeasurement {
	
	public List<MsdPeakMeasurement> getPeaks();
	boolean scale();
	boolean unscale();
	public boolean addPeak(MsdPeakMeasurement peak);
	public boolean addPeak(double mz, float signal);
	public int getNumberOfIons();
	public double getSourcep();
	public String getSPunits();
	public String getSigunits();
	public String getScanName();
	public String getFormula();
	public double getMolWeight();
	public String getCasNumber();
	public void setSourcep(double sourcep);
	public void setSPunits(String spunits);
	public void setSigunits(String sigunits);
	public void setScanName(String name);
	public void setFormula(String form);
	public void setMolWeight(double mw);
	public void setCasNumber(String s);
}
