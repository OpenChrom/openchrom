/*******************************************************************************
 * Copyright (c) 2020 Matthias Mailänder.
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.text.StrBuilder;
import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IRegularLibraryMassSpectrum;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.chemclipse.msd.model.implementation.MassSpectra;
import org.eclipse.core.runtime.IProgressMonitor;

public class PeaksWriter extends AbstractMassSpectraWriter implements IMassSpectraWriter {

	@Override
	public void write(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		IMassSpectra massSpectra = new MassSpectra();
		massSpectra.addMassSpectrum(massSpectrum);
		writeZip(file, massSpectra);
	}

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) throws FileNotFoundException, FileIsNotWriteableException, IOException {

		writeZip(file, massSpectra);
	}

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD massSpectrum, IProgressMonitor monitor) {

		throw new NotImplementedException();
	}

	private byte[] writePeaks(IScanMSD massSpectrum) throws IOException {

		StrBuilder peaks = new StrBuilder();
		List<IIon> ions = massSpectrum.getIons();
		for(IIon ion : ions) {
			peaks.appendln(ion.getIon());
		}
		return peaks.toString().getBytes();
	}

	private void writeZip(File file, IMassSpectra massSpectra) throws IOException {

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
		for(IScanMSD massSpectrum : massSpectra.getList()) {
			IRegularLibraryMassSpectrum regularLibraryMassSpectrum;
			if(massSpectrum instanceof IRegularLibraryMassSpectrum) {
				regularLibraryMassSpectrum = (IRegularLibraryMassSpectrum)massSpectrum;
			} else {
				fileOutputStream.close();
				zipOutputStream.close();
				throw new InvalidClassException("Not a compatible mass spectrum.");
			}
			String name = regularLibraryMassSpectrum.getLibraryInformation().getName();
			ZipEntry zipEntry = new ZipEntry(name + ".peaks");
			zipOutputStream.putNextEntry(zipEntry);
			byte[] peaks = writePeaks(massSpectrum);
			zipOutputStream.write(peaks, 0, peaks.length);
			zipOutputStream.closeEntry();
		}
		zipOutputStream.close();
		fileOutputStream.close();
	}
}
