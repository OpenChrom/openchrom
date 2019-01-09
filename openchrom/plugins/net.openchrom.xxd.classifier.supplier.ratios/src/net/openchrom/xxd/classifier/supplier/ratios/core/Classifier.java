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

import org.eclipse.chemclipse.chromatogram.msd.classifier.core.AbstractChromatogramClassifier;
import org.eclipse.chemclipse.chromatogram.msd.classifier.settings.IChromatogramClassifierSettings;
import org.eclipse.chemclipse.model.core.IMeasurementResult;
import org.eclipse.chemclipse.model.implementation.MeasurementResult;
import org.eclipse.chemclipse.msd.model.core.selection.IChromatogramSelectionMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.classifier.supplier.ratios.preferences.PreferenceSupplier;
import net.openchrom.xxd.classifier.supplier.ratios.settings.ClassifierSettings;

public class Classifier extends AbstractChromatogramClassifier {

	String IDENTIFIER = "net.openchrom.xxd.classifier.supplier.ratios";

	@Override
	public IProcessingInfo applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IChromatogramClassifierSettings chromatogramClassifierSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = validate(chromatogramSelection, chromatogramClassifierSettings);
		if(!processingInfo.hasErrorMessages()) {
			if(chromatogramClassifierSettings instanceof ClassifierSettings) {
				// ClassifierSettings classifierSettings = (ClassifierSettings)chromatogramClassifierSettings;
				IMeasurementResult measurementResult = new MeasurementResult("Ratio Classifier", IDENTIFIER, "Area Ratios", new Object());
				chromatogramSelection.getChromatogram().addMeasurementResult(measurementResult);
				processingInfo.setProcessingResult(measurementResult);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo applyClassifier(IChromatogramSelectionMSD chromatogramSelection, IProgressMonitor monitor) {

		ClassifierSettings classifierSettings = PreferenceSupplier.getClassifierSettings();
		return applyClassifier(chromatogramSelection, classifierSettings, monitor);
	}
}
