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

import java.util.ArrayList;
import java.util.List;

public class MsdScanMeasurement implements IMsdScanMeasurement {
	
	private double maxSignal = 0;
	private double minSignal = 0;
	private double minAbsSignal = 0;
	double sourceP = 0.0;
	private String SPunits = "";
	private String SigUnits = "";
	private float scaleOffset=0, scaleSlope=0;
	private List<MsdPeakMeasurement> peaksList;
	private String scanName = "";
	private String formula = "";
	private String casnumber = "";
	private double mweight = 0.0;
	
	public MsdScanMeasurement() {
		peaksList = new ArrayList<MsdPeakMeasurement>();
	}

	public MsdScanMeasurement makeDeepCopy() {
		return null;
	}

	public int getNumberOfIons() {
		if (null != peaksList) return peaksList.size();
		else return 0;
	}
	
	public String getCasNumber() {
		return casnumber;
	}
	public String getFormula() {
		return formula;
	}
	public double getMolWeight() {
		return mweight;
	}
	public void setCasNumber(String s) {
		if (null != s) casnumber = s;
	}
	public void setFormula(String form) {
		if (null != form) formula = form;
	}
	public void setMolWeight(double mw) {
		mweight = mw;
	}
	
	public List<MsdPeakMeasurement> getPeaks() {
		return peaksList;
	}

	public boolean scale() {
		if (0.0 != scaleSlope) return false; // must unscale() first
		scaleOffset = (float)(minAbsSignal - minSignal);
		scaleSlope = 1/(float)(maxSignal + scaleOffset);
		if (0.0 == scaleSlope) return false; // can't have zero slope
		for (MsdPeakMeasurement peak : getPeaks()) {
			peak.setSignal(scaleSlope*(peak.getSignal() + scaleOffset) );
		}
		return true;
	}
	
	public boolean unscale() {
		if (0.0 == scaleSlope) return false;
		for (MsdPeakMeasurement peak : getPeaks()) {
			peak.setSignal((peak.getSignal() / scaleSlope) - scaleOffset);
		}
		scaleSlope = 0;
		return true;
	}
	
	public boolean addPeak(MsdPeakMeasurement peak) {
		if(peak == null) {
			System.out.println("MsdScanMeasurement addPeak(peak): peak must be not null.");
			return false;
		}
		if (0.0 >= peak.getMZ()) {
			System.out.println("MsdScanMeasurement addPeak(peak): peak.getMZ() must be > 0.");
			return false;
		}
		float signal = peak.getSignal();
		if ((0.0 == maxSignal) && (0.0 == minSignal)) {
			maxSignal = minSignal = minAbsSignal = signal;
		}
		else {
			if (maxSignal < signal) maxSignal = signal;
			else if (minSignal > signal) minSignal = signal;
			if (0.0 != signal) {
				signal = java.lang.StrictMath.abs(signal);
				if ((0.0 == minAbsSignal) || (minAbsSignal > signal))
					minAbsSignal = signal;
			}
		}
		peaksList.add(peak);
		return true;
	}
	
	public boolean addPeak(double mz, float signal) {
		MsdPeakMeasurement peak = new MsdPeakMeasurement(mz, signal);
		return addPeak(peak);
	}

	public double getSourcep() {
		return sourceP;
	}
	
	public String getSPunits() {
		return SPunits;
	}
	
	public String getSigunits() {
		return SigUnits;
	}

	public String getScanName() {
		return scanName;
	}
	
	public void setSourcep(double sourcep) {
		sourceP = sourcep;
	}
	public void setSPunits(String spunits) {
		if (null != spunits) SPunits = spunits;
	}
	public void setSigunits(String sigunits) {
		if (null != sigunits) SigUnits = sigunits;
	}
	
	public void setScanName(String name) {
		if (null != name) scanName = name;
	}
	

}
