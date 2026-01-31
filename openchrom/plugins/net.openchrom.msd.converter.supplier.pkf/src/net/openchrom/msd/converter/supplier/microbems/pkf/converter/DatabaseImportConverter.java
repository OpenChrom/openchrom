/*******************************************************************************
 * Copyright (c) 2015, 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbems.pkf.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.converter.l10n.ConverterMessages;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.database.AbstractDatabaseImportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osgi.util.NLS;

import net.openchrom.msd.converter.supplier.microbems.pkf.converter.io.PkfReader;

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
				if(massSpectra != null && !massSpectra.isEmpty()) {
					processingInfo.setProcessingResult(massSpectra);
				} else {
					processingInfo.addErrorMessage(DESCRIPTION, NLS.bind(ConverterMessages.noMassSpectraStored, file.getAbsolutePath()));
				}
			} catch(final FileNotFoundException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, NLS.bind(ConverterMessages.fileNotFound, file.getAbsolutePath()));
			} catch(final FileIsNotReadableException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, NLS.bind(ConverterMessages.fileNotReadable, file.getAbsolutePath()));
			} catch(final FileIsEmptyException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, NLS.bind(ConverterMessages.emptyFile, file.getAbsolutePath()));
			} catch(final IOException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, NLS.bind(ConverterMessages.failedToWriteFile, file.getAbsolutePath()));
			}
		}
		return processingInfo;
	}
}
