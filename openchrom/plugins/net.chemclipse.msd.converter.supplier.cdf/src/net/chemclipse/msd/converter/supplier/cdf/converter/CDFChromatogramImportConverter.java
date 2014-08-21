/*******************************************************************************
 * Copyright (c) 2013, 2014 Dr. Philip Wenig.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.chemclipse.msd.converter.supplier.cdf.converter;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import net.chemclipse.converter.processing.chromatogram.ChromatogramOverviewImportConverterProcessingInfo;
import net.chemclipse.converter.processing.chromatogram.IChromatogramOverviewImportConverterProcessingInfo;
import net.chemclipse.chromatogram.model.core.IChromatogramOverview;
import net.chemclipse.msd.converter.chromatogram.AbstractChromatogramMSDImportConverter;
import net.chemclipse.msd.converter.io.IChromatogramMSDReader;
import net.chemclipse.msd.converter.processing.chromatogram.ChromatogramMSDImportConverterProcessingInfo;
import net.chemclipse.msd.converter.processing.chromatogram.IChromatogramMSDImportConverterProcessingInfo;
import net.chemclipse.msd.converter.supplier.cdf.internal.converter.SpecificationValidator;
import net.chemclipse.msd.converter.supplier.cdf.internal.support.IConstants;
import net.chemclipse.msd.converter.supplier.cdf.io.ChromatogramReader;
import net.chemclipse.msd.model.core.IChromatogramMSD;
import net.chemclipse.logging.core.Logger;
import net.chemclipse.processing.core.IProcessingInfo;

public class CDFChromatogramImportConverter extends AbstractChromatogramMSDImportConverter {

	private static final Logger logger = Logger.getLogger(CDFChromatogramImportConverter.class);
	private static final String DESCRIPTION = "NetCDF Import Converter";

	@Override
	public IChromatogramMSDImportConverterProcessingInfo convert(File file, IProgressMonitor monitor) {

		IChromatogramMSDImportConverterProcessingInfo processingInfo = new ChromatogramMSDImportConverterProcessingInfo();
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
			IChromatogramMSDReader reader = new ChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CDF_CHROMATOGRAM);
			try {
				IChromatogramMSD chromatogram = reader.read(file, monitor);
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
			IChromatogramMSDReader reader = new ChromatogramReader();
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
