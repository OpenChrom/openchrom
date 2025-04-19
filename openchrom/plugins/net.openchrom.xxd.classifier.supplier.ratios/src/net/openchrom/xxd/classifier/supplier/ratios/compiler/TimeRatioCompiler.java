/*******************************************************************************
 * Copyright (c) 2019, 2025 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.compiler;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;

import net.openchrom.xxd.classifier.supplier.ratios.core.ITemplateExport;
import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatio;
import net.openchrom.xxd.classifier.supplier.ratios.model.time.TimeRatios;
import net.openchrom.xxd.classifier.supplier.ratios.settings.TimeRatioExportSettings;

public class TimeRatioCompiler implements ITemplateExport {

	public boolean compilePeaks(File file, List<? extends IPeak> peaks, TimeRatioExportSettings timeRatioExportSettings) {

		TimeRatios settings = new TimeRatios();
		/*
		 * It's ok, the time ratio uses the error scheme
		 */
		float deviationWarn = timeRatioExportSettings.getAllowedDeviationOk();
		float deviationError = timeRatioExportSettings.getAllowedDeviationWarn();
		//
		for(IPeak peak : peaks) {
			String name = getName(peak);
			if(!name.isEmpty()) {
				IPeakModel peakModel = peak.getPeakModel();
				TimeRatio timeRatio = new TimeRatio();
				timeRatio.setName(name);
				timeRatio.setExpectedRetentionTime(peakModel.getRetentionTimeAtPeakMaximum());
				timeRatio.setDeviationWarn(deviationWarn);
				timeRatio.setDeviationError(deviationError);
				settings.add(timeRatio);
			}
		}
		//
		return settings.exportItems(file);
	}

	public boolean compileScans(File file, List<? extends IScan> scans, TimeRatioExportSettings timeRatioExportSettings) {

		TimeRatios settings = new TimeRatios();
		/*
		 * It's ok, the time ratio uses the error scheme
		 */
		float deviationWarn = timeRatioExportSettings.getAllowedDeviationOk();
		float deviationError = timeRatioExportSettings.getAllowedDeviationWarn();
		//
		for(IScan scan : scans) {
			String name = getName(scan);
			if(!name.isEmpty()) {
				TimeRatio timeRatio = new TimeRatio();
				timeRatio.setName(name);
				timeRatio.setExpectedRetentionTime(scan.getRetentionTime());
				timeRatio.setDeviationWarn(deviationWarn);
				timeRatio.setDeviationError(deviationError);
				settings.add(timeRatio);
			}
		}
		//
		return settings.exportItems(file);
	}
}