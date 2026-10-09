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
package net.openchrom.msd.converter.supplier.cdf.io;

import java.io.File;
import java.io.IOException;

import org.eclipse.chemclipse.converter.l10n.ConverterMessages;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.cdf.exceptions.NoCDFVariableDataFound;
import net.openchrom.msd.converter.supplier.cdf.exceptions.NoSuchScanStored;
import net.openchrom.msd.converter.supplier.cdf.io.support.CDFLibraryArrayReader;
import net.openchrom.msd.converter.supplier.cdf.model.VendorLibraryMassSpectrum;

import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFiles;

/**
 * Reads an AIA/ANDI library (*.CDF) as a list of library mass spectra.
 */
public class MassSpectraReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	public static final String CONVERTER_ID = "net.openchrom.msd.converter.supplier.cdf.database";

	private static final Logger logger = Logger.getLogger(MassSpectraReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws IOException {

		IMassSpectra massSpectra = new MassSpectra();
		massSpectra.setConverterId(CONVERTER_ID);
		massSpectra.setName(file.getName());
		try (NetcdfFile library = NetcdfFiles.open(file.getAbsolutePath())) {
			CDFLibraryArrayReader in = new CDFLibraryArrayReader(library);
			monitor.beginTask(ConverterMessages.importScan, in.getNumberOfEntries());
			for(int i = 1; i <= in.getNumberOfEntries(); i++) {
				try {
					VendorLibraryMassSpectrum massSpectrum = in.getMassSpectrum(i);
					if(massSpectrum.getNumberOfIons() > 0) {
						massSpectra.addMassSpectrum(massSpectrum);
					}
				} catch(NoSuchScanStored e) {
					logger.warn(e);
				}
				monitor.worked(1);
			}
		} catch(NoCDFVariableDataFound e) {
			logger.warn(e);
			return null;
		}
		return massSpectra;
	}
}
