/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
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
package net.openchrom.chromatogram.xxd.report.supplier.masc.escape.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.report.chromatogram.AbstractChromatogramReportGenerator;
import org.eclipse.chemclipse.chromatogram.xxd.report.settings.IChromatogramReportSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.xxd.report.supplier.masc.escape.io.MascEscapeReportWriter;
import net.openchrom.chromatogram.xxd.report.supplier.masc.escape.preferences.PreferenceSupplier;
import net.openchrom.chromatogram.xxd.report.supplier.masc.escape.settings.ChromatogramReportSettings;

public class MascEscapeReport extends AbstractChromatogramReportGenerator {

	private static final Logger logger = Logger.getLogger(MascEscapeReport.class);

	public IProcessingInfo<File> report(File file, IChromatogram chromatogram, IChromatogramReportSettings settings) {

		IProcessingInfo<File> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {

			if(settings instanceof ChromatogramReportSettings reportSettings) {
				try {
					MascEscapeReportWriter chromatogramReport = new MascEscapeReportWriter();
					if(chromatogram instanceof IChromatogramMSD chromatogramMSD) {
						chromatogramReport.generate(file, chromatogramMSD, reportSettings);
						processingInfo.setProcessingResult(file);
					} else {
						processingInfo.addErrorMessage("ESCAPE Report", "This report expects MSD chromatograms");
						return processingInfo;
					}
				} catch(IOException e) {
					logger.error(e);
					processingInfo.addErrorMessage("MaSC Report", "The report couldn't be created.");
				}
			} else {
				logger.warn("The settings are not of type: " + ChromatogramReportSettings.class);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, IChromatogram chromatogram, IProgressMonitor monitor) {

		IChromatogramReportSettings settings = PreferenceSupplier.getReportSettings();
		return report(file, chromatogram, settings);
	}

	@Override
	public IProcessingInfo<File> generate(File file, boolean append, List<IChromatogram> chromatograms, IProgressMonitor monitor) {

		IChromatogramReportSettings settings = PreferenceSupplier.getReportSettings();
		return report(file, chromatograms.getFirst(), settings);
	}

	@Override
	public IProcessingInfo<?> generate(File file, boolean append, IChromatogram chromatogram, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		return report(file, chromatogram, settings);
	}

	@Override
	public IProcessingInfo<?> generate(File file, boolean append, List<IChromatogram> chromatograms, IChromatogramReportSettings settings, IProgressMonitor monitor) {

		return report(file, chromatograms.getFirst(), settings);
	}
}
