/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.xic.IExtractedIonSignal;
import org.eclipse.chemclipse.wsd.model.core.IScanWSD;
import org.eclipse.chemclipse.wsd.model.xwc.IExtractedWavelengthSignal;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;
import net.openchrom.xxd.process.supplier.templates.settings.PeakDetectorSettings;
import net.openchrom.xxd.process.supplier.templates.settings.PeakReviewSettings;

public class ChromatogramValidator {

	public static List<DetectorSetting> filterValidDetectorSettings(IChromatogram<?> chromatogram, PeakDetectorSettings settings) {

		List<DetectorSetting> detectorSettings = new ArrayList<>();
		for(DetectorSetting detectorSetting : settings.getDetectorSettingsList()) {
			if(chromatogramContainsTraces(chromatogram, detectorSetting.getTraces())) {
				detectorSettings.add(detectorSetting);
			}
		}
		//
		return detectorSettings;
	}

	public static List<ReviewSetting> filterValidReviewSettings(IChromatogram<?> chromatogram, PeakReviewSettings settings) {

		List<ReviewSetting> reviewSettings = new ArrayList<>();
		for(ReviewSetting reviewSetting : settings.getReviewSettingsList()) {
			if(chromatogramContainsTraces(chromatogram, reviewSetting.getTraces())) {
				reviewSettings.add(reviewSetting);
			}
		}
		//
		return reviewSettings;
	}

	public static boolean chromatogramContainsTraces(IChromatogram<?> chromatogram, String tracesSetting) {

		boolean tracesAvailable = false;
		//
		if(!tracesSetting.isEmpty()) {
			PeakDetectorListUtil peakDetectorListUtil = new PeakDetectorListUtil();
			Set<Integer> traces = peakDetectorListUtil.extractTraces(tracesSetting);
			exitloop:
			for(IScan scan : chromatogram.getScans()) {
				if(scan instanceof IScanMSD) {
					IScanMSD scanMSD = (IScanMSD)scan;
					IExtractedIonSignal extractedIonSignal = scanMSD.getExtractedIonSignal();
					for(int trace : traces) {
						if(extractedIonSignal.getAbundance(trace) > 0) {
							tracesAvailable = true;
							break exitloop;
						}
					}
				} else if(scan instanceof IScanWSD) {
					IScanWSD scanWSD = (IScanWSD)scan;
					IExtractedWavelengthSignal extractedWavelengthSignal = scanWSD.getExtractedWavelengthSignal();
					for(int trace : traces) {
						if(extractedWavelengthSignal.getAbundance(trace) != 0) {
							tracesAvailable = true;
							break exitloop;
						}
					}
				}
			}
		} else {
			tracesAvailable = true; // TIC
		}
		//
		return tracesAvailable;
	}
}