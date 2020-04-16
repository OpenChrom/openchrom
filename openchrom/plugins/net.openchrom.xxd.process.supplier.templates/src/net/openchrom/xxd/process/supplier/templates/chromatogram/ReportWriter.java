/*******************************************************************************
 * Copyright (c) 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.xxd.process.supplier.templates.chromatogram;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.model.ReportSetting;
import net.openchrom.xxd.process.supplier.templates.settings.ChromatogramReportSettings;

public class ReportWriter {

	public void generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings, IProgressMonitor monitor) throws IOException {

		try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, append))) {
			Map<ReportSetting, String> mappedResults = collectTemplateResults(chromatograms, reportSettings, monitor);
			for(Map.Entry<ReportSetting, String> entry : mappedResults.entrySet()) {
				printWriter.print(entry.getKey().getName());
				printWriter.print(" - ");
				printWriter.println(entry.getValue());
			}
			printWriter.flush();
		}
	}

	private Map<ReportSetting, String> collectTemplateResults(List<IChromatogram<? extends IPeak>> chromatograms, ChromatogramReportSettings reportSettings, IProgressMonitor monitor) {

		/*
		 * TODO Optimize
		 */
		Map<ReportSetting, String> mappedResults = new HashMap<>();
		for(ReportSetting reportSetting : reportSettings.getReportSettingsList()) {
			mappedResults.put(reportSetting, "");
		}
		//
		for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
			mapChromatogram(chromatogram, reportSettings, mappedResults);
			// TODO Setting map references
			for(IChromatogram<? extends IPeak> referenceChromatogram : chromatogram.getReferencedChromatograms()) {
				mapChromatogram(referenceChromatogram, reportSettings, mappedResults);
			}
		}
		return mappedResults;
	}

	private void mapChromatogram(IChromatogram<? extends IPeak> chromatogram, ChromatogramReportSettings reportSettings, Map<ReportSetting, String> mappedResults) {

		for(ReportSetting reportSetting : reportSettings.getReportSettingsList()) {
			IPeak peak = findPeak(chromatogram, reportSetting);
			String result = mappedResults.get(reportSetting);
			if(peak == null) {
				result += "\t";
			} else {
				result += Double.toString(peak.getIntegratedArea()) + "\t";
			}
			mappedResults.put(reportSetting, result);
		}
	}

	private IPeak findPeak(IChromatogram<? extends IPeak> chromatogram, ReportSetting reportSetting) {

		int startRetentionTime = reportSetting.getStartRetentionTime();
		int stopRetentionTime = reportSetting.getStopRetentionTime();
		List<? extends IPeak> peaks = chromatogram.getPeaks(startRetentionTime, stopRetentionTime);
		/*
		 * TODO - enhanced retrieval
		 */
		if(peaks != null && peaks.size() > 0) {
			return peaks.get(0);
		} else {
			return null;
		}
	}
}
