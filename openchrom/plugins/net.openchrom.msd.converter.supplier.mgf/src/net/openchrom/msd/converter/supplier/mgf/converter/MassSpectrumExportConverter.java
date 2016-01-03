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

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.massspectrum.AbstractMassSpectrumExportConverter;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.IMassSpectrumExportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.converter.processing.massspectrum.MassSpectrumExportConverterProcessingInfo;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mgf.converter.internal.io.SpecificationValidator;
import net.openchrom.msd.converter.supplier.mgf.converter.io.MGFWriter;

public class MassSpectrumExportConverter extends AbstractMassSpectrumExportConverter {

	private static final String DESCRIPTION = "MGF MassSpectrum Export";
	private static final Logger logger = Logger.getLogger(MassSpectrumExportConverter.class);

	@Override
	public IMassSpectrumExportConverterProcessingInfo convert(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) {

		IMassSpectrumExportConverterProcessingInfo processingInfo = new MassSpectrumExportConverterProcessingInfo();
		file = SpecificationValidator.validateSpecification(file);
		IProcessingInfo processingInfoValidate = validate(file, massSpectra);
		/*
		 * Export
		 */
		if(processingInfoValidate.hasErrorMessages()) {
			processingInfo.addMessages(processingInfoValidate);
		} else {
			try {
				/*
				 * Convert the mass spectra.
				 */
				IMassSpectraWriter massSpectraWriter = new MGFWriter();
				massSpectraWriter.write(file, massSpectra, append);
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
	public IMassSpectrumExportConverterProcessingInfo convert(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) {

		IMassSpectra massSpectra = new MassSpectra();
		massSpectra.addMassSpectrum(massSpectrum);
		return convert(file, massSpectra, append, monitor);
	}

	private IProcessingInfo validate(File file, IMassSpectra massSpectra) {

		IProcessingInfo processingInfo = new ProcessingInfo();
		processingInfo.addMessages(super.validate(file));
		processingInfo.addMessages(super.validate(massSpectra));
		return processingInfo;
	}
}
