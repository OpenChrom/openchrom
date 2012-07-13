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

import net.openchrom.chromatogram.msd.converter.chromatogram.AbstractChromatogramExportConverter;
import net.openchrom.chromatogram.msd.converter.io.IChromatogramWriter;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.ChromatogramExportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.processing.chromatogram.IChromatogramExportConverterProcessingInfo;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.internal.converter.SpecificationValidator;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.internal.support.IConstants;
import net.openchrom.chromatogram.msd.converter.supplier.cdf.io.CDFChromatogramWriter;
import net.openchrom.chromatogram.msd.model.core.IChromatogram;
import net.openchrom.logging.core.Logger;
import net.openchrom.processing.core.IProcessingInfo;

public class CDFChromatogramExportConverter extends AbstractChromatogramExportConverter {

	private static final Logger logger = Logger.getLogger(CDFChromatogramExportConverter.class);
	private static final String DESCRIPTION = "NetCDF Export Converter";

	@Override
	public IChromatogramExportConverterProcessingInfo convert(File file, IChromatogram chromatogram, IProgressMonitor monitor) {

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
			IChromatogramWriter writer = new CDFChromatogramWriter();
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
