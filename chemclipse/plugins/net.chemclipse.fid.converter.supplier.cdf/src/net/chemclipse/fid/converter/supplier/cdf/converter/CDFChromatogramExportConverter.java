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
package net.chemclipse.fid.converter.supplier.cdf.converter;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import net.chemclipse.converter.processing.chromatogram.ChromatogramExportConverterProcessingInfo;
import net.chemclipse.converter.processing.chromatogram.IChromatogramExportConverterProcessingInfo;
import net.chemclipse.fid.converter.chromatogram.AbstractChromatogramFIDExportConverter;
import net.chemclipse.fid.converter.io.IChromatogramFIDWriter;
import net.chemclipse.fid.converter.supplier.cdf.internal.converter.IConstants;
import net.chemclipse.fid.converter.supplier.cdf.internal.converter.SpecificationValidator;
import net.chemclipse.fid.converter.supplier.cdf.io.ChromatogramWriter;
import net.chemclipse.fid.model.core.IChromatogramFID;
import net.chemclipse.logging.core.Logger;
import net.chemclipse.processing.core.IProcessingInfo;

public class CDFChromatogramExportConverter extends AbstractChromatogramFIDExportConverter {

	private static final Logger logger = Logger.getLogger(CDFChromatogramExportConverter.class);
	private static final String DESCRIPTION = "NetCDF Export Converter";

	@Override
	public IChromatogramExportConverterProcessingInfo convert(File file, IChromatogramFID chromatogram, IProgressMonitor monitor) {

		IChromatogramExportConverterProcessingInfo processingInfo = new ChromatogramExportConverterProcessingInfo();
		/*
		 * Validate the file.
		 */
		file = SpecificationValidator.validateCDFSpecification(file);
		IProcessingInfo processingInfoValidate = super.validate(file);
		/*
		 * Don't process if errors have occurred.
		 */
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			monitor.subTask(IConstants.EXPORT_CDF_CHROMATOGRAM);
			IChromatogramFIDWriter writer = new ChromatogramWriter();
			try {
				writer.writeChromatogram(file, chromatogram, monitor);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
			processingInfo.setFile(file);
		}
		return processingInfo;
	}
}
