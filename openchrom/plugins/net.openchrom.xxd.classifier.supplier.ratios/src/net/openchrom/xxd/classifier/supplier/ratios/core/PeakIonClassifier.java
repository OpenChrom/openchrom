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
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.chemclipse.model.identifier.IIdentificationTarget;
import org.eclipse.chemclipse.model.implementation.MeasurementResult;
import org.eclipse.chemclipse.msd.model.core.IChromatogramPeakMSD;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.msd.model.xic.ExtractedIonSignal;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.ClassifierResult;
import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.TraceRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.PeakIonClassifierSettings;

public class PeakIonClassifier extends AbstractChromatogramClassifier {

	public static final String CLASSIFIER_ID = "net.openchrom.xxd.classifier.supplier.ratios.peak.ion";
	//
	private static final Logger logger = Logger.getLogger(PeakIonClassifier.class);

	@Override
	public IProcessingInfo applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IChromatogramClassifierSettings chromatogramClassifierSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(chromatogramSelection, chromatogramClassifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			if(chromatogramClassifierSettings instanceof PeakIonClassifierSettings) {
				/*
				 * Calculate the result.
				 */
				TraceRatios traceRatios = calculateTraceRatios(chromatogramSelection, (PeakIonClassifierSettings)chromatogramClassifierSettings);
				ClassifierResult classifierResult = new ClassifierResult(ResultStatus.OK, "The chromatogram peaks have been classified.", traceRatios);
				//
				IMeasurementResult measurementResult = new MeasurementResult("Peak Ion Ratio Classifier", CLASSIFIER_ID, "Trace Ratios", traceRatios);
				chromatogramSelection.getChromatogram().addMeasurementResult(measurementResult);
				processingInfo.setProcessingResult(classifierResult);
			}
		}
		return processingInfo;
	}

	private TraceRatios calculateTraceRatios(IChromatogramSelectionMSD chromatogramSelection, PeakIonClassifierSettings classifierSettings) {

		TraceRatios traceRatios = new TraceRatios();
		traceRatios.add(new TraceRatio("2-Methoxy-4-vinylphenol", "150:135", 76.88));
		//
		List<IChromatogramPeakMSD> peaks = chromatogramSelection.getChromatogramMSD().getPeaks();
		for(TraceRatio traceRatio : traceRatios) {
			for(IChromatogramPeakMSD peak : peaks) {
				for(IIdentificationTarget identificationTarget : peak.getTargets()) {
					if(identificationTarget.getLibraryInformation().getName().equals(traceRatio.getName())) {
						String[] values = traceRatio.getTest().split(":");
						if(values.length == 2) {
							try {
								int reference = Integer.parseInt(values[0]);
								int target = Integer.parseInt(values[1]);
								//
								ExtractedIonSignal extractedIonSignal = new ExtractedIonSignal(peak.getPeakModel().getPeakMassSpectrum().getIons());
								float intensityReference = extractedIonSignal.getAbundance(reference);
								float intensityTarget = extractedIonSignal.getAbundance(target);
								if(intensityReference != 0) {
									double actual = 100.0d / intensityReference * intensityTarget;
									traceRatio.setPeakMSD(peak);
									traceRatio.setActual(actual);
								}
							} catch(NumberFormatException e) {
								logger.warn(e);
							}
						}
					}
				}
			}
		}
		//
		return traceRatios;
	}

	@Override
	public IProcessingInfo applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		PeakIonClassifierSettings classifierSettings = PreferenceSupplier.getClassifierSettings();
		return applyClassifier(chromatogramSelection, classifierSettings, monitor);
	}
}
