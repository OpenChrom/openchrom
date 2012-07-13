/*******************************************************************************
 * Copyright (c) 2008, 2012 Philip (eselmeister) Wenig.
 * 
 * This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307, USA
 * 
 * 
 * Contributors: Philip (eselmeister) Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.chromatogram.msd.converter.supplier.cdf.converter;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.chromatogram.msd.converter.chromatogram.AbstractChromatogramImportConverter;
import net.openchrom.chromatogram.msd.converter.io.IChromatogramReader;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.ChromatogramImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.ChromatogramOverviewImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramOverviewImportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.internal.converter.SpecificationValidator;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.internal.support.IConstants;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.CDFChromatogramReader;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.chromatogram.msd.model.core.IChromatogramOverview;
import net.openchrom.logging.core.Logger;
import net.openchrom.processing.core.IProcessingInfo;

public class CDFChromatogramImportConverter extends AbstractChromatogramImportConverter {

	private static final Logger logger = Logger.getLogger(CDFChromatogramImportConverter.class);
	private static final String DESCRIPTION = "NetCDF Import Converter";

	@Override
	public IChromatogramImportConverterProcessingInfo convert(File file, IProgressMonitor monitor) {

		IChromatogramImportConverterProcessingInfo processingInfo = new ChromatogramImportConverterProcessingInfo();
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
			IChromatogramReader reader = new CDFChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CDF_CHROMATOGRAM);
			try {
				IChromatogram chromatogram = reader.read(file, monitor);
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
			IChromatogramReader reader = new CDFChromatogramReader();
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
