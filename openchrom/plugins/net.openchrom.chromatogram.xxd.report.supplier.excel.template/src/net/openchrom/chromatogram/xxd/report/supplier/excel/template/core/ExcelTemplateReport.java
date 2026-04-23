/*******************************************************************************
 * Copyright (c) 2024, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.xxd.report.supplier.excel.template.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.report.chromatogram.AbstractChromatogramReportGenerator;
import org.eclipse.chemclipse.chromatogram.xxd.report.settings.IChromatogramReportSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.xxd.report.supplier.excel.template.io.ExcelTemplateReportWriter;
import net.openchrom.chromatogram.xxd.report.supplier.excel.template.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.xxd.report.supplier.excel.template.settings.ChromatogramReportSettings;

public class ExcelTemplateReport extends AbstractChromatogramReportGenerator {

	private static final Logger logger = Logger.getLogger(ExcelTemplateReport.class);

	public IProcessingInfo<File> report(File file, boolean append, List<IChromatogram> chromatograms, IChromatogramReportSettings settings) {

		IProcessingInfo<File> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof ChromatogramReportSettings reportSettings) {
				try {
					ExcelTemplateReportWriter chromatogramReport = new ExcelTemplateReportWriter();
					chromatogramReport.generate(file, append, chromatograms, reportSettings);
					processingInfo.setProcessingResult(file);
				} catch(IOException e) {
					logger.error(e);
					processingInfo.addErrorMessage("Excel Template Report", "The report couldn't be created.");
				}
			} else {
				logger.warn("The settings are not of type: " + ChromatogramReportSettings.class);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, IChromatogram chromatogram, IProgressMonitor monitor) {

		List<IChromatogram> chromatograms = getChromatogramList(chromatogram);
		ChromatogramReportSettings settings = PreferenceSupplier.getReportSettings();
		return report(file, append, chromatograms, settings);
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, List<IChromatogram> chromatograms, IProgressMonitor monitor) {

		ChromatogramReportSettings settings = PreferenceSupplier.getReportSettings();
		return report(file, append, chromatograms, settings);
	}

	@Override
	public IProcessingInfo<?> generate(File file, boolean append, IChromatogram chromatogram, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		List<IChromatogram> chromatograms = getChromatogramList(chromatogram);
		return report(file, append, chromatograms, settings);
	}

	@Override
	public IProcessingInfo<?> generate(File file, boolean append, List<IChromatogram> chromatograms, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		return report(file, append, chromatograms, settings);
	}

	protected List<IChromatogram> getChromatogramList(IChromatogram chromatogram) {

		List<IChromatogram> chromatograms = new ArrayList<>();
		chromatograms.add(chromatogram);
		return chromatograms;
	}
}
