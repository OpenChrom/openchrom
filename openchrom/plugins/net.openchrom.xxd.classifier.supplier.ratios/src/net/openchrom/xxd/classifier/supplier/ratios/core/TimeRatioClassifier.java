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

import org.eclipse.chemclipse.chromatogram.msd.classifier.core.AbstractChromatogramClassifier;
import org.eclipse.chemclipse.chromatogram.msd.classifier.result.ResultStatus;
import org.eclipse.chemclipse.chromatogram.msd.classifier.settings.IChromatogramClassifierSettings;
import org.eclipse.chemclipse.model.core.AbstractChromatogram;
import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.implementation.MeasurementResult;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.TimeRatioResult;
import net.openchrom.xxd.classifier.supplier.ratios.model.TimeRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TimeRatioSettings;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TraceRatioSettings;

public class TimeRatioClassifier extends AbstractChromatogramClassifier {

	public static final String CLASSIFIER_ID = "net.openchrom.xxd.classifier.supplier.ratios.timeratio";

	@Override
	public IProcessingInfo applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IChromatogramClassifierSettings chromatogramClassifierSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(chromatogramSelection, chromatogramClassifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			if(chromatogramClassifierSettings instanceof TimeRatioSettings) {
				/*
				 * Calculate the result.
				 */
				TimeRatios timeRatios = calculateTimeRatios(chromatogramSelection, (TimeRatioSettings)chromatogramClassifierSettings);
				TimeRatioResult classifierResult = new TimeRatioResult(ResultStatus.OK, "The chromatogram peaks have been classified.", timeRatios);
				IMeasurementResult measurementResult = new MeasurementResult("Time Ratio Classifier", CLASSIFIER_ID, "Time Ratios", timeRatios);
				chromatogramSelection.getChromatogram().addMeasurementResult(measurementResult);
				processingInfo.setProcessingResult(classifierResult);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		TraceRatioSettings classifierSettings = PreferenceSupplier.getClassifierSettings();
		return applyClassifier(chromatogramSelection, classifierSettings, monitor);
	}

	private TimeRatios calculateTimeRatios(IChromatogramSelectionMSD chromatogramSelection, TimeRatioSettings classifierSettings) {

		TimeRatios timeRatios = classifierSettings.getTimeRatioSettings();
		//
		List<IChromatogramPeakMSD> peaks = chromatogramSelection.getChromatogramMSD().getPeaks();
		for(TimeRatio timeRatio : timeRatios) {
			for(IChromatogramPeakMSD peak : peaks) {
				if(isPeakMatch(peak, timeRatio)) {
					int retentionTime = peak.getPeakModel().getRetentionTimeAtPeakMaximum();
					int expectedRetentionTime = (int)(timeRatio.getExpectedRetentionTime() * AbstractChromatogram.MINUTE_CORRELATION_FACTOR);
					if(expectedRetentionTime != 0) {
						timeRatio.setPeak(peak);
						double deviation = Math.abs(retentionTime - expectedRetentionTime) / expectedRetentionTime;
						timeRatio.setDeviation(deviation);
					}
				}
			}
		}
		//
		return timeRatios;
	}

	private boolean isPeakMatch(IPeak peak, TimeRatio timeRatio) {

		for(IIdentificationTarget identificationTarget : peak.getTargets()) {
			if(identificationTarget.getLibraryInformation().getName().equals(timeRatio.getName())) {
				return true;
			}
		}
		return false;
	}
}
