/*******************************************************************************
 * Copyright (c) 2016, 2018 Walter Whitlock, Philip Wenig.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Walter Whitlock - initial API and implementation
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.cms.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.database.AbstractDatabaseExportConverter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.processing.database.DatabaseExportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.converter.processing.database.IDatabaseExportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.cms.io.MassSpectrumWriter;
import net.openchrom.msd.converter.supplier.cms.io.SpecificationValidator;

public class DatabaseExportConverter extends AbstractDatabaseExportConverter {

	private static final Logger logger = Logger.getLogger(DatabaseExportConverter.class);
	private static final String DESCRIPTION = "CMS MassSpectrum Export";

	@Override
	public IDatabaseExportConverterProcessingInfo convert(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) {

		IDatabaseExportConverterProcessingInfo processingInfo = new DatabaseExportConverterProcessingInfo();
		/*
		 * Checks that file and mass spectrum are not null.
		 */
		file = SpecificationValidator.validateSpecification(file);
		IProcessingInfo processingInfoValidate = validate(file, massSpectrum);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			try {
				/*
				 * Convert the mass spectrum.
				 */
				IMassSpectraWriter massSpectraWriter = new MassSpectrumWriter();
				massSpectraWriter.write(file, massSpectrum, append, monitor);
				processingInfo.setFile(file);
			} catch(FileNotFoundException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file couldn't be found: " + file.getAbsolutePath());
			} catch(FileIsNotWriteableException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file is not writeable: " + file.getAbsolutePath());
			} catch(IOException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has gone completely wrong: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}

	@Override
	public IDatabaseExportConverterProcessingInfo convert(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) {

		IDatabaseExportConverterProcessingInfo processingInfo = new DatabaseExportConverterProcessingInfo();
		/*
		 * Checks that file and mass spectra are not null.
		 */
		file = SpecificationValidator.validateSpecification(file);
		IProcessingInfo processingInfoValidate = validate(file, massSpectra);
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			try {
				/*
				 * Convert the mass spectra.
				 */
				IMassSpectraWriter massSpectraWriter = new MassSpectrumWriter();
				massSpectraWriter.write(file, massSpectra, append, monitor);
				processingInfo.setFile(file);
			} catch(FileNotFoundException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file couldn't be found: " + file.getAbsolutePath());
			} catch(FileIsNotWriteableException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "The file is not writeable: " + file.getAbsolutePath());
			} catch(IOException e) {
				logger.warn(e);
				processingInfo.addErrorMessage(DESCRIPTION, "Something has gone completely wrong: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}

	private IProcessingInfo validate(File file, IScanMSD massSpectrum) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		processingInfo.addMessages(super.validate(file));
		processingInfo.addMessages(super.validate(massSpectrum));
		return processingInfo;
	}

	private IProcessingInfo validate(File file, IMassSpectra massSpectra) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		processingInfo.addMessages(super.validate(file));
		processingInfo.addMessages(super.validate(massSpectra));
		return processingInfo;
	}
}
