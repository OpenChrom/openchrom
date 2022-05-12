/*******************************************************************************
 * Copyright (c) 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.support;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IChromatogramPeak;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.support.RetentionIndexMap;

import net.openchrom.xxd.process.supplier.templates.model.DetectorSetting;
import net.openchrom.xxd.process.supplier.templates.model.PositionDirective;
import net.openchrom.xxd.process.supplier.templates.model.ReviewSetting;

public class RetentionIndexSupport {

	public static RetentionIndexMap getRetentionIndexMap(List<? extends IPeak> peaks) {

		RetentionIndexMap retentionIndexMap = new RetentionIndexMap();
		IChromatogram<?> chromatogram = getChromatogram(peaks);
		if(chromatogram != null) {
			retentionIndexMap.update(chromatogram);
		}
		//
		return retentionIndexMap;
	}

	public static List<DetectorSetting> adjustDetectorSettings(IChromatogram<?> chromatogram, List<DetectorSetting> settings) {

		List<DetectorSetting> settingsAdjusted = new ArrayList<>();
		//
		RetentionIndexMap retentionIndexMap = new RetentionIndexMap(chromatogram);
		for(DetectorSetting setting : settings) {
			if(setting.getPositionDirective().equals(PositionDirective.RETENTION_INDEX)) {
				/*
				 * Translate RI to retention time minutes.
				 */
				double positionStart = retentionIndexMap.getRetentionTime((int)Math.round(setting.getPositionStart()));
				double positionStop = retentionIndexMap.getRetentionTime((int)Math.round(setting.getPositionStop()));
				if(positionStart != RetentionIndexMap.VALUE_NOT_AVAILABLE && positionStop != RetentionIndexMap.VALUE_NOT_AVAILABLE) {
					setting.setPositionDirective(PositionDirective.RETENTION_TIME_MIN);
					setting.setPositionStart(positionStart / IChromatogram.MINUTE_CORRELATION_FACTOR);
					setting.setPositionStop(positionStop / IChromatogram.MINUTE_CORRELATION_FACTOR);
					settingsAdjusted.add(setting);
				}
			} else {
				settingsAdjusted.add(setting);
			}
		}
		//
		return settingsAdjusted;
	}

	public static List<ReviewSetting> adjustReviewSettings(IChromatogram<?> chromatogram, List<ReviewSetting> settings) {

		List<ReviewSetting> settingsAdjusted = new ArrayList<>();
		//
		RetentionIndexMap retentionIndexMap = new RetentionIndexMap(chromatogram);
		for(ReviewSetting setting : settings) {
			if(setting.getPositionDirective().equals(PositionDirective.RETENTION_INDEX)) {
				/*
				 * Translate RI to retention time minutes.
				 */
				double positionStart = retentionIndexMap.getRetentionTime((int)Math.round(setting.getPositionStart()));
				double positionStop = retentionIndexMap.getRetentionTime((int)Math.round(setting.getPositionStop()));
				if(positionStart != RetentionIndexMap.VALUE_NOT_AVAILABLE && positionStop != RetentionIndexMap.VALUE_NOT_AVAILABLE) {
					setting.setPositionDirective(PositionDirective.RETENTION_TIME_MIN);
					setting.setPositionStart(positionStart / IChromatogram.MINUTE_CORRELATION_FACTOR);
					setting.setPositionStop(positionStop / IChromatogram.MINUTE_CORRELATION_FACTOR);
					settingsAdjusted.add(setting);
				}
			} else {
				settingsAdjusted.add(setting);
			}
		}
		//
		return settingsAdjusted;
	}

	private static IChromatogram<?> getChromatogram(List<? extends IPeak> peaks) {

		for(IPeak peak : peaks) {
			if(peak instanceof IChromatogramPeak) {
				IChromatogramPeak chromatogramPeak = (IChromatogramPeak)peak;
				return chromatogramPeak.getChromatogram();
			}
		}
		//
		return null;
	}
}