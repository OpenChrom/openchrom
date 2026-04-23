/*******************************************************************************
 * Copyright (c) 2020, 2026 Lablicate GmbH.
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
package net.openchrom.xxd.process.supplier.templates.chromatogram;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.report.chromatogram.AbstractChromatogramReportGenerator;
import org.eclipse.chemclipse.chromatogram.xxd.report.settings.IChromatogramReportSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.notifier.UpdateNotifier;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.core.ReportWriter;
import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.ChromatogramReportSettings;

public class ChromatogramReport extends AbstractChromatogramReportGenerator {

	public static final String TOPIC_PROCESSING_FILE_CREATED = "processing/file/created/template/report"; // $NON-NLS-1$

	private static final Logger logger = Logger.getLogger(ChromatogramReport.class);

	public static final String DESCRIPTION = "Template Chromatogram Report";
	public static final String FILE_EXTENSION = ".tsv";
	public static final String FILE_NAME = DESCRIPTION.replaceAll("\\s", "") + FILE_EXTENSION;
	public static final String FILTER_EXTENSION = "*" + FILE_EXTENSION;
	public static final String FILTER_NAME = DESCRIPTION + " (*" + FILE_EXTENSION + ")";

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, List<IChromatogram> chromatograms, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof ChromatogramReportSettings reportSettings) {
				try {
					ReportWriter reportWriter = new ReportWriter();
					reportWriter.generate(file, append, chromatograms, reportSettings);
					processingInfo.setProcessingResult(file);
					if(reportSettings.isOpenReportAfterProcessing()) {
						UpdateNotifier.update(TOPIC_PROCESSING_FILE_CREATED, file);
					}
				} catch(IOException e) {
					logger.warn(e);
					processingInfo.addErrorMessage(DESCRIPTION, "The report couldn't be created. An error occured.");
				}
			} else {
				logger.warn("The settings are not of type: " + ChromatogramReportSettings.class);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, IChromatogram chromatogram, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		List<IChromatogram> chromatograms = getChromatogramList(chromatogram);
		return generate(file, append, chromatograms, settings, monitor);
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, IChromatogram chromatogram, IProgressMonitor monitor) {

		List<IChromatogram> chromatograms = getChromatogramList(chromatogram);
		ChromatogramReportSettings reportSettings = PreferenceSupplier.getChromatogramReportSettings();
		return generate(file, append, chromatograms, reportSettings, monitor);
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, List<IChromatogram> chromatograms, IProgressMonitor monitor) {

		ChromatogramReportSettings reportSettings = PreferenceSupplier.getChromatogramReportSettings();
		return generate(file, append, chromatograms, reportSettings, monitor);
	}

	private List<IChromatogram> getChromatogramList(IChromatogram chromatogram) {

		List<IChromatogram> chromatograms = new ArrayList<>();
		chromatograms.add(chromatogram);
		return chromatograms;
	}
}