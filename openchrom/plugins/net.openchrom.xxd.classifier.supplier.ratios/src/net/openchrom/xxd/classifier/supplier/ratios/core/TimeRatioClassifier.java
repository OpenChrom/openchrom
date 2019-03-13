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
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.core;

import java.util.List;

import org.eclipse.chemclipse.chromatogram.msd.classifier.result.IChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.ResultStatus;
import org.eclipse.chemclipse.chromatogram.msd.classifier.settings.IChromatogramClassifierSettings;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.chemclipse.model.implementation.MeasurementResult;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.PeakRatioResult;
import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TimeRatioSettings;

public class TimeRatioClassifier extends AbstractRatioClassifier {

	public static final String CLASSIFIER_ID = "net.openchrom.xxd.classifier.supplier.ratios.time";

	@Override
	public IProcessingInfo<IChromatogramClassifierResult> applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IChromatogramClassifierSettings chromatogramClassifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramClassifierResult> processingInfo = validate(chromatogramSelection, chromatogramClassifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			if(chromatogramClassifierSettings instanceof TimeRatioSettings) {
				/*
				 * Calculate the result.
				 */
				TimeRatios timeRatios = calculateRatios(chromatogramSelection, (TimeRatioSettings)chromatogramClassifierSettings);
				PeakRatioResult classifierResult = new PeakRatioResult(ResultStatus.OK, "The chromatogram peaks have been classified.", timeRatios);
				IMeasurementResult measurementResult = new MeasurementResult("Time Ratio Classifier", CLASSIFIER_ID, "Time Ratios", timeRatios);
				chromatogramSelection.getChromatogram().addMeasurementResult(measurementResult);
				processingInfo.setProcessingResult(classifierResult);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<IChromatogramClassifierResult> applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		TimeRatioSettings settings = PreferenceSupplier.getSettingsTime();
		return applyClassifier(chromatogramSelection, settings, monitor);
	}

	private TimeRatios calculateRatios(IChromatogramSelectionMSD chromatogramSelection, TimeRatioSettings classifierSettings) {

		TimeRatios ratios = classifierSettings.getRatioSettings();
		//
		List<IChromatogramPeakMSD> peaks = chromatogramSelection.getChromatogram().getPeaks();
		for(TimeRatio ratio : ratios) {
			for(IChromatogramPeakMSD peak : peaks) {
				if(isPeakMatch(peak, ratio)) {
					double retentionTime = peak.getPeakModel().getRetentionTimeAtPeakMaximum() / AbstractChromatogram.MINUTE_CORRELATION_FACTOR;
					double expectedRetentionTime = ratio.getExpectedRetentionTime();
					if(retentionTime != 0) {
						ratio.setPeak(peak);
						double deviation = (Math.abs(expectedRetentionTime - retentionTime) / retentionTime) * 100.0d;
						ratio.setDeviation(deviation);
					}
				}
			}
		}
		//
		return ratios;
	}
}
