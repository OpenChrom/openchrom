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
import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.implementation.MeasurementResult;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatioResult;
import net.openchrom.xxd.classifier.supplier.ratios.model.qual.QualRatios;
import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.QualRatioSettings;

public class QualRatioClassifier extends AbstractRatioClassifier {

	public static final String CLASSIFIER_ID = "net.openchrom.xxd.classifier.supplier.ratios.qual";

	@Override
	public IProcessingInfo<IChromatogramClassifierResult> applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IChromatogramClassifierSettings chromatogramClassifierSettings, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramClassifierResult> processingInfo = validate(chromatogramSelection, chromatogramClassifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			if(chromatogramClassifierSettings instanceof QualRatioSettings) {
				/*
				 * Calculate the peak quality
				 */
				QualRatios qualRatios = calculateRatios(chromatogramSelection, (QualRatioSettings)chromatogramClassifierSettings);
				QualRatioResult classifierResult = new QualRatioResult(ResultStatus.OK, "The chromatogram peaks have been classified.", qualRatios);
				IMeasurementResult measurementResult = new MeasurementResult("Qual Ratio Classifier", CLASSIFIER_ID, "Qual Ratios", qualRatios);
				chromatogramSelection.getChromatogram().addMeasurementResult(measurementResult);
				processingInfo.setProcessingResult(classifierResult);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<IChromatogramClassifierResult> applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		QualRatioSettings settings = PreferenceSupplier.getSettingsQual();
		return applyClassifier(chromatogramSelection, settings, monitor);
	}

	private QualRatios calculateRatios(IChromatogramSelectionMSD chromatogramSelection, QualRatioSettings classifierSettings) {

		QualRatios ratios = new QualRatios();
		//
		List<? extends IPeak> peaks = chromatogramSelection.getChromatogram().getPeaks();
		for(IPeak peak : peaks) {
			IPeakModel peakModel = peak.getPeakModel();
			String comment;
			if(peakModel.getLeading() <= 2 && peakModel.getTailing() <= 2) {
				comment = "Peak is OK.";
			} else if(peakModel.getLeading() <= 2 && peakModel.getTailing() <= 2) {
				comment = "Peak might contain additional peaks.";
			} else {
				comment = "Peak should be reviewed.";
			}
			ratios.add(new QualRatio(peak, comment));
		}
		//
		return ratios;
	}
}
