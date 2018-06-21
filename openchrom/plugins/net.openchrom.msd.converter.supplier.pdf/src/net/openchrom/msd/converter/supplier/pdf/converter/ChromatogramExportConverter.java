/*******************************************************************************
 * Copyright (c) 2011, 2018 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pdf.converter;

import java.io.File;

import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.chromatogram.AbstractChromatogramMSDExportConverter;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.pdf.internal.converter.SpecificationValidator;
import net.openchrom.msd.converter.supplier.pdf.io.ChromatogramWriter;

public class ChromatogramExportConverter extends AbstractChromatogramMSDExportConverter implements IChromatogramExportConverter {

	private static final Logger logger = Logger.getLogger(ChromatogramExportConverter.class);
	private static final String DESCRIPTION = "PDF Export Converter";

	@Override
	public IProcessingInfo convert(File file, IChromatogramMSD chromatogram, IProgressMonitor monitor) {

		file = SpecificationValidator.validateSpecification(file);
		IProcessingInfo processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			ChromatogramWriter writer = new ChromatogramWriter();
			try {
				writer.writeChromatogram(file, chromatogram, monitor);
				processingInfo.setProcessingResult(file);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
