/*******************************************************************************
 * Copyright (c) 2016, 2023 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 * Dr. Alexander Kerner - implementation
 *******************************************************************************/
package net.openchrom.msd.converter.supplier.mgf.converter.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IIon;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.core.runtime.IProgressMonitor;

public class MassSpectraWriter extends AbstractMassSpectraWriter implements IMassSpectraWriter {

	@Override
	public void write(File file, IMassSpectra massSpectra, boolean append, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		write(file, massSpectra.getList(), append, monitor);
	}

	public void write(File file, Collection<? extends IScanMSD> scans, boolean append, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		for(IScanMSD scan : scans) {
			write(file, scan, append, monitor);
		}
	}

	@Override
	public void write(File file, IScanMSD massSpectrum, boolean append, IProgressMonitor monitor) throws FileIsNotWriteableException, IOException {

		try (FileWriter fileWriter = new FileWriter(file, append)) {
			writeMassSpectrum(fileWriter, massSpectrum, monitor);
		}
	}

	@Override
	public void writeMassSpectrum(FileWriter fileWriter, IScanMSD massSpectrum, IProgressMonitor monitor) throws IOException {

		fileWriter.write("BEGIN IONS");
		fileWriter.write(System.getProperty("line.separator"));
		fileWriter.write("TITLE=" + massSpectrum.getIdentifier());
		fileWriter.write(System.getProperty("line.separator"));
		fileWriter.write("RTINSECONDS=" + Integer.toString(massSpectrum.getRetentionTime()));
		fileWriter.write(System.getProperty("line.separator"));
		fileWriter.write("SCANS=" + Integer.toString(massSpectrum.getScanNumber()));
		fileWriter.write(System.getProperty("line.separator"));
		for(IIon ion : massSpectrum.getIons()) {
			fileWriter.write(Double.toString(ion.getIon()));
			fileWriter.write(" ");
			fileWriter.write(Float.toString(ion.getAbundance()));
			fileWriter.write(System.getProperty("line.separator"));
		}
		fileWriter.write("END IONS");
		fileWriter.write(System.getProperty("line.separator"));
	}
}
