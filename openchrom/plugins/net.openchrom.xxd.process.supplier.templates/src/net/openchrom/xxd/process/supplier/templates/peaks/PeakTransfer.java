/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.peaks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.analysis.function.Gaussian;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.core.IPeakDetectorCSD;
import org.eclipse.chemclipse.chromatogram.csd.peak.detector.settings.IPeakDetectorSettingsCSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.core.IPeakDetectorMSD;
import org.eclipse.chemclipse.chromatogram.msd.peak.detector.settings.IPeakDetectorSettingsMSD;
import org.eclipse.chemclipse.chromatogram.peak.detector.core.AbstractPeakDetector;
import org.eclipse.chemclipse.chromatogram.peak.detector.settings.IPeakDetectorSettings;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.csd.model.core.IChromatogramPeakCSD;
import org.eclipse.chemclipse.csd.model.core.IPeakModelCSD;
import org.eclipse.chemclipse.csd.model.core.IScanCSD;
import org.eclipse.chemclipse.csd.model.core.selection.IChromatogramSelectionCSD;
import org.eclipse.chemclipse.csd.model.implementation.ChromatogramPeakCSD;
import org.eclipse.chemclipse.csd.model.implementation.PeakModelCSD;
import org.eclipse.chemclipse.csd.model.implementation.ScanCSD;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakIntensityValues;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.LibraryInformation;
import org.eclipse.chemclipse.model.implementation.IdentificationTarget;
import org.eclipse.chemclipse.model.implementation.PeakIntensityValues;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.numeric.core.Point;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakTransferSettings;
import net.openchrom.xxd.process.supplier.templates.support.PeakSupport;

@SuppressWarnings("rawtypes")
public class PeakTransfer extends AbstractPeakDetector implements IPeakDetectorMSD, IPeakDetectorCSD {

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakTransferSettings settings = getSettings();
		return detect(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakTransferSettings settings = getSettings();
		return detect(chromatogramSelection, settings, monitor);
	}

	private PeakTransferSettings getSettings() {

		PeakTransferSettings settings = new PeakTransferSettings();
		//
		settings.setUseIdentifiedPeaksOnly(PreferenceSupplier.isTransferUseIdentifiedPeaksOnly());
		settings.setUseBestTargetOnly(PreferenceSupplier.isTransferUseBestTargetOnly());
		settings.setDeltaRetentionTimeLeft(PreferenceSupplier.getTransferRetentionTimeMillisecondsLeft());
		settings.setDeltaRetentionTimeRight(PreferenceSupplier.getTransferRetentionTimeMillisecondsRight());
		settings.setOffsetRetentionTimePeakMaximum(PreferenceSupplier.getTransferOffsetRetentionTimePeakMaximum());
		settings.setAdjustPeakHeight(PreferenceSupplier.isTransferAdjustPeakHeight());
		settings.setCreateModelPeak(PreferenceSupplier.isTransferCreateModelPeak());
		settings.setPeakOverlapCoverage(PreferenceSupplier.getTransferPeakOverlapCoverage());
		settings.setOptimizeRange(PreferenceSupplier.isTransferOptimizeRange());
		settings.setCheckPurity(PreferenceSupplier.isTransferCheckPurity());
		settings.setNumberTraces(PreferenceSupplier.getTransferNumberTraces());
		//
		return settings;
	}

	@SuppressWarnings({"unchecked"})
	private IProcessingInfo applyDetector(IChromatogramSelection<? extends IPeak, ?> chromatogramSelection, IPeakDetectorSettings settings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = super.validate(chromatogramSelection, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakTransferSettings) {
				PeakTransferSettings peakTransferSettings = (PeakTransferSettings)settings;
				transferPeaks(chromatogramSelection, peakTransferSettings);
			} else {
				processingInfo.addErrorMessage(PeakDetectorSettings.DETECTOR_DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	@SuppressWarnings("unchecked")
	private void transferPeaks(IChromatogramSelection<? extends IPeak, ?> chromatogramSelection, PeakTransferSettings peakTransferSettings) {

		IChromatogram chromatogram = chromatogramSelection.getChromatogram();
		List<IPeak> peaks = chromatogram.getPeaks(chromatogramSelection);
		List<IChromatogram> referencedChromatograms = chromatogram.getReferencedChromatograms();
		for(IChromatogram referencedChromatogram : referencedChromatograms) {
			transferPeaks(peaks, referencedChromatogram, peakTransferSettings);
		}
	}

	private void transferPeaks(List<IPeak> peaks, IChromatogram<?> chromatogramSink, PeakTransferSettings peakTransferSettings) {

		Map<Integer, List<IPeak>> peakGroups = extractPeakGroups(peaks, peakTransferSettings);
		List<Integer> groups = new ArrayList<>();
		groups.addAll(peakGroups.keySet());
		Collections.sort(groups);
		//
		for(int group : groups) {
			List<IPeak> peakz = peakGroups.get(group);
			if(peakz.size() == 1) {
				/*
				 * Single Peak
				 */
				IPeak peak = peakz.get(0);
				double percentageIntensity = getPercentageIntensity(peak);
				transfer(peak, percentageIntensity, chromatogramSink, peakTransferSettings);
			} else {
				/*
				 * Peak Group
				 */
				for(IPeak peak : peakz) {
					transfer(peak, chromatogramSink, peakTransferSettings);
				}
			}
		}
	}

	private Map<Integer, List<IPeak>> extractPeakGroups(List<IPeak> peaks, PeakTransferSettings peakTransferSettings) {

		/*
		 * Select the peak(s).
		 */
		List<IPeak> peaksSource = new ArrayList<>();
		if(peakTransferSettings.isUseIdentifiedPeaksOnly()) {
			/*
			 * Add identified peak(s).
			 */
			for(IPeak peak : peaks) {
				if(!peak.getTargets().isEmpty()) {
					if(peak.isActiveForAnalysis()) {
					}
				}
			}
		} else {
			/*
			 * Add all peak(s).
			 */
			for(IPeak peak : peaks) {
				if(peak.isActiveForAnalysis()) {
					peaksSource.add(peak);
				}
			}
		}
		/*
		 * Sort by retention time.
		 */
		Collections.sort(peaksSource, (p1, p2) -> Integer.compare(p1.getPeakModel().getRetentionTimeAtPeakMaximum(), p2.getPeakModel().getRetentionTimeAtPeakMaximum()));
		ListIterator<IPeak> listIterator = peaksSource.listIterator();
		Map<Integer, List<IPeak>> peakGroups = new HashMap<>();
		//
		double peakOverlapCoverage = peakTransferSettings.getPeakOverlapCoverage();
		int group = 1;
		while(listIterator.hasNext()) {
			IPeak peakCurrent = listIterator.next();
			IPeakModel peakModelCurrent = peakCurrent.getPeakModel();
			int stopRetentionTimeCurrent = peakModelCurrent.getStopRetentionTime();
			//
			List<IPeak> peakz = peakGroups.get(group);
			if(peakz == null) {
				peakz = new ArrayList<>();
				peakGroups.put(group, peakz);
			}
			peakz.add(peakCurrent);
			//
			if(listIterator.hasNext()) {
				/*
				 * Test if the next peak covers the current peak.
				 */
				IPeak peakNext = listIterator.next();
				IPeakModel peakModelNext = peakNext.getPeakModel();
				int startRetentionTimeNext = peakModelNext.getStartRetentionTime();
				if(stopRetentionTimeCurrent <= startRetentionTimeNext) {
					group++;
				} else {
					int stopRetentionTimeNext = peakModelNext.getStopRetentionTime();
					double width = stopRetentionTimeNext - startRetentionTimeNext + 1;
					double part = stopRetentionTimeCurrent - startRetentionTimeNext + 1;
					double coverage = 100.0d / width * part;
					if(coverage < peakOverlapCoverage) {
						group++;
					}
				}
				//
				listIterator.previous();
			}
		}
		//
		return peakGroups;
	}

	private void transfer(IPeak peakSource, double percentageIntensity, IChromatogram chromatogramSink, PeakTransferSettings peakTransferSettings) {

		int deltaRetentionTimeLeft = peakTransferSettings.getDeltaRetentionTimeLeft();
		int deltaRetentionTimeRight = peakTransferSettings.getDeltaRetentionTimeRight();
		//
		IPeakModel peakModelSource = peakSource.getPeakModel();
		int startRetentionTime = peakModelSource.getStartRetentionTime() - deltaRetentionTimeLeft;
		int stopRetentionTime = peakModelSource.getStopRetentionTime() + deltaRetentionTimeRight;
		//
		transfer(peakSource, startRetentionTime, stopRetentionTime, percentageIntensity, chromatogramSink, peakTransferSettings);
	}

	@SuppressWarnings("unchecked")
	private void transfer(IPeak peakSource, int startRetentionTime, int stopRetentionTime, double percentageIntensity, IChromatogram chromatogramSink, PeakTransferSettings peakTransferSettings) {

		PeakSupport peakSupport = new PeakSupport();
		//
		boolean includeBackground = peakSource.getPeakType().equals(PeakType.VV);
		boolean optimizeRange = peakTransferSettings.isOptimizeRange();
		//
		Set<Integer> traces;
		if(chromatogramSink instanceof IChromatogramMSD && peakTransferSettings.isCheckPurity()) {
			int numberTraces = peakTransferSettings.getNumberTraces();
			traces = getTraces(peakSource, numberTraces);
		} else {
			traces = new HashSet<>();
		}
		//
		IPeak peakSink = peakSupport.extractPeakByRetentionTime(chromatogramSink, startRetentionTime, stopRetentionTime, includeBackground, optimizeRange, traces);
		if(peakSink != null) {
			adjustPeakIntensity(peakSource, peakSink, percentageIntensity, peakTransferSettings);
			transferTargets(peakSource, peakSink, peakTransferSettings);
			chromatogramSink.addPeak(peakSink);
		}
	}

	@SuppressWarnings("unchecked")
	private void transfer(IPeak peakSource, IChromatogram chromatogramSink, PeakTransferSettings peakTransferSettings) {

		IPeakModel peakModel = peakSource.getPeakModel();
		double percentageIntensity = getPercentageIntensity(peakSource);
		//
		if(chromatogramSink instanceof IChromatogramCSD && peakTransferSettings.isCreateModelPeak()) {
			/*
			 * Model peak
			 */
			if(peakModel.getLeading() >= 4.0f || peakModel.getTailing() >= 4.0) {
				/*
				 * Probably try to add a Gamma distribution modeled peak.
				 * https://commons.apache.org/proper/commons-math/userguide/distribution.html
				 */
				transfer(peakSource, percentageIntensity, chromatogramSink, peakTransferSettings);
			} else {
				/*
				 * Gaussian Peak
				 */
				IChromatogramCSD chromatogramCSD = (IChromatogramCSD)chromatogramSink;
				int deltaRetentionTimeLeft = peakTransferSettings.getDeltaRetentionTimeLeft();
				int deltaRetentionTimeRight = peakTransferSettings.getDeltaRetentionTimeRight();
				int startRetentionTime = peakModel.getStartRetentionTime() - deltaRetentionTimeLeft;
				int stopRetentionTime = peakModel.getStopRetentionTime() + deltaRetentionTimeRight;
				int offsetRetentionTime = peakTransferSettings.getOffsetRetentionTimePeakMaximum();
				Point maxPosition = getMaxPosition(chromatogramCSD, peakModel.getRetentionTimeAtPeakMaximum(), offsetRetentionTime);
				//
				if(maxPosition.getX() > 0 && maxPosition.getY() > 0) {
					double sigma = calculateSigma(peakSource);
					int centerRetentionTime = (int)maxPosition.getX();
					float intensity = (float)(maxPosition.getY() * percentageIntensity);
					IPeak peakSink = createDefaultGaussPeak(chromatogramCSD, startRetentionTime, centerRetentionTime, stopRetentionTime, sigma, intensity);
					if(peakSink != null) {
						transferTargets(peakSource, peakSink, peakTransferSettings);
						chromatogramSink.addPeak(peakSink);
					}
				}
			}
		} else {
			/*
			 * Standard
			 */
			transfer(peakSource, percentageIntensity, chromatogramSink, peakTransferSettings);
		}
	}

	private double calculateSigma(IPeak peak) {

		double sigma = 30.0d;
		double sigmaCorrection = 20.0d;
		//
		IPeakModel peakModel = peak.getPeakModel();
		double width0 = peakModel.getWidthByInflectionPoints(0.0f);
		if(width0 > 0) {
			double width50 = peakModel.getWidthByInflectionPoints(0.5f);
			double calculatedSigma = width50 / width0 * 100.0d - sigmaCorrection;
			if(calculatedSigma >= 10 && calculatedSigma <= 90) {
				sigma = calculatedSigma;
			}
		}
		//
		return sigma;
	}

	private Set<Integer> getTraces(IPeak peakSource, int numberTraces) {

		Set<Integer> traces = new HashSet<>();
		if(peakSource instanceof IChromatogramPeakMSD) {
			IChromatogramPeakMSD peakMSD = (IChromatogramPeakMSD)peakSource;
			if(peakMSD.getPurity() < 1.0f && numberTraces > 0) {
				IScanMSD scanMSD = peakMSD.getExtractedMassSpectrum();
				if(scanMSD.getIons().size() <= numberTraces) {
					IExtractedIonSignal extractedIonSignal = peakMSD.getExtractedMassSpectrum().getExtractedIonSignal();
					for(int ion = extractedIonSignal.getStartIon(); ion <= extractedIonSignal.getStopIon(); ion++) {
						if(extractedIonSignal.getAbundance(ion) > 0.0f) {
							traces.add(ion);
						}
					}
				}
			}
		}
		return traces;
	}

	private void adjustPeakIntensity(IPeak peakSource, IPeak peakSink, double percentageIntensity, PeakTransferSettings peakTransferSettings) {

		if(peakTransferSettings.isAdjustPeakHeight()) {
			if(percentageIntensity > 0.0d && percentageIntensity < 1.0d) {
				IScan peakMaximum = peakSink.getPeakModel().getPeakMaximum();
				float totalSignal = peakMaximum.getTotalSignal();
				peakMaximum.adjustTotalSignal((float)(totalSignal * percentageIntensity));
			}
		}
	}

	private double getPercentageIntensity(IPeak peakSource) {

		double percentageIntensity = 1.0d;
		if(peakSource instanceof IChromatogramPeak) {
			IChromatogramPeak peak = (IChromatogramPeak)peakSource;
			IChromatogram chromatogram = peak.getChromatogram();
			if(chromatogram != null) {
				int scanMax = peak.getScanMax();
				if(scanMax > 0 && scanMax <= chromatogram.getNumberOfScans()) {
					IScan scan = chromatogram.getScan(scanMax);
					float chromatogramTotalSignal = scan.getTotalSignal();
					IPeakModel peakModel = peak.getPeakModel();
					float peakTotalSignal = peakModel.getBackgroundAbundance() + peakModel.getPeakAbundance();
					//
					if(chromatogramTotalSignal > 0) {
						percentageIntensity = 1.0d / chromatogramTotalSignal * peakTotalSignal;
					}
				}
			}
		}
		return percentageIntensity;
	}

	private void transferTargets(IPeak peakSource, IPeak peakSink, PeakTransferSettings peakTransferSettings) {

		if(peakTransferSettings.isUseBestTargetOnly()) {
			IIdentificationTarget identificationTarget = IIdentificationTarget.getBestIdentificationTarget(peakSource.getTargets());
			if(identificationTarget != null) {
				peakSink.getTargets().add(createIdentificationTarget(identificationTarget));
			}
		} else {
			for(IIdentificationTarget identificationTarget : peakSource.getTargets()) {
				peakSink.getTargets().add(createIdentificationTarget(identificationTarget));
			}
		}
	}

	private IIdentificationTarget createIdentificationTarget(IIdentificationTarget identificationTarget) {

		ILibraryInformation libraryInformation = new LibraryInformation(identificationTarget.getLibraryInformation());
		IComparisonResult comparisonResult = new ComparisonResult(identificationTarget.getComparisonResult());
		IIdentificationTarget identificationTargetSink = new IdentificationTarget(libraryInformation, comparisonResult);
		identificationTargetSink.setIdentifier(PeakTransferSettings.IDENTIFIER_DESCRIPTION);
		return identificationTargetSink;
	}

	private Point getMaxPosition(IChromatogram<?> chromatogram, int centerRetentionTime, int offsetRetentionTime) {

		int retentionTime = 0;
		float maxIntensity = Float.MIN_VALUE;
		//
		int startScan = chromatogram.getScanNumber(centerRetentionTime - offsetRetentionTime);
		int stopScan = chromatogram.getScanNumber(centerRetentionTime + offsetRetentionTime);
		if(startScan > 0 && stopScan <= chromatogram.getNumberOfScans()) {
			for(int i = startScan; i <= stopScan; i++) {
				IScan scan = chromatogram.getScan(i);
				if(scan.getTotalSignal() > maxIntensity) {
					retentionTime = scan.getRetentionTime();
					maxIntensity = scan.getTotalSignal();
				}
			}
		}
		//
		return new Point(retentionTime, maxIntensity);
	}

	public IChromatogramPeakCSD createDefaultGaussPeak(IChromatogramCSD chromatogram, int startRetentionTime, int centerRetentionTime, int stopRetentionTime, double sigma, float intensity) {

		int startScan = chromatogram.getScanNumber(startRetentionTime);
		int centerScan = chromatogram.getScanNumber(centerRetentionTime);
		int stopScan = chromatogram.getScanNumber(stopRetentionTime);
		IScanCSD peakMaximum = new ScanCSD(intensity);
		/*
		 * Intensity profile
		 */
		IPeakIntensityValues peakIntensityValues = new PeakIntensityValues(intensity);
		//
		double norm = intensity;
		double mean = centerScan;
		Gaussian gaussian = new Gaussian(norm, mean, sigma);
		//
		for(int i = startScan; i <= stopScan; i++) {
			IScan scan = chromatogram.getScan(i);
			int retentionTime = scan.getRetentionTime();
			float peakIntensity = (float)gaussian.value(i);
			peakIntensityValues.addIntensityValue(retentionTime, peakIntensity);
		}
		peakIntensityValues.normalize();
		//
		IPeakModelCSD peakModel = new PeakModelCSD(peakMaximum, peakIntensityValues, 0, 0);
		ChromatogramPeakCSD peak = new ChromatogramPeakCSD(peakModel, chromatogram);
		peak.setDetectorDescription(PeakTransferSettings.DETECTOR_DESCRIPTION);
		return peak;
	}
}