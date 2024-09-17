/*******************************************************************************
 * Copyright (c) 2019, 2024 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 * Christoph Läubrich - adjust API
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.core;

import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.IChromatogramClassifierResult;
import org.eclipse.chemclipse.chromatogram.xxd.classifier.result.ResultStatus;
import org.eclipse.chemclipse.chromatogram.xxd.classifier.settings.IChromatogramClassifierSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.implementation.MeasurementResult;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.msd.model.core.IPeakModelMSD;
import org.eclipse.chemclipse.msd.model.xic.ExtractedIonSignal;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.PeakRatioResult;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.trace.TraceRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TraceRatioSettings;

public class TraceRatioClassifier extends AbstractRatioClassifier {

	public static final String CLASSIFIER_ID = "net.openchrom.xxd.classifier.supplier.ratios.trace";
	//
	private static final Logger logger = Logger.getLogger(TraceRatioClassifier.class);

	@Override
	public IProcessingInfo<IChromatogramClassifierResult> applyClassifier(IChromatogramSelection<?, ?> chromatogramSelection, IChromatogramClassifierSettings chromatogramClassifierSettings, IProgressMonitor monitor) {

		TraceRatioSettings settings;
		if(chromatogramClassifierSettings instanceof TraceRatioSettings traceRatioSettings) {
			settings = traceRatioSettings;
		} else {
			settings = PreferenceSupplier.getSettingsTrace();
		}
		//
		IProcessingInfo<IChromatogramClassifierResult> processingInfo = validate(chromatogramSelection, chromatogramClassifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			/*
			 * Calculate the result.
			 */
			TraceRatios traceRatios = calculateRatios(chromatogramSelection.getChromatogram(), settings);
			PeakRatioResult classifierResult = new PeakRatioResult(ResultStatus.OK, "The chromatogram peaks have been classified.", traceRatios);
			IMeasurementResult<?> measurementResult = new MeasurementResult("Trace Ratio Classifier", CLASSIFIER_ID, "Trace Ratios", traceRatios);
			chromatogramSelection.getChromatogram().addMeasurementResult(measurementResult);
			processingInfo.setProcessingResult(classifierResult);
		}
		//
		return processingInfo;
	}

	private TraceRatios calculateRatios(IChromatogram<?> chromatogram, TraceRatioSettings classifierSettings) {

		TraceRatios ratios = classifierSettings.getRatioSettingsList();
		//
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		for(TraceRatio traceRatio : ratios) {
			for(IPeak peak : peaks) {
				if(isPeakMatch(peak, traceRatio)) {
					String[] values = traceRatio.getTestCase().split(":");
					if(values.length == 2) {
						try {
							IPeakModel peakModel = peak.getPeakModel();
							if(peakModel instanceof IPeakModelMSD peakModelMSD) {
								/*
								 * E.g. 104:103
								 * By default, set the ratio and deviation as not being calculated.
								 */
								int reference = Integer.parseInt(values[0]);
								int target = Integer.parseInt(values[1]);
								ExtractedIonSignal extractedIonSignal = new ExtractedIonSignal(peakModelMSD.getPeakMassSpectrum().getIons());
								float intensityReference = extractedIonSignal.getAbundance(reference);
								traceRatio.setPeak(peak);
								traceRatio.setRatio(Double.NaN);
								traceRatio.setDeviation(Double.NaN);
								//
								if(intensityReference != 0) {
									double expectedRatio = traceRatio.getExpectedRatio();
									if(expectedRatio != 0) {
										float intensityTarget = extractedIonSignal.getAbundance(target);
										if(intensityReference > 0 && intensityTarget > 0) {
											double actualRatio = 100.0d / intensityReference * intensityTarget;
											double deviation = Math.abs(100.0d - (100.0d / expectedRatio * actualRatio));
											traceRatio.setRatio(actualRatio);
											traceRatio.setDeviation(deviation);
										}
									}
								}
							}
						} catch(NumberFormatException e) {
							logger.warn(e);
						}
					}
				}
			}
		}
		//
		return ratios;
	}
}