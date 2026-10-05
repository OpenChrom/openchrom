/*******************************************************************************
 * Copyright (c) 2026 Lablicate GmbH.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.microbenet.converter;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.massspectrum.AbstractMassSpectrumImportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.chemclipse.processing.core.ProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectrumImportConverter extends AbstractMassSpectrumImportConverter {

	@Override
	public IProcessingInfo<IMassSpectra> convert(File file, IProgressMonitor monitor) {

		IProcessingInfo<IMassSpectra> processingInfo = new ProcessingInfo<>();
		processingInfo.addWarnMessage("MicrobeNet MALDI XML Import Converter", "Importing data is not supported yet.");
		return processingInfo;
	}
}