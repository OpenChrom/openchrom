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
package net.openchrom.msd.converter.supplier.muf.converter;

import java.io.File;

import org.eclipse.chemclipse.msd.converter.massspectrum.AbstractMassSpectrumExportConverter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.processing.core.IProcessingInfo;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectrumExportConverter extends AbstractMassSpectrumExportConverter {

	@Override
	public IProcessingInfo<File> convert(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) {

		throw new UnsupportedOperationException();
	}

	@Override
	public IProcessingInfo<File> convert(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) {

		throw new UnsupportedOperationException();
	}
}
