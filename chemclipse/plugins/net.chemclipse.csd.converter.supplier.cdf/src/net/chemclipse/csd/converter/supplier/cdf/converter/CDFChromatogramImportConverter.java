/*******************************************************************************
 * Copyright (c) 2014, 2015 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.csd.converter.supplier.cdf.converter;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import net.chemclipse.converter.processing.chromatogram.ChromatogramOverviewImportConverterProcessingInfo;
import net.chemclipse.converter.processing.chromatogram.IChromatogramOverviewImportConverterProcessingInfo;
import net.chemclipse.csd.converter.chromatogram.AbstractChromatogramFIDImportConverter;
import net.chemclipse.csd.converter.io.IChromatogramFIDReader;
import net.chemclipse.csd.converter.processing.chromatogram.ChromatogramFIDImportConverterProcessingInfo;
import net.chemclipse.csd.converter.processing.chromatogram.IChromatogramFIDImportConverterProcessingInfo;
import net.chemclipse.csd.converter.supplier.cdf.internal.converter.IConstants;
import net.chemclipse.csd.converter.supplier.cdf.internal.converter.SpecificationValidator;
import net.chemclipse.csd.converter.supplier.cdf.io.ChromatogramReader;
import net.chemclipse.csd.model.core.IChromatogramCSD;
import net.chemclipse.model.core.IChromatogramOverview;
import net.chemclipse.logging.core.Logger;
import net.chemclipse.processing.core.IProcessingInfo;

public class CDFChromatogramImportConverter extends AbstractChromatogramFIDImportConverter {

	private static final Logger logger = Logger.getLogger(CDFChromatogramImportConverter.class);
	private static final String DESCRIPTION = "NetCDF Import Converter";

	@Override
	public IChromatogramFIDImportConverterProcessingInfo convert(File file, IProgressMonitor monitor) {

		IChromatogramFIDImportConverterProcessingInfo processingInfo = new ChromatogramFIDImportConverterProcessingInfo();
		/*
		 * Validate the file.
		 */
		IProcessingInfo processingInfoValidate = super.validate(file);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			/*
			 * Read the chromatogram.
			 */
			file = SpecificationValidator.validateCDFSpecification(file);
			IChromatogramFIDReader reader = new ChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CDF_CHROMATOGRAM);
			try {
				IChromatogramCSD chromatogram = reader.read(file, monitor);
				processingInfo.setChromatogram(chromatogram);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}

	@Override
	public IChromatogramOverviewImportConverterProcessingInfo convertOverview(File file, IProgressMonitor monitor) {

		IChromatogramOverviewImportConverterProcessingInfo processingInfo = new ChromatogramOverviewImportConverterProcessingInfo();
		/*
		 * Validate the file.
		 */
		IProcessingInfo processingInfoValidate = super.validate(file);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			/*
			 * Read the chromatogram overview.
			 */
			file = SpecificationValidator.validateCDFSpecification(file);
			IChromatogramFIDReader reader = new ChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CDF_CHROMATOGRAM_OVERVIEW);
			try {
				IChromatogramOverview chromatogramOverview = reader.readOverview(file, monitor);
				processingInfo.setChromatogramOverview(chromatogramOverview);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
