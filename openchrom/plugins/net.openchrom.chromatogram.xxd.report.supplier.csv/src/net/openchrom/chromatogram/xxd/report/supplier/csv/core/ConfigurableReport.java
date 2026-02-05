/*******************************************************************************
 * Copyright (c) 2012, 2025 Lablicate GmbH.
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
package net.openchrom.chromatogram.xxd.report.supplier.csv.core;

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

import net.openchrom.chromatogram.xxd.report.supplier.csv.io.ConfigurableReportWriter;
import net.openchrom.chromatogram.xxd.report.supplier.csv.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.xxd.report.supplier.csv.settings.ChromatogramReportSettings;

public class ConfigurableReport extends AbstractChromatogramReportGenerator {

	private static final Logger logger = Logger.getLogger(ConfigurableReport.class);

	public IProcessingInfo<File> report(File file, boolean append, List<IChromatogram> chromatograms, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof ChromatogramReportSettings reportSettings) {
				try {
					ConfigurableReportWriter chromatogramReport = new ConfigurableReportWriter();
					chromatogramReport.generate(file, append, chromatograms, reportSettings);
					processingInfo.setProcessingResult(file);
				} catch(IOException e) {
					logger.error(e);
					processingInfo.addErrorMessage("OpenChrom CSV Chromatogram Report", "The report couldn't be created.");
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
		return report(file, append, chromatograms, settings, monitor);
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, List<IChromatogram> chromatograms, IProgressMonitor monitor) {

		ChromatogramReportSettings settings = PreferenceSupplier.getReportSettings();
		return report(file, append, chromatograms, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> generate(File file, boolean append, IChromatogram chromatogram, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		List<IChromatogram> chromatograms = getChromatogramList(chromatogram);
		return report(file, append, chromatograms, settings, monitor);
	}

	@Override
	public IProcessingInfo<?> generate(File file, boolean append, List<IChromatogram> chromatograms, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		return report(file, append, chromatograms, settings, monitor);
	}

	protected List<IChromatogram> getChromatogramList(IChromatogram chromatogram) {

		List<IChromatogram> chromatograms = new ArrayList<>();
		chromatograms.add(chromatogram);
		return chromatograms;
	}
}
