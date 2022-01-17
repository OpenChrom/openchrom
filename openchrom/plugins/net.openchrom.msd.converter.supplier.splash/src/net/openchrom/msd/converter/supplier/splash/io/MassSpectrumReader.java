/*******************************************************************************
 * Copyright (c) 2021, 2022 Lablicate GmbH.
 * 
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.splash.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This is just a dummy. A mass spectrum hash can't be easily converted back into a spectrum.
 */
public class MassSpectrumReader implements IMassSpectraReader {

	@Override
	public String getBaseFileDirectory(File arg0) {

		return null;
	}

	@Override
	public String getBaseFileName(File arg0) {

		return null;
	}

	@Override
	public void extractNameAndReferenceIdentifier(IRegularLibraryMassSpectrum arg0, String arg1, String arg2, String arg3) {

	}

	@Override
	public void extractRetentionIndices(IRegularLibraryMassSpectrum arg0, String arg1, String arg2) {

	}

	@Override
	public IMassSpectra read(File arg0, IProgressMonitor arg1) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		return null;
	}
}
