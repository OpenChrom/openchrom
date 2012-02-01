/*******************************************************************************
 * Copyright (c) 2011, 2012 Philip (eselmeister) Wenig.
 * 
 * All rights reserved.
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.pdf.converter;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.converter.chromatogram.AbstractChromatogramExportConverter;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.ChromatogramExportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramExportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;

// import net.openchrom.logging.core.Logger;
public class PDFChromatogramExportConverter extends AbstractChromatogramExportConverter {

	// private static final Logger logger = Logger.getLogger(PDFChromatogramExportConverter.class);
	private static final String DESCRIPTION = "PDF Export Converter";

	@Override
	public IChromatogramExportConverterProcessingInfo convert(File file, IChromatogram chromatogram, IProgressMonitor monitor) {

		IChromatogramExportConverterProcessingInfo processingInfo = new ChromatogramExportConverterProcessingInfo();
		processingInfo.addErrorMessage(DESCRIPTION, "The PDF converter is not implemented.");
		return processingInfo;
		/*
		 * Check the key.
		 */
		// if(!Activator.isValidVersion()) {
		// processingInfo.addErrorMessage(DESCRIPTION, "The PDF chromatogram export converter has no valid licence.");
		// return processingInfo;
		// }
		/*
		 * Validate the file.
		 */
		// file = SpecificationValidator.validateInitialDirectory(file);
		// IProcessingInfo processingInfoValidate = super.validate(file);
		/*
		 * Don't process if errors have occurred.
		 */
		// if(processingInfoValidate.hasErrorMessages()) {
		// processingInfo.addMessages(processingInfoValidate);
		// } else {
		// file = SpecificationValidator.validateAgilentSpecification(file);
		// ChromatogramWriter writer = new ChromatogramWriter();
		// monitor.subTask(IConstants.EXPORT_AGILENT_CHROMATOGRAM);
		// try {
		// writer.writeChromatogram(file, chromatogram, monitor);
		// processingInfo.setFile(file);
		// } catch(Exception e) {
		// logger.warn(e);
		// processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
		// }
		// }
		// return processingInfo;
	}
}
