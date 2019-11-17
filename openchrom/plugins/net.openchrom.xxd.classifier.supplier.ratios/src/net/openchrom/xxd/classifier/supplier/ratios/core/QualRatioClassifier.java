/*******************************************************************************
 * Copyright (c) 2019 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - Adjust to new API
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.core;

import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.classifier.result.IChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.ResultStatus;
import org.eclipse.chemclipse.chromatogram.msd.classifier.settings.IChromatogramClassifierSettings;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.implementation.MeasurementResult;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.qual.PeakQuality;
import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatioResult;
import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.QualRatioSettings;

public class QualRatioClassifier extends AbstractRatioClassifier {

	public static final String CLASSIFIER_ID = "net.openchrom.xxd.classifier.supplier.ratios.qual";

	@Override
	public IProcessingInfo<IChromatogramClassifierResult> applyClassifier(IChromatogramSelection<?, ?> chromatogramSelection, IChromatogramClassifierSettings chromatogramClassifierSettings, IProgressMonitor monitor) {

		QualRatioSettings settings;
		if(chromatogramClassifierSettings instanceof QualRatioSettings) {
			settings = (QualRatioSettings)chromatogramClassifierSettings;
		} else {
			settings = PreferenceSupplier.getSettingsQual();
		}
		IProcessingInfo<IChromatogramClassifierResult> processingInfo = validate(chromatogramSelection, chromatogramClassifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			/*
			 * Calculate the peak quality
			 */
			QualRatios qualRatios = calculateRatios(chromatogramSelection.getChromatogram(), settings);
			QualRatioResult classifierResult = new QualRatioResult(ResultStatus.OK, "The chromatogram peaks have been classified.", qualRatios);
			IMeasurementResult measurementResult = new MeasurementResult("Qual Ratio Classifier", CLASSIFIER_ID, "Qual Ratios", qualRatios);
			chromatogramSelection.getChromatogram().addMeasurementResult(measurementResult);
			processingInfo.setProcessingResult(classifierResult);
		}
		return processingInfo;
	}

	private QualRatios calculateRatios(IChromatogram<?> chromatogram, QualRatioSettings classifierSettings) {

		QualRatios ratios = new QualRatios();
		//
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			QualRatio qualRatio = new QualRatio(peak);
			//
			calculateLeadingTailing(qualRatio, peakModel);
			calculateSignalToNoiseRatio(qualRatio, peak);
			calculateSymmetry(qualRatio, peakModel);
			//
			ratios.add(qualRatio);
		}
		//
		return ratios;
	}

	private void calculateLeadingTailing(QualRatio qualRatio, IPeakModel peakModel) {

		PeakQuality peakQuality;
		float leading = peakModel.getLeading();
		float tailing = peakModel.getTailing();
		//
		if(leading <= 1 && tailing <= 1) {
			peakQuality = PeakQuality.VERY_GOOD;
		} else if(leading <= 2 && tailing <= 2) {
			peakQuality = PeakQuality.GOOD;
		} else if(leading <= 3 && tailing <= 3) {
			peakQuality = PeakQuality.ACCEPTABLE;
		} else if(leading <= 4 && tailing <= 4) {
			peakQuality = PeakQuality.BAD;
		} else if(leading <= 5 && tailing <= 5) {
			peakQuality = PeakQuality.VERY_BAD;
		} else {
			peakQuality = PeakQuality.NONE;
		}
		//
		qualRatio.setLeadingTailing(peakQuality);
	}

	private void calculateSignalToNoiseRatio(QualRatio qualRatio, IPeak peak) {

		PeakQuality peakQuality;
		if(peak instanceof IChromatogramPeak) {
			IChromatogramPeak chromatogramPeak = (IChromatogramPeak)peak;
			//
			float signalToNoiseRatio = chromatogramPeak.getSignalToNoiseRatio();
			if(signalToNoiseRatio <= 1.0f) {
				peakQuality = PeakQuality.VERY_BAD;
			} else if(signalToNoiseRatio <= 2.0f) {
				peakQuality = PeakQuality.BAD;
			} else if(signalToNoiseRatio <= 4.0f) {
				peakQuality = PeakQuality.ACCEPTABLE;
			} else if(signalToNoiseRatio <= 10.0f) {
				peakQuality = PeakQuality.GOOD;
			} else {
				peakQuality = PeakQuality.VERY_GOOD;
			}
			//
			qualRatio.setSignalToNoise(peakQuality);
		}
	}

	private void calculateSymmetry(QualRatio qualRatio, IPeakModel peakModel) {

		double startRetentionTime = peakModel.getStartRetentionTime();
		double stopRetentionTime = peakModel.getStopRetentionTime();
		double centerRetentionTime = peakModel.getRetentionTimeAtPeakMaximum();
		/*
		 * Avoid division by 0 exception.
		 */
		if(centerRetentionTime < stopRetentionTime) {
			//
			double factor = Math.abs((centerRetentionTime - startRetentionTime) / (stopRetentionTime - centerRetentionTime));
			factor = (factor > 1) ? 1 / factor : factor;
			//
			PeakQuality peakQuality;
			if(factor >= 0.88f) {
				peakQuality = PeakQuality.VERY_GOOD;
			} else if(factor >= 0.68f) {
				peakQuality = PeakQuality.GOOD;
			} else if(factor >= 0.45f) {
				peakQuality = PeakQuality.ACCEPTABLE;
			} else if(factor >= 0.23f) {
				peakQuality = PeakQuality.BAD;
			} else {
				peakQuality = PeakQuality.VERY_BAD;
			}
			//
			qualRatio.setSymmetry(peakQuality);
		}
	}
}
