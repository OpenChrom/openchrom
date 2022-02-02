/*******************************************************************************
 * Copyright (c) 2014, 2022 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.animl.converter;

import java.io.File;

import org.eclipse.chemclipse.converter.chromatogram.AbstractChromatogramExportConverter;
import org.eclipse.chemclipse.converter.chromatogram.IChromatogramExportConverter;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.core.IChromatogram;
import org.eclipse.chemclipse.model.core.IPeak;
import org.eclipse.chemclipse.msd.converter.io.IChromatogramMSDWriter;
import org.eclipse.chemclipse.msd.model.core.IChromatogramMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.animl.io.ChromatogramWriter;
import net.openchrom.xxd.converter.supplier.animl.internal.converter.IConstants;

public class ChromatogramExportConverter extends AbstractChromatogramExportConverter implements IChromatogramExportConverter {

	private static final Logger logger = Logger.getLogger(ChromatogramExportConverter.class);
	private static final String DESCRIPTION = "AnIML Export Converter";

	@Override
	public IProcessingInfo<File> convert(File file, IChromatogram<? extends IPeak> chromatogram, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = super.validate(file);
		/*
		 * Don't process if errors have occurred.
		 */
		if(!processingInfo.hasErrorMessages() && chromatogram instanceof IChromatogramMSD) {
			IChromatogramMSD chromatogramMSD = (IChromatogramMSD)chromatogram;
			monitor.subTask(IConstants.EXPORT_CHROMATOGRAM);
			IChromatogramMSDWriter writer = new ChromatogramWriter();
			try {
				writer.writeChromatogram(file, chromatogramMSD, monitor);
			} catch(Exception e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has definitely gone wrong with the file: " + file.getAbsolutePath());
			}
			processingInfo.setProcessingResult(file);
		}
		return processingInfo;
	}
}
