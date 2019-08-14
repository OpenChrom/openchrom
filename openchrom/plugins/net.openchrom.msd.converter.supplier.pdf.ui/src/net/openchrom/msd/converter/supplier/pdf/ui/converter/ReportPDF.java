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
 * Christoph Läubrich - enhance settings, support merge
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.ui.converter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.eclipse.chemclipse.chromatogram.xxd.report.chromatogram.AbstractChromatogramReportGenerator;
import org.eclipse.chemclipse.chromatogram.xxd.report.settings.IChromatogramReportSettings;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import net.openchrom.msd.converter.supplier.pdf.ui.internal.io.ReportConverterPDF;
import net.openchrom.msd.converter.supplier.pdf.ui.settings.ReportSettings;

public class ReportPDF extends AbstractChromatogramReportGenerator {

	/**
	 * Use 100 MB of main memory to merge pdfs
	 */
	private static final int MAX_MERGE_MEMORY = 1024 * 1024 * 100;
	private static final String REPORT_PDF = "ReportPDF";

	//
	@Override
	public IProcessingInfo generate(File file, boolean append, List<IChromatogram<? extends IPeak>> chromatograms, IChromatogramReportSettings chromatogramReportSettings, IProgressMonitor monitor) {

		SubMonitor subMonitor = SubMonitor.convert(monitor, 100 * chromatograms.size());
		ReportSettings settings;
		if(chromatogramReportSettings instanceof ReportSettings) {
			settings = (ReportSettings)chromatogramReportSettings;
		} else {
			settings = new ReportSettings();
		}
		IProcessingInfo processingInfo = new ProcessingInfo();
		try {
			for(IChromatogram<? extends IPeak> chromatogram : chromatograms) {
				ReportConverterPDF pdfSupport = new ReportConverterPDF(settings);
				if(append && file.exists()) {
					File newFile = File.createTempFile("report", ".pdf");
					newFile.deleteOnExit();
					try {
						pdfSupport.createPDF(newFile, chromatogram, subMonitor.split(100));
						File oldFile = new File(file.getParentFile(), file.getName() + "." + UUID.randomUUID() + ".pdf");
						String destinationFile = file.getAbsolutePath();
						if(file.renameTo(oldFile)) {
							PDFMergerUtility mergerUtility = new PDFMergerUtility();
							mergerUtility.setDestinationFileName(destinationFile);
							mergerUtility.addSource(oldFile);
							mergerUtility.addSource(newFile);
							mergerUtility.mergeDocuments(MemoryUsageSetting.setupMixed(MAX_MERGE_MEMORY));
							if(!oldFile.delete()) {
								oldFile.deleteOnExit();
								processingInfo.addWarnMessage(REPORT_PDF, "can't delete old file " + oldFile.getAbsolutePath());
							}
						} else {
							processingInfo.addErrorMessage(REPORT_PDF, "can't move file " + file.getAbsolutePath() + " to " + oldFile);
						}
					} finally {
						newFile.delete();
					}
				} else {
					pdfSupport.createPDF(file, chromatogram, subMonitor.split(100));
				}
			}
		} catch(IOException e) {
			processingInfo.addErrorMessage(REPORT_PDF, "creation of PDF failed", e);
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
