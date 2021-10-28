/*******************************************************************************
 * Copyright (c) 2008, 2021 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Matthias Mailänder - adapted for MALDI
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.animl.converter;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.massspectrum.AbstractMassSpectrumExportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectrumExportConverter extends AbstractMassSpectrumExportConverter {

	private static final String DESCRIPTION = "AnIML Mass Spectra Export Converter";

	@Override
	public IProcessingInfo<IMassSpectra> convert(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) {

		return getProcessingInfo();
	}

	@Override
	public IProcessingInfo<IMassSpectra> convert(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) {

		return getProcessingInfo();
	}

	private IProcessingInfo<IMassSpectra> getProcessingInfo() {

		IProcessingInfo<IMassSpectra> processingInfo = new ProcessingInfo<>();
		processingInfo.addErrorMessage(DESCRIPTION, "It's not possible to export mass spectrum data as mzXML yet.");
		return processingInfo;
	}
}
