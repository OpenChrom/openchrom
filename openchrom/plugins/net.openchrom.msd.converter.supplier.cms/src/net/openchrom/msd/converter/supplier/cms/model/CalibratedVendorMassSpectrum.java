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
import org.eclipse.chemclipse.msd.model.core.AbstractRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.implementation.Ion;

public class CalibratedVendorMassSpectrum extends AbstractRegularLibraryMassSpectrum implements ICalibratedVendorMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 788113431263082687L;
	private static final Logger logger = Logger.getLogger(CalibratedVendorMassSpectrum.class);
	//
	private List<IIonMeasurement> ionMeasurements;
	private double minSignal = 0;
	private double minAbsSignal = 0;
	private double maxSignal = 0;
	private float scaleOffset = 0;
	private float scaleSlope = 0;
	//
	private double sourcePressure = 0.0;
	private String sourcePressureUnits = "";
	private String signalUnits = "";
	private String scanName = "";
	private String timeStamp = "";
	private String instrumentName = "";
	private double eEnergyV = 0;
	private double iEnergyV = 0;
	private double eTimeS = 0;

	public CalibratedVendorMassSpectrum() {
		/*
		 * Initialize the values.
		 */
		ionMeasurements = new ArrayList<IIonMeasurement>(10);
	}

	@Override
	public List<IIon> getIons() {

		/*
		 * Initialization is a bit more elegant now.
		 */
		if(getNumberOfIons() == 0) {
			updateIons();
		}
		return super.getIons();
	}

	@Override
	public void updateIons() {

		/*
		 * Remove the currently used ions.
		 */
		removeAllIons();
		/*
		 * Set the scaled ions.
		 */
		for(IIonMeasurement ionMeasurement : getIonMeasurements()) {
			float signal = ionMeasurement.getSignal();
			double mz = ionMeasurement.getMZ();
			if(signal > 0.0f && mz > 0.0d) {
				try {
					addIon(new Ion(mz, signal));
				} catch(Exception e) {
					logger.warn(e);
				}
			}
		}
	}

	@Override
	public boolean scale() {

		/*
		 * Make suitable for log scale plotting
		 */
		if(0.0 != scaleSlope) {
			boolean isUnscalable = unscale();
			if(!isUnscalable) {
				return false;
			}
		}
		/*
		 * Calculate the limits on demand.
		 */
		if(!isMinMaxSignalCalculated()) {
			updateSignalLimits();
		}
		//
		scaleOffset = (float)(minAbsSignal - minSignal);
		scaleSlope = 1.0f / (float)(maxSignal + scaleOffset);
		/*
		 * Can't have zero slope
		 */
		if(0.0 == scaleSlope) {
			return false;
		} else {
			/*
			 * Adjust the ion measurements.
			 */
			for(IIonMeasurement ionMeasurement : ionMeasurements) {
				ionMeasurement.setSignal(scaleSlope * (ionMeasurement.getSignal() + scaleOffset));
			}
			/*
			 * Update the ions and finish the method.
			 */
			updateIons();
			return true;
		}
	}

	@Override
	public boolean unscale() {

		if(0.0 == scaleSlope) {
			return false;
		}
		/*
		 * Adjust the ion measurements.
		 */
		for(IIonMeasurement ionMeasurement : ionMeasurements) {
			ionMeasurement.setSignal((ionMeasurement.getSignal() / scaleSlope) - scaleOffset);
		}
		/*
		 * Update the ions and finish the method.
		 */
		updateIons();
		scaleSlope = 0;
		return true;
	}

	@Override
	public List<IIonMeasurement> getIonMeasurements() {

		if(!isMinMaxSignalCalculated()) {
			updateSignalLimits();
		}
		return ionMeasurements;
	}

	@Override
	public IIonMeasurement getIonMeasurement(int scanIndex) {

		if(!isMinMaxSignalCalculated()) {
			updateSignalLimits();
		}
		//
		if(scanIndex > 0 && scanIndex < ionMeasurements.size()) {
			return ionMeasurements.get(scanIndex);
		} else {
			return null;
		}
	}

	@Override
	public void updateSignalLimits() {

		for(IIonMeasurement peak : ionMeasurements) {
			float signal = peak.getSignal();
			if(maxSignal == 0 && minSignal == 0) {
				maxSignal = minSignal = minAbsSignal = signal;
			} else {
				/*
				 * Validations
				 */
				if(maxSignal < signal) {
					maxSignal = signal;
				}
				//
				if(minSignal > signal) {
					minSignal = signal;
				}
				//
				if(signal != 0) {
					signal = java.lang.StrictMath.abs(signal);
					if(minAbsSignal == 0 || minAbsSignal > signal) {
						minAbsSignal = signal;
					}
				}
			}
		}
	}

	@Override
	public boolean addIonMeasurement(double mz, float signal) {

		return addIonMeasurement(new IonMeasurement(mz, signal));
	}

	@Override
	public boolean addIonMeasurement(IIonMeasurement ionMeasurement) {

		if(ionMeasurement == null || ionMeasurement.getMZ() <= 0) {
			return false;
		}
		//
		ionMeasurements.add(ionMeasurement);
		return true;
	}

	@Override
	public double getSourcePressure() {

		return sourcePressure;
	}

	@Override
	public void setSourcePressure(double sourcePressure) {

		this.sourcePressure = sourcePressure;
	}

	@Override
	public String getSourcePressureUnits() {

		return sourcePressureUnits;
	}

	@Override
	public void setSourcePressureUnits(String spunits) {

		sourcePressureUnits = spunits;
	}

	@Override
	public String getSignalUnits() {

		return signalUnits;
	}

	@Override
	public void setSignalUnits(String sigunits) {

		signalUnits = sigunits;
	}

	@Override
	public String getScanName() {

		return scanName;
	}

	@Override
	public void setScanName(String scanName) {

		if(null != scanName)
			this.scanName = scanName;
	}

	@Override
	public String getTimeStamp() {

		return timeStamp;
	}

	@Override
	public void setTimeStamp(String tstamp) {

		timeStamp = tstamp;
	}

	@Override
	public String getInstrumentName() {

		return instrumentName;
	}

	@Override
	public void setInstrumentName(String iname) {

		instrumentName = iname;
	}

	@Override
	public double getEtimes() {

		return eTimeS;
	}

	@Override
	public double getEenergy() {

		return eEnergyV;
	}

	@Override
	public double getIenergy() {

		return iEnergyV;
	}

	@Override
	public void setEtimes(double etimes) {

		eTimeS = etimes;
	}

	@Override
	public void setEenergy(double eenergy) {

		eEnergyV = eenergy;
	}

	@Override
	public void setIenergy(double ienergy) {

		iEnergyV = ienergy;
	}

	@Override
	public ICalibratedVendorMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		CalibratedVendorMassSpectrum vendorMassSpectrum = (CalibratedVendorMassSpectrum)super.clone();
		vendorMassSpectrum.resetMinMaxSignal();
		//
		for(IIonMeasurement ionMeasurement : this.getIonMeasurements()) {
			float signal = ionMeasurement.getSignal();
			vendorMassSpectrum.addIonMeasurement(ionMeasurement.getMZ(), signal);
		}
		vendorMassSpectrum.updateIons();
		return vendorMassSpectrum;
	}

	@Override
	public ICalibratedVendorMassSpectrum makeNoisyCopy(long seed, double relativeError) throws CloneNotSupportedException {

		Random random = new Random(seed);
		CalibratedVendorMassSpectrum vendorMassSpectrum = (CalibratedVendorMassSpectrum)super.clone();
		vendorMassSpectrum.resetMinMaxSignal();
		//
		for(IIonMeasurement ionMeasurement : this.getIonMeasurements()) {
			float signal = ionMeasurement.getSignal();
			float noise = (float)(relativeError * signal * random.nextGaussian());
			vendorMassSpectrum.addIonMeasurement(ionMeasurement.getMZ(), signal + noise);
		}
		vendorMassSpectrum.updateIons();
		return vendorMassSpectrum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}

	private boolean isMinMaxSignalCalculated() {

		if(minSignal == 0 && minAbsSignal == 0 && maxSignal == 0) {
			return false;
		} else {
			return true;
		}
	}

	private void resetMinMaxSignal() {

		minSignal = 0;
		minAbsSignal = 0;
		maxSignal = 0;
	}
}
