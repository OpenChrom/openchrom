/*******************************************************************************
 * Copyright (c) 2020, 2022 Matthias Mailänder.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Matthias Mailänder - initial API and implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.peaks.converter.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.chemclipse.converter.exceptions.FileIsEmptyException;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotReadableException;
import org.eclipse.chemclipse.logging.core.Logger;
import org.eclipse.chemclipse.model.exceptions.AbundanceLimitExceededException;
import org.eclipse.chemclipse.model.identifier.ILibraryInformation;
import org.eclipse.chemclipse.model.identifier.PeakLibraryInformation;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraReader;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraReader;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.exceptions.IonLimitExceededException;
import org.eclipse.chemclipse.msd.model.implementation.Ion;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.peaks.converter.model.PeaksMassSpectrum;

public class PeaksReader extends AbstractMassSpectraReader implements IMassSpectraReader {

	private static final Logger logger = Logger.getLogger(PeaksReader.class);

	@Override
	public IMassSpectra read(File file, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotReadableException, FileIsEmptyException, IOException {

		IMassSpectra massSpectra = new MassSpectra();
		massSpectra.setName(StringUtils.substringBefore(file.getName(), ".zip"));
		ZipFile zipFile = new ZipFile(file);
		if(zipFile.size() == 0) {
			zipFile.close();
			throw new FileIsNotReadableException();
		}
		Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
		while(zipEntries.hasMoreElements()) {
			ZipEntry zipEntry = zipEntries.nextElement();
			String name = zipEntry.getName();
			if(!name.endsWith(".peaks"))
				continue;
			PeaksMassSpectrum peakList = new PeaksMassSpectrum();
			ILibraryInformation libraryInformation = new PeakLibraryInformation();
			libraryInformation.setName(StringUtils.substringBefore(name, ".peaks"));
			peakList.setLibraryInformation(libraryInformation);
			InputStream textFile = zipFile.getInputStream(zipEntry);
			BufferedReader reader = new BufferedReader(new InputStreamReader(textFile));
			String peak;
			while((peak = reader.readLine()) != null) {
				try {
					peakList.addIon(new Ion(Double.parseDouble(peak), 1));
				} catch(NumberFormatException | AbundanceLimitExceededException
						| IonLimitExceededException e) {
					logger.warn(e);
				}
			}
			massSpectra.addMassSpectrum(peakList);
		}
		zipFile.close();
		return massSpectra;
	}
}
