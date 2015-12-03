/*******************************************************************************
 * Copyright (c) 2015 Lablicate UG (haftungsbeschränkt).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.openchrom.msd.converter.supplier.mgf.converter.io.MGFReader;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.massspectrum.AbstractMassSpectrumImportConverter;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.MassSpectrumImportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectrumImportConverter extends AbstractMassSpectrumImportConverter {

	private static final Logger logger = Logger.getLogger(MassSpectrumImportConverter.class);
	private static final String DESCRIPTION = "MGF MassSpectrum Import";

	@Override
	public IMassSpectrumImportConverterProcessingInfo convert(File file, IProgressMonitor monitor) {

		IMassSpectrumImportConverterProcessingInfo processingInfo = new MassSpectrumImportConverterProcessingInfo();
		IProcessingInfo processingInfoValidate = super.validate(file);
		/*
		 * Import
		 */
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			try {
				MGFReader mgfReader = new MGFReader();
				IMassSpectra massSpectra = mgfReader.read(file, monitor);
				processingInfo.setMassSpectra(massSpectra);
			} catch(FileNotFoundException e) {
				logger.warn(e);
				// for testing
				e.printStackTrace();
				processingInfo.addErrorMessage(DESCRIPTION, "The file couldn't be found: " + file.getAbsolutePath());
			} catch(FileIsNotReadableException e) {
				logger.warn(e);
				// for testing
				e.printStackTrace();
				processingInfo.addErrorMessage(DESCRIPTION, "The file is not readable: " + file.getAbsolutePath());
			} catch(FileIsEmptyException e) {
				logger.warn(e);
				// for testing
				e.printStackTrace();
				processingInfo.addErrorMessage(DESCRIPTION, "The file is empty: " + file.getAbsolutePath());
			} catch(IOException e) {
				logger.warn(e);
				// for testing
				e.printStackTrace();
				processingInfo.addErrorMessage(DESCRIPTION, "Something has gone completely wrong: " + file.getAbsolutePath());
			}
		}
		return processingInfo;
	}
}
