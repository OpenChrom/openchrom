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
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.chemclipse.converter.exceptions.FileIsNotWriteableException;
import org.eclipse.chemclipse.msd.converter.io.AbstractMassSpectraWriter;
import org.eclipse.chemclipse.msd.converter.io.IMassSpectraWriter;
import org.eclipse.chemclipse.msd.model.core.IMassSpectra;
import org.eclipse.chemclipse.msd.model.core.IScanMSD;
import org.eclipse.core.runtime.IProgressMonitor;

import net.openchrom.msd.converter.supplier.mgf.converter.model.TransformerIScanMSDMGFElement;
import net.sf.bioutils.proteomics.peak.Peak;
import net.sf.jmgf.MGFElement;
import net.sf.jmgf.MGFFile;
import net.sf.kerner.utils.io.UtilIO;

public class MGFWriter extends AbstractMassSpectraWriter implements IMassSpectraWriter {

	// public final static String PEAK_LINE_FORMAT_MZ = "%10.4f";
	// public final static String PEAK_LINE_FORMAT_MZ_INT = "%10.4f %10.0f";
	// public final static String PEAK_LINE_FORMAT_MZ_INT_CHARGE = "%10.4f
	// %10.0f %1d";
	private final static TransformerIScanMSDMGFElement TRANSFORMER_ISCANMSD_MGFELEMENT = new TransformerIScanMSDMGFElement();

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

		List<MGFElement> mgfElements = TRANSFORMER_ISCANMSD_MGFELEMENT.transform(massSpectrum);
		for(MGFElement element : mgfElements) {
			fileWriter.write(MGFFile.Format.FIRST_LINE_MS2);
			fileWriter.write(UtilIO.NEW_LINE_STRING);
			for(Entry<String, String> entry : element.getTags().entrySet()) {
				fileWriter.write(entry.getKey());
				fileWriter.write(MGFFile.Format.KEY_VALUE_SEPARATOR);
				fileWriter.write(entry.getValue());
				fileWriter.write(UtilIO.NEW_LINE_STRING);
			}
			for(Peak ion : element.getPeaks()) {
				fileWriter.write(Double.toString(ion.getMz()));
				fileWriter.write(MGFFile.Format.PEAK_PROPERTIES_SEPARATOR);
				fileWriter.write(Double.toString(ion.getIntensity()));
				fileWriter.write(UtilIO.NEW_LINE_STRING);
			}
			fileWriter.write(MGFFile.Format.LAST_LINE_MS2);
			fileWriter.write(UtilIO.NEW_LINE_STRING);
		}
	}
}
