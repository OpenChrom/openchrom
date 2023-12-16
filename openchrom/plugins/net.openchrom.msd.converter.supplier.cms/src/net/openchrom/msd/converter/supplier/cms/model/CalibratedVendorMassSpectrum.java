/*******************************************************************************
 * Copyright (c) 2016, 2023 Walter Whitlock, Philip Wenig.
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
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.eclipse.chemclipse.model.columns.SeparationColumnType;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.msd.model.core.AbstractScanMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IIonBounds;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.support.IMarkedIons;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;

// public class CalibratedVendorMassSpectrum implements IRegularLibraryMassSpectrum {
public class CalibratedVendorMassSpectrum extends CalibratedVendorLibraryMassSpectrum implements ICalibratedVendorMassSpectrum {

	/**
	 * Renew the serialVersionUID any time you have changed some fields or
	 * methods.
	 */
	private static final long serialVersionUID = 788113431263082687L;
	// private static final Logger logger = Logger.getLogger(CalibratedVendorMassSpectrum.class);
	//
	private List<IIonMeasurement> ionMeasurements;
	private String scanName = "";
	private float minSignal = 0; // ignored in compareTo()
	private float minAbsSignal = 0; // ignored in compareTo()
	private float maxSignal = 0; // ignored in compareTo()
	private boolean minMaxSignalIsValid = false; // ignored in compareTo()
	private float sumSignal = 0f; // ignored in compareTo()
	private boolean sumSignalIsValid = false; // ignored in compareTo()
	private float scaleOffset = 0; // ignored in compareTo()
	private float scaleSlope = 0; // ignored in compareTo()
	private double baseMZ = 0; // ignored in compareTo()
	private float signalOffset = 0f; // ignored in compareTo()

	public CalibratedVendorMassSpectrum() {

		/*
		 * Initialize the values.
		 */
		ionMeasurements = new ArrayList<IIonMeasurement>(100);
	}

	/**
	 * sorts the measurement list into increasing MZ order
	 */
	// @Override
	// public void sortMZ() {
	//
	// Collections.sort(this.getIonMeasurements()); // uses IonMeasurement.compareTo(IIonMeasurement)
	// }
	@Override
	public float getSignalOffset() {

		return signalOffset;
	}

	@Override
	public void setSignalOffset(float signalOffset) {

		this.signalOffset = signalOffset;
	}

	/**
	 * calculates the zero signalOffset by taking median value of signals found at m/z 5, 6, and 7
	 * returns true if signalOffset was calculated
	 */
	@Override
	public boolean calculateSignalOffset() {

		float sig1 = 0f;
		float sig2 = 0f;
		float sig3 = 0f;
		float temp;
		int count = 0;
		double massTol = 0.2;
		for(IIonMeasurement ion : ionMeasurements) {
			if(ion.massLess(5, massTol)) {
				continue;
			}
			if(ion.massEqual(5, massTol)) {
				sig1 = ion.getSignal();
				count++;
			}
			if(ion.massLess(6, massTol)) {
				continue;
			}
			if(ion.massEqual(6, massTol)) {
				sig2 = ion.getSignal();
				count++;
			}
			if(ion.massLess(7, massTol)) {
				continue;
			}
			if(ion.massEqual(7, massTol)) {
				sig3 = ion.getSignal();
				count++;
			}
			break;
		}
		if(3 != count) {
			return false;
		}
		if(sig1 > sig2) {
			temp = sig1;
			sig1 = sig2;
			sig2 = temp;
		}
		if(sig2 > sig3) {
			temp = sig2;
			sig2 = sig3;
			sig3 = temp;
		}
		if(sig1 > sig2) {
			temp = sig1;
			sig1 = sig2;
			sig2 = temp;
		}
		signalOffset = sig2;
		return true;
	}

	/**
	 * subtracts signalOffset from the signal for each ionMeasurement
	 */
	@Override
	public void subtractSignalOffset() {

		if(0f != signalOffset) {
			for(IIonMeasurement ion : ionMeasurements) {
				ion.setSignal(ion.getSignal() - signalOffset);
			}
			minMaxSignalIsValid = false;
			sumSignalIsValid = false;
		}
	}

	/**
	 * subtracts the offsetValue from the signal for each ionMeasurement
	 */
	@Override
	public void subtractSignalOffset(float offsetValue) {

		if(0f != offsetValue) {
			signalOffset = offsetValue;
			subtractSignalOffset();
		}
	}

	/**
	 * removes the offset for each ionMeasurement and sets signalOffset to 0f;
	 */
	@Override
	public void resetSignalOffset() {

		if(0f != signalOffset) {
			for(IIonMeasurement ion : ionMeasurements) {
				ion.setSignal(signalOffset + ion.getSignal());
			}
			signalOffset = 0f;
			minMaxSignalIsValid = false;
			sumSignalIsValid = false;
		}
	}

	@Override
	/**
	 * calculates 2-norm of signals
	 */
	public double get2Norm() {

		if(!valid2Norm) {
			value2Norm = 0;
			for(IIonMeasurement ion : this.getIonMeasurements()) {
				value2Norm += ion.getSignal() * ion.getSignal();
			}
			value2Norm = java.lang.StrictMath.sqrt(value2Norm);
			valid2Norm = true;
		}
		return value2Norm;
	}

	@Override
	public float getMinAbsSignal() {

		if(!minMaxSignalIsValid) {
			updateSignalLimits();
		}
		return minAbsSignal;
	}

	@Override
	public int compareTo(ICalibratedVendorMassSpectrum spectrum) {

		int result;
		result = super.compareTo(spectrum);
		if(0 != result) {
			return result;
		}
		result = this.getScanName().compareTo(spectrum.getScanName());
		if(0 != result) {
			return result;
		}
		result = this.getNumberOfIons() - spectrum.getNumberOfIons();
		if(0 != result) {
			return result;
		}
		if(0 < this.getNumberOfIons()) {
			IIonMeasurement set1[] = new IIonMeasurement[this.getNumberOfIons()];
			IIonMeasurement set2[] = new IIonMeasurement[this.getNumberOfIons()];
			set1 = ((ICalibratedVendorMassSpectrum)this).getIonMeasurements().toArray(set1);
			set2 = spectrum.getIonMeasurements().toArray(set2);
			for(int i = 0; i < this.getNumberOfIons(); i++) {
				result = (int)java.lang.StrictMath.signum(set1[i].getMZ() - set2[i].getMZ());
				if(0 != result) {
					return result;
				}
				result = (int)java.lang.StrictMath.signum(set1[i].getSignal() - set2[i].getSignal());
				if(0 != result) {
					return result;
				}
			}
		}
		return 0;
	}

	@Override
	public boolean scale() {

		/*
		 * Make suitable for log scale plotting
		 */
		boolean isUnscalable = unscale();
		if(!isUnscalable) {
			return false;
		}
		/*
		 * Calculate the limits on demand.
		 */
		if(!minMaxSignalIsValid) {
			updateSignalLimits();
		}
		//
		scaleOffset = minAbsSignal - minSignal;
		scaleSlope = 1.0f / (maxSignal + scaleOffset);
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
			sumSignalIsValid = false;
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
		scaleSlope = 0;
		sumSignalIsValid = false;
		return true;
	}

	@Override
	public List<IIonMeasurement> getIonMeasurements() {

		return ionMeasurements;
	}

	/**
	 * The getIonMeasurement() method may return null.
	 *
	 * @param peakIndex
	 * @return {@link IIonMeasurement}
	 */
	@Override
	public IIonMeasurement getIonMeasurement(int peakIndex) {

		if(peakIndex >= 0 && peakIndex < ionMeasurements.size()) {
			return ionMeasurements.get(peakIndex);
		} else {
			return null;
		}
	}

	@Override
	public void resetSumSignal() {

		sumSignalIsValid = false;
	}

	@Override
	public float getSumSignal() {

		if(sumSignalIsValid) {
			return sumSignal;
		}
		sumSignal = 0;
		for(IIonMeasurement peak : ionMeasurements) {
			sumSignal += peak.getSignal();
		}
		sumSignalIsValid = true;
		return sumSignal;
	}

	@Override
	public void updateSignalLimits() {

		minSignal = 0;
		minAbsSignal = 0;
		maxSignal = 0;
		for(IIonMeasurement peak : ionMeasurements) {
			float signal = peak.getSignal();
			if(maxSignal == 0 && minSignal == 0) {
				maxSignal = minSignal = signal;
				minAbsSignal = java.lang.StrictMath.abs(signal);
			} else {
				/*
				 * Validations
				 */
				if(maxSignal < signal) {
					maxSignal = signal;
					baseMZ = peak.getMZ();
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
		minMaxSignalIsValid = true;
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
		resetSumSignal();
		resetMinMaxSignal();
		return true;
	}

	@Override
	public String getScanName() {

		return scanName;
	}

	@Override
	public void setScanName(String scanName) {

		if(null != scanName) {
			this.scanName = scanName;
		}
	}

	/**
	 * This method is used only to create a new IIonMeasurement list
	 * after cloning. Otherwise, the old list is re-used. The list has
	 * to be filled by the implementation specific ions of each
	 * implementing class.
	 */
	@Override
	public void createNewIonMeasurementList() {

		ionMeasurements = new ArrayList<IIonMeasurement>(200);
	}

	@Override
	public ICalibratedVendorMassSpectrum makeDeepCopy() throws CloneNotSupportedException {

		ICalibratedVendorMassSpectrum vendorMassSpectrum = (ICalibratedVendorMassSpectrum)super.clone();
		vendorMassSpectrum.createNewIonMeasurementList();
		//
		for(IIonMeasurement ionMeasurement : this.getIonMeasurements()) {
			float signal = ionMeasurement.getSignal();
			vendorMassSpectrum.addIonMeasurement(ionMeasurement.getMZ(), signal);
		}
		vendorMassSpectrum.resetSumSignal();
		vendorMassSpectrum.resetMinMaxSignal();
		return vendorMassSpectrum;
	}

	@Override
	public ICalibratedVendorMassSpectrum makeNoisyCopy(long seed, double relativeError) throws CloneNotSupportedException {

		Random random = new Random(seed);
		ICalibratedVendorMassSpectrum vendorMassSpectrum = (ICalibratedVendorMassSpectrum)super.clone();
		vendorMassSpectrum.createNewIonMeasurementList();
		//
		for(IIonMeasurement ionMeasurement : this.getIonMeasurements()) {
			float signal = ionMeasurement.getSignal();
			float noise = (float)(relativeError * signal * random.nextGaussian());
			vendorMassSpectrum.addIonMeasurement(ionMeasurement.getMZ(), signal + noise);
		}
		vendorMassSpectrum.resetSumSignal();
		vendorMassSpectrum.resetMinMaxSignal();
		return vendorMassSpectrum;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {

		return makeDeepCopy();
	}

	@Override
	public void resetMinMaxSignal() {

		minMaxSignalIsValid = false;
	}

	/*
	 * Override methods from class CalibratedVendorLibraryMassSpectrum that can be implemented without using Class IIon
	 */
	@Override
	public boolean hasIons() {

		return !ionMeasurements.isEmpty();
	}

	@Override
	public double getBasePeak() {

		if(hasIons()) {
			if(!minMaxSignalIsValid) {
				updateSignalLimits();
			}
			return baseMZ;
		} else {
			return 0.0f;
		}
	}

	@Override
	public float getBasePeakAbundance() {

		if(hasIons()) {
			if(!minMaxSignalIsValid) {
				updateSignalLimits();
			}
			return maxSignal;
		} else {
			return 0.0f;
		}
	}

	@Override
	public float getTotalSignal() {

		return this.getSumSignal();
	}

	@Override
	public int getNumberOfIons() {

		return ionMeasurements.size();
	}

	/*
	 * override methods from super that are not used (there are lots of them)
	 */
	@Override
	public short getMassSpectrometer() {

		return 0;
	}

	@Override
	public void setMassSpectrometer(short massSpectrometer) {

	}

	@Override
	public short getMassSpectrumType() {

		return 0;
	}

	@Override
	public String getMassSpectrumTypeDescription() {

		return null;
	}

	@Override
	public void setMassSpectrumType(short massSpectrumType) {

	}

	@Override
	public double getPrecursorIon() {

		return 0;
	}

	@Override
	public void setPrecursorIon(double precursorIon) {

	}

	@Override
	public float getTotalSignal(IMarkedIons excludedIons) {

		return 0;
	}

	@Override
	public IExtractedIonSignal getExtractedIonSignal() {

		return null;
	}

	@Override
	public IExtractedIonSignal getExtractedIonSignal(double startIon, double stopIon) {

		return null;
	}

	@Override
	public IIon getLowestIon() {

		return null;
	}

	@Override
	public IIon getLowestAbundance() {

		return null;
	}

	@Override
	public IIon getHighestIon() {

		return null;
	}

	@Override
	public IIon getHighestAbundance() {

		return null;
	}

	@Override
	public IIonBounds getIonBounds() {

		return null;
	}

	@Override
	public AbstractScanMSD addIons(List<IIon> ions, boolean addIntensities) {

		return null;
	}

	@Override
	public AbstractScanMSD addIon(IIon ion, boolean checked) {

		return null;
	}

	@Override
	public AbstractScanMSD addIon(IIon ion) {

		return null;
	}

	@Override
	public AbstractScanMSD addIon(boolean addIntensity, IIon ion) {

		return null;
	}

	@Override
	public AbstractScanMSD removeIon(IIon ion) {

		return null;
	}

	@Override
	public AbstractScanMSD removeAllIons() {

		return null;
	}

	@Override
	public AbstractScanMSD removeIons(IMarkedIons excludedIons) {

		return null;
	}

	@Override
	public AbstractScanMSD removeIon(int ion) {

		return null;
	}

	@Override
	public AbstractScanMSD removeIons(Set<Integer> ions) {

		return null;
	}

	@Override
	public IIon getIon(int ion) throws AbundanceLimitExceededException, IonLimitExceededException {

		return null;
	}

	@Override
	public IIon getIon(double ion) throws AbundanceLimitExceededException, IonLimitExceededException {

		return null;
	}

	@Override
	public IIon getIon(double ion, int precision) throws AbundanceLimitExceededException, IonLimitExceededException {

		return null;
	}

	@Override
	public void adjustIons(float percentage) {

	}

	@Override
	public IScanMSD getMassSpectrum(IMarkedIons excludedIons) {

		return null;
	}

	@Override
	public void enforceLoadScanProxy() {

	}

	@Override
	public void setOptimizedMassSpectrum(IScanMSD optimizedMassSpectrum) {

	}

	@Override
	public IScanMSD getOptimizedMassSpectrum() {

		return null;
	}

	@Override
	public IChromatogram<? extends IPeak> getParentChromatogram() {

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setParentChromatogram(IChromatogram parentChromatogram) {

	}

	@Override
	public int getRetentionTime() {

		return 0;
	}

	@Override
	public void setRetentionTime(int retentionTime) {

	}

	@Override
	public int getRetentionTimeColumn1() {

		return 0;
	}

	@Override
	public void setRetentionTimeColumn1(int retentionTimeColumn1) {

	}

	@Override
	public int getRetentionTimeColumn2() {

		return 0;
	}

	@Override
	public void setRetentionTimeColumn2(int retentionTimeColumn2) {

	}

	@Override
	public int getRelativeRetentionTime() {

		return 0;
	}

	@Override
	public void setRelativeRetentionTime(int relativeRetentionTime) {

	}

	@Override
	public float getRetentionIndex() {

		return 0;
	}

	@Override
	public void setRetentionIndex(float retentionIndex) {

	}

	@Override
	public boolean hasAdditionalRetentionIndices() {

		return false;
	}

	@Override
	public float getRetentionIndex(SeparationColumnType separationColumnType) {

		return 0;
	}

	@Override
	public Map<SeparationColumnType, Float> getRetentionIndicesTyped() {

		return null;
	}

	@Override
	public void setRetentionIndex(SeparationColumnType separationColumnType, float retentionIndex) {

	}

	@Override
	public int getTimeSegmentId() {

		return 0;
	}

	@Override
	public void setTimeSegmentId(int timeSegmentId) {

	}

	@Override
	public int getCycleNumber() {

		return 0;
	}

	@Override
	public void setCycleNumber(int cycleNumber) {

	}

	@Override
	public boolean isDirty() {

		return false;
	}

	@Override
	public void setDirty(boolean isDirty) {

	}

	@Override
	public String getIdentifier() {

		return null;
	}

	@Override
	public void setIdentifier(String identifier) {

	}

	@Override
	public void adjustTotalSignal(float totalSignal) {

	}

	// @Override
	// public <T> T getAdapter(Class<T> adapter) {
	//
	// return null;
	// }
	@Override
	public IScanMSD normalize() {

		return null;
	}

	@Override
	public IScanMSD normalize(float base) {

		return null;
	}

	@Override
	public boolean isNormalized() {

		return false;
	}

	@Override
	public float getNormalizationBase() {

		return 0;
	}

	@Override
	public List<IIon> getIons() {

		return null;
	}

	@Override
	public void setLibraryInformation(ILibraryInformation libraryInformation) {

	}
}