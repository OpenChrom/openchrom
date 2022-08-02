/*******************************************************************************
 * Copyright (c) 2008, 2022 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Christoph Läubrich - add generics
 *******************************************************************************/
package net.openchrom.wsd.converter.supplier.animl.converter;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramImportConverter;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogramOverview;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.wsd.converter.io.IChromatogramWSDReader;
import org.eclipse.chemclipse.wsd.model.core.IChromatogramWSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.wsd.converter.supplier.animl.io.ChromatogramReader;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.IConstants;

public class ChromatogramImportConverter extends AbstractChromatogramImportConverter<IChromatogramWSD> {

	private static final Logger logger = Logger.getLogger(ChromatogramImportConverter.class);
	private static final String DESCRIPTION = "AniML Chromatogram Import Converter";

	@Override
	public IProcessingInfo<IChromatogramWSD> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramWSD> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			/*
			 * Read the chromatogram.
			 */
			IChromatogramWSDReader reader = new ChromatogramReader();
			monitor.subTask(IConstants.IMPORT_CHROMATOGRAM);
			try {
				IChromatogramWSD chromatogram = reader.read(file, monitor);
				processingInfo.setProcessingResult(chromatogram);
			} catch(IOException e) {
				processingInfo.addErrorMessage(DESCRIPTION, "Can't read file: " + file.getAbsolutePath());
				logger.warn(e);
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<IChromatogramOverview> convertOverview(File file, IProgressMonitor monitor) {

		IProcessingInfo<IChromatogramOverview> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			IChromatogramWSDReader reader = new ChromatogramReader();
			try {
				IChromatogramOverview chromatogramOverview = reader.readOverview(file, monitor);
				processingInfo.setProcessingResult(chromatogramOverview);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
