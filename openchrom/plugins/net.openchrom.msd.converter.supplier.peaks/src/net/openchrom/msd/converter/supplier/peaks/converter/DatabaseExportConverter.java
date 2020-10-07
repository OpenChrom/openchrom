/*******************************************************************************
 * Copyright (c) 2015, 2020 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.peaks.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.database.AbstractDatabaseExportConverter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.peaks.converter.io.PeaksWriter;

public class DatabaseExportConverter extends AbstractDatabaseExportConverter {

	private static final String DESCRIPTION = "Spectra Bank / SPECLUST .peaks Library Export Converter";
	private static final Logger logger = Logger.getLogger(DatabaseExportConverter.class);

	@Override
	public IProcessingInfo<File> convert(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		try {
			IMassSpectraWriter massSpectraWriter = new PeaksWriter();
			massSpectraWriter.write(file, massSpectrum, append, monitor);
			processingInfo.setProcessingResult(file);
		} catch(FileNotFoundException e) {
			logger.warn(e);
			processingInfo.addErrorMessage(DESCRIPTION, "The file couldn't be found: " + file.getAbsolutePath());
		} catch(FileIsNotWriteableException e) {
			logger.warn(e);
			processingInfo.addErrorMessage(DESCRIPTION, "The file is not writeable: " + file.getAbsolutePath());
		} catch(IOException e) {
			logger.warn(e);
			processingInfo.addErrorMessage(DESCRIPTION, "Failed or interrupted file access: " + file.getAbsolutePath());
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<File> convert(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		try {
			IMassSpectraWriter massSpectraWriter = new PeaksWriter();
			massSpectraWriter.write(file, massSpectra, append, monitor);
			processingInfo.setProcessingResult(file);
		} catch(FileNotFoundException e) {
			logger.warn(e);
			processingInfo.addErrorMessage(DESCRIPTION, "The file couldn't be found: " + file.getAbsolutePath());
		} catch(FileIsNotWriteableException e) {
			logger.warn(e);
			processingInfo.addErrorMessage(DESCRIPTION, "The file is not writeable: " + file.getAbsolutePath());
		} catch(IOException e) {
			logger.warn(e);
			processingInfo.addErrorMessage(DESCRIPTION, "Failed or interrupted file access: " + file.getAbsolutePath());
		}
		return processingInfo;
	}
}