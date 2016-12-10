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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.msd.model.core.AbstractRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;

public class CalibratedVendorMassSpectrum extends AbstractRegularLibraryMassSpectrum implements ICalibratedVendorMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 788113431263082687L;
	private static final Logger logger = Logger.getLogger(CalibratedVendorMassSpectrum.class);
	
	private double maxSignal = 0;
	private double minSignal = 0;
	private double minAbsSignal = 0;
	private String source = "";
	double sourceP = 0.0;
	private String sPunits = "";
	private String sigUnits = "";
	private String scanName = "";
	private float scaleOffset=0, scaleSlope=0;
	private List<MsdPeakMeasurement> peaksList;
	
	public CalibratedVendorMassSpectrum() {
		peaksList = new ArrayList<MsdPeakMeasurement>(200);
	}
	
	public boolean updateIons() {
		this.removeAllIons();
		for (MsdPeakMeasurement peak : getPeaks()) {
			try {
				this.addIon(new Ion(peak.getMZ(), peak.getSignal()));
			}

			catch(AbundanceLimitExceededException e) {
				System.out.println("CalibratedVendorMassSpectrum.updateRegularLibraryMassSpectrum(): " + e); 
			}
			catch(IonLimitExceededException e) {
				System.out.println("CalibratedVendorMassSpectrum.updateRegularLibraryMassSpectrum(): " + e); 
			}
		}
		return true;
	}
	

	private void createNewPeakList() {
		peaksList = new ArrayList<MsdPeakMeasurement>(200);
	}
	
	public ICalibratedVendorMassSpectrum makeNoisyCopy(long seed, double relativeError) throws CloneNotSupportedException {
		Random random = new Random(seed);

		CalibratedVendorMassSpectrum massSpectrum = (CalibratedVendorMassSpectrum)super.clone();
		massSpectrum.createNewPeakList();
		float signal, noise;
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for (MsdPeakMeasurement peak : this.getPeaks()) {
			signal = peak.getSignal();
			noise = (float)(relativeError * signal * random.nextGaussian());
			massSpectrum.addPeak(peak.getMZ(), signal + noise);
		}
		massSpectrum.updateIons();
		return massSpectrum;
	}
	
	public boolean scale() {
		if (0.0 != scaleSlope) return false; // must unscale() first
		scaleOffset = (float)(minAbsSignal - minSignal);
		scaleSlope = 1/(float)(maxSignal + scaleOffset);
		if (0.0 == scaleSlope) return false; // can't have zero slope
		for (MsdPeakMeasurement peak : getPeaks()) {
			peak.setSignal(scaleSlope*(peak.getSignal() + scaleOffset) );
		}
		for (IIon ion : getIons()) {
			try {
				ion.setAbundance(scaleSlope*(ion.getAbundance() + scaleOffset) );
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			}
		}
		return true;
	}
	
	public boolean unscale() {
		if (0.0 == scaleSlope) return false;
		for (MsdPeakMeasurement peak : getPeaks()) {
			peak.setSignal((peak.getSignal() / scaleSlope) - scaleOffset);
		}
		for (IIon ion : getIons()) {
			try {
				ion.setAbundance((ion.getAbundance() / scaleSlope) - scaleOffset);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			}
		}
		scaleSlope = 0;
		return true;
	}
	
	public List<MsdPeakMeasurement> getPeaks() {
		return peaksList;
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
		return sPunits;
	}
	
	public String getSigunits() {
		return sigUnits;
	}
	
	public void setSourcep(double sourcep) {
		sourceP = sourcep;
	}
	public void setSPunits(String spunits) {
		sPunits = spunits;
	}
	public void setSigunits(String sigunits) {
		sigUnits = sigunits;
	}
	public String getScanName() {
		return scanName;
	}
	public void setScanName(String name) {
		if (null != name) scanName = name;
	}

	// -------------------------------------------IAmdisMassSpectrum
	@Override
	public String getSource() {

		return source;
	}

	@Override
	public void setSource(String source) {

		if(source != null) {
			this.source = source;
		}
	}

	// -------------------------------------------IAmdisMassSpectrum
	// -------------------------------IMassSpectrumCloneable
	/**
	 * Keep in mind, it is a covariant return.<br/>
	 * IMassSpectrum is needed. IAmdisMassSpectrum is a subtype of
	 * ILibraryMassSpectrum is a subtype of IMassSpectrum.
	 */
	@Override
	public ICalibratedVendorMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		CalibratedVendorMassSpectrum massSpectrum = (CalibratedVendorMassSpectrum)super.clone();
		massSpectrum.createNewPeakList();
		IIon amdisIon;
		/*
		 * The instance variables have been copied by super.clone();.<br/> The
		 * ions in the ion list need not to be removed via
		 * removeAllIons as the method super.clone() has created a new
		 * list.<br/> It is necessary to fill the list again, as the abstract
		 * super class does not know each available type of ion.<br/>
		 * Make a deep copy of all ions.
		 */
		for(IIon ion : getIons()) {
			try {
				amdisIon = new Ion(ion.getIon(), ion.getAbundance());
				massSpectrum.addIon(amdisIon);
			} catch(AbundanceLimitExceededException e) {
				logger.warn(e);
			} catch(IonLimitExceededException e) {
				logger.warn(e);
			}
		}
		for (MsdPeakMeasurement peak : getPeaks()) {
			massSpectrum.addPeak(peak);
		}
		return massSpectrum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}
	// -------------------------------IMassSpectrumCloneable
}
