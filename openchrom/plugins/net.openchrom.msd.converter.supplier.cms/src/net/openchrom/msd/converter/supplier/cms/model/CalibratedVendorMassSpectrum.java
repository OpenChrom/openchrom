/*******************************************************************************
 * Copyright (c) 2016, 2017 Walter Whitlock, Philip Wenig.
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

import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.RetentionIndexType;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.msd.model.core.AbstractRegularMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.AbstractScanMSD;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IIonBounds;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.identifier.massspectrum.IMassSpectrumTarget;
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
	private static final Logger logger = Logger.getLogger(CalibratedVendorMassSpectrum.class);
	//
	private List<IIonMeasurement> ionMeasurements;
	private String scanName = "";
	private double minSignal = 0;
	private double minAbsSignal = 0;
	private double maxSignal = 0;
	private boolean sumSignalCalculated = false;
	private boolean minMaxSignalCalculated = false;
	private double sumSignal = 0;
	private float scaleOffset = 0;
	private float scaleSlope = 0;

	public CalibratedVendorMassSpectrum() {
		/*
		 * Initialize the values.
		 */
		ionMeasurements = new ArrayList<IIonMeasurement>(100);
	}

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
		if(!minMaxSignalCalculated) {
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
			sumSignalCalculated = false;
			return true;
		}
	}

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
		sumSignalCalculated = false;
		return true;
	}

	public List<IIonMeasurement> getIonMeasurements() {

		return ionMeasurements;
	}

	/**
	 * The getIonMeasurement() method may return null.
	 * 
	 * @param peakIndex
	 * @return {@link IIonMeasurement}
	 */
	public IIonMeasurement getIonMeasurement(int peakIndex) {

		if(peakIndex >= 0 && peakIndex < ionMeasurements.size()) {
			return ionMeasurements.get(peakIndex);
		} else {
			return null;
		}
	}

	public void resetSumSignal() {

		sumSignalCalculated = false;
	}

	public double getSumSignal() {

		if(!sumSignalCalculated) {
			sumSignal = 0;
			for(IIonMeasurement peak : ionMeasurements) {
				sumSignal += peak.getSignal();
			}
			sumSignalCalculated = true;
		}
		return sumSignal;
	}

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
		minMaxSignalCalculated = true;
	}

	public boolean addIonMeasurement(double mz, float signal) {

		return addIonMeasurement(new IonMeasurement(mz, signal));
	}

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

	public String getScanName() {

		return scanName;
	}

	public void setScanName(String scanName) {

		if(null != scanName)
			this.scanName = scanName;
	}

	/**
	 * This method is used only to create a new IIonMeasurement list
	 * after cloning. Otherwise, the old list is re-used. The list has
	 * to be filled by the implementation specific ions of each
	 * implementing class.
	 */
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

	public void resetMinMaxSignal() {

		minMaxSignalCalculated = false;
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
	public AbstractRegularMassSpectrum setPrecursorIon(double precursorIon) {

		return null;
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
	public double getBasePeak() {

		return 0;
	}

	@Override
	public float getBasePeakAbundance() {

		return 0;
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
	public boolean hasIons() {

		return false;
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
	public IChromatogram getParentChromatogram() {

		return null;
	}

	@Override
	public void setParentChromatogram(IChromatogram parentChromatogram) {

	}

	@Override
	public int getScanNumber() {

		return 0;
	}

	@Override
	public void setScanNumber(int scanNumber) {

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
	public float getRetentionIndex(RetentionIndexType retentionIndexType) {

		return 0;
	}

	@Override
	public Map<RetentionIndexType, Float> getRetentionIndicesTyped() {

		return null;
	}

	@Override
	public void setRetentionIndex(RetentionIndexType retentionIndexType, float retentionIndex) {

	}

	@Override
	public float getTotalSignal() {

		return 0;
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
	public void addTarget(IMassSpectrumTarget massSpectrumTarget) {

	}

	@Override
	public void removeTarget(IMassSpectrumTarget massSpectrumTarget) {

	}

	@Override
	public void removeTargets(List<IMassSpectrumTarget> targetsToDelete) {

	}

	@Override
	public void removeAllTargets() {

	}

	@Override
	public List<IMassSpectrumTarget> getTargets() {

		return null;
	}

	@Override
	public List<IIon> getIons() {

		return null;
	}

	@Override
	public int getNumberOfIons() {

		return 0;
	}

	// @Override
	// public ILibraryInformation getLibraryInformation() {
	//
	// return null;
	// }
	@Override
	public void setLibraryInformation(ILibraryInformation libraryInformation) {

	}
}
