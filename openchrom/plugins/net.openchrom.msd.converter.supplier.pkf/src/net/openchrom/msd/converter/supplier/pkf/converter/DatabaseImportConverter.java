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
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.pkf.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.database.AbstractDatabaseImportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.pkf.converter.io.PkfReader;

public class DatabaseImportConverter extends AbstractDatabaseImportConverter {

	private static final String DESCRIPTION = "MicrobeMS Peak List Files .pkf Import";
	private static final Logger logger = Logger.getLogger(DatabaseImportConverter.class);

	@Override
	public IProcessingInfo<IMassSpectra> convert(final File file, final IProgressMonitor monitor) {

		final IProcessingInfo<IMassSpectra> processingInfo = super.validate(file);
		if(!processingInfo.hasErrorMessages()) {
			try {
				final PkfReader peaksReader = new PkfReader();
				final IMassSpectra massSpectra = peaksReader.read(file, monitor);
				if(massSpectra != null && massSpectra.size() > 0) {
					processingInfo.setProcessingResult(massSpectra);
				} else {
					processingInfo.addErrorMessage(DESCRIPTION, "No mass spectra are stored." + file.getAbsolutePath());
				}
			} catch(final FileNotFoundException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file couldn't be found: " + file.getAbsolutePath());
			} catch(final FileIsNotReadableException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file is not readable: " + file.getAbsolutePath());
			} catch(final FileIsEmptyException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file is empty: " + file.getAbsolutePath());
			} catch(final IOException e) {
				logger.warn(e.getLocalizedMessage(), e);
				processingInfo.addErrorMessage(DESCRIPTION, "Failed or interrupted file access: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
