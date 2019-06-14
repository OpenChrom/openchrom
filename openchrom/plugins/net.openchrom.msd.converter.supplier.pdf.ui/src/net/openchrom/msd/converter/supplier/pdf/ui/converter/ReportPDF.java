/*******************************************************************************
 * Copyright (c) 2018,2019 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - enhance settings
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.converter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.chemclipse.chromatogram.xxd.report.chromatogram.AbstractChromatogramReportGenerator;
import org.eclipse.chemclipse.chromatogram.xxd.report.settings.IChromatogramReportSettings;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.pdf.ui.internal.io.ReportConverterPDF;
import net.openchrom.msd.converter.supplier.pdf.ui.settings.ReportSettings;

public class ReportPDF extends AbstractChromatogramReportGenerator {

	private static final Logger logger = Logger.getLogger(ReportPDF.class);

	//
	@Override
	public IProcessingInfo generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, IChromatogramReportSettings chromatogramReportSettings, IProgressMonitor monitor) {

		ReportSettings settings;
		if(chromatogramReportSettings instanceof ReportSettings) {
			settings = (ReportSettings)chromatogramReportSettings;
		} else {
			settings = new ReportSettings();
		}
		IProcessingInfo processingInfo = new ProcessingInfo();
		try {
			if(chromatograms.size() > 0) {
				ReportConverterPDF pdfSupport = new ReportConverterPDF(settings);
				pdfSupport.createPDF(file, chromatograms.get(0), monitor);
			}
		} catch(IOException e) {
			logger.warn(e);
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo generate(File file, boolean append, IChromatogram<? extends IPeak> chromatogram, IChromatogramReportSettings chromatogramReportSettings, IProgressMonitor monitor) {

		return generate(file, append, Collections.singletonList(chromatogram), chromatogramReportSettings, monitor);
	}

	@Override
	public IProcessingInfo generate(File file, boolean append, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		return generate(file, append, Collections.singletonList(chromatogram), null, monitor);
	}

	@Override
	public IProcessingInfo generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, IProgressMonitor monitor) {

		return generate(file, append, chromatograms, null, monitor);
	}
}
