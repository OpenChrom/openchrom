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
import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.implementation.MeasurementResult;
import org.eclipse.chemclipse.model.quantitation.IQuantitationEntry;
import org.eclipse.chemclipse.model.selection.IChromatogramSelection;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.PeakRatioResult;
import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.quant.QuantRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.QuantRatioSettings;

public class QuantRatioClassifier extends AbstractRatioClassifier {

	public static final String CLASSIFIER_ID = "net.openchrom.xxd.classifier.supplier.ratios.quant";

	@Override
	public IProcessingInfo<IChromatogramClassifierResult> applyClassifier(IChromatogramSelection<?, ?> chromatogramSelection, IChromatogramClassifierSettings chromatogramClassifierSettings, IProgressMonitor monitor) {

		QuantRatioSettings settings;
		if(chromatogramClassifierSettings instanceof QuantRatioSettings) {
			settings = (QuantRatioSettings)chromatogramClassifierSettings;
		} else {
			settings = PreferenceSupplier.getSettingsQuant();
		}
		IProcessingInfo<IChromatogramClassifierResult> processingInfo = validate(chromatogramSelection, chromatogramClassifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			/*
			 * Calculate the result.
			 */
			QuantRatios traceRatios = calculateRatios(chromatogramSelection.getChromatogram(), settings);
			PeakRatioResult classifierResult = new PeakRatioResult(ResultStatus.OK, "The chromatogram peaks have been classified.", traceRatios);
			IMeasurementResult measurementResult = new MeasurementResult("Quant Ratio Classifier", CLASSIFIER_ID, "Quant Ratios", traceRatios);
			chromatogramSelection.getChromatogram().addMeasurementResult(measurementResult);
			processingInfo.setProcessingResult(classifierResult);
		}
		return processingInfo;
	}

	private QuantRatios calculateRatios(IChromatogram<?> chromatogram, QuantRatioSettings classifierSettings) {

		QuantRatios ratios = classifierSettings.getRatioSettings();
		//
		List<? extends IPeak> peaks = chromatogram.getPeaks();
		for(QuantRatio ratio : ratios) {
			for(IPeak peak : peaks) {
				if(isPeakMatch(peak, ratio)) {
					IQuantitationEntry quantitationEntry = getQuantitationEntry(peak, ratio);
					if(quantitationEntry != null) {
						double concentration = quantitationEntry.getConcentration();
						double expectedConcentration = ratio.getExpectedConcentration();
						if(concentration != 0) {
							ratio.setPeak(peak);
							ratio.setConcentration(concentration);
							double deviation = (Math.abs(expectedConcentration - concentration) / concentration) * 100.0d;
							ratio.setDeviation(deviation);
						}
					}
				}
			}
		}
		//
		return ratios;
	}

	private IQuantitationEntry getQuantitationEntry(IPeak peak, QuantRatio quantRatio) {

		if(peak != null) {
			for(IQuantitationEntry quantitationEntry : peak.getQuantitationEntries()) {
				if(quantitationEntry.getName().equals(quantRatio.getQuantitationName()) && quantitationEntry.getConcentrationUnit().equals(quantRatio.getConcentrationUnit())) {
					return quantitationEntry;
				}
			}
		}
		return null;
	}
}
