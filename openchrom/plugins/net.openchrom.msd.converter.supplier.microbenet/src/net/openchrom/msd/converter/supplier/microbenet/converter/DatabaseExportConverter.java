/*******************************************************************************
 * Copyright (c) 2021, 2023 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbenet.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.database.AbstractDatabaseExportConverter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.microbenet.io.MicrobeNetWriter;

public class DatabaseExportConverter extends AbstractDatabaseExportConverter {

	private static final Logger logger = Logger.getLogger(DatabaseExportConverter.class);
	private static final String DESCRIPTION = "MicrobeNet MALDI XML Export Converter";

	@Override
	public IProcessingInfo<File> convert(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) {

		IProcessingInfo<File> processingInfo = validate(file, massSpectra);
		if(!processingInfo.hasErrorMessages()) {
			try {
				/*
				 * Convert the mass spectra.
				 */
				IMassSpectraWriter massSpectraWriter = new MicrobeNetWriter();
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
				processingInfo.addErrorMessage(DESCRIPTION, "Failed to write file: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}

	@Override
	public IProcessingInfo<File> convert(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) {

		IMassSpectra massSpectra = new MassSpectra();
		massSpectra.addMassSpectrum(massSpectrum);
		return convert(file, massSpectra, append, monitor);
	}

	private IProcessingInfo<File> validate(File file, IMassSpectra massSpectra) {

		IProcessingInfo<File> processingInfo = new ProcessingInfo<>();
		processingInfo.addMessages(super.validate(file));
		processingInfo.addMessages(super.validate(massSpectra));
		return processingInfo;
	}
}
