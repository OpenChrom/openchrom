/*******************************************************************************
 * Copyright (c) 2011, 2014 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf.converter;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.converter.processing.chromatogram.ChromatogramOverviewImportConverterProcessingInfo;
import net.openchrom.chromatogram.converter.processing.chromatogram.IChromatogramOverviewImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.chromatogram.AbstractChromatogramMSDImportConverter;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.ChromatogramMSDImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.supplier.pdf.Activator;

public class ChromatogramImportConverter extends AbstractChromatogramMSDImportConverter {

	private static final String DESCRIPTION = "PDF Import Converter";
	private static final String EXPORT_ERROR = "The PDF converter is not able to import chromatograms.";

	@Override
	public IChromatogramMSDImportConverterProcessingInfo convert(File file, IProgressMonitor monitor) {

		IChromatogramMSDImportConverterProcessingInfo processingInfo = new ChromatogramMSDImportConverterProcessingInfo();
		/*
		 * Check the key.
		 */
		if(!Activator.isValidVersion()) {
			processingInfo.addErrorMessage(DESCRIPTION, "The PDF chromatogram overview import converter has no valid licence.");
			return processingInfo;
		}
		processingInfo.addErrorMessage(DESCRIPTION, EXPORT_ERROR);
		return processingInfo;
	}

	@Override
	public IChromatogramOverviewImportConverterProcessingInfo convertOverview(File file, IProgressMonitor monitor) {

		IChromatogramOverviewImportConverterProcessingInfo processingInfo = new ChromatogramOverviewImportConverterProcessingInfo();
		/*
		 * Check the key.
		 */
		if(!Activator.isValidVersion()) {
			processingInfo.addErrorMessage(DESCRIPTION, "The PDF chromatogram overview import converter has no valid licence.");
			return processingInfo;
		}
		processingInfo.addErrorMessage(DESCRIPTION, EXPORT_ERROR);
		return processingInfo;
	}
}
