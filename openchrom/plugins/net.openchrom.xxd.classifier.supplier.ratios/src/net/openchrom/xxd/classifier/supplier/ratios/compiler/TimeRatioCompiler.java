/*******************************************************************************
 * Copyright (c) 2019, 2022 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.classifier.supplier.ratios.compiler;

import java.io.File;
import java.util.List;

import org.eclipse.chemclipse.model.comparator.IdentificationTargetComparator;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.model.core.IPeakModel;
import org.eclipse.chemclipse.model.core.IScan;
import org.eclipse.chemclipse.support.comparator.SortOrder;

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
			float retentionIndex = peak.getPeakModel().getPeakMaximum().getRetentionIndex();
			IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
			String name = getName(peak, identificationTargetComparator);
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
			float retentionIndex = scan.getRetentionIndex();
			IdentificationTargetComparator identificationTargetComparator = new IdentificationTargetComparator(SortOrder.DESC, retentionIndex);
			String name = getName(scan, identificationTargetComparator);
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