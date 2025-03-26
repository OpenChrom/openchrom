/*******************************************************************************
 * Copyright (c) 2020, 2025 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Matthias Mailänder - optimize sigma estimation
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

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.solvers.BrentSolver;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
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
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeakIntensityValues;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.model.core.PeakType;
import org.eclipse.chemclipse.model.identifier.ComparisonResult;
import org.eclipse.chemclipse.model.identifier.IComparisonResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
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

public class PeakTransfer extends AbstractPeakDetector implements IPeakDetectorMSD, IPeakDetectorCSD {

	private static final Logger logger = Logger.getLogger(PeakTransfer.class);

	@Override
	public IProcessingInfo<?> detect(IChromatogramSelectionMSD chromatogramSelection, IPeakDetectorSettingsMSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> detect(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakTransferSettings settings = getSettings();
		return detect(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> detect(IChromatogramSelectionCSD chromatogramSelection, IPeakDetectorSettingsCSD settings, IProgressMonitor monitor) {

		return applyDetector(chromatogramSelection, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> detect(IChromatogramSelectionCSD chromatogramSelection, IProgressMonitor monitor) {

		PeakTransferSettings settings = getSettings();
		return detect(chromatogramSelection, settings, monitor);
	}

	private PeakTransferSettings getSettings() {

		PeakTransferSettings settings = new PeakTransferSettings();

		settings.setUseIdentifiedPeaksOnly(PreferenceSupplier.isTransferUseIdentifiedPeaksOnly());
		settings.setUseBestTargetOnly(PreferenceSupplier.isTransferUseBestTargetOnly());
		settings.setMatchQuality(PreferenceSupplier.getMatchQualityTransfer());
		settings.setDeltaRetentionTimeLeft(PreferenceSupplier.getTransferRetentionTimeMillisecondsLeft());
		settings.setDeltaRetentionTimeRight(PreferenceSupplier.getTransferRetentionTimeMillisecondsRight());
		settings.setOffsetRetentionTimePeakMaximum(PreferenceSupplier.getTransferOffsetRetentionTimePeakMaximum());
		settings.setAdjustPeakHeight(PreferenceSupplier.isTransferAdjustPeakHeight());
		settings.setCreateModelPeak(PreferenceSupplier.isTransferCreateModelPeak());
		settings.setPeakOverlapCoverage(PreferenceSupplier.getTransferPeakOverlapCoverage());
		settings.setOptimizeRange(PreferenceSupplier.isTransferOptimizeRange());
		settings.setCheckPurity(PreferenceSupplier.isTransferCheckPurity());
		settings.setNumberTraces(PreferenceSupplier.getTransferNumberTraces());

		return settings;
	}

	private IProcessingInfo<?> applyDetector(IChromatogramSelection chromatogramSelection, IPeakDetectorSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<?> processingInfo = super.validate(chromatogramSelection, settings, monitor);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof PeakTransferSettings peakTransferSettings) {
				transferPeaks(chromatogramSelection, peakTransferSettings, monitor);
			} else {
				processingInfo.addErrorMessage(PeakDetectorSettings.DETECTOR_DESCRIPTION, "The settings instance is wrong.");
			}
		}
		return processingInfo;
	}

	private void transferPeaks(IChromatogramSelection chromatogramSelection, PeakTransferSettings peakTransferSettings, IProgressMonitor monitor) {

		IChromatogram chromatogram = chromatogramSelection.getChromatogram();
		List<? extends IChromatogramPeak> peaks = chromatogram.getPeaks(chromatogramSelection);
		List<IChromatogram> referencedChromatograms = chromatogram.getReferencedChromatograms();
		for(IChromatogram referencedChromatogram : referencedChromatograms) {
			transferPeaks(peaks, referencedChromatogram, peakTransferSettings, monitor);
		}
	}

	private void transferPeaks(List<? extends IChromatogramPeak> peaks, IChromatogram chromatogramSink, PeakTransferSettings peakTransferSettings, IProgressMonitor monitor) {

		Map<Integer, List<IChromatogramPeak>> peakGroups = extractPeakGroups(peaks, peakTransferSettings);
		List<Integer> groups = new ArrayList<>();
		groups.addAll(peakGroups.keySet());
		Collections.sort(groups);
		monitor.beginTask("Transfer Peaks", peaks.size());
		for(int group : groups) {
			if(monitor.isCanceled()) {
				return;
			}
			List<IChromatogramPeak> groupedPeaks = peakGroups.get(group);
			if(groupedPeaks.size() == 1) {
				/*
				 * Single Peak
				 */
				IChromatogramPeak peak = groupedPeaks.get(0);
				double percentageIntensity = getPercentageIntensity(peak);
				transfer(peak, percentageIntensity, chromatogramSink, peakTransferSettings);
			} else {
				/*
				 * Peak Group
				 */
				if(chromatogramSink instanceof IChromatogramCSD chromatogramCSD && peakTransferSettings.isCreateModelPeak()) {
					for(IChromatogramPeak peak : groupedPeaks) {
						transferModelPeak(peak, chromatogramCSD, peakTransferSettings);
						monitor.worked(1);
					}
				} else {
					transferPeakGroup(groupedPeaks, chromatogramSink, peakTransferSettings);
					int worked = groupedPeaks.size();
					monitor.worked(worked);
				}
			}
		}
	}

	private Map<Integer, List<IChromatogramPeak>> extractPeakGroups(List<? extends IChromatogramPeak> peaks, PeakTransferSettings peakTransferSettings) {

		/*
		 * Select the peaks.
		 */
		List<IChromatogramPeak> peaksSource = new ArrayList<>();
		if(peakTransferSettings.isUseIdentifiedPeaksOnly()) {
			/*
			 * Add identified peaks.
			 */
			for(IChromatogramPeak peak : peaks) {
				if(!peak.getTargets().isEmpty()) {
					if(peak.isActiveForAnalysis()) {
						peaksSource.add(peak);
					}
				}
			}
		} else {
			/*
			 * Add all peaks.
			 */
			for(IChromatogramPeak peak : peaks) {
				if(peak.isActiveForAnalysis()) {
					peaksSource.add(peak);
				}
			}
		}
		/*
		 * Sort by retention time.
		 */
		Collections.sort(peaksSource, (p1, p2) -> Integer.compare(p1.getPeakModel().getRetentionTimeAtPeakMaximum(), p2.getPeakModel().getRetentionTimeAtPeakMaximum()));
		ListIterator<IChromatogramPeak> listIterator = peaksSource.listIterator();
		Map<Integer, List<IChromatogramPeak>> peakGroups = new HashMap<>();

		double peakOverlapCoverage = peakTransferSettings.getPeakOverlapCoverage();
		int group = 1;
		while(listIterator.hasNext()) {
			IChromatogramPeak peakCurrent = listIterator.next();
			IPeakModel peakModelCurrent = peakCurrent.getPeakModel();
			int stopRetentionTimeCurrent = peakModelCurrent.getStopRetentionTime();

			List<IChromatogramPeak> groupedPeaks = peakGroups.get(group);
			if(groupedPeaks == null) {
				groupedPeaks = new ArrayList<>();
				peakGroups.put(group, groupedPeaks);
			}
			groupedPeaks.add(peakCurrent);
			if(listIterator.hasNext()) {
				/*
				 * Test if the next peak covers the current peak.
				 */
				IChromatogramPeak peakNext = listIterator.next();
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

				listIterator.previous();
			}
		}

		return peakGroups;
	}

	private void transferPeakGroup(List<IChromatogramPeak> groupedPeaks, IChromatogram chromatogramSink, PeakTransferSettings peakTransferSettings) {

		for(int i = 0; i < groupedPeaks.size() - 1; i++) {
			IChromatogramPeak currentPeak = groupedPeaks.get(i);
			double percentageIntensity = getPercentageIntensity(currentPeak);
			IChromatogramPeak nextPeak = groupedPeaks.get(i + 1);

			Double crossingPoint = calculateIntersectionX(currentPeak, nextPeak);
			if(crossingPoint != null) {
				int startRetentionTime = currentPeak.getPeakModel().getStartRetentionTime();
				int scanNumber = chromatogramSink.getScanNumber(crossingPoint.intValue() + 1);
				int stopRetentionTime = chromatogramSink.getScan(scanNumber).getRetentionTime();
				transfer(currentPeak, startRetentionTime, stopRetentionTime, percentageIntensity, chromatogramSink, peakTransferSettings);

				// Next peak is last peak.
				if(i == groupedPeaks.size() - 2) {
					startRetentionTime = stopRetentionTime;
					stopRetentionTime = nextPeak.getPeakModel().getStopRetentionTime();
					transfer(nextPeak, startRetentionTime, stopRetentionTime, percentageIntensity, chromatogramSink, peakTransferSettings);
				}
			} else {
				transferScan(currentPeak, chromatogramSink, peakTransferSettings);

				// Next peak is last peak.
				if(i == groupedPeaks.size() - 2) {
					transferScan(nextPeak, chromatogramSink, peakTransferSettings);
				}
			}
		}
	}

	private Double calculateIntersectionX(IChromatogramPeak currentPeak, IChromatogramPeak nextPeak) {

		double[] x1 = new double[currentPeak.getPeakModel().getRetentionTimes().size()];
		double[] y1 = new double[currentPeak.getPeakModel().getRetentionTimes().size()];
		int i = 0;
		for(int rt : currentPeak.getPeakModel().getRetentionTimes()) {
			x1[i] = rt;
			y1[i] = currentPeak.getPeakModel().getPeakAbundance(rt);
			i++;
		}
		UnivariateFunction f1 = new LinearInterpolator().interpolate(x1, y1);

		double[] x2 = new double[nextPeak.getPeakModel().getRetentionTimes().size()];
		double[] y2 = new double[nextPeak.getPeakModel().getRetentionTimes().size()];
		i = 0;
		for(int rt : nextPeak.getPeakModel().getRetentionTimes()) {
			x2[i] = rt;
			y2[i] = nextPeak.getPeakModel().getPeakAbundance(rt);
			i++;
		}
		UnivariateFunction f2 = new LinearInterpolator().interpolate(x2, y2);

		UnivariateFunction h = x -> f1.value(x) - f2.value(x);
		BrentSolver solver = new BrentSolver(1e-10, 1e-14);
		double min = Math.max(x1[0], x2[0]); // Start of the interval
		double max = Math.min(x1[x1.length - 1], x2[x2.length - 1]); // End of the interval
		try {
			return solver.solve(1000, h, min, max);
		} catch(TooManyEvaluationsException | NoBracketingException e) {
			logger.warn(e);
			return null;
		}
	}

	private void transferScan(IChromatogramPeak currentPeak, IChromatogram chromatogramSink, PeakTransferSettings peakTransferSettings) {

		IScan scan = chromatogramSink.getScan(chromatogramSink.getScanNumber(currentPeak.getPeakModel().getPeakMaximum().getRetentionTime()));

		if(peakTransferSettings.isUseBestTargetOnly()) {
			IIdentificationTarget identificationTarget = IIdentificationTarget.getIdentificationTarget(currentPeak);
			if(identificationTarget != null) {
				scan.getTargets().add(createIdentificationTarget(identificationTarget, peakTransferSettings));
			}
		} else {
			for(IIdentificationTarget identificationTarget : currentPeak.getTargets()) {
				scan.getTargets().add(createIdentificationTarget(identificationTarget, peakTransferSettings));
			}
		}
	}

	private void transfer(IChromatogramPeak peakSource, double percentageIntensity, IChromatogram chromatogramSink, PeakTransferSettings peakTransferSettings) {

		int deltaRetentionTimeLeft = peakTransferSettings.getDeltaRetentionTimeLeft();
		int deltaRetentionTimeRight = peakTransferSettings.getDeltaRetentionTimeRight();

		IPeakModel peakModelSource = peakSource.getPeakModel();
		int startRetentionTime = peakModelSource.getStartRetentionTime() - deltaRetentionTimeLeft;
		int stopRetentionTime = peakModelSource.getStopRetentionTime() + deltaRetentionTimeRight;

		transfer(peakSource, startRetentionTime, stopRetentionTime, percentageIntensity, chromatogramSink, peakTransferSettings);
	}

	private void transfer(IChromatogramPeak peakSource, int startRetentionTime, int stopRetentionTime, double percentageIntensity, IChromatogram chromatogramSink, PeakTransferSettings peakTransferSettings) {

		PeakSupport peakSupport = new PeakSupport();

		boolean includeBackground = peakSource.getPeakType().equals(PeakType.VV);
		boolean optimizeRange = peakTransferSettings.isOptimizeRange();

		Set<Integer> traces;
		if(chromatogramSink instanceof IChromatogramMSD && peakTransferSettings.isCheckPurity()) {
			int numberTraces = peakTransferSettings.getNumberTraces();
			traces = getTraces(peakSource, numberTraces);
		} else {
			traces = new HashSet<>();
		}

		IChromatogramPeak peakSink = peakSupport.extractPeakByRetentionTime(chromatogramSink, startRetentionTime, stopRetentionTime, includeBackground, optimizeRange, traces);
		if(peakSink != null) {
			adjustPeakIntensity(peakSink, percentageIntensity, peakTransferSettings);
			transferTargets(peakSource, peakSink, peakTransferSettings);
			PeakSupport.addPeak(chromatogramSink, peakSink);
		} else {
			transferScan(peakSource, chromatogramSink, peakTransferSettings);
		}
	}

	private void transferModelPeak(IChromatogramPeak peakSource, IChromatogramCSD chromatogramCSD, PeakTransferSettings peakTransferSettings) {

		double percentageIntensity = getPercentageIntensity(peakSource);
		/*
		 * Model peak
		 */
		IPeakModel peakModel = peakSource.getPeakModel();
		if(peakModel.getLeading() >= 4.0f || peakModel.getTailing() >= 4.0) {
			/*
			 * Probably try to add a Gamma distribution modeled peak.
			 * https://commons.apache.org/proper/commons-math/userguide/distribution.html
			 */
			transfer(peakSource, percentageIntensity, chromatogramCSD, peakTransferSettings);
		} else {
			/*
			 * Gaussian Peak
			 * https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/fitting/GaussianCurveFitter.html
			 */
			int deltaRetentionTimeLeft = peakTransferSettings.getDeltaRetentionTimeLeft();
			int deltaRetentionTimeRight = peakTransferSettings.getDeltaRetentionTimeRight();
			int startRetentionTime = peakModel.getStartRetentionTime() - deltaRetentionTimeLeft;
			int stopRetentionTime = peakModel.getStopRetentionTime() + deltaRetentionTimeRight;
			int offsetRetentionTime = peakTransferSettings.getOffsetRetentionTimePeakMaximum();
			Point maxPosition = getMaxPosition(chromatogramCSD, peakModel.getRetentionTimeAtPeakMaximum(), offsetRetentionTime);

			if(maxPosition.getX() > 0 && maxPosition.getY() > 0) {
				IChromatogramPeak peakSink = modelPeak(chromatogramCSD, maxPosition, percentageIntensity, startRetentionTime, stopRetentionTime, peakTransferSettings);
				if(peakSink != null) {
					transferTargets(peakSource, peakSink, peakTransferSettings);
					PeakSupport.addPeak(chromatogramCSD, peakSink);
				}
			}
		}
	}

	private IChromatogramPeak modelPeak(IChromatogramCSD chromatogramCSD, Point maxPosition, double percentageIntensity, int startRetentionTime, int stopRetentionTime, PeakTransferSettings peakTransferSettings) {

		int centerRetentionTime = (int)maxPosition.getX();
		float intensity = (float)(maxPosition.getY() * percentageIntensity);
		int centerScan = chromatogramCSD.getScanNumber(centerRetentionTime);
		Gaussian gaussian = new Gaussian(intensity, centerScan, peakTransferSettings.getSigma());
		IChromatogramPeak peakSink = createDefaultGaussPeakNormal(chromatogramCSD, startRetentionTime, stopRetentionTime, gaussian, intensity);
		if(peakSink == null) {
			return null;
		}
		gaussian = new Gaussian(intensity, centerScan, peakTransferSettings.getSigma());
		peakSink = createDefaultGaussPeakNormal(chromatogramCSD, startRetentionTime, stopRetentionTime, gaussian, intensity);
		if(peakSink == null) {
			return null;
		}
		return peakSink;
	}

	private Set<Integer> getTraces(IChromatogramPeak peakSource, int numberTraces) {

		Set<Integer> traces = new HashSet<>();
		if(peakSource instanceof IChromatogramPeakMSD peakMSD) {
			if(peakMSD.getPurity() < 1.0f && numberTraces > 0) {
				// probably a deconvoluted peak
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

	private void adjustPeakIntensity(IChromatogramPeak peakSink, double percentageIntensity, PeakTransferSettings peakTransferSettings) {

		if(peakTransferSettings.isAdjustPeakHeight()) {
			if(percentageIntensity > 0.0d && percentageIntensity < 1.0d) {
				IScan peakMaximum = peakSink.getPeakModel().getPeakMaximum();
				float totalSignal = peakMaximum.getTotalSignal();
				peakMaximum.adjustTotalSignal((float)(totalSignal * percentageIntensity));
			}
		}
	}

	private double getPercentageIntensity(IChromatogramPeak peakSource) {

		double percentageIntensity = 1.0d;
		if(peakSource instanceof IChromatogramPeak peak) {
			IChromatogram chromatogram = peak.getChromatogram();
			if(chromatogram != null) {
				int scanMax = peak.getScanMax();
				if(scanMax > 0 && scanMax <= chromatogram.getNumberOfScans()) {
					IScan scan = chromatogram.getScan(scanMax);
					float chromatogramTotalSignal = scan.getTotalSignal();
					IPeakModel peakModel = peak.getPeakModel();
					float peakTotalSignal = peakModel.getBackgroundAbundance() + peakModel.getPeakAbundance();

					if(chromatogramTotalSignal > 0) {
						percentageIntensity = 1.0d / chromatogramTotalSignal * peakTotalSignal;
					}
				}
			}
		}
		return percentageIntensity;
	}

	private void transferTargets(IChromatogramPeak peakSource, IChromatogramPeak peakSink, PeakTransferSettings peakTransferSettings) {

		if(peakTransferSettings.isUseBestTargetOnly()) {
			IIdentificationTarget identificationTarget = IIdentificationTarget.getIdentificationTarget(peakSource);
			if(identificationTarget != null) {
				peakSink.getTargets().add(createIdentificationTarget(identificationTarget, peakTransferSettings));
			}
		} else {
			for(IIdentificationTarget identificationTarget : peakSource.getTargets()) {
				peakSink.getTargets().add(createIdentificationTarget(identificationTarget, peakTransferSettings));
			}
		}
	}

	private IIdentificationTarget createIdentificationTarget(IIdentificationTarget identificationTarget, PeakTransferSettings peakTransferSettings) {

		float matchFactor = peakTransferSettings.getMatchQuality();
		IComparisonResult comparisonResult = matchFactor > 0 ? new ComparisonResult(matchFactor) : identificationTarget.getComparisonResult();
		IdentificationTarget identificationTargetSink = new IdentificationTarget(identificationTarget.getLibraryInformation(), comparisonResult);
		identificationTargetSink.setIdentifier(PeakTransferSettings.IDENTIFIER_DESCRIPTION);
		identificationTargetSink.setVerified(identificationTarget.isVerified());
		return identificationTargetSink;
	}

	private Point getMaxPosition(IChromatogram chromatogram, int centerRetentionTime, int offsetRetentionTime) {

		int retentionTime = 0;
		float maxIntensity = Float.MIN_VALUE;

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

		return new Point(retentionTime, maxIntensity);
	}

	public IChromatogramPeakCSD createDefaultGaussPeakNormal(IChromatogramCSD chromatogram, int startRetentionTime, int stopRetentionTime, Gaussian gaussian, float norm) {

		int startScan = chromatogram.getScanNumber(startRetentionTime);
		int stopScan = chromatogram.getScanNumber(stopRetentionTime);
		/*
		 * Intensity profile
		 */
		IScanCSD peakMaximum = new ScanCSD(norm);
		IPeakIntensityValues peakIntensityValues = new PeakIntensityValues(norm);

		for(int i = startScan; i <= stopScan; i++) {
			IScan scan = chromatogram.getScan(i);
			int retentionTime = scan.getRetentionTime();
			float peakIntensity = (float)gaussian.value(i);
			peakIntensityValues.addIntensityValue(retentionTime, peakIntensity);
		}
		peakIntensityValues.normalize();

		IPeakModelCSD peakModel = new PeakModelCSD(peakMaximum, peakIntensityValues, 0, 0);
		ChromatogramPeakCSD peak = new ChromatogramPeakCSD(peakModel, chromatogram);
		peak.setDetectorDescription(PeakTransferSettings.DETECTOR_DESCRIPTION);
		return peak;
	}
}
