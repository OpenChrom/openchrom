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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.report.chromatogram.AbstractChromatogramReportGenerator;
import org.eclipse.chemclipse.chromatogram.xxd.report.settings.IChromatogramReportSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.xxd.process.supplier.templates.preferences.PreferenceSupplier;
import net.openchrom.xxd.process.supplier.templates.settings.ChromatogramReportSettings;

public class ChromatogramReport extends AbstractChromatogramReportGenerator {

	private static final Logger logger = Logger.getLogger(ChromatogramReport.class);

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			if(settings instanceof ChromatogramReportSettings) {
				ChromatogramReportSettings reportSettings = (ChromatogramReportSettings)settings;
				ReportWriter reportWriter = new ReportWriter();
				try {
					reportWriter.generate(file, append, chromatograms, reportSettings, monitor);
					processingInfo.setProcessingResult(file);
				} catch(IOException e) {
					logger.warn(e);
					processingInfo.addErrorMessage("Template Chromatogram Report", "The report couldn't be created. An error occured.");
				}
			} else {
				logger.warn("The settings are not of type: " + ChromatogramReportSettings.class);
			}
		}
		//
		return processingInfo;
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, IChromatogram<? extends IPeak> chromatogram, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		List<IChromatogram<? extends IPeak>> chromatograms = getChromatogramList(chromatogram);
		return generate(file, append, chromatograms, settings, monitor);
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		List<IChromatogram<? extends IPeak>> chromatograms = getChromatogramList(chromatogram);
		ChromatogramReportSettings reportSettings = PreferenceSupplier.getChromatogramReportSettings();
		return generate(file, append, chromatograms, reportSettings, monitor);
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, IProgressMonitor monitor) {

		ChromatogramReportSettings reportSettings = PreferenceSupplier.getChromatogramReportSettings();
		return generate(file, append, chromatograms, reportSettings, monitor);
	}

	private List<IChromatogram<? extends IPeak>> getChromatogramList(IChromatogram<? extends IPeak> chromatogram) {

		List<IChromatogram<? extends IPeak>> chromatograms = new ArrayList<IChromatogram<? extends IPeak>>();
		chromatograms.add(chromatogram);
		return chromatograms;
	}
}
