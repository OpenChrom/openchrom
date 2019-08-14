/*******************************************************************************
 * Copyright (c) 2014, 2019 Lablicate GmbH.
 *
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Alexander Kerner - Generics
 *******************************************************************************/
package net.openchrom.csd.converter.supplier.cdf.converter;

import java.io.File;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramImportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramImportConverter;
import org.eclipse.chemclipse.csd.converter.io.IChromatogramCSDReader;
import org.eclipse.chemclipse.csd.model.core.IChromatogramCSD;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.csd.converter.supplier.cdf.internal.converter.IConstants;
import net.openchrom.csd.converter.supplier.cdf.internal.converter.SpecificationValidator;
import net.openchrom.csd.converter.supplier.cdf.io.ChromatogramReader;

public class ChromatogramImportConverter extends AbstractChromatogramImportConverter<IChromatogramCSD> implements IChromatogramImportConverter<IChromatogramCSD> {

	private static final Logger logger = Logger.getLogger(ChromatogramImportConverter.class);
	private static final String DESCRIPTION = "NetCDF Import Converter";

	@Override
	public IProcessingInfo<IChromatogramCSD> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramCSD> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			file = SpecificationValidator.validateSpecification(file);
			IChromatogramCSDReader reader = new ChromatogramReader();
			try {
				IChromatogramCSD chromatogram = reader.read(file, monitor);
				processingInfo.setProcessingResult(chromatogram);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<IChromatogramOverview> convertOverview(File file, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramOverview> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			file = SpecificationValidator.validateSpecification(file);
			IChromatogramCSDReader reader = new ChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CDF_CHROMATOGRAM_OVERVIEW);
			try {
				IChromatogramOverview chromatogramOverview = reader.readOverview(file, monitor);
				processingInfo.setProcessingResult(chromatogramOverview);
			} catch(Exception e) {
				logger.error(e.getLocalizedMessage(), e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
