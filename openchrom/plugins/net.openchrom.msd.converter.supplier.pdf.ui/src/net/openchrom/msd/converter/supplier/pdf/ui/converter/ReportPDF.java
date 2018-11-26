/*******************************************************************************
 * Copyright (c) 2018 Lablicate GmbH.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.converter;

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
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.pdf.ui.internal.io.PDFSupport;
import net.openchrom.msd.converter.supplier.pdf.ui.settings.ReportSettings;

public class ReportPDF extends AbstractChromatogramReportGenerator {

	private static final Logger logger = Logger.getLogger(ReportPDF.class);

	//
	@Override
	public IProcessingInfo generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, IChromatogramReportSettings chromatogramReportSettings, IProgressMonitor monitor) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		try {
			if(chromatograms.size() > 0) {
				PDFSupport pdfSupport = new PDFSupport();
				pdfSupport.createPDF(file, chromatograms.get(0), monitor);
			}
		} catch(IOException e) {
			logger.warn(e);
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo generate(File file, boolean append, IChromatogram<? extends IPeak> chromatogram, IChromatogramReportSettings chromatogramReportSettings, IProgressMonitor monitor) {

		List<IChromatogram<? extends IPeak>> chromatograms = new ArrayList<>();
		chromatograms.add(chromatogram);
		return generate(file, append, chromatograms, chromatogramReportSettings, monitor);
	}

	@Override
	public IProcessingInfo generate(File file, boolean append, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		ReportSettings reportSettings = new ReportSettings();
		List<IChromatogram<? extends IPeak>> chromatograms = new ArrayList<>();
		chromatograms.add(chromatogram);
		return generate(file, append, chromatograms, reportSettings, monitor);
	}

	@Override
	public IProcessingInfo generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, IProgressMonitor monitor) {

		ReportSettings reportSettings = new ReportSettings();
		return generate(file, append, chromatograms, reportSettings, monitor);
	}
}
